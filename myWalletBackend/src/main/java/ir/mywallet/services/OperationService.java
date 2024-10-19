package ir.mywallet.services;

import ir.mywallet.dto.ChargeReq;
import ir.mywallet.dto.Responses;
import ir.mywallet.dto.TransferReq;
import ir.mywallet.model.Account;
import ir.mywallet.model.Deposit;
import ir.mywallet.model.Wallet;
import ir.mywallet.model.Withdrawal;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service

public class OperationService {
	
	private final WithdrawalService withdrawalService;
	private final DepositService depositService;
	private final AccountService accountService;
	private final WalletService walletService;
	
	@Autowired
	public OperationService(WithdrawalService withdrawalService,DepositService depositService,AccountService accountService,WalletService walletService){
		this.withdrawalService = withdrawalService;
		this.depositService = depositService;
		this.accountService = accountService;
		this.walletService = walletService;
	}
	
	
	@Transactional
	public Responses operationCharge(ChargeReq req){
		long amount = req.getAmount();
		Account fromAccount = this.accountService.getAccount(req.getFromAccount());
		
		Wallet toWallet = this.walletService.getWallet(req.getToWallet());
		
		String refNumber = this.createRefNumber(fromAccount.getAccNumber());
		
		withdrawalService.withdrawalFromAccount(fromAccount,amount);
		
		depositService.depositToWallet(toWallet,amount);
		
		Deposit deposit = new Deposit(amount,toWallet,refNumber,req.getDescription());
		depositService.recordDeposit(deposit);
		
		Map<String,List<Object>> msg = new HashMap<>();
		msg.put("success",new ArrayList<>(List.of("با موفقیت کیف پول شما شارژ شد")));
		return this.successResponse(msg);
	}
	
	
	@Transactional
	public Responses operationTransfer(TransferReq req){
		long amount = req.getAmount();
		Wallet fromWallet = this.walletService.getWallet(req.getFromWallet());
		
		Account toAccount = this.accountService.getAccountByAccountNumber(req.getToAccount().getAccNumber());
		
		String refNumber = this.createRefNumber(toAccount.getAccNumber());
		
		withdrawalService.withdrawalFromWallet(fromWallet,amount);
		depositService.depositToAccount(toAccount,amount);
		
		Withdrawal withdrawal = new Withdrawal(amount,fromWallet,refNumber,req.getDescription());
		withdrawalService.recordWithdrawal(withdrawal);
		
		Map<String,List<Object>> msg = new HashMap<>();
		msg.put("success",new ArrayList<>(List.of("انتقال از کیف پول با موفقیت انجام شد")));
		return this.successResponse(msg);
	}
	
	private String createRefNumber(String accountNumber){
		Instant instant = Instant.now();
		long i = instant.toEpochMilli();
		long acc = Long.valueOf(accountNumber);
		long refNumber = i + acc;
		return String.valueOf(refNumber);
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
