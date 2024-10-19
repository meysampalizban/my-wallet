package ir.mywallet.controller;

import ir.mywallet.dto.Responses;
import ir.mywallet.model.User;
import ir.mywallet.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200",
		allowCredentials = "true", maxAge = 3000L, methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE},
		allowedHeaders = {HttpHeaders.AUTHORIZATION,HttpHeaders.ACCEPT,HttpHeaders.CONTENT_TYPE,"userId"})

@RequestMapping("/api/user")
public class UserController {
	
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService){
		this.userService = userService;
	}
	
	
	// گرفتن اطلاعات کاربر با ایدی
	@GetMapping(path = "/getuser/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public User getUser(@NotNull @PathVariable("userId") int userId)
	{
		return userService.getUserById(userId);
	}
	
	// ساخت کاربر
	@PostMapping(path = "/createuser", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Responses> createUserAndWalletEndPoint(@Valid @RequestBody User req){
		Responses res = userService.createUserAndWallet(req);
		return new ResponseEntity<>(res,new HttpHeaders(),HttpStatus.CREATED);
	}
	
	
}
