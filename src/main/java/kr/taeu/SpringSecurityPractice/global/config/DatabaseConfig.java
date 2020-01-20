package kr.taeu.SpringSecurityPractice.global.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class DatabaseConfig {
	@Bean
	public DataSource dataSource() {
		log.info("dataSource setup...");
		// no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder
				.setType(EmbeddedDatabaseType.H2) //.H2 or .DERBY
				.build();
		return db;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		log.info("entityManagerFactory setup...");
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);
		
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setDataSource(dataSource());
		factory.setPackagesToScan("kr.taeu.SpringSecurityPractice");
		factory.setJpaProperties(additionalProperties());
		return factory;
	}
	
	private Properties additionalProperties() {
		log.info("additionalProperties setup...");
		Properties properties = new Properties();
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.format_sql", "true");
		properties.setProperty("hibernate.use_sql_comments", "true");
		properties.setProperty("logging.level.org.hibernate.type.descriptor.sql.BasicBinder", "trace");
		return properties;
	}
}
