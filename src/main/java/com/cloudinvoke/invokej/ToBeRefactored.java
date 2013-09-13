package com.cloudinvoke.invokej;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to mark entities that should be refactored or removed.
 * 
 * @author Hannes de Jager
 * @since 18 Apr 2008
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD, 
         ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.PACKAGE})
@Documented
public @interface ToBeRefactored {
    
    /** The reason for refactoring or removal */
    String value() default "";
}
