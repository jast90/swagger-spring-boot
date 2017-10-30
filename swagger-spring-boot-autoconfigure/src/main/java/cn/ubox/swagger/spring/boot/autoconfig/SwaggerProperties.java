package cn.ubox.swagger.spring.boot.autoconfig;

import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhiwen
 * @date 2017/10/27
 */
@ConfigurationProperties(prefix = SwaggerProperties.SWAGGER_PREFIX)
public class SwaggerProperties {
    public static final String SWAGGER_PREFIX = "swagger";

    /**
     * 映射的url
     */
    private String pathMapping = "/";

    /**
     * 扫描的包
     */
    private String basePackage;

    /**
     * 排除的url
     */
    private List<String> excludePath = new ArrayList<>();

    /**
     * 文档标题
     */
    private String title = "文档标题";

    /**
     * 文档描述
     */
    private String description = "文档描述";

    /**
     * 版本号
     */
    private String version = "版本号";

    /**
     * 安全模式
     */
    private String scheme = "api_key";

    @NestedConfigurationProperty
    private Param param;

    @NestedConfigurationProperty
    private Oauth2 oauth2;

    @NestedConfigurationProperty
    private Oauth2.GrantType grantType;

    public String getPathMapping() {
        return pathMapping;
    }

    public void setPathMapping(String pathMapping) {
        this.pathMapping = pathMapping;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public List<String> getExcludePath() {
        return excludePath;
    }

    public void setExcludePath(List<String> excludePath) {
        this.excludePath = excludePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public Param getParam() {
        return param;
    }

    public void setParam(Param param) {
        this.param = param;
    }

    public Oauth2 getOauth2() {
        return oauth2;
    }

    public void setOauth2(Oauth2 oauth2) {
        this.oauth2 = oauth2;
    }

    public class Param {
        private String name;
        private String type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public class Oauth2 {
        private String clientId;
        private String secretId;
        private String realm;
        private List<Scope> scope = Lists.newArrayList();
        private GrantType grantType;

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getSecretId() {
            return secretId;
        }

        public void setSecretId(String secretId) {
            this.secretId = secretId;
        }

        public String getRealm() {
            return realm;
        }

        public void setRealm(String realm) {
            this.realm = realm;
        }

        public List<Scope> getScope() {
            return scope;
        }

        public void setScope(List<Scope> scope) {
            this.scope = scope;
        }

        public GrantType getGrantType() {
            return grantType;
        }

        public void setGrantType(GrantType grantType) {
            this.grantType = grantType;
        }

        public class Scope {
            private String name;
            private String description;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }

        public class GrantType {
            private String name;
            private String authorizeUrl;
            private String tokenUrl;
            private String loginUrl;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAuthorizeUrl() {
                return authorizeUrl;
            }

            public void setAuthorizeUrl(String authorizeUrl) {
                this.authorizeUrl = authorizeUrl;
            }

            public String getTokenUrl() {
                return tokenUrl;
            }

            public void setTokenUrl(String tokenUrl) {
                this.tokenUrl = tokenUrl;
            }

            public String getLoginUrl() {
                return loginUrl;
            }

            public void setLoginUrl(String loginUrl) {
                this.loginUrl = loginUrl;
            }
        }
    }

    public Oauth2.GrantType getGrantType() {
        return grantType;
    }

    public void setGrantType(Oauth2.GrantType grantType) {
        this.grantType = grantType;
    }
}
