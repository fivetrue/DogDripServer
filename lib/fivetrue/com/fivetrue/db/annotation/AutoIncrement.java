package com.fivetrue.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
/**
 * DB table 중 해당 속성이 들어가 있을 경우 insert 시 제외 시킨다.
 * @author Fivetrue
 *
 */
public @interface AutoIncrement {

}
