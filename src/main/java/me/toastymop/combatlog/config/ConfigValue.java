package me.toastymop.combatlog.config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class ConfigValue<T> {
	public static final ConfigValue<Integer> INT = new ConfigValue<>() {
		@Override
		public boolean valid(Object raw) {
			return raw instanceof Number;
		}

		@Override
		public Integer read(Object raw) {
			return raw instanceof Number ? ((Number) raw).intValue() : null;
		}

		@Override
		public Integer parse(String input) {
			try {
				return Integer.parseInt(input.trim());
			} catch (NumberFormatException e) {
				return null;
			}
		}
	};

	public static final ConfigValue<Boolean> BOOL = new ConfigValue<>() {
		@Override
		public boolean valid(Object raw) {
			return raw instanceof Boolean;
		}

		@Override
		public Boolean read(Object raw) {
			return raw instanceof Boolean ? (Boolean) raw : null;
		}

		@Override
		public Boolean parse(String input) {
			String value = input.trim();
			if (value.equalsIgnoreCase("true")) return true;
			if (value.equalsIgnoreCase("false")) return false;
			return null;
		}

		@Override
		public List<String> suggestions() {
			return List.of("true", "false");
		}
	};

	public static final ConfigValue<String> STRING = new ConfigValue<>() {
		@Override
		public boolean valid(Object raw) {
			return raw instanceof String;
		}

		@Override
		public String read(Object raw) {
			return raw instanceof String ? (String) raw : null;
		}

		@Override
		public String parse(String input) {
			return input;
		}
	};

	public abstract boolean valid(Object raw);

	public abstract T read(Object raw);

	public abstract T parse(String input);

	public T add(T current, String input) {
		return null;
	}

	public T remove(T current, String input) {
		return null;
	}

	public boolean contains(T current, String input) {
		return false;
	}

	public boolean isList() {
		return false;
	}

	public List<String> suggestions() {
		return List.of();
	}

	public static ConfigValue<String> oneOf(String... choices) {
		List<String> allowed = List.of(choices);
		return new ConfigValue<>() {
			@Override
			public boolean valid(Object raw) {
				return raw instanceof String && allowed.contains(raw);
			}

			@Override
			public String read(Object raw) {
				return raw instanceof String ? (String) raw : null;
			}

			@Override
			public String parse(String input) {
				String value = input.trim();
				for (String choice : allowed) {
					if (choice.equalsIgnoreCase(value)) return choice;
				}
				return null;
			}

			@Override
			public List<String> suggestions() {
				return allowed;
			}
		};
	}

	public static ConfigValue<List<String>> list(Supplier<List<String>> suggestions) {
		return new ConfigValue<>() {
			@Override
			public boolean valid(Object raw) {
				return raw instanceof List;
			}

			@Override
			public List<String> read(Object raw) {
				if (!(raw instanceof List)) return null;
				List<String> values = new ArrayList<>();
				for (Object entry : (List<?>) raw) {
					values.add(String.valueOf(entry));
				}
				return values;
			}

			@Override
			public List<String> parse(String input) {
				List<String> values = new ArrayList<>();
				for (String part : input.split(",")) {
					String trimmed = part.trim();
					if (!trimmed.isEmpty()) values.add(trimmed);
				}
				return values;
			}

			@Override
			public List<String> add(List<String> current, String input) {
				List<String> values = new ArrayList<>(current);
				String trimmed = input.trim();
				if (!values.contains(trimmed)) values.add(trimmed);
				return values;
			}

			@Override
			public List<String> remove(List<String> current, String input) {
				List<String> values = new ArrayList<>(current);
				values.remove(input.trim());
				return values;
			}

			@Override
			public boolean contains(List<String> current, String input) {
				return current.contains(input.trim());
			}

			@Override
			public boolean isList() {
				return true;
			}

			@Override
			public List<String> suggestions() {
				return suggestions.get();
			}
		};
	}
}
