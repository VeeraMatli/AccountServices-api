package com.idexcel.accountservices.AccountServicesapi;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailsService {

	@Autowired
	public AccountDetailsRepository accountDetailsRepository;
	
	
	
	public AccountDetails checkLogin(String userName, String password) {
		AccountDetails userDetails = accountDetailsRepository.findByUserNameAndPassword(userName, password);
		if(userDetails.getUserName() !=null) {
			System.out.println("userVerified -- "+userDetails.getFirstName());
			System.out.println("userVerified -- "+userDetails.getAccountId());
			return userDetails;
		} else { 
			
			System.out.println("Unable to find User. Please check User Name and Password.");
			return new AccountDetails();
		}
	}
	
	
	public List<AccountDetails> getAllAccountDetails() {
		return accountDetailsRepository.findAll();
	}
	
	public AccountDetails save(AccountDetails entity) {
		return accountDetailsRepository.save(entity);
	}

	public AccountDetails getAccountDetails(String userName) {
		return accountDetailsRepository.findByUserName(userName);
	}

	public String patchUser(AccountDetails accountDetails, String userName ) {
		AccountDetails details  = accountDetailsRepository.findByUserName(userName);
		
		if(accountDetails.getFirstName()!=null) {
			details.setFirstName(accountDetails.getFirstName());
		}
		if(accountDetails.getEmail()!=null) {
			details.setEmail(accountDetails.getEmail());
		}
		if(accountDetails.getPassword()!=null) {
			details.setPassword(accountDetails.getPassword());
		}
		if(accountDetails.getUserName() !=null) {
			details.setUserName(accountDetails.getUserName());
		}
		accountDetailsRepository.save(details);
		return "Updated Account Information";
	}
	
	public void deleteUser(String userName) {
		accountDetailsRepository.deleteByUserName(userName);
	}


}
