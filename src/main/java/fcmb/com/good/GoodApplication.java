package fcmb.com.good;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SpringBootApplication
@RequiredArgsConstructor
@ComponentScan(basePackages = "fcmb.com.good.config.CustomUserDetailsService.class")
public class GoodApplication {

  /*  @PostConstruct
    public void initUsers() {
        List<User> users = Stream.of(
                new User( "emmy", "emmytemmy@gmail.com", "N0 3 raye street","emmytemmy","emmy"),
                new User( "emmy", "emmytemmy@gmail.com", "N0 3 raye street","user1","pwd1")
                ).collect(Collectors.toList());
        userRepository.saveAll(users);
    }*/

	public static void main(String[] args) {

        SpringApplication.run(GoodApplication.class, args);
	}

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:4200");
//            }
//        };
//    }




}
