package ir.mywallet.controller;

import ir.mywallet.dto.ChargeReq;
import ir.mywallet.dto.Responses;
import ir.mywallet.dto.TransferReq;
import ir.mywallet.model.Deposit;
import ir.mywallet.model.Withdrawal;
import ir.mywallet.services.DepositService;
import ir.mywallet.services.OperationService;
import ir.mywallet.services.WithdrawalService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200",
		allowCredentials = "true", maxAge = 3000L, methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE},
		allowedHeaders = {HttpHeaders.AUTHORIZATION,HttpHeaders.ACCEPT,HttpHeaders.CONTENT_TYPE,"userId"})
@RequestMapping("/api/transaction")
public class TransactionController {
	
	private final OperationService operationService;
	private final DepositService depositService;
	private final WithdrawalService withdrawalService;
	
	@Autowired
	public TransactionController(OperationService operationService,DepositService depositService,WithdrawalService withdrawalService){
		this.operationService = operationService;
		this.depositService = depositService;
		this.withdrawalService = withdrawalService;
	}
	
	// گرفتن لیست برداشت های کیف پول
	@GetMapping(path = "/getwithdrawalhistory/{walletId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Withdrawal> getWithdrawalHistory(@NotNull @PathVariable("walletId") int walletId){
		return this.withdrawalService.getWithdrawalByWalletId(walletId);
	}
	
	// گرفتن لیست واریز ها به کیف پول
	@GetMapping(path = "/getdeposithistory/{walletId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Deposit> getDepositHistory(@NotNull @PathVariable("walletId") int walletId){
		return this.depositService.getDepositByWalletId(walletId);
	}
	
	// شارژ کیف پول
	@PostMapping(path = "/chargewallet", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Responses> depositToWallet(@Valid @RequestBody ChargeReq req){
		Responses res = operationService.operationCharge(req);
		return new ResponseEntity<>(res,HttpStatus.CREATED);
	}
	
	// برداشت از کیف پول
	@PostMapping(path = "/transferfromwallet", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Responses> withdrawalOperation(@Valid @RequestBody TransferReq req){
		Responses res = operationService.operationTransfer(req);
		return new ResponseEntity<>(res,new HttpHeaders(),HttpStatus.CREATED);
	}
	
	
}
