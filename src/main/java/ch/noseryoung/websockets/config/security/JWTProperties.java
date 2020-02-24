package ch.noseryoung.websockets.config.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "jwt")
@Component
public class JWTProperties {

    @NotNull
    private String key;
    @NotNull
    private String headerName;
    @NotNull
    private String tokenPrefix;
    @NotNull
    private Long expiration;

    public String getKey() {
        return key;
    }

    public JWTProperties setKey(String key) {
        this.key = key;
        return this;
    }

    public String getHeaderName() {
        return headerName;
    }

    public JWTProperties setHeaderName(String headerName) {
        this.headerName = headerName;
        return this;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public JWTProperties setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
        return this;
    }

    public Long getExpiration() {
        return expiration;
    }

    public JWTProperties setExpiration(Long expiration) {
        this.expiration = expiration;
        return this;
    }
}
