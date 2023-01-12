package com.example.springsecurityjwtdemo;

import com.example.springsecurityjwtdemo.entity.User;
import com.example.springsecurityjwtdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringSecurityJwtDemoApplication {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void initUsers(){
        List<User> users = Stream.of(
                new User(1, "firstUser", "password", "first@email.com"),
                new User(1, "firstUser", "password", "first@email.com")

        ).collect(Collectors.toList());
        userRepository.saveAll(users);
    }


    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJwtDemoApplication.class, args);
    }

}
