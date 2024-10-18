package ir.mywallet.services;

import ir.mywallet.model.User;
import ir.mywallet.model.Wallet;
import ir.mywallet.repository.WalletRepo;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
	
	private final WalletRepo walletRepo;
	
	@Autowired
	WalletService(WalletRepo walletRepo){
		this.walletRepo = walletRepo;
	}
	
	@Transactional
	@NotNull
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
	
	protected Wallet createWallet(Wallet wallet){
		return walletRepo.save(wallet);
	}
}
