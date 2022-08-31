package com.demo.processors.custom;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
public class Options {

    private Map<String, Object> config;

    public Options() {
        config = new HashMap<>();
    }

    public Options(Map<String, Object> config) {
        this.config = config;
    }

    public boolean has(String key) {
        return config.containsKey(key);
    }

    public String getString(String key) {
        return config.containsKey(key) ? config.get(key).toString() : null;
    }

    public String getStringOrDefault(String key, String dft) {
        return config.getOrDefault(key, dft).toString();
    }

    public String getStringWithValidator(String key, Validator validator) {
        String val = getString(key);
        if(validator.validate(val))
            return val;

        throw new OptionValidationException(key, val, validator);
    }

    public int getIntOrDefault(String key, int dft) {
        return getIntOrDefaultWithValidator(key, dft, Validator.MUST_BE_INTEGER_VALIDATOR);
    }

    public int getIntOrDefaultWithValidator(String key, int dft, Validator validator) {
        if(config.containsKey(key)) {
            Object val = config.get(key);
            if(validator.validate(val))
                return (int) val;

            throw new OptionValidationException(key, val, validator);
        }

        return dft;
    }

    public long getLongOrDefault(String key, long dft) {
        return getLongOrDefaultWithValidator(key, dft, Validator.MUST_BE_LONG_VALIDATOR);
    }

    public long getLongOrDefaultWithValidator(String key, long dft, Validator validator) {
        if(config.containsKey(key)) {
            Object val = config.get(key);
            if(validator.validate(val))
                return Long.parseLong(val.toString());

            throw new OptionValidationException(key, val, validator);
        }

        return dft;
    }

    public double getDoubleOrDefault(String key, double dft) {
        return getDoubleOrDefaultWithValidator(key, dft, Validator.MUST_BE_DOUBLE_VALIDATOR);
    }

    public double getDoubleOrDefaultWithValidator(String key, double dft, Validator validator) {
        if(config.containsKey(key)) {
            Object val = config.get(key);
            if(validator.validate(val))
                return (double) val;

            throw new OptionValidationException(key, val, validator);
        }

        return dft;
    }

    public boolean getBoolOrDefault(String key, boolean dft) {
        return getBoolOrDefaultWithValidator(key, dft, Validator.MUST_BE_BOOL_VALIDATOR);
    }

    public boolean getBoolOrDefaultWithValidator(String key, boolean dft, Validator validator) {
        if(config.containsKey(key)) {
            Object val = config.get(key);
            if(validator.validate(val))
                return (boolean) val;

            throw new OptionValidationException(key, val, validator);
        }

        return dft;
    }

    @SuppressWarnings("unchecked")
    public Options getOptions(String key, String... keysIncluded) {
        if(config.containsKey(key)) {
            Object val = config.get(key);
            if(Validator.MUST_BE_MAP_VALIDATOR.validate(val))
                return new Options((Map<String, Object>) val);

            throw new OptionValidationException(key, val, Validator.MUST_BE_MAP_VALIDATOR);
        }

        return new Options(Collections.emptyMap());
    }

    public Options with(String key, Object value) {
        config.put(key, value);
        return this;
    }

    public enum Validator {

        MUST_BE_BOOL_VALIDATOR("MUST_BE_BOOL_VALIDATOR",
                obj -> canCast(obj, Boolean.class)),
        MUST_BE_INTEGER_VALIDATOR("MUST_BE_INTEGER_VALIDATOR",
                obj -> canCast(obj, Integer.class)),
        MUST_BE_LONG_VALIDATOR("MUST_BE_INTEGER_VALIDATOR",
                obj -> canCast(obj, Long.class)),
        MUST_BE_DOUBLE_VALIDATOR("MUST_BE_DOUBLE_VALIDATOR",
                obj -> canCast(obj, Double.class)),
        MUST_BE_MAP_VALIDATOR("MUST_BE_DOUBLE_VALIDATOR",
                obj -> canCast(obj, Map.class)),
        POSITIVE_INTEGER_VALIDATOR("POSITIVE_INTEGER_VALIDATOR",
                obj -> MUST_BE_INTEGER_VALIDATOR.validate(obj) && ((int)obj) > 0),
        NOT_NULL_OR_EMPTY_STRING_VALIDATOR("NOT_NULL_OR_EMPTY_STRING_VALIDATOR", obj -> true);

        private final String name;
        private final Function<Object, Boolean> validation;

        Validator(String name, Function<Object, Boolean> validation) {
            this.name = name;
            this.validation = validation;
        }

        boolean validate(Object obj) {
            return validation.apply(obj);
        }

        @SuppressWarnings("unchecked")
        private static <T> boolean canCast(Object obj, Class<T> clz) {
            try {
                T casted = (T) obj;
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

}
