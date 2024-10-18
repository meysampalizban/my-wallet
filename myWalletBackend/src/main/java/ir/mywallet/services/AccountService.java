package ir.mywallet.services;

import ir.mywallet.dto.Responses;
import ir.mywallet.model.Account;
import ir.mywallet.repository.AccountRepo;
import ir.mywallet.validation.ExceptionErrors;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {
	
	private final Logger LOG = LoggerFactory.getLogger(AccountService.class);
	private final AccountRepo accountRepo;
	
	@Autowired
	public AccountService(AccountRepo accountRepo){
		this.accountRepo = accountRepo;
	}
	
	public List<Account> getAccounts(@NotNull int userId){
		return accountRepo.getAllByUserId(userId);
	}
	
	@Transactional
	public Responses createAccount(Account account){
		Map<String,List<Object>> msg = new HashMap<>();
		this.checkShabaNumber(account.getShabaNumber());
		this.checkAccNumber(account.getAccNumber());
		accountRepo.save(account);
		msg.put("success",new ArrayList<>(List.of("افتتاح حساب با موفقیت انجام شد")));
		return this.successResponse(msg);
	}
	
	@Transactional
	public Account updateAccount(Account account){
		return accountRepo.save(account);
	}
	
	@Transactional
	public Responses deleteAccount(@NotNull int accId){
		accountRepo.deleteById(accId);
		Map<String,List<Object>> msg = new HashMap<>();
		msg.put("success",new ArrayList<>(List.of("با موفقیعت حذف شد")));
		return this.successResponse(msg);
	}
	
	private Responses successResponse(Map<String,List<Object>> msg){
		Responses res = new Responses();
		res.setStatusCode(200);
		res.setStatusType("success");
		res.setMessages(msg);
		res.setTimestamp(new Date());
		return res;
	}
	
	private Boolean checkExsistAccnumber(String AccNumber){
		if(AccNumber == null || AccNumber.isEmpty()){
			return false;
		}
		try{
			return this.accountRepo.existsByAccNumber(AccNumber);
		}catch(Exception e){
			LOG.error(e.getMessage());
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
			LOG.error(e.getMessage());
			return true;
		}
	}
	
	private void checkShabaNumber(@NotNull String shabaNumber) throws ExceptionErrors{
		boolean exsistShabaNumber = this.checkExsistShabaNumber(shabaNumber);
		if(exsistShabaNumber){
			Map<String,List<Object>> msg = new HashMap<>();
			msg.put("shabaNumber",Arrays.asList("شماره شبا تکراری است"));
			throw new ExceptionErrors(msg);
		}
	}
	
	private void checkAccNumber(@NotNull String accountNumber) throws ExceptionErrors{
		boolean exsistAccNumber = this.checkExsistAccnumber(accountNumber);
		if(exsistAccNumber){
			Map<String,List<Object>> msg = new HashMap<>();
			msg.put("accountNumber",Arrays.asList("شماره حساب تکراری است"));
			throw new ExceptionErrors(msg);
		}
	}
	
	
}
