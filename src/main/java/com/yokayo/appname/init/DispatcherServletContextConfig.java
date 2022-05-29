package com.yokayo.appname.init;

import javax.annotation.PreDestroy;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.format.FormatterRegistry;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import com.yokayo.appname.controllers.UserController;
import com.yokayo.appname.controllers.supplementary.StringToEnumConverter;
import com.yokayo.appname.services.UserService;
import com.yokayo.appname.dao.UserDao;

@Configuration
@EnableWebMvc
public class DispatcherServletContextConfig implements WebMvcConfigurer {
    
    private Session session;
    
    // сессия Хибернейта
    @Bean
    public Session createSession() throws Exception {
        SessionFactory sessionFactory;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
			.configure()
			.build();
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		}
		catch (Exception e) {
			StandardServiceRegistryBuilder.destroy(registry);
            e.printStackTrace(System.err);
            throw e;
		}
        Session session = sessionFactory.openSession();
        this.session = session;
        return session;
    }
    
    @Bean
    public UserController createUserController() {
        return new UserController();
    }
    
    // поддержка multipart
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(1000000);
        resolver.setMaxUploadSizePerFile(1000000);
        return resolver;
    }
    
    @Bean
    public UserService userService() {
        return new UserService();
    }
    
    @Bean
    public UserDao userDao() {
        return new UserDao();
    }
    
    // нужно для резолвинга строчного названия enum'а в заглавное (используется в смене статуса)
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToEnumConverter());
    }
    
    // закрыть сессию
    @PreDestroy
    public void shutdown() {
        session.close();
    }
    
}
