package com.shibi.app.usermanagement;


import com.shibi.app.models.User;
import com.shibi.app.models.security.Role;
import com.shibi.app.models.security.UserRole;
import com.shibi.app.services.impl.UserService;
import com.shibi.app.utils.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashSet;
import java.util.Set;


@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages = {"com.shibi.app"})
public class UsermanagementApplication implements CommandLineRunner{


	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public static void main(String[] args) {
		SpringApplication.run(UsermanagementApplication.class, args);
	}

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
//		return super.configure(builder);
//	}




//	@Bean
//	public ContentFilter replaceHtmlFilter() {
//		return new ContentFilter();
//	}

	@Override
	public void run(String...args) throws Exception {
		User user1 = new User();
		user1.setFirstName("olumide");
		user1.setLastName("ogundele");
		user1.setUsername("bidemi");
		user1.setEmail("ogundeleolumuyiwaolumide@gmail.com");
		user1.setPhone("07069606863");
		user1.setPassword(SecurityUtility.passwordEncoder().encode("aramide"));
		Set<UserRole> userRoles = new HashSet<>();
		Role role1 = new Role();
		role1.setRoleId(Long.valueOf("2"));
		role1.setName("ROLE_ADMIN");
		userRoles.add(new UserRole(user1,role1));

		userService.generateUser(user1,userRoles);

		userRoles.clear();

		User user2 = new User();
		user2.setFirstName("aramide");
		user2.setLastName("anofiu");
		user2.setUsername("administrator");
		user2.setEmail("dharmykoya38@gmail.com");
		user2.setPhone("07038678576");
		user2.setPassword(SecurityUtility.passwordEncoder().encode("admin"));
		Role role2 = new Role();
		role2.setRoleId(Long.valueOf("1"));
		role2.setName("ROLE_SUPERADMIN");
		userRoles.add(new UserRole(user2,role2));

		userService.generateUser(user2,userRoles);






	}
}
