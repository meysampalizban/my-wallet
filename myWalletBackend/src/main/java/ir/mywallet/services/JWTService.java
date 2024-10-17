package ir.mywallet.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTService {
	private static final String SECRETKEY = "5367566B59703373364646381021253698412355667639792F423F4528482B4D6251655468576D5A71347437";
	private static final long EXPIRATIONTIME = 1000 * 60 * 60 * 31;
	
	public String createToken(String email){
		Map<String,Object> claims = new HashMap<>();
		return createToken(claims,email);
	}
	
	public Boolean checkValidToken(String token,String email){
		final String extractedUsername = extractUsername(token);
		return (extractedUsername.equals(email) && !isTokenExpired(token));
	}
	
	
	private String createToken(Map<String,Object> claims,String subject){
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(getSignKey(),SignatureAlgorithm.HS512)
				.compact();
	}
	
	private Key getSignKey(){
		byte[] keyBytes = Decoders.BASE64.decode(SECRETKEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	

	public String extractUsername(String token){
		return Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody().getSubject();
	}
	
	private Boolean isTokenExpired(String token){
		return extractExpiration(token).before(new Date());
	}
	
	private Date extractExpiration(String token){
		return Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody().getExpiration();
	}
	
	
}
