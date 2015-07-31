package zero.easymvc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PositionalParameter {

    String after() default "<initial>";

    Class<? extends BeanParser> parser() default BeanParser.class;

    boolean optional() default true;

}
