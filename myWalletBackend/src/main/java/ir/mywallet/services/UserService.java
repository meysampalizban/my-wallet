package ir.mywallet.services;

import ir.mywallet.dto.Responses;
import ir.mywallet.model.User;
import ir.mywallet.model.Wallet;
import ir.mywallet.repository.UserRepo;
import ir.mywallet.services.jwt.JWTService;
import ir.mywallet.validation.ExceptionErrors;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
	private final Logger LOG = LoggerFactory.getLogger(UserService.class);
	private final UserRepo userRepo;
	private final WalletService walletService;
	private final JWTService jwtService;
	
	@Autowired
	public UserService(UserRepo userRepo,WalletService walletService,JWTService jwtService){
		this.userRepo = userRepo;
		this.walletService = walletService;
		this.jwtService = jwtService;
	}
	
	public User getUserById(@NotNull int userId) throws ExceptionErrors{
		boolean checkIsPresent = userRepo.findById(userId).isPresent();
		if(!checkIsPresent){
			LOG.warn("user not found");
			Map<String,List<Object>> msg = new HashMap<>();
			msg.put("userNotFound",Arrays.asList("کاربری یافت نشد"));
			throw new ExceptionErrors(msg);
		}
		return userRepo.findById(userId).get();
	}
	
	@Transactional
	public Responses createUserAndWallet(User req) throws ExceptionErrors{
		Map<String,List<Object>> msg = new HashMap<>();
		this.checkExistsEmailOrPhoneNumberOrNationalCode(req);
		User user = this.createUser(req);
		boolean checkUserIsPresent = userRepo.findById(user.getId()).isPresent();
		if(checkUserIsPresent){
			// created wallet
			Wallet createWallet = walletService.createWalletByUser(user);
			//update user add wallet id to user
			userRepo.updateWallet(user.getId(),createWallet.getId());
			// create token with user id
			String sUserId = String.valueOf(user.getId());
			String _token = this.jwtService.createToken(sUserId);
			userRepo.updateToken(user.getId(),_token);
			// add success messages
			msg.put("success",new ArrayList<>(List.of("با موفقعیت کاربر ساخته شد")));
			msg.put("userId",new ArrayList<>(List.of(user.getId())));
			msg.put("_token",new ArrayList<>(List.of(_token)));
			return this.successResponse(msg);
			
		}
		// error
		LOG.error("error in crete user and wallet");
		msg.put("error",new ArrayList<>(List.of("خطا در هنگام ایجاد کیف پول رخ داد")));
		throw new ExceptionErrors(msg);
	}
	@Transactional
	public User createUser(User user){
		return userRepo.save(user);
	}
	private void checkExistsEmailOrPhoneNumberOrNationalCode(User user) throws ExceptionErrors{
		boolean checkExistsEmail = checkExistsEmail(user.getEmail());
		boolean checkExistsNationalCode = checkExistsNationalCode(user.getNationalCode());
		boolean checkExistsPhoneNumber = checkExistsPhoneNumber(user.getPhoneNumber());
		Map<String,List<Object>> msg = new HashMap<>();
		
		if(checkExistsEmail || checkExistsPhoneNumber || checkExistsNationalCode){
			if(checkExistsEmail){
				msg.put("email",new ArrayList<>(List.of("ایمیل تکراری است")));
				throw new ExceptionErrors(msg);
			}
			if(checkExistsPhoneNumber){
				msg.put("phoneNumber",new ArrayList<>(List.of("شماره تلفن تکراری است")));
				throw new ExceptionErrors(msg);
			}
			if(checkExistsNationalCode){
				msg.put("nationalCode",new ArrayList<>(List.of("کد ملی تکراری است")));
				throw new ExceptionErrors(msg);
			}
			
		}
	}
	
	private Responses successResponse(Map<String,List<Object>> msg){
		Responses res = new Responses();
		res.setStatusCode(200);
		res.setStatusType("success");
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
			LOG.error(e.getMessage());
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
			LOG.error(e.getMessage());
			return true;
		}
	}
	
	private Boolean checkExistsPhoneNumber(String phoneNumber){
		if(phoneNumber == null || phoneNumber.isEmpty()){
			return false;
		}
		try{
			return this.userRepo.existsByPhoneNumber(phoneNumber);
		}catch(Exception e){
			LOG.error(e.getMessage());
			return true;
		}
	}
	
	
	
	
	
}
