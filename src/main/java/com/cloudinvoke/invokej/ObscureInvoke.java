package com.cloudinvoke.invokej;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark code entities that seems to be unused, but are actually 
 * invoked from an less visible source like code in JNI modules or via reflection. It is meant
 * to make the coder intent on something. Yes all of this can be added to comments, but normally
 * developer's don't read comments ;-)
 * 
 * @author Hannes de Jager
 * @since 06 Feb 2008
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD})
@Documented
public @interface ObscureInvoke
{
    /** Comments on the obscure invocation */
    String value();
}
