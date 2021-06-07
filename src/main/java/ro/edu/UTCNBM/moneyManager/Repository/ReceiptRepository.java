package ro.edu.UTCNBM.moneyManager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.edu.UTCNBM.moneyManager.Component.Receipt;

public interface ReceiptRepository extends JpaRepository<Receipt, Long>  {

}
