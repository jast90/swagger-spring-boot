package me.jastz.swagger.spring.boot;

import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.annotation.*;

/**
 * @author zhiwen
 * @date 2017/10/27
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({EnableSwagger2.class, SwaggerAutoConfiguration.class})
public @interface EnableSwagger2Doc {
}
