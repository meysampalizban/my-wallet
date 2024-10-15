package ir.mywallet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

// جدول برداشت ها
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "withdrawal_from_wallet")
public class Withdrawal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "withdrawal_amount", unique = false, nullable = false)
	@NotNull(message = "مقدار برداشت باید وارد شود")
	private Long withdrawalAmount;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wallet_id", nullable = false, referencedColumnName = "id")
	@Valid
	private Wallet wallet;
	
	@Column(name = "reference_number", unique = true, length = 25, nullable = false)
	@NotEmpty(message = "شماره مرجع باید وارد شود")
	@Size(max = 25, message = "طول کاراکتر شماره مرجع زیاد است")
	private String refNumber;
	
	@Column(name = "desciption", length = 60, nullable = true)
	@Size(max = 60, message = "طول کاراکتر توضیحات زیاد است")
	private String desciption;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	
	public Withdrawal(Long withdrawalAmount,Wallet wallet,String refNumber,String desciption){
		this.withdrawalAmount = withdrawalAmount;
		this.wallet = wallet;
		this.refNumber = refNumber;
		this.desciption = desciption;
	}
	
}
