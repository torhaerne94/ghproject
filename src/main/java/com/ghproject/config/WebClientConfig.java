package com.ghproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	private final String BASIC_GITHUB_URI = "https://api.github.com";

	@Bean
	public WebClient webClient() {
		return WebClient.builder().baseUrl(BASIC_GITHUB_URI).build();
	}
}
