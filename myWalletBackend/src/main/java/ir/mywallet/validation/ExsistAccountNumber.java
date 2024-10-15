package ir.mywallet.validation;

import ir.mywallet.repository.AccountRepo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ExsistAccountNumber implements ConstraintValidator<IsExsistAccountNumber,String> {
	@Autowired
	private AccountRepo accountRepo;
	
	@Override
	public boolean isValid(String accNumber,ConstraintValidatorContext context){
		if(accNumber == null){
			return true;
		}
		
		try{
			return !accountRepo.existsByAccNumber(accNumber);
		}catch(Exception e){
			return true;
		}
	}
}
