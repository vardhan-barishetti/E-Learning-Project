package com.elearn.app;

import com.elearn.app.config.AppConstants;
import com.elearn.app.entities.Role;
import com.elearn.app.entities.User;
import com.elearn.app.repositories.RoleRepo;
import com.elearn.app.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.UUID;


@SpringBootApplication
public class ELearningBackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ELearningBackendApplication.class, args);
	}


	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;


	@Autowired
	private RoleRepo roleRepo;

	@Override
	public void run(String... args) throws Exception {

		Role role1 = new Role();
		role1.setRoleName(AppConstants.ROLE_ADMIN);
		role1.setRoleId(UUID.randomUUID().toString());

		Role role2 = new Role();
		role2.setRoleName(AppConstants.ROLE_GUEST);
		role2.setRoleId(UUID.randomUUID().toString());

		//Creating Admin role if not exists
		roleRepo.findByRoleName(AppConstants.ROLE_ADMIN).ifPresentOrElse(
				role -> {
					System.out.println(role.getRoleName()+" already exists");
					role1.setRoleId(role.getRoleId());
				},
				() -> {
					roleRepo.save(role1);
				}
		);


		roleRepo.findByRoleName(AppConstants.ROLE_GUEST).ifPresentOrElse(
				role -> {
					System.out.println(role.getRoleName()+" already exists");
					role2.setRoleId(role.getRoleId());
				},
				() -> {
					roleRepo.save(role2);
				}
		);



		User user = new User();
		user.setUserId(UUID.randomUUID().toString());
		user.setName("Dheeraj");
		user.setEmail("abc@gmail.com");
		user.setPassword(passwordEncoder.encode("abc"));
		user.setActive(true);
		user.setCreateAt(new Date());
		user.setEmailVerified(true);
		user.setAbout("This is Dummy User");

		user.assignRole(role1);
		user.assignRole(role2);

		userRepo.findByEmail("abc@gmail.com").ifPresentOrElse(user1->{
			System.out.println(user1.getEmail());

		}, ()->{
			userRepo.save(user);
			System.out.println("User Created");
		});

		User user2 = new User();
		user2.setUserId(UUID.randomUUID().toString());
		user2.setName("Varma");
		user2.setEmail("abcd@gmail.com");
		user2.setPassword(passwordEncoder.encode("abc"));
		user2.setActive(true);
		user2.setCreateAt(new Date());
		user2.setEmailVerified(true);
		user2.setAbout("This is 2nd Dummy User");
		user2.assignRole(role2);

		userRepo.findByEmail("abcd@gmail.com").ifPresentOrElse(user3->{
			System.out.println(user3.getEmail());

		}, ()->{
			userRepo.save(user2);
			System.out.println("User Created");
		});


		User user3 = new User();
		user3.setUserId(UUID.randomUUID().toString());
		user3.setName("kitu");
		user3.setEmail("kittu@gmail.com");
		user3.setPassword(passwordEncoder.encode("abc"));
		user3.setActive(true);
		user3.setCreateAt(new Date());
		user3.setEmailVerified(true);
		user3.setAbout("This is 3rd Dummy User");
		user3.assignRole(role2);

		userRepo.findByEmail("kittu@gmail.com").ifPresentOrElse(user4->{
			System.out.println(user4.getEmail());

		}, ()->{
			userRepo.save(user3);
			System.out.println("User Created");
		});

	}
}
