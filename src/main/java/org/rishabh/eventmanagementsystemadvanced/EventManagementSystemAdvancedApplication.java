package org.rishabh.eventmanagementsystemadvanced;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EventManagementSystemAdvancedApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventManagementSystemAdvancedApplication.class, args);
    }

}
