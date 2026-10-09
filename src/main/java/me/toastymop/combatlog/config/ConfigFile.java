package me.toastymop.combatlog.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.toml.TomlFormat;
import com.electronwill.nightconfig.toml.TomlParser;
import com.electronwill.nightconfig.toml.TomlWriter;
import me.toastymop.combatlog.CombatLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.LinkedHashMap;

public class ConfigFile {
	private static final Logger log = LogManager.getLogger(CombatLog.LOGGER);

	public static void load(File file) {
		CommentedConfig parsed = read(file);
		CommentedConfig result = empty();
		boolean changed = false;

		for (ConfigOption<?> option : ConfigRegistry.OPTIONS) {
			Object value = null;
			if (parsed != null && !option.missing(parsed)) {
				value = parsed.get(option.path);
			}
			if (value == null) {
				if (parsed != null && parsed.contains(option.path)) {
					log.warn("Resetting '{}' in {}, the value in the file is invalid", option.path, file.getName());
				}
				value = option.fallback;
				changed = true;
			}
			try {
				option.applyValue(value);
			} catch (Exception e) {
				log.error("Failed to apply '" + option.path + "', using the default", e);
				value = option.fallback;
				option.applyValue(value);
				changed = true;
			}
			result.set(option.path, value);
			result.setComment(option.path, option.comment);
		}

		if (changed) write(file, result);
	}

	public static void save(File file) {
		CommentedConfig config = empty();
		for (ConfigOption<?> option : ConfigRegistry.OPTIONS) {
			option.write(config);
		}
		write(file, config);
	}

	private static CommentedConfig empty() {
		return CommentedConfig.of(LinkedHashMap::new, TomlFormat.instance());
	}

	private static CommentedConfig read(File file) {
		if (!file.exists()) return null;
		try (FileInputStream stream = new FileInputStream(file)) {
			return new TomlParser().parse(stream);
		} catch (Exception e) {
			log.error("Failed to read " + file.getName() + ", a fresh one will be made", e);
			try {
				Files.move(file.toPath(), new File(file + ".bak").toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException ignored) {}
			return null;
		}
	}

	private static void write(File file, CommentedConfig config) {
		File folder = file.getParentFile();
		if (folder != null && !folder.exists()) {
			folder.mkdirs();
		}
		try (FileWriter writer = new FileWriter(file)) {
			new TomlWriter().write(config, writer);
		} catch (IOException e) {
			log.error("Failed to save config", e);
		}
	}
}
