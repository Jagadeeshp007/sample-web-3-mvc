package com.sample.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.app.model.LoginDetails;
import com.sample.app.model.LoginInput;
import com.sample.app.model.ResponseStatus;
import com.sample.app.model.UserDetails;
import com.sample.app.repository.LoginRepository;
import com.sample.app.repository.UserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/userDetails")
public class UserController {
	
	LoginRepository loginRepo;
	UserRepository userRepo;
	
	@Autowired
	public UserController(LoginRepository loginRepo, UserRepository userRepo) {
		this.loginRepo=loginRepo;
		this.userRepo=userRepo;
	}

	@PostMapping("/login")
	public ResponseStatus login(@RequestBody LoginInput loginInput) {
		System.out.println("usernaeme :"+loginInput.getUserName()+" .");
		LoginDetails loginDetails = loginRepo.findByEmailId(loginInput.getUserName());
		
		System.out.println("check 1");
		if (loginDetails == null) {
			System.out.println("fail 1");
			return new ResponseStatus(false);
		} else if (loginDetails.getEmailId().equals(loginInput.getUserName())
				&& loginDetails.getPassword().equals(loginInput.getPassword())) {
				System.out.println("success");
			return new ResponseStatus(true);
		} else {
			System.out.println("fail 2");
			return new ResponseStatus(false);
		}
	}

	@PostMapping("/addUser")
	public UserDetails addUser(@RequestBody UserDetails userd) {
		return userRepo.save(userd);
	}
	
	@GetMapping("/loginDetail/{id}")
	public ResponseEntity<LoginDetails> showLoginDetail(@PathVariable(value="id")int loginId) {
		LoginDetails loginDetails = loginRepo.findById(loginId).orElse(new LoginDetails());
		return ResponseEntity.ok().body(loginDetails);
		
	}
	
	@PostMapping("/regDetails")
	public LoginDetails regDetails(@RequestBody LoginDetails logind) {
		return loginRepo.save(logind);
	}
	@GetMapping("/listUser")
	public Iterable<UserDetails> listUser() {
		return userRepo.findAll();
	}

	@GetMapping("/showDetails/{id}")
	public ResponseEntity<UserDetails> showDetails(@PathVariable(value="id")int userId) {
		UserDetails userd= userRepo.findById(userId).orElse(new UserDetails());
		return ResponseEntity.ok().body(userd);
	}
	
	@PutMapping("/updateDetails/{id}")
	public ResponseEntity<UserDetails> updateDetails(@PathVariable(value="id")int userId, @RequestBody UserDetails userDetails) {
		UserDetails updateDetails = userRepo.findById(userId).orElse(new UserDetails());
		
		updateDetails.setName(userDetails.getName());
		updateDetails.setEmail(userDetails.getEmail());
		updateDetails.setMobile(userDetails.getMobile());
		updateDetails.setLocation(userDetails.getLocation());
		
		return ResponseEntity.ok(userRepo.save(updateDetails));
	}
	@DeleteMapping("/deleteUser/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") int userId) {
		UserDetails userd = userRepo.findById(userId).orElse(new UserDetails());
		 userRepo.delete(userd);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
		
	}

}
