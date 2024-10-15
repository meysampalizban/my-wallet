package ir.mywallet.services;

import ir.mywallet.dto.Responses;
import ir.mywallet.model.Account;
import ir.mywallet.model.Wallet;
import ir.mywallet.model.Withdrawal;
import ir.mywallet.repository.AccountRepo;
import ir.mywallet.repository.WalletRepo;
import ir.mywallet.repository.WithdrawalRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WithdrawalService {
	@Autowired
	private WithdrawalRepo withdrawalRepo;
	@Autowired
	private AccountRepo accountRepo;
	@Autowired
	private WalletRepo walletRepo;
	
	public List<Withdrawal> getWithdrawalByWalletId(int wallet_id){
		return withdrawalRepo.findAllByWalletId(wallet_id);
	}
	
	@Transactional
	public Responses withdrawalFromAccount(Account account,Long amount){
		Responses res = new Responses();
		Map<String,List<Object>> msg = new HashMap<>();
		int id = account.getId();
		Long balance = account.getAccBalance();
		
		if(isCheckAccountBalance(balance,amount)){
			Long finalBalance = balance - amount;
			accountRepo.updateBalance(id,finalBalance);
			res.setStatusCode(200);
			res.setTimestamp(new Date());
			res.setStatusType("success");
			msg.put("success",new ArrayList<>(List.of("برداشت با موفقیت انجام شد")));
			res.setMessages(msg);
			return res;
		}
		res.setStatusCode(420);
		res.setTimestamp(new Date());
		res.setStatusType("error");
		msg.put("error",new ArrayList<>(List.of("موجودی کافی نیست")));
		res.setMessages(msg);
		return res;
	}
	
	@Transactional
	public Responses withdrawalFromWallet(Wallet wallet,Long amount){
		Responses res = new Responses();
		Map<String,List<Object>> msg = new HashMap<>();
		int id = wallet.getId();
		Long balance = wallet.getWBalance();
		
		if(isCheckWalletBalance(balance,amount)){
			Long finalBalance = balance - amount;
			walletRepo.updateBalance(id,finalBalance);
			res.setStatusCode(200);
			res.setTimestamp(new Date());
			res.setStatusType("sueesss");
			msg.put("success",new ArrayList<>(List.of("برداشت با موفقیت انجام شد")));
			
			res.setMessages(msg);
			return res;
		}
		res.setStatusCode(420);
		res.setTimestamp(new Date());
		res.setStatusType("error");
		msg.put("error",new ArrayList<>(List.of("موجودی کافی نیست")));
		res.setMessages(msg);
		return res;
	}
	
	
	@Transactional
	public Withdrawal recordWithdrawal(Withdrawal withdrawal){
		Withdrawal res = withdrawalRepo.save(withdrawal);
		return res;
	}
	
	private Boolean isCheckAccountBalance(Long balanceAccount,Long amount){
		Long isCheckAmount = balanceAccount - amount;
		if(isCheckAmount >= 50000){
			return true;
		}
		return false;
	}
	
	private Boolean isCheckWalletBalance(Long balanceWallet,Long amount){
		Long isCheckAmount = balanceWallet - amount;
		if(isCheckAmount >= 0){
			return true;
		}
		return false;
	}
}
