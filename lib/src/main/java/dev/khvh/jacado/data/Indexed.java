package dev.khvh.jacado.data;

import java.lang.annotation.*;

@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Indexed {

  boolean unique() default false;

}
