@file:Suppress("unused", "DuplicatedCode")

import dev.kikugie.stonecutter.build.StonecutterBuildExtension
import kotlinx.serialization.json.Json
import me.modmuss50.mpp.ReleaseType
import net.peanuuutz.tomlkt.Toml
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.internal.extensions.stdlib.toDefaultLowerCase
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.maven
import java.util.*

val Project.sc: StonecutterBuildExtension
	get() = extensions.getByType<StonecutterBuildExtension>()

fun Project.prop(name: String): String = (project.sc.properties.getAs<String>(name))

fun Project.propsList(vararg segments: String): List<String> = runCatching {
	sc.properties.raw(*segments).asList().map { it.toString() }
}.getOrElse { error("Missing or malformed '${segments.joinToString(".")}' in stonecutter.properties.toml") }

fun Project.minecraftCompatibleVersions(): List<String> =
	sc.properties.rawOrNull("deps", "minecraft-compatible")?.to<List<String>>().orEmpty()

fun Project.supportedMinecraftVersions(): List<String> =
	(listOf(prop("deps.minecraft")) + minecraftCompatibleVersions())
		.distinct()
		.sortedWith(Comparator(::compareMinecraftVersions))

fun Project.fabricMinecraftRange(): String = supportedMinecraftVersions().let { versions ->
	if (versions.size == 1) versions.first() else ">=${versions.first()} <=${versions.last()}"
}

fun Project.forgeMinecraftRange(): String = supportedMinecraftVersions().let { versions ->
	if (versions.size == 1) versions.first() else "[${versions.first()},${versions.last()}]"
}

private val MANIFEST_MINECRAFT_RANGE = Regex("\"minecraft\"\\s*:\\s*\"([^\"]+)\"")

fun minecraftRangeFromManifest(manifestPath: String, text: String): String? {
	if (manifestPath.endsWith(".json")) return MANIFEST_MINECRAFT_RANGE.find(text)?.groupValues?.get(1)

	var minecraft = false
	for (line in text.lines()) {
		val trimmed = line.trim()
		if (trimmed.startsWith("[[")) minecraft = false
		if (trimmed.startsWith("modId") && trimmed.contains("minecraft")) minecraft = true
		if (minecraft && trimmed.startsWith("versionRange")) return trimmed.substringAfter('=').trim().trim('"')
	}
	return null
}

fun minecraftRangeCovers(manifestRange: String, version: String): Boolean {
	val range = manifestRange.trim()
	if (range.startsWith("[") && range.endsWith("]")) {
		val bounds = range.removeSurrounding("[", "]").split(",", limit = 2).map { it.trim() }
		return compareMinecraftVersions(version, bounds.first()) >= 0 &&
			compareMinecraftVersions(version, bounds.last()) <= 0
	}
	if (!range.startsWith(">") && !range.startsWith("<")) {
		return compareMinecraftVersions(version, range) == 0
	}
	val parts = range.split(" ").filter { it.isNotBlank() }
	val minimum = parts.firstOrNull { it.startsWith(">=") }?.removePrefix(">=")
	val maximum = parts.firstOrNull { it.startsWith("<=") }?.removePrefix("<=")
	return (minimum == null || compareMinecraftVersions(version, minimum) >= 0) &&
		(maximum == null || compareMinecraftVersions(version, maximum) <= 0)
}

private fun compareMinecraftVersions(left: String, right: String): Int {
	val leftParts = left.split('.')
	val rightParts = right.split('.')
	for (index in 0 until maxOf(leftParts.size, rightParts.size)) {
		val leftPart = leftParts.getOrNull(index)?.toIntOrNull() ?: 0
		val rightPart = rightParts.getOrNull(index)?.toIntOrNull() ?: 0
		if (leftPart != rightPart) return leftPart - rightPart
	}
	return 0
}

fun Project.env(variable: String): String? {
	providers.environmentVariable(variable).orNull?.let { return it }
	return rootProject.file(".env").takeIf { it.exists() }?.let { f ->
		Properties().apply { f.inputStream().use(::load) }.getProperty(variable)
	}
}

fun Project.envTrue(variable: String): Boolean = env(variable)?.toDefaultLowerCase() == "true"

fun releaseTypeFromChannelTag(channelTag: String): ReleaseType =
	ReleaseType.of(channelTag.substringAfter('-').substringBefore('.').ifEmpty { "stable" })

fun RepositoryHandler.strictMaven(
	url: String, vararg groups: String, configure: MavenArtifactRepository.() -> Unit = {}
) = exclusiveContent {
	forRepository { maven(url) { configure() } }
	filter { groups.forEach(::includeGroup) }
}

val JSON = Json { prettyPrint = true; encodeDefaults = true; explicitNulls = false }
val TOML = Toml { }
