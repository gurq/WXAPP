package com.gutongxue.wxapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WxappApplication {

	public static void main(String[] args) {
		SpringApplication.run(WxappApplication.class, args);
	}
}
