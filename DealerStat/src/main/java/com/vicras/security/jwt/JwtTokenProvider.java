package com.vicras.security.jwt;

import com.vicras.entity.User;
import com.vicras.service.UserService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@PropertySource("classpath:application.properties")
@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secretKey;

    @Value("${jwt.token.expired}")
    private long expiredTimeMillis;


    final private UserService userService;

    @Autowired
    public JwtTokenProvider(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("role", user.getRole());

        Date now = new Date();
        Date validity = new Date(now.getTime() + expiredTimeMillis);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Optional<Authentication> getAuthenticationIfTokenValid(HttpServletRequest request) {
        var token = resolveTokenFromRequest(request);
        if (isTokenValid(token)) {
            var userDetails = getUserDetails(token);
            return getAuthentication(userDetails);
        }
        return Optional.empty();
    }

    private String resolveTokenFromRequest(HttpServletRequest request) {
        var headerString = request.getHeader("Authorization");
        if (headerString != null && headerString.startsWith("Bearer ")) {
            return headerString.substring(7);
        }
        return null;
    }

    private UserDetails getUserDetails(String token) {
        return userService.loadUserByUsername(getUsername(token));
    }

    private Optional<Authentication> getAuthentication(UserDetails userDetails) {
        return Optional.of(
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        "",
                        userDetails.getAuthorities()
                ));
    }

    private String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return !isTokenExpired(claims);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private boolean isTokenExpired(Jws<Claims> claims) {
        return claims.getBody().getExpiration().before(new Date());
    }
}

