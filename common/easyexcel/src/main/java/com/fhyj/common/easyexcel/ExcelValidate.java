package com.fhyj.common.easyexcel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Description
 *
 * @author djdgt
 * @since 2023/3/2 13:54
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelValidate {

    String name();

    boolean notNull() default false;

    int maxFieldLength() default 255;

    int minFieldLength() default 0;

    String pattern() default "";

}
