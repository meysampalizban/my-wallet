package ir.mywallet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

//جدول واریزی ها
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "deposit_to_wallet")
public class Deposit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "deposit_amount", unique = false, nullable = false)
	@NotNull(message = "مقدار واریز وارد شود")
	private Long depositAmount;
	
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
	
	public Deposit(Long depositAmount,Wallet wallet,String refNumber,String desciption){
		this.depositAmount = depositAmount;
		this.wallet = wallet;
		this.refNumber = refNumber;
		this.desciption = desciption;
	}
	
}
