package com.totvs.gestao_contas_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = {
		"jwt.secret=chave-teste-com-pelo-menos-256-bits-para-hs256-no-teste-aqui-com-32-caracteres!",
		"spring.flyway.enabled=false",
		"spring.jpa.hibernate.ddl-auto=update",
		"spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
		"spring.datasource.driver-class-name=org.h2.Driver"
})
@ActiveProfiles("test")
class GestaoContasServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
