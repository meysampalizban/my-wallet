package ir.mywallet.repository;

import ir.mywallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepo extends CrudRepository<Wallet,Integer>, JpaRepository<Wallet,Integer> {
	@Modifying
	@Query(value = "UPDATE wallet SET wallet_balance=:balance WHERE id=:id", nativeQuery = true)
	int updateBalance(@Param(value = "id") int id,@Param(value = "balance") Long balance);
	
}
