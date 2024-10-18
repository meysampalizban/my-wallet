package ir.mywallet.controller;

import ir.mywallet.dto.Responses;
import ir.mywallet.model.Account;
import ir.mywallet.services.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200",
		allowCredentials = "true", maxAge = 3000L, methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE},
		allowedHeaders = {HttpHeaders.AUTHORIZATION,HttpHeaders.ACCEPT,HttpHeaders.CONTENT_TYPE,"userId"})
@RequestMapping("/api/account")
public class AccountController {
	
	private final AccountService accountService;
	
	@Autowired
	public AccountController(AccountService accountService){
		this.accountService = accountService;
	}
	
	
	// برای گرفتن اطلاعات حساب های یک کاربر با ایدی کاربر
	@GetMapping(path = "/getaccounts/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Account> getAllAccount(@NotNull @PathVariable("userId") int userId){
		return accountService.getAccounts(userId);
	}
	
	// ساخت حساب
	@PostMapping(path = "/createaccount", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Responses> createAccount(@Valid @RequestBody Account account){
		Responses res = accountService.createAccount(account);
		return new ResponseEntity<>(res,new HttpHeaders(),HttpStatus.CREATED);
	}
	
	
	// حذف حساب با ایدی حساب
	@DeleteMapping(path = "/deleteaccount/{accId}")
	public ResponseEntity<Responses> deleteaccount(@NotNull @PathVariable("accId") int accId){
		Responses res = accountService.deleteAccount(accId);
		return new ResponseEntity<>(res,new HttpHeaders(),HttpStatus.ACCEPTED);
	}
	
}
