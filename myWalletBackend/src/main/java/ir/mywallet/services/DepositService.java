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
	public void depositToWallet(Wallet wallet,long amount){
		long walletBalance = wallet.getWBalance();
		long finalBalance = walletBalance + amount;
		wallet.setWBalance(finalBalance);
		walletService.updateWallet(wallet);
	}
	
	
	@Transactional
	public void depositToAccount(Account account,long amount){
		long walletBalance = account.getAccBalance();
		long finalBalance = walletBalance + amount;
		account.setAccBalance(finalBalance);
		accountService.updateAccount(account);
	}
	
	@Transactional
	public Deposit recordDeposit(Deposit deposit){
		return depositRepo.save(deposit);
	}
	

	
	
}
