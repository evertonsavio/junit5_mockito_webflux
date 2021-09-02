package dev.evertonsavio.app.webfluxtests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;


class WebfluxTestsApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void fluxTest(){
		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
				.concatWith(Flux.error(new RuntimeException("Exception Occurred"))).log();

		stringFlux.subscribe(System.out::println, (e) -> System.err.println(e));
	}

}
