package com.grupoHasten.pruebaSalahdin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@EnableAspectJAutoProxy
@EnableJpaRepositories("com.grupoHasten.pruebaSalahdin.*")
public class PruebaSalahdinApplication {

	public static void main(String[] args) {
		SpringApplication.run(PruebaSalahdinApplication.class, args);
	}

}
