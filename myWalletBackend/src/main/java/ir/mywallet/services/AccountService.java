package ir.mywallet.services;

import ir.mywallet.dto.Responses;
import ir.mywallet.model.Account;
import ir.mywallet.repository.AccountRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepo accountRepo;
	
	@Transactional
	public Responses createAccount(Account account){
		accountRepo.save(account);
		Responses res = new Responses();
		Map<String,List<Object>> msg = new HashMap<>();
		res.setStatusType("success");
		res.setStatusCode(201);
		res.setTimestamp(new Date());
		msg.put("success",new ArrayList<>(List.of("افتتاح حساب با موفقیت انجام شد")));
		res.setMessages(msg);
		return res;
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
