package com.iam_user_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class IamUserManagementApplication {
    public static void main(final String[] args) {
        SpringApplication.run(IamUserManagementApplication.class, args);
    }

}
