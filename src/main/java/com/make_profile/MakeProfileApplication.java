package com.make_profile;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.make_profile.configuration.MakeProfileInterceptor;
import com.make_profile.repository.common.EnvironmentRepository;

import jakarta.annotation.PostConstruct;

import java.time.Duration;

@SpringBootApplication
public class MakeProfileApplication {

	@Autowired
	MakeProfileInterceptor makeProfileInterceptor;

	@Autowired
	EnvironmentRepository environmentRepository;

	private String openAiKey;

	@PostConstruct
	private void getOpenAiKeyFromDb() {

		openAiKey = environmentRepository.getEnvironmentValueByKey("OPEN_AI");
	}

	public static void main(String[] args) {
		SpringApplication.run(MakeProfileApplication.class, args);
	}

	@Bean
	ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}

//	@Bean
//	RestTemplate restTemplate(RestTemplateBuilder builder) {
//		return builder.build();
//	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		RestTemplate restTemplate = builder.setConnectTimeout(Duration.ofSeconds(30))
				.setReadTimeout(Duration.ofSeconds(30)).build();
		restTemplate.getInterceptors().add((request, body, execution) -> {
			request.getHeaders().add("Authorization", "Bearer " + openAiKey);
			return execution.execute(request, body);
		});
		return restTemplate;
	}

	@Bean
	FreeMarkerConfigurer freeMarkerConfigurer() {
		FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
		freeMarkerConfigurer.setTemplateLoaderPath("classpath:/resume-templates");
		return freeMarkerConfigurer;
	}

	@Bean
	WebMvcConfigurer configurer() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("*").allowedOrigins("*");
			}

			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(makeProfileInterceptor);
			}

		};
	}

	@Bean
	ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(100);
		threadPoolTaskExecutor.setThreadNamePrefix("make_profile_executor_thread");
		threadPoolTaskExecutor.initialize();
		return threadPoolTaskExecutor;
	}

//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").allowedOrigins("http://localhost:4500") // Angular frontend
//						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedHeaders("*")
//						.allowCredentials(true);
//			}
//		};
//	}

}
