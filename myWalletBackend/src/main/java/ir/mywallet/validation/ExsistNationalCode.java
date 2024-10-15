package ir.mywallet.validation;

import ir.mywallet.repository.UserRepo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ExsistNationalCode implements ConstraintValidator<IsExsistNationalCode,String> {
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public boolean isValid(String nationalCode,ConstraintValidatorContext context){
		if(nationalCode == null){
			return true;
		}
		
		try{
			return !this.userRepo.existsByNationalCode(nationalCode);
		}catch(Exception e){
			return true;
		}
	}
}
