package ir.mywallet.dto;

import ir.mywallet.model.Account;
import ir.mywallet.model.Wallet;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferReq {
	@Valid
	private Wallet fromWallet;
	@Valid
	private Account toAccount;
	@NotNull(message = "مقدار را وارد کنید")
	private Long amount;
	@Size(max = 60, message = "بیشترین طول متن توضیحات باید 60 کاراکتر باشد")
	private String description;
	
}
