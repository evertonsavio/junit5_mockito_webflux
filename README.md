### Service Success Test

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