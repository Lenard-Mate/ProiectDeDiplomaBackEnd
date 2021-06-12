package ro.edu.UTCNBM.moneyManager;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



import ro.edu.UTCNBM.moneyManager.Component.Receipt;
import ro.edu.UTCNBM.moneyManager.Component.User;
import ro.edu.UTCNBM.moneyManager.Component.Validation;
import ro.edu.UTCNBM.moneyManager.Repository.ReceiptRepository;
import ro.edu.UTCNBM.moneyManager.Repository.UserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")

public class Controller {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ReceiptRepository ReceiptRepositoryData;


	@PostMapping(value = "/saveNewUser")
	public User saveUser(@RequestBody final User myuser) {
		userRepository.save(myuser);
		myuser.setEmail("dataSaved");
		myuser.setPassword("");
		return myuser;
	}

	@PostMapping(value = "/LogIn")
	public Validation findUser(@RequestBody final User myuser) {

		
		Validation validation = new Validation();
		
		java.util.List<User> customers = userRepository.findAll();

		for (int i = 0; i < customers.size(); i++) {
			if (customers.get(i).getEmail().equals(myuser.getEmail())) {
				validation.setValidation(true);
				validation.setNumberId(customers.get(i).getId());
				validation.setMessage("Valid");
				return validation;
			}

		}
		validation.setValidation(false);
		validation.setMessage("Invalid");
		validation.setNumberId(null);
		return validation;
	}
	
	
	@PostMapping(value = "/Receipt")
	public String saveReceipt(@RequestBody final Receipt myReceipt) {

		ReceiptRepositoryData.save(myReceipt);

		return "dataSaved";
	}

	@PostMapping(value = "/Chart")
	public List<Receipt> getChart() {

		return ReceiptRepositoryData.findAll();
	}
	
	
	@PostMapping(value = "getReceipt")
	public List<Receipt> getValuesOfReceipt(@RequestBody final Validation userId) {

		
		
//		Receipt monyAndTime = new Receipt();
//		Receipt monyAndTime2 = new Receipt();
//		Receipt monyAndTime3 = new Receipt();
//		Receipt monyAndTime4 = new Receipt();
//		Receipt monyAndTime5 = new Receipt();
//		Receipt monyAndTime6 = new Receipt();
//		Receipt monyAndTime7 = new Receipt();
//		Receipt monyAndTime8 = new Receipt();
//		Receipt monyAndTime9 = new Receipt();
//
//		monyAndTime.setDate("2020/03/04");
//		monyAndTime.setTotal(32422);
//		
//		monyAndTime2.setDate("2020/04/04");
//		monyAndTime2.setTotal(52422);
//		
//		monyAndTime3.setDate("2020/05/04");
//		monyAndTime3.setTotal(62422);
//		
//		monyAndTime4.setDate("2020/06/04");
//		monyAndTime4.setTotal(72422);
//		
//		monyAndTime5.setDate("2020/07/04");
//		monyAndTime5.setTotal(82422);
//		
//		return Arrays.asList(monyAndTime, monyAndTime2, monyAndTime3, monyAndTime3, monyAndTime4, monyAndTime5);
		
		
		java.util.List<Receipt> receipts = ReceiptRepositoryData.findAll();
		java.util.List<Receipt> userReceipts = new ArrayList<Receipt>();
		
		
		
		for(int i =0;i<receipts.size();i++) {
			
			if (receipts.get(i).getUserId().equals(userId.getNumberId())) {
				
				
				userReceipts.add(receipts.get(i));
				System.out.println(receipts.get(i));
			}
		}
		
		
		Collections.sort(userReceipts, new Comparator<Receipt>() {
            @Override
            public int compare(Receipt r1, Receipt r2) {
                return r1.getDate().compareTo(r2.getDate());
            }
        });
		
		
		return userReceipts;
	}
	
	

	@PostMapping(value = "/setReceipt")
	public Validation setReceipt(@RequestBody final Receipt receipt) {
		System.out.println(receipt.getTotal());
		
		Validation validation =new Validation();
		validation.setMessage("All is good");
		ReceiptRepositoryData.save(receipt);
		
		return validation;
	}
	
}
