package de.shifen.yaochi.client.annotation;

import de.shifen.yaochi.client.handler.BuiltinTypeHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author ms404 <yaochi.github@404.ms>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogTag {
    String alias() default "";

    BuiltinTypeHandler builtinType() default BuiltinTypeHandler.NORMAL;

    String extendedType() default "";
}
