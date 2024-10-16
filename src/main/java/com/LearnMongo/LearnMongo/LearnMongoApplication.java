package com.LearnMongo.LearnMongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class LearnMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnMongoApplication.class, args);
	}

}
