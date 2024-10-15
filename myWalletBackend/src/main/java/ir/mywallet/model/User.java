package ir.mywallet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import ir.mywallet.validation.IsExsistEmail;
import ir.mywallet.validation.IsExsistNationalCode;
import ir.mywallet.validation.IsExsistPhoneNumber;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.format.annotation.DateTimeFormat;

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
	
	@Column(name = "email", unique = true, nullable = false)
	@NotBlank(message = "مقدار ایمیل باید وارد شود")
	@Email(message = "ایمیل باید به فرمت درست وارد شود")
	@IsExsistEmail(message = "ایمیل تکراری است")
	private String email;
	
	
	@NotBlank(message = "شماره تلفن باید وارد شود")
	@Size(max = 11, message = "بیشترین طول شماره تلفن باید 11 باشد")
	@Pattern(regexp = "^(0\\d{10}|9\\d{9})$", message = "شماره تلفن را به طرز صحیح وارد کنید")
	@IsExsistPhoneNumber(message = "شماره تلفن تکراری است")
	@Column(name = "phone_number", length = 15, unique = true, nullable = false)
	private String phoneNumber;
	
	
	@NotBlank(message = "کد ملی را  باید وارد شود")
	@Size(max = 10, message = "بیشترین طول کد ملی باید 10 باشد")
	@Pattern(regexp = "^\\d{10}$", message = "کد ملی را به طرز صحیح وارد کنید")
	@IsExsistNationalCode(message = "کد ملی تکراری است")
	@Column(name = "national_code", unique = true, length = 15, nullable = false)
	private String nationalCode;
	
	
	@Column(name = "birth_date", unique = false, nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date birthDate;
	
	@Column(name = "first_name", length = 50, nullable = false, unique = false)
	@NotBlank(message = "نام را وارد کنید")
	@Size(max =  50, message = "طول نام زیاد است")
	private String firstName;
	
	@Column(name = "last_name", length = 70, nullable = false, unique = false)
	@NotBlank(message = " نام خانوادکی را وارد کنید")
	@Size(max =  70, message = "نام خانوادگی طول زیاد دارد")
	private String lastName;
	
	@Column(name = "sex", length = 10, nullable = true, unique = false)
	@NotBlank(message = "جنسیت را وارد کنید")
	@Size(max = 10, message = "طول جنسیت زیاد است باید 10 تا باشد")
	private String sex;
	
	@Column(name = "military_status", length = 25, nullable = true, unique = false)
	@Size(max =  25, message = "طول خدمت سربازی زیاد است")
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
	private Date createdAt;
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	
}
