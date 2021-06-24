package ro.edu.UTCNBM.moneyManager;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ro.edu.UTCNBM.moneyManager.Component.Receipt;
import ro.edu.UTCNBM.moneyManager.Component.ResetPassword;
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

	@Autowired
	public JavaMailSender javaMailSender;

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
			if (customers.get(i).getEmail().equals(myuser.getEmail()) && customers.get(i).getPassword().equals(myuser.getPassword())) {
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

	@PostMapping(value = "/sendEmail")
	public String sendemail() {

		return "emailSend";
	}

	@PostMapping(value = "/Chart")
	public List<Receipt> getChart() {

		return ReceiptRepositoryData.findAll();
	}

	@PostMapping(value = "/GetPassowrd")
	public Validation getUserPassword(@RequestBody final User myUser) {

		Validation myValidation = new Validation();
		java.util.List<User> customers = userRepository.findAll();

		for (int i = 0; i < customers.size(); i++) {
			if (customers.get(i).getEmail().equals(myUser.getEmail())) {

				SimpleMailMessage myemail = new SimpleMailMessage();
				myemail.setTo(myUser.getEmail());
				myemail.setSubject("Your  Password:");
				myemail.setText("Here is your password:"+customers.get(i).getPassword());
				javaMailSender.send(myemail);
				myValidation.setMessage("An email was send for you to recover your password");

			}
		}

		myValidation.setValidation(true);

		return myValidation;
	}

	@PostMapping(value = "getReceipt")
	public List<Receipt> getValuesOfReceipt(@RequestBody final Validation userId) {

		java.util.List<Receipt> receipts = ReceiptRepositoryData.findAll();
		java.util.List<Receipt> userReceipts = new ArrayList<Receipt>();

		for (int i = 0; i < receipts.size(); i++) {

			if (receipts.get(i).getUserId().equals(userId.getNumberId())) {
				userReceipts.add(receipts.get(i));
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

		Validation validation = new Validation();
		validation.setMessage("All is good");
		ReceiptRepositoryData.save(receipt);

		return validation;
	}

	@PostMapping(value = "/delteReceipt")
	public Validation deleteReceipt(@RequestBody final Receipt receipt) {

		Validation validation = new Validation();
		validation.setMessage("Was Deleted");
		ReceiptRepositoryData.deleteById(receipt.getId());

		return validation;
	}

	@PostMapping(value = "/updateReceipt")
	public Validation updateReceipt(@RequestBody final ResetPassword userData) {

		Validation validation = new Validation();
		User myUser = userRepository.findById(userData.getId()).get();
		
		if (myUser.getPassword().equals(userData.getOldPassword())) {
			myUser.setPassword(userData.getNewPassword());
			userRepository.save(myUser);

			validation.setMessage("User Password was uppdated!");
		} else {
			validation.setMessage("Wrong original password");
		}

		return validation;
	}

}
