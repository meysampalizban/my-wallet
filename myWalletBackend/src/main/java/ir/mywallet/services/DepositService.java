package ir.mywallet.services;

import ir.mywallet.dto.Responses;
import ir.mywallet.model.Account;
import ir.mywallet.model.Deposit;
import ir.mywallet.model.Wallet;
import ir.mywallet.repository.AccountRepo;
import ir.mywallet.repository.DepositRepo;
import ir.mywallet.repository.WalletRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DepositService {
	@Autowired
	private DepositRepo depositRepo;
	@Autowired
	private AccountRepo accountRepo;
	@Autowired
	private WalletRepo walletRepo;
	
	public List<Deposit> getDepositByWalletId(int wallet_id){
		
		return depositRepo.findAllByWalletId(wallet_id);
	}
	
	@Transactional
	public Responses depositToWallet(Wallet wallet,Long amount){
		Responses res = new Responses();
		Map<String,List<Object>> msg = new HashMap<>();
		int id = wallet.getId();
		Long walletBalance = wallet.getWBalance();
		Long finalBalance = walletBalance + amount;
		walletRepo.updateBalance(id,finalBalance);
		res.setStatusCode(200);
		res.setTimestamp(new Date());
		res.setStatusType("sueesss");
		msg.put("success",new ArrayList<>(List.of("واریز با موفقیت انجام شد")));
		res.setMessages(msg);
		return res;
	}
	
	@Transactional
	public Deposit recordDeposit(Deposit deposit){
		Deposit res = depositRepo.save(deposit);
		return res;
	}
	
	@Transactional
	public Responses depositToAccount(Account account,Long amount){
		Responses res = new Responses();
		Map<String,List<Object>> msg = new HashMap<>();
		int id = account.getId();
		Long walletBalance = account.getAccBalance();
		Long finalBalance = walletBalance + amount;
		accountRepo.updateBalance(id,finalBalance);
		res.setStatusCode(200);
		res.setTimestamp(new Date());
		res.setStatusType("sueesss");
		msg.put("success",new ArrayList<>(List.of("واریز با موفقیت انجام شد")));
		res.setMessages(msg);
		return res;
	}
	
	
}
