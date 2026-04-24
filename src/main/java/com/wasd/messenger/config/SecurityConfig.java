package com.wasd.messenger.config;

import com.wasd.messenger.service.SimpleUserDetailsService;
import com.wasd.messenger.utils.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.DeferredSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

	private final JwtTokenProvider         jwtTokenProvider;
	private final SimpleUserDetailsService userDetailsService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) throws Exception {

		CorsConfigurationSource corsConfigurationSource = hsr -> {
			CorsConfiguration corsConfiguration = new CorsConfiguration();
			corsConfiguration.setAllowedOriginPatterns(List.of("http://localhost:*", "https://*"));
			corsConfiguration.addAllowedHeader("*");
			corsConfiguration.addAllowedMethod("*");
			corsConfiguration.addExposedHeader("X-FILE-NAME");
			corsConfiguration.addExposedHeader("Content-Disposition");
			corsConfiguration.addExposedHeader("Content-Length");
			corsConfiguration.setAllowCredentials(true);
			return corsConfiguration;
		};

		return http.csrf(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource))
			.authenticationManager(authenticationManager)
			.securityContext(
				securityContextConfigurer -> securityContextConfigurer.securityContextRepository(securityContextRepository)
			)
			.authorizeHttpRequests(
				authorizeRequests -> authorizeRequests
//					.requestMatchers("/error", "/api/auth/**", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/swagger-config", "/healthz")
					.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
					.anyRequest()
					.permitAll()
//					.anyRequest()
//					.hasAuthority("USER_AUTHORITY")
			)
			.exceptionHandling(
				exceptionHandling -> 
					exceptionHandling
						.authenticationEntryPoint((hsr, response, ae) -> 
													  response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
						.accessDeniedHandler((hsr, response, ade) ->
										 response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden")))
			.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		return authenticationManagerBuilder.build();
	}

	@Bean
	public SecurityContextRepository securityContextRepository() {
		return new SecurityContextRepository() {

			@Override
			public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
				String token = jwtTokenProvider.resolveToken(requestResponseHolder.getRequest());
				if (token != null && jwtTokenProvider.validateToken(token)) {
					Authentication authentication = jwtTokenProvider.getAuthentication(token);
					return new SecurityContextImpl(authentication);
				}
				return SecurityContextHolder.createEmptyContext();
			}

			@Override
			public DeferredSecurityContext loadDeferredContext(HttpServletRequest request) {
				return SecurityContextRepository.super.loadDeferredContext(request);
			}

			@Override
			public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
				
			}

			@Override
			public boolean containsContext(HttpServletRequest request) {
				String token = jwtTokenProvider.resolveToken(request);
				return token != null && jwtTokenProvider.validateToken(token);
			}
		};
	}
}