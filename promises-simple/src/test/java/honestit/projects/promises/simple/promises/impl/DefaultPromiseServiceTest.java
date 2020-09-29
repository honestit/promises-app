package honestit.projects.promises.simple.promises.impl;

import honestit.projects.promises.simple.promises.MakePromiseRequest;
import honestit.projects.promises.simple.promises.MakePromiseResponse;
import honestit.projects.promises.simple.promises.domain.Promise;
import honestit.projects.promises.simple.promises.domain.PromiseRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.time.LocalDateTime;

@DisplayName("Promises Service Tests")
class DefaultPromiseServiceTest {

    private DefaultPromiseService promiseService;
    private PromiseRepository promiseRepository;

    @BeforeAll
    public void prepareTest() {
        promiseRepository = Mockito.mock(PromiseRepository.class);
        promiseService = new DefaultPromiseService(promiseRepository);
    }

    @Nested
    @DisplayName("Promises Service Tests - Method make promise tests")
    class MakePromiseTest {

        @Test
        @DisplayName("When make promise then promise should be saved")
        public void whenMakePromiseThenPromiseShouldBeSaved() {
            Mockito.when(promiseRepository.save(ArgumentMatchers.any(Promise.class))).then(invocation -> {
                invocation.getArgument(0, Promise.class).setId(1L);
                return invocation.getArgument(0, Promise.class);
            });

            MakePromiseRequest request = new MakePromiseRequest("User", "Joe", "I will love you forever!", LocalDateTime.now().plusDays(1));
            MakePromiseResponse response = promiseService.makePromise(request);

            Assertions.assertThat(response).isNotNull();
            Assertions.assertThat(response).extracting("promiseId").isNotNull();
        }
    }


}