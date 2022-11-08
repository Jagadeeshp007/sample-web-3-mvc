package com.sample.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.app.model.LoginDetails;

@Repository
public interface LoginRepository extends JpaRepository<LoginDetails, Integer> {

	LoginDetails findByEmailId(String emailId);
}


// repository