package com.idexcel.accountservices.AccountServicesapi;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountDetailsRepository  extends MongoRepository<AccountDetails, String>{
	
	AccountDetails findByUserNameAndPassword(String userName, String password);
	AccountDetails findByUserName(String userName);
	void deleteByUserName(String userName);
	
}
