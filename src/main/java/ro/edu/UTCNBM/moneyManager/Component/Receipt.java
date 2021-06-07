package ro.edu.UTCNBM.moneyManager.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "receipt")
public class Receipt {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	@Column(nullable = false, unique = false)
    private Long userId;
     
    @Column(nullable = true, length = 64)
    private String receiptValue;
     
    @Column(name = "total", nullable = false, length = 20)
    private float total;
     
    @Column(name = "location", nullable = false, length = 20)
    private String location;
    
    @Column(name = "date", nullable = false)
    private String date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getReceiptValue() {
		return receiptValue;
	}

	public void setReceiptValue(String receiptValue) {
		this.receiptValue = receiptValue;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
    
    
    
}
