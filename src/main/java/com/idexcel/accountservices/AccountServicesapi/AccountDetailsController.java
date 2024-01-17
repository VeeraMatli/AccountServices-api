package com.idexcel.accountservices.AccountServicesapi;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class AccountDetailsController {

	@Autowired
	public AccountDetailsService accountDetailsService;

	public String jwtToken;
	
	@GetMapping(path = "/Accounts/login/{userName}/{password}")
	@ResponseBody
	public ResponseEntity<?> checkLogin(@PathVariable String userName, @PathVariable String password) {
		HttpHeaders headers = new HttpHeaders();
		System.out.println("Username -- " + userName + " password -- " + password);
		AccountDetails accountDetails = accountDetailsService.checkLogin(userName, password);
		
		
		if (accountDetails != null) {
			jwtToken = generateJWTToken(userName);
			headers.add("jwtToken", jwtToken);
			System.out.println("jwtToken -- " + jwtToken);
		}
		return new ResponseEntity<>("User Logged In Successfully", headers, HttpStatus.OK);
	}


	@GetMapping(value = "/Accounts")
	public ResponseEntity<List<AccountDetails>> getAllAccountDetails(HttpServletResponse response) {
		List<AccountDetails> detailsList = accountDetailsService.getAllAccountDetails();
		if (detailsList == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		HttpHeaders header = new HttpHeaders();
		header.add("jwtToken", jwtToken);
		return new ResponseEntity<List<AccountDetails>>(detailsList, header, HttpStatus.OK);
	}

	@PostMapping(value = "/Accounts/add")
	@ResponseBody
	public ResponseEntity<?> addDetails(@RequestBody AccountDetails details) {
		Map<String, Object> json = new HashMap<String, Object>();
		jwtToken = generateJWTToken(details.getUserName());
		AccountDetails accountDetails = new AccountDetails();
		try {
			accountDetails = accountDetailsService.save(details);
			if (accountDetails.getAccountId() != null) {
				json.put("success", true);
				json.put("message", "Account has been Created for User " + accountDetails.getUserName());
				return new ResponseEntity<>(json, HttpStatus.CREATED);
			} else {
				json.put("success", false);
				json.put("message", "Issue creating Account for User " + accountDetails.getUserName());
				return new ResponseEntity<>(json, HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "Issue creating Account for User " + details.getUserName());
			return new ResponseEntity<>(json, HttpStatus.CONFLICT);
		}

	}

	@GetMapping(value = "/Accounts/{userName}",produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	@PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<AccountDetails> getAccountDetails(@PathVariable String userName) {
		AccountDetails accountDetails = accountDetailsService.getAccountDetails(userName);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<>(accountDetails,headers, HttpStatus.OK);
	}

	@PutMapping(value = "/Accounts/{userName}")
	public String putAccountDetails(@RequestBody AccountDetails details) {
		accountDetailsService.save(details);
		return details.getAccountId();
	}

	@DeleteMapping(value = "/Accounts/{userName}")
	public String deleteAccount(@PathVariable String userName) {
		accountDetailsService.deleteUser(userName);
		return "Account Deleted";
	}

	@PatchMapping(value = "/Accounts/{userName}")
	public String patchAccountDetails(@RequestBody AccountDetails details, @PathVariable String userName) {
		accountDetailsService.patchUser(details, userName);
		return details.getAccountId();
	}

	
	private String generateJWTToken(String userName) {
		jwtToken = Jwts.builder().setSubject(userName).claim("roles", "user").setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "9642").compact();
		return jwtToken;
	}
}
