package ir.mywallet.dto;

import ir.mywallet.model.Account;
import ir.mywallet.model.Wallet;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ChargeReq {
	@Valid
	private Account fromAccount;
	@Valid
	private Wallet toWallet;
	@NotNull(message = "مبلغ شارژ باید وارد شود")
	private Long amount;
	@Size(max = 60, message = "بیشترین طول متن توضیحات باید 60 کاراکتر باشد")
	private String description;
	
}
