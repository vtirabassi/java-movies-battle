package com.tirabassi.javamoviesbattle.securities;


import com.tirabassi.javamoviesbattle.domain.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class JwtService {

    @Value("5")
    private String expirationTime;

    @Value("${jwt.chave.assinatura}")
    private String signatureKey;

    public String createToken(User user){

        Long tempoExp = Long.valueOf(this.expirationTime);
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(tempoExp);
        Date date = Date.from(localDateTime.atZone(ZoneOffset.systemDefault()).toInstant());

        return Jwts
                .builder()
                .setSubject(user.getLogin())
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, signatureKey)
                .compact();

    }

    public String getLoginUser(String token) throws ExpiredJwtException{
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public boolean tokenIsValid(String token){

        try{
            Claims claims = getClaims(token);
            Date dataExp = claims.getExpiration();
            LocalDateTime data = dataExp.toInstant().atZone(ZoneOffset.systemDefault()).toLocalDateTime();

            return !LocalDateTime.now()
                    .isAfter(data);

        }catch (Exception ex){
            return false;
        }
    }

    private Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(signatureKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
