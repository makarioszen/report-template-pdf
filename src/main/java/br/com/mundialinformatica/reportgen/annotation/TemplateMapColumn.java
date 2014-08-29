package br.com.mundialinformatica.reportgen.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TemplateMapColumn {
	public String title() default "";
	public String value() default "";
	public String category() default "";
}
