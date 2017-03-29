package com.example.assignment.utils;

import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;

import com.example.assignment.account.repository.Account;
import com.google.common.hash.Hashing;

@Component
public class AssignmentUtility {
	
	public String createJsonString(Account account) {
		String str = "";

		if (account != null) {
			str = "{\"password\"" + ":\"" + account.getPassword() + "\",\"description\"" + ":\"" + "Your account is opened"
					+ "\",\"success\":\""	+ "true" + "\"}";
		} else {
			str = "{\"id\"" + ":\"" + "null" + "\",\"description\"" + ":\"" + "account with that ID already exists"
					+ "\",\"success\":\""	+ "false" + "\"}";		}

		return str;
	}
	
	public String createRandomPassword(String accountId) {
		return Hashing.murmur3_32().hashString(accountId, StandardCharsets.UTF_8).toString();
	}

}
