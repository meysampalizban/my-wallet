package ir.mywallet.validation;

import ir.mywallet.repository.UserRepo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ExsistPhoneNumberValidation implements ConstraintValidator<IsExsistPhoneNumber,String> {
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public boolean isValid(String phoneNumber,ConstraintValidatorContext context){
		if(phoneNumber == null){
			return true;
		}
		try{
			return !this.userRepo.existsByPhoneNumber(phoneNumber);
		}catch(Exception e){
			return true;
		}
	}
}
