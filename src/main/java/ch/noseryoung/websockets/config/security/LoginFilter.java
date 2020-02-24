package ch.noseryoung.websockets.config.security;

import ch.noseryoung.websockets.domain.user.User;
import ch.noseryoung.websockets.domain.user.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    private JWTProperties jwtProperties;
    private ObjectMapper objectMapper;

    public LoginFilter(RequestMatcher requestMatcher, AuthenticationManager authenticationManager, JWTProperties jwtProperties) {
        super(requestMatcher);
        setAuthenticationManager(authenticationManager);
        this.jwtProperties = jwtProperties;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        UsernameAndPassword usernameAndPassword = objectMapper.readValue(request.getInputStream(), UsernameAndPassword.class);

        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(usernameAndPassword.getUsername(), usernameAndPassword.getPassword()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User authenticatedUser = ((UserDetailsImpl) authResult.getPrincipal()).getUser();

        String authorizationToken = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getKey().getBytes())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .setSubject(authenticatedUser.getId())
                .compact();

        response.addHeader(jwtProperties.getHeaderName(), jwtProperties.getTokenPrefix() + " " + authorizationToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.addHeader("Access-Control-Expose-Headers", jwtProperties.getHeaderName());
    }
}
