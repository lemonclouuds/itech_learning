package com.itech.learning;

import com.itech.learning.domain.*;
import com.itech.learning.domain.dto.LessonDto;
import com.itech.learning.domain.dto.RatingDto;
import com.itech.learning.repository.LessonRepository;
import com.itech.learning.repository.RatingRepository;
import com.itech.learning.repository.SubjectRepository;
import com.itech.learning.repository.UserRepository;
import com.itech.learning.service.LessonService;
import com.itech.learning.service.RatingService;
import com.itech.learning.service.SubjectService;
import com.itech.learning.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class LearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository,
										SubjectRepository subjectRepository,
										LessonRepository lessonRepository,
										RatingRepository ratingRepository,
										RatingService ratingService,
										SubjectService subjectService,
										LessonService lessonService,
										UserService userService) {
		return args -> {
			User annie = new User("Annie", "James", "aj@gg.com",
					"anniejames", "123123", UserRole.ADMIN);
			userRepository.save(annie);

			User jackie = new User("Jackie", "Jones", "jackie@ko.com",
					"jackiejones", "111111", UserRole.STUDENT);
			userRepository.save(jackie);

			Subject subjectMath = new Subject("Mathematics");
			Subject subjectBiology = new Subject("Biology");
			Subject subjectPhysics = new Subject("Physics");

			subjectRepository.saveAll(List.of(subjectMath, subjectBiology, subjectPhysics));

			Lesson lesson1 = new Lesson("Archimedes' principle",
					"Let's imagine there is a solution...",
					subjectPhysics);

			Lesson lesson2 = new Lesson("How to understand hibernate",
					"You need to research more about fetch types and @Transactional",
					subjectMath);

			lessonRepository.saveAll(List.of(lesson1, lesson2));

			//lessonService.updateTitle(lesson2.getId(), "New title");
			//lessonService.updateSolution(lesson2.getId(), "Come on, research more!");

			/*Rating rating = ratingService.addRating(8.5, annie.getId(), lesson1.getId());
			ratingRepository.save(rating);

			Rating rating2 = ratingService.addRating(8.0, jackie.getId(), lesson1.getId());
			ratingRepository.save(rating2);

			ratingService.updateRating(rating.getId(), 10.0);
			ratingService.updateRating(rating.getId(), 9.9);*/
			subjectService.updateTitle(subjectMath.getId(), "Матем");
			//subjectService.addLesson(subjectMath.getId(), lesson2);

			LessonDto lessonDto = new LessonDto(lesson1.getId(), "This has been changed", "New solution", 5L);
			lessonService.update(lessonDto);
			List<Rating> rates = lesson1.getRates();
			//List<Rating> rates = ratingService.getAllByLessonId(11L);
			System.out.println(rates);

			Rating rating =  new Rating(10.0, annie, lesson1);
			RatingDto ratingDto = new RatingDto(rating.getId(), 9.0, annie.getId(), lesson1.getId());

			ratingService.update(ratingDto);
			System.out.println(lessonService.calculateLessonRating(lesson1.getId()));

			LessonDto lessonDto1 = new LessonDto(67L, "For id 67L", "67L solution", subjectPhysics.getId());

			lessonService.create(lessonDto1);
			List<Lesson> lessonList = subjectPhysics.getLessons();
			System.out.println(lessonList);

			System.out.println(lessonService.calculateLessonRating(lesson1.getId()));

			System.out.println(subjectPhysics == subjectService.findById(5L)); //false
			System.out.println(subjectPhysics.getId());
			System.out.println(subjectPhysics.equals(subjectService.findById(5L)));
			//System.out.println(subjectService.findById(5L).getLessons());
			//Subject subject = subjectService.findById(5L);
			//System.out.println(subject.getLessons());
			//lessonService.deleteByIdIn(List.of(lesson1.getId(), lesson2.getId()));
			//lessonService.deleteById(lesson2.getId());
			//ratingService.deleteByIdIn(List.of(rating2.getId()));
			//subjectService.deleteByIdIn(List.of(subjectMath.getId()));
			//userService.deleteByIdIn(List.of(jackie.getId()));

		};
	}
	//использовать flyway
}
