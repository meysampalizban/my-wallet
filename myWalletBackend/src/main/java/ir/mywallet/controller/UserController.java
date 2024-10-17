package ir.mywallet.controller;

import ir.mywallet.dto.Responses;
import ir.mywallet.model.User;
import ir.mywallet.services.JWTService;
import ir.mywallet.services.OperationService;
import ir.mywallet.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OperationService operationService;
	
	@Autowired
	private JWTService jwtService;
	
	// گرفتن اطلاعات کاربر با ایدی
	@GetMapping(path = "/getuser/{userId}")
	@ResponseBody
	public User getUser(@PathVariable("userId") int userId){
		return userService.getUser(userId);
	}
	
	// ساخت کاربر
	@PostMapping(path = "/createuser", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Responses> createUserAndWalletEndPoint(@Valid @RequestBody User req){
		Responses res = userService.createUserAndWallet(req);
		return new ResponseEntity<>(res,new HttpHeaders(),HttpStatus.CREATED);
	}
	
	
}
