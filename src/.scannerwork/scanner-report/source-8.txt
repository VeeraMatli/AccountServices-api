package com.idexcel.accountservices.AccountServicesapi;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)

@WebMvcTest
public class AccountServicesApiApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountDetailsRepository accountDetialsRepo;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void getAccountByUserName() throws Exception {

		AccountDetails mockModel = new AccountDetails();
		mockModel.setAccountId("5c5c479cc10473b1f097616a");
		mockModel.setFirstName("AJ011");
		mockModel.setLastName("R");
		mockModel.setEmail("ajr@gmail.com");
		mockModel.setUserName("ajreddy08");
		mockModel.setPassword("12345");
		mockModel.setRole("admin");

		Mockito.when(accountDetialsRepo.findByUserName(Mockito.anyString())).thenReturn(mockModel);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/Account/ajreddy08")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("result is " + result.getResponse().getContentAsString());

		MockHttpServletResponse output = result.getResponse();

		String output1 = result.getResponse().getContentAsString();
		
		System.out.print("Output1 is " + output1);
		System.out.print("-------------------------------");
		System.out.println("output is"+output.getContentAsString());
		assertEquals(HttpStatus.OK.value(), output.getStatus());
	}

}
