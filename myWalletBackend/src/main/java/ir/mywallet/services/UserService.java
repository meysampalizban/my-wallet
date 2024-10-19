package ir.mywallet.services;

import com.github.eloyzone.jalalicalendar.JalaliDate;
import com.github.eloyzone.jalalicalendar.JalaliDateFormatter;
import ir.mywallet.dto.Responses;
import ir.mywallet.model.User;
import ir.mywallet.model.Wallet;
import ir.mywallet.repository.UserRepo;
import ir.mywallet.services.jalali.JalaliService;
import ir.mywallet.services.jwt.JWTService;
import ir.mywallet.validation.ExceptionErrors;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UserService {
	private final Logger LOG = LoggerFactory.getLogger(UserService.class);
	private final UserRepo userRepo;
	private final WalletService walletService;
	private final JWTService jwtService;
	private final JalaliService jalaliService;
	
	@Autowired
	public UserService(UserRepo userRepo,WalletService walletService,JWTService jwtService,JalaliService jalaliService){
		this.userRepo = userRepo;
		this.walletService = walletService;
		this.jwtService = jwtService;
		this.jalaliService = jalaliService;
	}
	
	public User getUserById(@NotNull int userId) throws ExceptionErrors{
		boolean checkIsPresent = userRepo.findById(userId).isPresent();
		if(!checkIsPresent){
			LOG.warn("user not found");
			Map<String,List<Object>> msg = new HashMap<>();
			msg.put("userNotFound",Arrays.asList("کاربری یافت نشد"));
			throw new ExceptionErrors(msg);
		}
		User user = userRepo.findById(userId).get();
		String brithDateGregorian = String.valueOf(user.getBirthDate());
		JalaliDate brithDatejalali = this.jalaliService.convertGregorianToJalali(brithDateGregorian);
		String brithDate = brithDatejalali.format(new JalaliDateFormatter("yyyy-mm-dd"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(brithDate,formatter);
		user.setBirthDate(date);
		return user;
		
	}
	
	
	@Transactional
	public Responses createUserAndWallet(User req) throws ExceptionErrors{
		Map<String,List<Object>> msg = new HashMap<>();
		this.checkExistsEmailOrPhoneNumberOrNationalCode(req);
		this.checkAgeUser(req.getBirthDate());
		this.checkSexUserForMilitaryStatus(req.getSex(),req.getMilitaryStatus());
		User user = this.changeBrithDateToGregorian(req);
		User createdUser = this.createUser(user);
		boolean checkUserIsPresent = userRepo.findById(createdUser.getId()).isPresent();
		if(checkUserIsPresent){
			// created wallet
			Wallet createWallet = walletService.createWalletByUser(createdUser);
			//update createdUser add wallet id to createdUser
			userRepo.updateWallet(createdUser.getId(),createWallet.getId());
			// create token with createdUser id
			String sUserId = String.valueOf(createdUser.getId());
			String _token = this.jwtService.createToken(sUserId);
			userRepo.updateToken(createdUser.getId(),_token);
			// add success messages
			msg.put("success",new ArrayList<>(List.of("با موفقعیت کاربر ساخته شد")));
			msg.put("userId",new ArrayList<>(List.of(createdUser.getId())));
			msg.put("_token",new ArrayList<>(List.of(_token)));
			return this.successResponse(msg);
		}
		// error
		LOG.error("error in crete createdUser and wallet");
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
	
	private void checkSexUserForMilitaryStatus(String sex,String militaryStatus) throws ExceptionErrors{
		if(sex.equals("man") && (militaryStatus == null || militaryStatus.isEmpty() || militaryStatus.equals("none"))){
			Map<String,List<Object>> msg = new HashMap<>();
			msg.put("militaryStatus",Arrays.asList("خدمت سربازی را مشخص کنید"));
			throw new ExceptionErrors(msg);
		}
	}
	
	private void checkAgeUser(@NotBlank LocalDate birthDate) throws ExceptionErrors{
		String date = String.valueOf(birthDate);
		LocalDate miladiDate = this.jalaliService.convertJalaliToGregorian(date);
		int age = Period.between(miladiDate,LocalDate.now()).getYears();
		if(age <= 18){
			Map<String,List<Object>> msg = new HashMap<>();
			msg.put("birthDate",Arrays.asList("سن کاربر کمتر از 18 سال است و امکان ثبت نام وجود ندارد"));
			throw new ExceptionErrors(msg);
		}
	}
	
	private User changeBrithDateToGregorian(User user){
		String brithDate = String.valueOf(user.getBirthDate());
		LocalDate miladiDate = this.jalaliService.convertJalaliToGregorian(brithDate);
		user.setBirthDate(miladiDate);
		return user;
	}
}
