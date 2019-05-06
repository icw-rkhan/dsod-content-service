
package com.thenextmediagroup.dsod.util;

import java.security.Key;
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenUtil {

	private static Key getKeyInstance(String secret) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		byte[] apiKeySecretBytes = "123".getBytes();
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		return signingKey;
	}

	public static String createJavaWebToken(Map<String, Object> claims, String secret) {
		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance(secret)).compact();
	}
	
	public static Map<String, Object> parserJavaWebToken(String jwt, String secret) {
			try {
					Map<String, Object> jwtClaims = Jwts.parser().setSigningKey(getKeyInstance(secret)).parseClaimsJws(jwt).getBody();
				return jwtClaims;
				} catch (Exception e) {
					return null;
				}
			}
}
