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

    }

    @Test
    void decryptLogAndGetAllValuesTest() {

        StepVerifier.create(logService.getTest().log())
                .expectSubscription()
                .expectNext("OK")
                .expectNext("Then")
                .expectNext("ok...")
                .verifyComplete();

    }
}    
```