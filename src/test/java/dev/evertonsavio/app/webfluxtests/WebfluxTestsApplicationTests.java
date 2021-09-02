package dev.evertonsavio.app.webfluxtests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class) //JUnit 5
class WebfluxTestsApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void fluxTest(){
		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
				//.concatWith(Flux.error(new RuntimeException("Exception Occurred")))
				.concatWith(Flux.just("After error"))
				.log();

		StepVerifier.create(stringFlux)
				.expectSubscription()
				.expectNext("Spring")
				.expectNext("Spring Boot")
				.expectNext("Reactive Spring")
				.expectNext("After error")
				.verifyComplete();

		//		stringFlux.subscribe(System.out::println,
//				(e) -> System.err.println("Exception is :" + e),
//				() -> System.out.println("Complete"));
	}

}