package ir.mywallet.controller;


import ir.mywallet.dto.Responses;
import ir.mywallet.model.Account;
import ir.mywallet.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.*;

@WebMvcTest
public class AccountControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Mock
	private AccountService accountService;

	@InjectMocks
	private AccountController accountController;

	private Account account;

	@BeforeEach
	public void setUp(){
		MockitoAnnotations.openMocks(this);
		account = new Account();
		account.setId(5446);
		account.setAccBalance(20L);
		account.setAccNumber("1561615");
		account.setShabaNumber("456465165165");
	}


	@Test
	public void testCreateAccount() throws Exception {


		Responses res = new Responses();
		Map<String,List<Object>> msg = new HashMap<>();
		res.setStatusType("success");
		res.setStatusCode(201);
		res.setTimestamp(new Date());
		msg.put("success",new ArrayList<>(List.of("افتتاح حساب با موفقیت انجام شد")));
		res.setMessages(msg);

		when(accountService.createAccount(any(Account.class))).thenReturn(res);


		mockMvc.perform(post("/createaccount")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"accNumber\":\"6034477961\", \"shabaNumber\":\"9546477465456465\",\"accBalance\": 25555,\"user\":{ \"id\": 4 } }"))
				.andExpect(status().isCreated());

	}




}
