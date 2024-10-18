package ir.mywallet.services;

import ir.mywallet.dto.Responses;
import ir.mywallet.model.Account;
import ir.mywallet.model.Deposit;
import ir.mywallet.model.Wallet;
import ir.mywallet.repository.AccountRepo;
import ir.mywallet.repository.DepositRepo;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DepositService {
	
	private final DepositRepo depositRepo;
	private final WalletService walletService;
	private final AccountService accountService;
	
	@Autowired
	public DepositService(DepositRepo depositRepo,AccountRepo accountRepo,WalletService walletService,AccountService accountService){
		this.depositRepo = depositRepo;
		this.walletService = walletService;
		this.accountService = accountService;
	}
	
	public List<Deposit> getDepositByWalletId(@NotNull int walletId){
		return depositRepo.findAllByWalletId(walletId);
	}
	
	@Transactional
	public void depositToWallet(Wallet wallet,Long amount){
		Wallet upWallet = new Wallet();
		Long walletBalance = wallet.getWBalance();
		Long finalBalance = walletBalance + amount;
		upWallet.setWBalance(finalBalance);
		walletService.updateWallet(upWallet);
	}
	
	
	@Transactional
	public void depositToAccount(Account account,Long amount){
		Account upAccount = new Account();
		Long walletBalance = account.getAccBalance();
		Long finalBalance = walletBalance + amount;
		upAccount.setAccBalance(finalBalance);
		accountService.updateAccount(upAccount);
	}
	
	@Transactional
	public Deposit recordDeposit(Deposit deposit){
		return depositRepo.save(deposit);
	}
	
	private Responses successResponse(Map<String,List<Object>> msg){
		Responses res = new Responses();
		res.setStatusCode(200);
		res.setStatusType("success");
		res.setMessages(msg);
		res.setTimestamp(new Date());
		return res;
	}
	
	
}
