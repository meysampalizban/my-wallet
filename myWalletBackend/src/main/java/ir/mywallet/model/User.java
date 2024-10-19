package ir.mywallet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.security.Key;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "first_name", length = 50, nullable = false, unique = false)
	@NotBlank(message = "نام را وارد کنید")
	@Size(max = 50, message = "طول نام زیاد است")
	private String firstName;
	
	@Column(name = "last_name", length = 70, nullable = false, unique = false)
	@NotBlank(message = " نام خانوادکی را وارد کنید")
	@Size(max = 70, message = "نام خانوادگی طول زیاد دارد")
	private String lastName;
	
	@Column(name = "email", length = 101, unique = true, nullable = false)
	@NotBlank(message = "مقدار ایمیل باید وارد شود")
	@Email(message = "ایمیل باید به فرمت درست وارد شود")
	@Size(max = 100, message = "بیشترین طول ایمیل باید 100 کاراکتر باشد")
	private String email;
	
	
	@NotBlank(message = "شماره تلفن باید وارد شود")
	@Size(max = 11, message = "بیشترین طول شماره تلفن باید 11 باشد")
	@Pattern(regexp = "^09[0-9]{9}$", message = "شماره تلفن را به طرز صحیح وارد کنید")
	@Column(name = "phone_number", length = 14, unique = true, nullable = false)
	private String phoneNumber;
	
	
	@NotBlank(message = "کد ملی را  باید وارد شود")
	@Size(max = 10, message = "بیشترین طول کد ملی باید 10 باشد")
	@Pattern(regexp = "^[0-9]{10}$", message = "کد ملی را به طرز صحیح وارد کنید")
	@Column(name = "national_code", unique = true, length = 15, nullable = false)
	private String nationalCode;
	
	
	@Column(name = "birth_date", unique = false, nullable = false)
	@NotNull(message = "تاریخ تولد را وارد کنید")
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;
	
	
	@Column(name = "sex", length = 10, nullable = true, unique = false)
	@NotBlank(message = "جنسیت را وارد کنید")
	@Size(max = 10, message = "طول جنسیت زیاد است باید 10 تا باشد")
	private String sex;
	
	@Column(name = "military_status", length = 25, nullable = true, unique = false)
	@Size(max = 25, message = "طول خدمت سربازی زیاد است")
	private String militaryStatus;
	
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
	@ReadOnlyProperty
	private List<Account> accounts;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "wallet_id", referencedColumnName = "id")
	@JsonIgnoreProperties("user")
	@Valid
	private Wallet wallet;
	
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@ReadOnlyProperty
	private Instant createdAt;
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Instant updatedAt;
	
	@Column(name = "token")
	private String _token;
	
}
