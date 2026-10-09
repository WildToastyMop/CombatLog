package me.toastymop.combatlog.config;

import com.electronwill.nightconfig.core.CommentedConfig;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ConfigOption<T> {
	public final String path;
	public final String comment;
	public final T fallback;
	public final ConfigValue<T> type;
	private final Supplier<T> getter;
	private final Consumer<T> setter;

	public ConfigOption(String path, String comment, T fallback, ConfigValue<T> type, Supplier<T> getter, Consumer<T> setter) {
		this.path = path;
		this.comment = comment;
		this.fallback = fallback;
		this.type = type;
		this.getter = getter;
		this.setter = setter;
	}

	public T get() {
		return getter.get();
	}

	public void set(T value) {
		setter.accept(value);
	}

	public void reset() {
		set(fallback);
	}

	public boolean isList() {
		return type.isList();
	}

	public boolean contains(String input) {
		return type.contains(get(), input);
	}

	public List<String> suggestions() {
		return type.suggestions();
	}

	public String display() {
		return String.valueOf(get());
	}

	public boolean setFromString(String input) {
		T value = type.parse(input);
		if (value == null) return false;
		set(value);
		return true;
	}

	public boolean addFromString(String input) {
		T value = type.add(get(), input);
		if (value == null) return false;
		set(value);
		return true;
	}

	public boolean removeFromString(String input) {
		T value = type.remove(get(), input);
		if (value == null) return false;
		set(value);
		return true;
	}

	boolean missing(CommentedConfig config) {
		Object raw = config.get(path);
		return raw == null || !type.valid(raw);
	}

	void applyValue(Object raw) {
		T value = type.read(raw);
		set(value != null ? value : fallback);
	}

	void write(CommentedConfig config) {
		config.set(path, get());
		config.setComment(path, comment);
	}
}
