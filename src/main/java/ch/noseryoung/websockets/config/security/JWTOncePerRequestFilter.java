package ch.noseryoung.websockets.config.security;

import ch.noseryoung.websockets.domain.user.UserDetailsImpl;
import ch.noseryoung.websockets.domain.user.UserService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

@Component
public class JWTOncePerRequestFilter extends OncePerRequestFilter {

    private UserService userService;
    private JWTProperties jwtProperties;

    @Autowired
    public JWTOncePerRequestFilter(UserService userService, JWTProperties jwtProperties) {
        this.userService = userService;
        this.jwtProperties = jwtProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(jwtProperties.getHeaderName());

        if(authorizationHeader != null) {
            SecurityContextHolder.getContext().setAuthentication(authenticate(authorizationHeader));
        }

        filterChain.doFilter(request, response);
    }

    private Authentication authenticate(String header) {
        if(header.startsWith(jwtProperties.getTokenPrefix())) {
            try {
                String userId = parseSubject(header);

                UserDetails userDetails = new UserDetailsImpl(userService.findById(userId));

                return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            } catch (JwtException e) {
                logger.debug("Exception while parsing JsonWebToken", e);
            } catch (NoSuchElementException ignored) {}
        }

        throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }

    private String parseSubject(String header) throws JwtException {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getKey().getBytes())
                .parseClaimsJws(header.replace(jwtProperties.getTokenPrefix() + " ", "")).getBody()
                .getSubject();
    }
}
