package cn.ubox.swagger.spring.boot.autoconfig;

import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

import java.lang.annotation.*;

/**
 * @author zhiwen
 * @date 2017/10/27
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({Swagger2DocumentationConfiguration.class, SwaggerAutoConfiguration.class})
public @interface EnableSwagger {
}
