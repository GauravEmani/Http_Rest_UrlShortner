package com.example.assignment.account.repository;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.HeaderParam;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.assignment.authentication.filter.DemoAuthenticationFilter;
import com.example.assignment.url.shorten.repository.UrlController;
import com.example.assignment.utils.AssignmentUtility;
import com.google.common.hash.Hashing;

@RestController
public class AccountController {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private DemoAuthenticationFilter authenticationFilter;
	
	@Autowired
	AssignmentUtility utility;

	@CrossOrigin
	@RequestMapping(path ="/accounts" , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> createWorkshop(@RequestBody Account account) {
		
		String response = "";
		
		if (accountRepository.findById(account.getId()) == null) {
		
			Account account1 = new Account();
			try {
				account1.setId(account.getId());
				account1.setPassword(utility.createRandomPassword(""+account.getId()));
				response = utility.createJsonString(account1);
			} catch (Exception e) {

			}			
			accountRepository.save(account1);
		}
		else {
			response = utility.createJsonString(null);				
		}		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic "+authenticationFilter.createAuthenticationToken());
		ResponseEntity<String> re = new ResponseEntity<String>(response, headers, HttpStatus.OK);
		return re;
	}
	
}
