package ir.mywallet.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "mywallet")
public class Wallet {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "wallet_balance", nullable = false)
	@NotNull(message = "حساب کیف پول نمیتواند خالی باشد")
	private Long wBalance;
	
	@OneToOne(mappedBy = "wallet")
	private User user;
	
	
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "wallet")
	@ReadOnlyProperty
	private List<Deposit> deposit;
	
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "wallet")
	@ReadOnlyProperty
	private List<Withdrawal> withdrawals;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Instant createdAt;
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Instant updatedAt;
	
	
	
}
