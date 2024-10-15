package ir.mywallet.validation;

import ir.mywallet.repository.AccountRepo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ExsistShabaNumber implements ConstraintValidator<IsExsistShabaNumber,String> {
	@Autowired
	private AccountRepo accountRepo;
	
	@Override
	public boolean isValid(String shabaNummber,ConstraintValidatorContext context){
		if(shabaNummber == null){
			return true;
		}
		try{
			return !this.accountRepo.existsByShabaNumber(shabaNummber);
		}catch(Exception e){
			return true;
		}
	}
}
