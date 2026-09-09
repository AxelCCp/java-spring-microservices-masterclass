package com.demo.pureClient;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PureClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(PureClientApplication.class, args);
	}

	@Bean 
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public OAuth2AuthorizedClientService auth2AuthorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
		return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
	}

	@Bean 
	public OAuth2AuthorizedClientManager auth2AuthorizedClientManager(ClientRegistrationRepository repos, OAuth2AuthorizedClientService clientService) {
		var manager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(repos, clientService);
		OAuth2AuthorizedClientProvider provided = OAuth2AuthorizedClientProviderBuilder.builder().clientCredentials().build();
		manager.setAuthorizedClientProvider(provided);
		return manager;
	}

	@Bean 
	public CommandLineRunner run (OAuth2AuthorizedClientManager manager, RestTemplate rest, @Value("${service2.url}") String serviceUrl) {
		
		return args -> {
			
			var authRequest = OAuth2AuthorizeRequest.withClientRegistrationId("keycloak-client").principal("machine").build();
			var client = manager.authorize(authRequest);
			String token = client.getAccessToken().getTokenValue();
			HttpHeaders headers = new HttpHeaders();
			headers.setBearerAuth(token);
			var resp = rest.exchange(serviceUrl + "/data", HttpMethod.GET, new HttpEntity<>(headers), String .class);
			System.out.println("Response from service 2: " + resp);
		
		};

	} 
}
