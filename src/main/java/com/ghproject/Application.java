package com.ghproject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		Environment env = SpringApplication.run(Application.class, args).getEnvironment();

		String protocol = "http";
		if (env.getProperty("server.ssl.key-store") != null) {
			protocol = "https";
		}
		String contextPath = env.getProperty("server.servlet.context-path");
		String appName = env.getProperty("spring.application.name");

		log.info("\n----------------------------------------------------------\n\t"
						+ "Application '{}' is running! Access URLs:\n\t"
						+ "Local: \t\t{}://localhost:{}{}\n\t"
						+ "\n----------------------------------------------------------\n\t",
				appName,
				protocol,
				env.getProperty("server.port"),
				contextPath);
	}

}
