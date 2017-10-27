package me.jastz.swagger.spring.boot;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import com.google.common.collect.Lists;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * 用于创建Docket ,SecurityConfiguration 和UiConfiguration
 *
 * @author zhiwen
 * @date 2017/10/27
 */
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerAutoConfiguration {

    private final SwaggerProperties properties;

    public SwaggerAutoConfiguration(SwaggerProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public Docket docket() {
        List<Predicate<String>> excludePath = new ArrayList();
        for (String path : properties.getExcludePath()) {
            excludePath.add(PathSelectors.ant(path));
        }

        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping(properties.getPathMapping())
                .select()
                .apis(RequestHandlerSelectors.basePackage(properties.getBasePackage()))
                .paths(Predicates.and(Predicates.not(Predicates.or(excludePath))))
                .build()
                .securitySchemes(newArrayList(new OAuthBuilder().name("oauth2").grantTypes(grantTypes())
                        .scopes(scopes()).build()))
                .securityContexts(newArrayList(oauth()))
                .apiInfo(apiInfo())
                ;
    }

    private List<AuthorizationScope> scopes() {
        List<AuthorizationScope> authorizationScopes = Lists.newArrayList();
        for (SwaggerProperties.Oauth2.Scope scope : properties.getOauth2().getScope()) {
            authorizationScopes.add(new AuthorizationScope(scope.getName(), scope.getDescription()));
        }
        return authorizationScopes;
    }

    private List<GrantType> grantTypes() {
        String tokenUrl = properties.getGrantType().getTokenUrl(), authorizeUrl = properties.getGrantType().getAuthorizeUrl(), loginUrl = properties.getGrantType().getLoginUrl();
        List<GrantType> grantTypes = new ArrayList<>();
        if ("authorization_code".equals(properties.getScheme())) {
            AuthorizationCodeGrant authorizationCodeGrant = new AuthorizationCodeGrant(
                    new TokenRequestEndpoint(authorizeUrl, properties.getOauth2().getClientId()
                            , properties.getOauth2().getSecretId()),
                    new TokenEndpoint(tokenUrl, "access_token"));
            grantTypes.add(authorizationCodeGrant);
        } else if ("client_credentials".equals(properties.getScheme())) {
            ClientCredentialsGrant clientCredentialsGrant = new ClientCredentialsGrant(tokenUrl);
            grantTypes.add(clientCredentialsGrant);
        } else if ("password".equals(properties.getScheme())) {
            ResourceOwnerPasswordCredentialsGrant resourceOwnerPasswordCredentialsGrant =
                    new ResourceOwnerPasswordCredentialsGrant(tokenUrl);
            grantTypes.add(resourceOwnerPasswordCredentialsGrant);
        } else if ("implicit".equals(properties.getScheme())) {
            ImplicitGrant implicitGrant = new ImplicitGrant(new LoginEndpoint(tokenUrl), "access_token");
            grantTypes.add(implicitGrant);
        }
        return grantTypes;
    }

    private ApiKey apiKey() {
        return new ApiKey("mykey", "api_key", "header");
    }


    private SecurityContext oauth() {
        List<SecurityReference> auth;
        if ("apiKey".equals(properties.getScheme())) {
            auth = apiAuth();
        } else {
            auth = oauth2Auth();
        }
        return SecurityContext.builder()
                .securityReferences(auth)
                .forPaths(PathSelectors.ant("/**"))
                .build();
    }

    private List<SecurityReference> apiAuth() {
        return newArrayList(
                new SecurityReference("mykey", scopes().toArray(new AuthorizationScope[0])));
    }

    private List<SecurityReference> oauth2Auth() {
        return newArrayList(
                new SecurityReference("oauth2", scopes().toArray(new AuthorizationScope[0])));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(properties.getTitle()).description(properties.getDescription()).version(properties.getVersion()).build();
    }
}
