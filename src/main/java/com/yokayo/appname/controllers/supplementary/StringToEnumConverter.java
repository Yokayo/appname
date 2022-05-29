package com.yokayo.appname.controllers.supplementary;

import org.springframework.core.convert.converter.Converter;
import com.yokayo.appname.entities.User;

public class StringToEnumConverter implements Converter<String, User.Status> {
    @Override
    public User.Status convert(String source) {
        return User.Status.valueOf(source.toUpperCase());
    }
}