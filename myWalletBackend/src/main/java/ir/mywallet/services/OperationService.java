package ir.mywallet.services;

import ir.mywallet.dto.ChargeReq;
import ir.mywallet.dto.Responses;
import ir.mywallet.dto.TransferReq;
import ir.mywallet.model.*;
import ir.mywallet.repository.AccountRepo;
import ir.mywallet.repository.UserRepo;
import ir.mywallet.repository.WalletRepo;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service

public class OperationService {
	Logger logger = LoggerFactory.getLogger(OperationService.class);
	@Autowired
	private WithdrawalService withdrawalService;
	@Autowired
	private DepositService depositService;
	@Autowired
	private AccountRepo accountRepo;
	@Autowired
	private WalletRepo walletRepo;
	
	@Autowired
	private WalletService walletService;
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private UserService userService;
	
	@Transactional
	public Responses operationCharge(ChargeReq req){
		Account fromAccount = accountRepo.findById(req.getFromAccount().getId()).get();
		Wallet toWallet = walletRepo.findById(req.getToWallet().getId()).get();
		Long amount = req.getAmount();
		String refNumber = this.createRefNumber(fromAccount.getAccNumber());
		
		Responses withdrawalRes = withdrawalService.withdrawalFromAccount(fromAccount,amount);
		if(withdrawalRes.getStatusType() == "error"){
			return withdrawalRes;
		}
		Responses depositRes = depositService.depositToWallet(toWallet,amount);
		if(depositRes.getStatusType() == "error"){
			return depositRes;
		}
		
		Deposit deposit = new Deposit(amount,toWallet,refNumber,req.getDescription());
		depositService.recordDeposit(deposit);
		
		Responses res = new Responses();
		Map<String,List<Object>> msg = new HashMap<>();
		res.setStatusType("success");
		res.setTimestamp(new Date());
		res.setStatusCode(200);
		msg.put("success",new ArrayList<>(List.of("با موفقیت کیف پول شما شارژ شد")));
		res.setMessages(msg);
		return res;
	}
	
	
	@Transactional
	public Responses operationTransfer(TransferReq req){
		Wallet fromWallet = walletRepo.findById(req.getFromWallet().getId()).get();
		Account toAccount = accountRepo.findByaccountNumber(req.getToAccount().getAccNumber());
		Long amount = req.getAmount();
		String refNumber = this.createRefNumber(toAccount.getAccNumber());
		Responses withdrawalRes = withdrawalService.withdrawalFromWallet(fromWallet,amount);
		if(withdrawalRes.getStatusType() == "error"){
			return withdrawalRes;
		}
		Responses depositRes = depositService.depositToAccount(toAccount,amount);
		if(depositRes.getStatusType() == "error"){
			return depositRes;
		}
		
		Withdrawal withdrawal = new Withdrawal(amount,fromWallet,refNumber,req.getDescription());
		withdrawalService.recordWithdrawal(withdrawal);
		
		
		Responses res = new Responses();
		Map<String,List<Object>> msg = new HashMap<>();
		res.setStatusType("success");
		res.setTimestamp(new Date());
		res.setStatusCode(200);
		msg.put("success",new ArrayList<>(List.of("انتقال از کیف پول با موفقیت انجام شد")));
		res.setMessages(msg);
		return res;
	}
	
	
	@Transactional
	public Responses createUser(User req){
		User user = userService.createUser(req);
		Responses res = new Responses();
		Map<String,List<Object>> msg = new HashMap<>();
		Wallet wallet = new Wallet();
		wallet.setWBalance(0L);
		wallet.setUser(user);
		Wallet createWallet = walletService.createWallet(wallet);
		userRepo.updateUserById(user.getId(),createWallet.getId());
		Boolean checkUserIsPresent = userRepo.findById(user.getId()).isPresent();
		int userId = 0;
		if(checkUserIsPresent){
			userId = userRepo.findById(user.getId()).get().getId();
		}
		res.setStatusCode(201);
		res.setStatusType("success");
		msg.put("success",new ArrayList<>(List.of("با موفقعیت کاربر ساخته شد")));
		msg.put("userId",new ArrayList<>(List.of(userId)));
		res.setMessages(msg);
		res.setTimestamp(new Date());
		return res;
	}
	
	
	private String createRefNumber(String accountNumber){
		Instant instant = Instant.now();
		Long i = instant.toEpochMilli();
		Long refNumber = Long.valueOf(accountNumber + i);
		String ref = refNumber.toString();
		return ref;
	}
	
}
