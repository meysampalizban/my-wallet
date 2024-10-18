package ir.mywallet.services;

import ir.mywallet.dto.ChargeReq;
import ir.mywallet.dto.Responses;
import ir.mywallet.dto.TransferReq;
import ir.mywallet.model.Account;
import ir.mywallet.model.Deposit;
import ir.mywallet.model.Wallet;
import ir.mywallet.model.Withdrawal;
import ir.mywallet.repository.AccountRepo;
import ir.mywallet.repository.WalletRepo;
import ir.mywallet.validation.ExceptionErrors;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service

public class OperationService {
	private final Logger LOG = LoggerFactory.getLogger(OperationService.class);
	
	private final WithdrawalService withdrawalService;
	private final DepositService depositService;
	private final AccountRepo accountRepo;
	private final WalletRepo walletRepo;
	
	@Autowired
	public OperationService(WithdrawalService withdrawalService,DepositService depositService,AccountRepo accountRepo,WalletRepo walletRepo){
		this.withdrawalService = withdrawalService;
		this.depositService = depositService;
		this.accountRepo = accountRepo;
		this.walletRepo = walletRepo;
	}
	
	
	@Transactional
	public Responses operationCharge(ChargeReq req){
		
		Map<String,List<Object>> msg = new HashMap<>();
		boolean checkIsExistsAccount = accountRepo.findById(req.getFromAccount().getId()).isPresent();
		if(!checkIsExistsAccount){
			msg.put("error",new ArrayList<>(List.of(" حساب کاربر یافت نشد")));
			throw new ExceptionErrors(msg);
		}
		Account fromAccount = accountRepo.findById(req.getFromAccount().getId()).get();
		
		Wallet toWallet = walletRepo.findById(req.getToWallet().getId()).get();
		
		String refNumber = this.createRefNumber(fromAccount.getAccNumber());
		
		withdrawalService.withdrawalFromAccount(fromAccount,req.getAmount());
		depositService.depositToWallet(toWallet,req.getAmount());
		
		Deposit deposit = new Deposit(req.getAmount(),toWallet,refNumber,req.getDescription());
		depositService.recordDeposit(deposit);
		
		
		msg.put("success",new ArrayList<>(List.of("با موفقیت کیف پول شما شارژ شد")));
		return this.successResponse(msg);
	}
	
	
	@Transactional
	public Responses operationTransfer(TransferReq req) throws ExceptionErrors{
		Map<String,List<Object>> msg = new HashMap<>();
		boolean checkIsExistsWallet = walletRepo.findById(req.getFromWallet().getId()).isPresent();
		if(!checkIsExistsWallet){
			msg.put("error",new ArrayList<>(List.of("کبف پول یافت نشد")));
			throw new ExceptionErrors(msg);
		}
		Wallet fromWallet = walletRepo.findById(req.getFromWallet().getId()).get();
		
		Account toAccount = accountRepo.findByaccountNumber(req.getToAccount().getAccNumber());
		
		String refNumber = this.createRefNumber(toAccount.getAccNumber());
		
		withdrawalService.withdrawalFromWallet(fromWallet,req.getAmount();
		depositService.depositToAccount(toAccount,req.getAmount();
		
		Withdrawal withdrawal = new Withdrawal(req.getAmount(),fromWallet,refNumber,req.getDescription());
		withdrawalService.recordWithdrawal(withdrawal);
		
		msg.put("success",new ArrayList<>(List.of("انتقال از کیف پول با موفقیت انجام شد")));
		return this.successResponse(msg);
	}
	
	private String createRefNumber(String accountNumber){
		Instant instant = Instant.now();
		long i = instant.toEpochMilli();
		Long refNumber = (Long) Long.valueOf(accountNumber + i);
		return refNumber.toString();
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
