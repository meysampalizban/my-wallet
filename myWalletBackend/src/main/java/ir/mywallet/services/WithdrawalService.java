package ir.mywallet.services;

import ir.mywallet.model.Account;
import ir.mywallet.model.Wallet;
import ir.mywallet.model.Withdrawal;
import ir.mywallet.repository.WithdrawalRepo;
import ir.mywallet.validation.ExceptionErrors;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WithdrawalService {
	private final WithdrawalRepo withdrawalRepo;
	private final AccountService accountService;
	private final WalletService walletService;
	
	@Autowired
	public WithdrawalService(WithdrawalRepo withdrawalRepo,AccountService accountService,WalletService walletService){
		this.withdrawalRepo = withdrawalRepo;
		this.accountService = accountService;
		this.walletService = walletService;
	}
	
	public List<Withdrawal> getWithdrawalByWalletId(@NotNull  int wallet_id){
		return withdrawalRepo.findAllByWalletId(wallet_id);
	}
	
	@Transactional
	public void withdrawalFromAccount(Account account,long amount){
		long balance = account.getAccBalance();
		this.isCheckAccountBalance(balance,amount);
		long finalBalance = balance - amount;
		account.setAccBalance(finalBalance);
		accountService.updateAccount(account);
	}
	
	@Transactional
	public void withdrawalFromWallet(Wallet wallet,long amount){
		long balance = wallet.getWBalance();
		this.isCheckWalletBalance(balance,amount);
		long finalBalance = balance - amount;
		wallet.setWBalance(finalBalance);
		walletService.updateWallet(wallet);
	}
	
	
	@Transactional
	public Withdrawal recordWithdrawal(Withdrawal withdrawal){
		return withdrawalRepo.save(withdrawal);
	}
	
	private void isCheckAccountBalance(long balanceAccount,long amount) throws ExceptionErrors{
		long isCheckAmount = balanceAccount - amount;
		if(isCheckAmount <= 50000){
			Map<String,List<Object>> msg = new HashMap<>();
			msg.put("error",new ArrayList<>(List.of("موجودی کافی نیست")));
			throw new ExceptionErrors(msg);
		}
	}
	
	private void isCheckWalletBalance(long balanceWallet,long amount) throws ExceptionErrors{
		long isCheckAmount = balanceWallet - amount;
		if(isCheckAmount <= 0){
			Map<String,List<Object>> msg = new HashMap<>();
			msg.put("error",new ArrayList<>(List.of("موجودی کافی نیست")));
			throw new ExceptionErrors(msg);
		}
	}
	
	
}
