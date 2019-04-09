package cn.edu.jxnu.reflect;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
public @interface NotNull {
	String value() default "";
}
