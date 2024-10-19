package ir.mywallet.services;

import ir.mywallet.model.User;
import ir.mywallet.model.Wallet;
import ir.mywallet.repository.WalletRepo;
import ir.mywallet.validation.ExceptionErrors;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WalletService {
	
	private final WalletRepo walletRepo;
	
	@Autowired
	WalletService(WalletRepo walletRepo){
		this.walletRepo = walletRepo;
	}
	
	public Wallet getWallet(Wallet wallet){
		Map<String,List<Object>> msg = new HashMap<>();
		msg.put("wallet",Arrays.asList(" کبف پول یافت نشد"));
		return walletRepo.findById(wallet.getId()).orElseThrow(()->new ExceptionErrors(msg));
	}
	
	@Transactional
	public Wallet createWalletByUser(User user){
		Wallet wallet = new Wallet();
		wallet.setWBalance(0L);
		wallet.setUser(user);
		return this.createWallet(wallet);
	}
	
	@Transactional
	public Wallet updateWallet(Wallet wallet){
		return walletRepo.save(wallet);
	}
	@Transactional
	protected Wallet createWallet(Wallet wallet){
		return walletRepo.save(wallet);
	}
}
