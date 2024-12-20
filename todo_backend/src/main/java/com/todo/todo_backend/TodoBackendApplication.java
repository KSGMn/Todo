package com.todo.todo_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 스케쥴러 활성화
public class TodoBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoBackendApplication.class, args);
	}

}
