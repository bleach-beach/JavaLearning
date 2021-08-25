package ioc.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @program: javaExercise
 * @description:
 * @author: admin
 * @create: 2019/7/7 17:03
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnno {
    public String value();
}
