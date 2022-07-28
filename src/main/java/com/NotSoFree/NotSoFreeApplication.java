package com.NotSoFree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class }) //1#
public class NotSoFreeApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotSoFreeApplication.class, args);
	}

}

/*1#
I have the database dependence but I do not have it configured,
I need this to make the project work for now (remove it when adding it the configuration)
*/