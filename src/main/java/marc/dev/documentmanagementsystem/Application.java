package marc.dev.documentmanagementsystem;

import lombok.extern.slf4j.Slf4j;
import marc.dev.documentmanagementsystem.domain.RequestContext;
import marc.dev.documentmanagementsystem.entity.RoleEntity;
import marc.dev.documentmanagementsystem.enumaration.Authority;
import marc.dev.documentmanagementsystem.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
@Slf4j
public class Application {
	@Value("${spring.mail.host}")
	private String host;
	@Value("${spring.mail.username}")
	private String fromEmail;

	@Value("${spring.mail.password}")
	private String password;

	@Value("${spring.mail.protocol}")
	private String protocol;

	@Value("${spring.mail.port}")
	private String port;

	@Value("${spring.mail.properties.mail.smtp.auth}")
	private String auth;
	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private String enable;
	@Value("${spring.mail.properties.mail.smtp.starttls.required}")
	private String required;
	@Value("${spring.mail.verify.host}")
	private String verifyHost;

	public static void main(String[] args) {



		SpringApplication.run(Application.class, args);


	}


	@Bean
	CommandLineRunner commandLineRunner(RoleRepository roleRepository){
		return args ->{
			log.info("SMTP Host: {}", host);
			log.info("From Email: {}", fromEmail);
			log.info("SMTP Password: {}", password);
			log.info("From Protocol: {}", protocol);
			log.info("SMTP Port: {}", port);
			log.info("SMTP Auth: {}", auth);
			log.info("From Enabled: {}", enable);
			log.info("SMTP Required: {}", required);
			log.info("SMTP VerifyHost: {}", verifyHost);

//			RequestContext.setUserId(0L);
//			var userRole =  new RoleEntity();
//			userRole.setName(Authority.USER.name());
//			userRole.setAuthorities(Authority.USER);
//			roleRepository.save(userRole);
//
//			var adminRole = new RoleEntity();
//			adminRole.setName(Authority.ADMIN.name());
//			adminRole.setAuthorities(Authority.ADMIN);
//			roleRepository.save(adminRole);
//			RequestContext.start();
		};
	}


}
