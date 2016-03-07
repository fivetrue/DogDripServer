package com.fivetrue.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)

/**
 * DB Table 중 해당 속성이 있을 경우 Foreign table을 찾아 inner join 시킨다.
 * Foreign 값을 담으려면 Class 내에 MemeberVariable 이 선언된 Foreign table에 해당하는 변수가 있어야한다.
 * @author Fivetrue
 *
 */
public @interface ForeignKey {
	
	/**
	 * 
	 * @return tableName
	 */
	Class<?> value();
}
