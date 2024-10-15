package ir.mywallet.controller;

import ir.mywallet.dto.Responses;
import ir.mywallet.model.Account;
import ir.mywallet.services.AccountService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/account")
public class AccountController {
	Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	@Autowired
	private AccountService accountService;
	
	// برای گرفتن اطلاعات حساب های یک کاربر با ایدی کاربر
	@GetMapping(path = "/getaccounts/{userId}")
	@ResponseBody
	public List<Account> getAllAccount(@PathVariable("userId") int userId){
		
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
	@ResponseBody
	public ResponseEntity<Responses> deleteaccount(@PathVariable("accId") int accId){
		Responses res = accountService.deleteaccount(accId);
		return new ResponseEntity<>(res,new HttpHeaders(),HttpStatus.CREATED);
	}
	
}
