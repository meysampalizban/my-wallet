package ir.mywallet.services;

import ir.mywallet.dto.Responses;
import ir.mywallet.model.User;
import ir.mywallet.model.Wallet;
import ir.mywallet.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
	private final Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private WalletService walletService;
	
	@Autowired
	private JWTService jwtService;
	
	@Transactional
	public Responses createUserAndWallet(User req){
		Responses res = new Responses();
		Map<String,List<Object>> msg = new HashMap<>();
		boolean checkExistsEmail = checkExistsEmail(req.getEmail());
		boolean checkExistsNationalCode = checkExistsNationalCode(req.getNationalCode());
		boolean checkExistsPhoneNumber = checkExistsPhoneNumber(req.getPhoneNumber());
		if(checkExistsEmail || checkExistsPhoneNumber || checkExistsNationalCode){
			List<Object> msgExists = new ArrayList<>();
			res.setStatusCode(400);
			res.setStatusType("error");
			res.setTimestamp(new Date());
			if(checkExistsEmail){
				msgExists.add("ایمیل تکراری است");
			}
			if(checkExistsNationalCode){
				msgExists.add("کد ملی تکراری است");
			}
			if(checkExistsPhoneNumber){
				msgExists.add("شماره تلفن تکراری است");
			}
			msg.put("error",msgExists);
			res.setMessages(msg);
			return res;
		}
		
		User user = this.createUser(req);
		boolean checkUserIsPresent = userRepo.findById(user.getId()).isPresent();
		if(checkUserIsPresent){
			
			Wallet wallet = new Wallet();
			wallet.setWBalance(0L);
			wallet.setUser(user);
			Wallet createWallet = walletService.createWallet(wallet);
			userRepo.updateUserById(user.getId(),createWallet.getId());
			String _token = this.jwtService.createToken(user.getEmail());
			res.setStatusCode(201);
			res.setStatusType("success");
			msg.put("success",new ArrayList<>(List.of("با موفقعیت کاربر ساخته شد")));
			msg.put("userId",new ArrayList<>(List.of(user.getId())));
			msg.put("_token",new ArrayList<>(List.of(_token)));
			res.setMessages(msg);
			res.setTimestamp(new Date());
			return res;
		}
		res.setStatusCode(400);
		res.setStatusType("error");
		msg.put("error",new ArrayList<>(List.of("خطا در هنگام ایجاد کیف پول رخ داد")));
		res.setMessages(msg);
		res.setTimestamp(new Date());
		return res;
		
	}
	
	
	private Boolean checkExistsEmail(String email){
		if(email == null || email.isEmpty()){
			return false;
		}
		try{
			return this.userRepo.existsByEmail(email);
		}catch(Exception e){
			logger.error(e.getMessage());
			return true;
		}
	}
	
	private Boolean checkExistsNationalCode(String nationalCode){
		if(nationalCode == null || nationalCode.isEmpty()){
			return false;
		}
		try{
			return this.userRepo.existsByNationalCode(nationalCode);
		}catch(Exception e){
			logger.error(e.getMessage());
			return true;
		}
	}
	
	private Boolean checkExistsPhoneNumber(String phoneNumer){
		if(phoneNumer == null || phoneNumer.isEmpty()){
			return false;
		}
		try{
			return this.userRepo.existsByPhoneNumber(phoneNumer);
		}catch(Exception e){
			logger.error(e.getMessage());
			return true;
		}
	}
	
	
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
