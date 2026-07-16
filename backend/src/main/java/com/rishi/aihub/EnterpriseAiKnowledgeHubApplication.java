package com.rishi.aihub;

import com.rishi.aihub.features.auth.security.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class EnterpriseAiKnowledgeHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnterpriseAiKnowledgeHubApplication.class, args);
	}

}
