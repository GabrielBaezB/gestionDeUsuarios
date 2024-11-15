package com.miportafolio.gestion_usuarios.security.jwt;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.miportafolio.gestion_usuarios.security.dto.JwtDto;
import com.miportafolio.gestion_usuarios.security.entity.UsuarioPrincipal;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    // Genera el token JWT para el usuario autenticado
    public String generateToken(Authentication authentication) {
        UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
        List<String> roles = usuarioPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        
        return Jwts.builder()
                .setSubject(usuarioPrincipal.getUsername())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 180))  // Puede ser necesario ajustar la duración
                .signWith(getSecret(secret))  // Firma con la clave secreta
                .compact();
    }

    // Extrae el nombre de usuario del token JWT
    public String getNombreUsuarioFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecret(secret))  // Se utiliza la clave secreta para verificar la firma
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Valida la firma del token JWT
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSecret(secret))  // Verifica la firma con la clave secreta
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("token mal formado");
        } catch (UnsupportedJwtException e) {
            logger.error("token no soportado");
        } catch (ExpiredJwtException e) {
            logger.error("token expirado");
        } catch (IllegalArgumentException e) {
            logger.error("token vacío");
        } catch (SignatureException e) {
            logger.error("fail en la firma");
        }
        return false;
    }

    // Refresca el token JWT en caso de que haya expirado
    public String refreshToken(JwtDto jwtDto) throws ParseException {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSecret(secret))  // Verifica la firma del token existente
                .build()
                .parseClaimsJws(jwtDto.getToken());
        } catch (ExpiredJwtException e) {
            JWT jwt = JWTParser.parse(jwtDto.getToken());
            JWTClaimsSet claims = jwt.getJWTClaimsSet();
            String nombreUsuario = claims.getSubject();
            List<String> roles = (List<String>) claims.getClaim("roles");

            return Jwts.builder()
                    .setSubject(nombreUsuario)
                    .claim("roles", roles)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime() + expiration))  // Expiración ajustada
                    .signWith(getSecret(secret))  // Firma con la clave secreta
                    .compact();
        }
        return null;
    }

    // Método para obtener la clave secreta (HMAC-SHA256)
    private Key getSecret(String secret) {
        // Si el secreto no tiene la longitud adecuada, generamos una nueva clave segura
        if (secret.length() < 32) {
            logger.warn("La clave proporcionada es demasiado corta. Se generará una clave segura de 256 bits.");
            // Generar una clave secreta segura de 256 bits (32 bytes) utilizando HMAC-SHA256
            return Keys.secretKeyFor(SignatureAlgorithm.HS256);  // Genera una clave de 256 bits
        } else {
            byte[] secretBytes = Decoders.BASE64URL.decode(secret);  // Decodifica el secreto base64
            return Keys.hmacShaKeyFor(secretBytes);  // Retorna la clave en el formato adecuado (256 bits)
        }
    }
}
