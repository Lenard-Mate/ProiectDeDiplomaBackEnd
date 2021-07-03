package ro.edu.UTCNBM.moneyManager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.edu.UTCNBM.moneyManager.Component.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
