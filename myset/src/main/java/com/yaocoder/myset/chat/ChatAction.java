package com.yaocoder.myset.chat;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChatAction {

    public String method() default "";
}
