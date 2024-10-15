package ir.mywallet.validation;

import ir.mywallet.repository.UserRepo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ExistEmailValidation implements ConstraintValidator<IsExsistEmail,String> {
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public void initialize(IsExsistEmail constraintAnnotation){
	}
	
	@Override
	public boolean isValid(String email,ConstraintValidatorContext context){
		if(email == null || email.isEmpty()){
			return true;
		}
		try{
			return !userRepo.existsByEmail(email);
		}catch(Exception e){
			return true;
		}
	}
}
