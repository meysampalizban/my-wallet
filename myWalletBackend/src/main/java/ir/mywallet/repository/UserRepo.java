package ir.mywallet.repository;

import ir.mywallet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User,Integer>, JpaRepository<User,Integer> {
	
	boolean existsByEmail(String email);
	
	boolean existsByPhoneNumber(String phoneNumber);
	
	boolean existsByNationalCode(String nationalCode);
	
	Optional<User> findByEmail(String email);
	
	@Modifying
	@Query(value = "UPDATE users  SET wallet_id =:wallet_id WHERE id =:id", nativeQuery = true)
	void updateWallet(@Param(value = "id") int id,@Param(value = "wallet_id") int wallet);
	
	@Modifying
	@Query(value = "UPDATE users  SET token =:_token WHERE id =:id", nativeQuery = true)
	void updateToken(@Param(value = "id") int id,@Param(value = "_token") String token);
	
}
