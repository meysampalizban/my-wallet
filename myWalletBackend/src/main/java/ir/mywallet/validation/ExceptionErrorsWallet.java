package ir.mywallet.validation;

import ir.mywallet.dto.Responses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class ExceptionErrorsWallet {
	
	@ExceptionHandler(ExceptionErrors.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Responses> handleMethodArgumentNotValid(ExceptionErrors exception){
		Responses res = new Responses();
		res.setTimestamp(new Date());
		res.setStatusType("error");
		res.setStatusCode(400);
		res.setMessages(exception.getMsg());
		return new ResponseEntity<>(res,new HttpHeaders(),HttpStatus.BAD_REQUEST);
	}
}