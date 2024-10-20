package ir.mywallet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.Instant;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "users_accounts")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@Column(name = "account_number", unique = true, length = 16, nullable = false)
	@NotBlank(message = "شماره حساب را وارد کنید")
	@Size(max = 16, message = "طول شماره حساب حداکثر یاید 16  کاراکتر باشد")
	private String accNumber;
	
	@Column(name = "shaba_number", unique = true, length = 24, nullable = false)
	@Size(max =  24, message = "طول شماره شبا باید 24 تا باشد")
	@NotBlank(message = "شماره شبا را وارد کنید")
	
	private String shabaNumber;
	
	@Column(name = "account_balance", unique = false, nullable = false)
	@NotNull(message = "مقدار را باید وارد کنید")
	private Long accBalance;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private User user;
	
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@ReadOnlyProperty
	private Instant createdAt;
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Instant updatedAt;
	
	public Account(String accNumber,String shabaNumber,Long accBalance,User user){
		this.accNumber = accNumber;
		this.shabaNumber = shabaNumber;
		this.accBalance = accBalance;
		this.user = user;
	}
	
}
