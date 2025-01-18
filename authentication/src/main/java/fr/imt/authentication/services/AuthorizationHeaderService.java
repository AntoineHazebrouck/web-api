package fr.imt.authentication.services;

import java.util.Base64;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationHeaderService {
	public boolean isBasic(String authorization) {
		return authorization.startsWith("Basic");
	}

	public Pair<String, String> retrieveCredentials(String authorization) {
		String decoded = new String(Base64.getDecoder().decode(authorization.replace("Basic ", "")));
		String login = decoded.split(":")[0];
		String password = decoded.split(":")[1];

		return Pair.of(login, password);
	}

	public boolean isBearer(String authorization) {
		return authorization.startsWith("Bearer");
	}

	public String retrieveToken(String authorization) {
		return authorization.replace("Bearer ", "");
	}
}
