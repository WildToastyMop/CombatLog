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

fun Project.prop(name: String): String = (project.sc.properties.get<String>(name))

fun Project.propsList(vararg segments: String): List<String> = runCatching {
	sc.properties.raw(*segments).asList().map { it.toString() }
}.getOrElse { error("Missing or malformed '${segments.joinToString(".")}' in stonecutter.properties.toml") }

fun Project.publishAdditionalVersions(): List<String> =
	sc.properties.rawOrNull("publish", "additionalVersions")?.to<List<String>>().orEmpty()

fun Project.supportedMinecraftVersions(): List<String> =
	(listOf(prop("deps.minecraft")) + publishAdditionalVersions())
		.distinct()
		.sortedWith(Comparator(::compareMinecraftVersions))

fun Project.fabricMinecraftRange(): String = supportedMinecraftVersions().let { versions ->
	if (versions.size == 1) versions.first() else ">=${versions.first()} <=${versions.last()}"
}

fun Project.forgeMinecraftRange(): String = supportedMinecraftVersions().let { versions ->
	if (versions.size == 1) versions.first() else "[${versions.first()},${versions.last()}]"
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
