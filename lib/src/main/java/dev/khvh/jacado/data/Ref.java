package dev.khvh.jacado.data;

import java.lang.annotation.*;

import dev.khvh.jacado.Model;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface Ref {

  Class<?> link() default Model.class;

  boolean lazy() default false;

}
