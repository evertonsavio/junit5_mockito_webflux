### Webflux with Mockito

```
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
class LogServiceImplTest {

    @InjectMocks
    LogServiceImpl logService;

    @Mock
    LogRepository logRepository;

    @Mock
    LockGroupRepository lockGroupRepository;

    @BeforeEach
    void setUp() {

        BDDMockito.when(lockGroupRepository.findFirstByLockGroupIdAndMac("1","1"))
                .thenReturn(Mono.just(new LockGroup())
                        .flatMap(lockGroup -> {
                            lockGroup.setLockGroupId("123");
                            return Mono.just(lockGroup);
                        }));

    }

    @Test
    void getLog() {
        logService.accessLockGroup().doOnSuccess(lockGroup -> System.out.println(lockGroup.getLockGroupId()))
                .subscribe();
    }

    @Test
    void decryptLogAndGetAllValuesTest() {

        Mono<String> decryptedHexBytes$ = logService.decryptHexLogs("FF");
        Mono<LogExtracted> logExtracted$ = logService.extractValuesFromHexBytes(decryptedHexBytes$);
        logExtracted$.subscribe(System.out::println);

        StepVerifier.create(decryptedHexBytes$)
                .expectSubscription()
                .expectNext("FF")
                .verifyComplete();

        StepVerifier.create(logService.getTest().log())
                .expectSubscription()
                .expectNext("OK")
                .expectNext("Then")
                .expectNext("ok...")
                .verifyComplete();
    }
}    
```

### Coverage jacoco dependencies
```yaml
	<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>3.8.0</version>
				<configuration>
					<source>${java.version}</source>
					<target>${java.version}</target>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.jacoco</groupId>
				<artifactId>jacoco-maven-plugin</artifactId>
				<version>0.8.6</version>
				<executions>
					<execution>
						<goals>
							<goal>prepare-agent</goal>
						</goals>
					</execution>
					<execution>
						<id>report</id>
						<phase>prepare-package</phase>
						<goals>
							<goal>report</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-surefire-plugin</artifactId>
				<version>2.22.2</version>
			</plugin>
		</plugins>

	</build>
```