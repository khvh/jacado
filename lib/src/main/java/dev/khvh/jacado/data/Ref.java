package dev.khvh.jacado.data;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface Ref {

  Class<?> link();

  boolean lazy() default false;

}
