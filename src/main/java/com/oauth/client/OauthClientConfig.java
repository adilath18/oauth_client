package com.oauth.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Arrays;

/**
 * @author Aditya lath
 */
@Configuration
public class OauthClientConfig {

    private static final String YOUR_AUTH_SERVER_CLIENT_ID = "clientId";
    private static final String YOUR_AUTH_SERVER_CLIENT_SECRET = "clientSecret";

    public static class OauthWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .oauth2Login()
                    .clientRegistrationRepository(clientRegistrationRepository())
                    .and()
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated();
        }

        @Bean
        public ClientRegistrationRepository clientRegistrationRepository() {
            return new InMemoryClientRegistrationRepository(Arrays.asList(myOwnAuthServer(),
                    googleClient()));
        }

    }

    /**
     * If Authorization Server is your own
     * @return ClientRegistration
     */
    private static ClientRegistration myOwnAuthServer() {
        return ClientRegistration
                .withRegistrationId("APPLICATION_NAME")
                .clientId(YOUR_AUTH_SERVER_CLIENT_ID)
                .clientSecret(YOUR_AUTH_SERVER_CLIENT_SECRET)
                .scope("read", "write")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationUri("http://localhost:8081/oauth/authorize")
                .tokenUri("http://localhost:8081/oauth/token")
                .redirectUri("{baseUrl}/{action}/oauth2/code/{registrationId}")
                .userInfoUri("http://localhost:8081/user/info")
                .userNameAttributeName("userName")
                .clientName("ClientName")
                .build();
    }

    /**
     * If you are using Popular Authorization Servers such as Google, Facebook
     *
     * To change the AuthorizationServer to some other vendor such as FACEBOOK , Just use this CommonOAuth2Provider.GOOGLE,
     * For more custom Configuration, You can declare it in same way above @ref myOwnAuthServer()
     * @return ClientRegistration
     */
    private static ClientRegistration googleClient() {
        return CommonOAuth2Provider.GOOGLE
                .getBuilder("Google")
                .clientId("clientId")
                .clientSecret("secret")
                .build();
    }


}
