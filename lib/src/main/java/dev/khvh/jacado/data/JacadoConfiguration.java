package dev.khvh.jacado.data;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface JacadoConfiguration {

  String[] packages() default "";

}
