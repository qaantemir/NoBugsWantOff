package api.generators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import api.models.RoleType;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GeneratingRule {
  String regex() default "";
  RoleType role() default RoleType.USER;
}