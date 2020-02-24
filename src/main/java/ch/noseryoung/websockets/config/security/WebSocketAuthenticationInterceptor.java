package ch.noseryoung.websockets.config.security;

import ch.noseryoung.websockets.domain.user.UserDetailsImpl;
import ch.noseryoung.websockets.domain.user.UserService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

@Component
public class WebSocketAuthenticationInterceptor implements ChannelInterceptor {

    private JWTProperties jwtProperties;
    private UserService userService;
    private Logger logger;

    @Autowired
    public WebSocketAuthenticationInterceptor(JWTProperties jwtProperties, UserService userService, Logger logger) {
        this.jwtProperties = jwtProperties;
        this.userService = userService;
        this.logger = logger;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        List<String> authorization = headerAccessor.getNativeHeader(jwtProperties.getHeaderName());

        if (authorization != null) {
            Authentication authentication = authenticate(authorization.get(0));

            if (authentication != null) {
                headerAccessor.setUser(authentication);
            }
        }

        return message;
    }

    private Authentication authenticate(String header) {
        if (header.startsWith(jwtProperties.getTokenPrefix())) {
            try {
                String userId = parseSubject(header);

                UserDetails userDetails = new UserDetailsImpl(userService.findById(userId));

                return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            } catch (JwtException e) {
                logger.debug("Exception while parsing JsonWebToken", e);
            } catch (NoSuchElementException ignored) {
            }
        }

        return null;
    }

    private String parseSubject(String header) throws JwtException {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getKey().getBytes())
                .parseClaimsJws(header.replace(jwtProperties.getTokenPrefix() + " ", "")).getBody()
                .getSubject();
    }
}
