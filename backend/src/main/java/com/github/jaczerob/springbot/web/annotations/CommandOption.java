package com.github.jaczerob.springbot.web.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.dv8tion.jda.api.interactions.commands.OptionType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(CommandOptions.class)
public @interface CommandOption {
    String value() default "";
    OptionType type() default OptionType.STRING;
    String description() default "";
    boolean required() default true;
    boolean autoComplete() default false;
}
