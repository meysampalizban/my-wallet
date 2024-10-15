package ir.mywallet.repository;

import ir.mywallet.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositRepo extends CrudRepository<Deposit,Integer> , JpaRepository<Deposit,Integer> {
	@Query(value = "SELECT * FROM deposit_to_wallet WHERE wallet_id =:wallet_id order by createdAt", nativeQuery = true)
	List<Deposit> findAllByWalletId(@Param(value = "wallet_id") int wallet_id);
}
