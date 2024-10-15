package ir.mywallet.repository;

import ir.mywallet.model.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WithdrawalRepo extends CrudRepository<Withdrawal,Integer>, JpaRepository<Withdrawal,Integer> {
	
	@Query(value = "SELECT * FROM WITHDRAWAL_FROM_WALLET WHERE wallet_id =:wallet_id order by createdAt", nativeQuery = true)
	List<Withdrawal> findAllByWalletId(@Param(value = "wallet_id") int wallet_id);
}
