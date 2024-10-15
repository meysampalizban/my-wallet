package ir.mywallet.services;

import ir.mywallet.model.User;
import ir.mywallet.repository.AccountRepo;
import ir.mywallet.repository.UserRepo;
import ir.mywallet.repository.WalletRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private AccountRepo accUser;
	@Autowired
	private WalletRepo walletRepo;
	
	
	@Transactional
	public User createUser(User user){
		User res = userRepo.save(user);
		return res;
	}
	
	
	public User getUser(int userId){
		User user = new User();
		boolean checkIsPresent = userRepo.findById(userId).isPresent();
		if(checkIsPresent){
			user = userRepo.findById(userId).get();
		}else{
			user.setId(0);
		}
		return user;
	}
	
	
}
