package com.demo.processors.custom;

import static com.demo.processors.custom.Options.Validator;

public class OptionValidationException extends RuntimeException{

    public OptionValidationException() {
        super();
    }

    public OptionValidationException(String msg) {
        super(msg);
    }

    public OptionValidationException(String msg, Throwable e) {
        super(msg, e);
    }

    public OptionValidationException(String key, Object value, Validator validator) {
        this(String.format("Validator %s can not validate option %s with value %s",
                validator.name(), key, value));
    }
}
