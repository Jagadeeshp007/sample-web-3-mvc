package com.sample.app.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.app.model.UserDetails;
import com.sample.app.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;

	@PostConstruct
	public void saveUserDetails() throws IOException {

		List<UserDetails> userDetailsList = new ArrayList<>();

		BufferedReader br = new BufferedReader(new FileReader("./src/main/resources/details.csv"));

		String line = "";
		while ((line = br.readLine()) != null) {
			String[] data = line.split(",");
			UserDetails userDetails = new UserDetails();
			userDetails.setId(Integer.parseInt(data[0]));
			userDetails.setName(data[1]);
			userDetails.setEmail(data[2]);
			userDetails.setLocation(data[3]);
			userDetails.setMobile(data[4]);
			
			userDetailsList.add(userDetails);
//			br.close();

		}
		userRepo.saveAll(userDetailsList);
		br.close();
	}
}
