package ir.mywallet.repository;

import ir.mywallet.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepo extends CrudRepository<Account,Integer>, JpaRepository<Account,Integer> {
	
	Boolean existsByAccNumber(String accNumber);
	Boolean existsByShabaNumber(String shabaNumber);
	
	@Modifying
	@Query(value = "update users_accounts set account_balance = :balance where id = :id", nativeQuery = true)
	int updateBalance(@Param(value = "id") int id,@Param(value = "balance") Long balance);
	
	@Query(value = "SELECT * FROM users_accounts  WHERE user_id=:userId", nativeQuery = true)
	List<Account> getAllByUserId(@Param(value = "userId") int id);
	
	@Query(value = "SELECT * FROM users_accounts  WHERE account_number=:accountNumber", nativeQuery = true)
	Account findByaccountNumber(@Param(value = "accountNumber") String accNum);
}
