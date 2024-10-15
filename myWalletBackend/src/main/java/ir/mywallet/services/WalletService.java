package ir.mywallet.services;

import ir.mywallet.model.Wallet;
import ir.mywallet.repository.WalletRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
	@Autowired
	private WalletRepo walletRepo;
	
	@Transactional
	public Wallet createWallet(Wallet wallet){
		Wallet res=walletRepo.save(wallet);
		return res;
	}
}
