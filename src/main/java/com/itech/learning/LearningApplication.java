package com.itech.learning;

import com.itech.learning.domain.User;
import com.itech.learning.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningApplication.class, args);
	}

	/*@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository) {
		return args -> {
			User annie = new User("Annie", "James", "aj@gg.com", "asd");
			userRepository.save(annie);
		};
	}*/

}
