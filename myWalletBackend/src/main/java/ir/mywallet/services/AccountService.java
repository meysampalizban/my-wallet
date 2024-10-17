package ir.mywallet.services;

import ir.mywallet.dto.Responses;
import ir.mywallet.model.Account;
import ir.mywallet.repository.AccountRepo;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {
	
	private final Logger logger = LoggerFactory.getLogger(AccountService.class);
	
	@Autowired
	private AccountRepo accountRepo;
	
	@Transactional
	public Responses createAccount(Account account){
		Responses res = new Responses();
		Map<String,List<Object>> msg = new HashMap<>();
		boolean exsistShabaNumber = this.checkExsistShabaNumber(account.getShabaNumber());
		boolean exsistAccNumber = this.checkExsistAccnumber(account.getAccNumber());
		if(exsistShabaNumber || exsistAccNumber){
			res.setStatusType("error");
			res.setStatusCode(400);
			res.setTimestamp(new Date());
			List<Object> msgIsValidExsist = new ArrayList<>();
			if(exsistAccNumber){
				msgIsValidExsist.add("شماره حساب تکراری است");
			}
			if(exsistShabaNumber){
				msgIsValidExsist.add("شماره شبا تکراری است");
			}
			msg.put("existAccnumberAndShabaNumbber",msgIsValidExsist);
			res.setMessages(msg);
			return res;
		}
		accountRepo.save(account);
		res.setStatusType("success");
		res.setStatusCode(201);
		res.setTimestamp(new Date());
		msg.put("success",new ArrayList<>(List.of("افتتاح حساب با موفقیت انجام شد")));
		res.setMessages(msg);
		return res;
	}
	
	private Boolean checkExsistAccnumber(String AccNumber){
		if(AccNumber == null || AccNumber.isEmpty()){
			return false;
		}
		try{
			return this.accountRepo.existsByAccNumber(AccNumber);
		}catch(Exception e){
			logger.error(e.getMessage());
			return true;
		}
	}
	
	private Boolean checkExsistShabaNumber(String shabaNumber){
		if(shabaNumber == null || shabaNumber.isEmpty()){
			return false;
		}
		try{
			return this.accountRepo.existsByShabaNumber(shabaNumber);
		}catch(Exception e){
			logger.error(e.getMessage());
			return true;
		}
	}
	
	public List<Account> getAccounts(int userId){
		List<Account> account = new ArrayList<>();
		account = accountRepo.getAllByUserId(userId);
		return account;
	}
	
	public Responses deleteaccount(int accId){
		accountRepo.deleteById(accId);
		Responses res = new Responses();
		Map<String,List<Object>> msg = new HashMap<>();
		res.setStatusType("success");
		res.setStatusCode(201);
		res.setTimestamp(new Date());
		msg.put("success",new ArrayList<>(List.of("با موفقیعت حذف شد")));
		res.setMessages(msg);
		return res;
	}
	
}
