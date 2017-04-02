package com.example.assignment.authentication.filter;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class DemoAuthenticationFilter {

	String user = "test";
	String password = "password";

	public String createAuthenticationToken() {
		
		String authString = user + ":" + password;
		byte[] authTokenBytes = authString.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(authTokenBytes);
		String authStringEnc = new String(base64CredsBytes);
		System.out.println("Base64 encoded auth string: " + authStringEnc);

		return authStringEnc;
	}

	public boolean isUserAuthenticated(String authString) {

		String decodedAuth = "";
		String[] authParts = authString.split("\\s+");
		String authInfo = authParts[1];
		
		// Decode the data back to original string
		byte[] bytes = null;
		bytes = Base64.decodeBase64(authInfo);
		decodedAuth = new String(bytes);
		System.out.println(decodedAuth);
		if(decodedAuth.equals("test:password")){
			return true;
		}
		return false;
	}
}
