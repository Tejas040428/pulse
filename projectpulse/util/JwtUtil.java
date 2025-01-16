package com.cds.projectpulse.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.List;

import com.cds.projectpulse.permission.Permission;

public class JwtUtil {

    private static final String SECRET_KEY = "cb1c5eef59d9b11a29c654b5c3cad6ca3c9db3e9e6fc4fc6901de2f01f5f2a2f8e69bdd5630e6b89887aee94d29b4547a3684734344aa1420ea29e38abad5a751b7ac7a4211813ad7e3f432fcb25ead05605cd457895b8071658b0659c399ccc64afb3d5cb7c0e18f90fb3bf412955968af6929073e3fff098595e504786f307f0d53d3e9a8a95cde60baee5147b8e070ae21eadce954b79d1f25261279ff2d48afd0f36a8e5fbc371964da086ca7c9f8dcd7acc9466e07db2002da48eabf09cfe0927f9ff270c0636478f4f8290828214567839ee88611c36ee92b11790e615e7c0150133068800deba4c977a514cbdbc978dc3b13d629d5775fb42798306bd"; // Replace with a secure key
    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    /**
     * Generate a JWT token with userId, permissions, and projects as claims.
     * 
     * @param userId      the user ID
     * @param list the user's permissions
     * @param projects    the user's projects
     * @return a signed JWT token
     */
    public String generateToken(String userId, List<Permission> list, String projects) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("permissions", list)
                .claim("projects", projects)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * Extract claims from the JWT token.
     * 
     * @param token the JWT token
     * @return the claims contained in the token
     */
    public static Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Check if the JWT token is expired.
     * 
     * @param token the JWT token
     * @return true if the token is expired, false otherwise
     */
    public static boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
