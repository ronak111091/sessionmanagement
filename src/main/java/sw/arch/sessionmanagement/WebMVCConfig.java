package sw.arch.sessionmanagement;

import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer{
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}
	
	@Bean
	public HttpSessionListener httpSessionListener() {
		return new HttpSessionListener() {
			
			@Override
			public void sessionDestroyed(HttpSessionEvent event) {
				// TODO Auto-generated method stub
				 System.out.printf("Session ID %s destroyed at %s%n", event.getSession().getId(), new Date());
			}
			
			@Override
			public void sessionCreated(HttpSessionEvent event) {
				// TODO Auto-generated method stub
				event.getSession().setMaxInactiveInterval(60);
				System.out.printf("Session ID %s created at %s%n", event.getSession().getId(), new Date());
			}
		};
	}
	
//	@Bean
//	public SimpleMappingExceptionResolver exceptionResolver() {
//		SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
//		simpleMappingExceptionResolver.setDefaultErrorView("/error");
//		simpleMappingExceptionResolver.setExcludedExceptions(AccessDeniedException.class);
//		return simpleMappingExceptionResolver;
//	}
}
