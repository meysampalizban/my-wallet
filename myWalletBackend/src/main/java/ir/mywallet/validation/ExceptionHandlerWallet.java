package ir.mywallet.validation;

import ir.mywallet.dto.Responses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerWallet {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Responses> handleMethodArgumentNotValid(MethodArgumentNotValidException exception){
		Responses res = new Responses();
		res.setTimestamp(new Date());
		res.setStatusType("error");
		res.setStatusCode(400);
		Map<String,List<Object>> messages =
				exception.getBindingResult().getFieldErrors().stream().
						collect(Collectors.toMap(T -> T.getField()
								,S -> new ArrayList<>(List.of(S.getDefaultMessage()))
								,(r,n) ->{ r.addAll(n); return r; } ));
		res.setMessages(messages);
		return new ResponseEntity<Responses>(res,new HttpHeaders(),HttpStatus.BAD_REQUEST);
	}
	
	
}
