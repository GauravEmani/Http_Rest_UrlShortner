package com.example.assignment.account.repository;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.assignment.url.shorten.repository.UrlController;
import com.example.assignment.utils.AssignmentUtility;
import com.google.common.hash.Hashing;

@RestController
public class AccountController {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	AssignmentUtility utility;

	@CrossOrigin
	@RequestMapping(path ="/accounts" , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> createWorkshop(@RequestBody Account account) {
		/*String authToken = "userAuthxxyybsdisn123nd";
		byte[] authTokenBytes = authToken.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(authTokenBytes);
		String base64Creds = new String(base64CredsBytes);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", base64Creds);*/
		/*Account ac = new Account();
		ac.setId(1234);
		ac.setPassword("test");
		return ResponseEntity.ok(ac);*/
		
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
		/*String authToken = account.getId()+":"+account.getPassword();
		byte[] authTokenBytes = authToken.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(authTokenBytes);
		String base64Creds = new String(base64CredsBytes);*/
		ResponseEntity<String> re = new ResponseEntity<String>(response, HttpStatus.OK);
		// re.getHeaders().add("Authorization", base64Creds);
		return re;
	}
	
}
