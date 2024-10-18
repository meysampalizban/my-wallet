package ir.mywallet.validation;


import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class ExceptionErrors extends RuntimeException {
	private final Map<String,List<Object>> msg;
	
	public ExceptionErrors(Map<String,List<Object>> msg){
		this.msg = msg;
	}
	
}
