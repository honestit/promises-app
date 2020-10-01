package honestit.projects.promises.simple.timelapse.impl;

import honestit.projects.promises.simple.promises.domain.Promise;
import honestit.projects.promises.simple.promises.domain.PromiseRepository;
import honestit.projects.promises.simple.timelapse.IncomingPromisesRequest;
import honestit.projects.promises.simple.timelapse.IncomingPromisesResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.internal.verification.api.VerificationData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DefaultTimelapseService unit tests")
class DefaultTimelapseServiceTest {

    private DefaultTimelapseService timelapseService;
    private PromiseRepository promiseRepository;

    @BeforeEach
    public void prepareTests() {
        promiseRepository = Mockito.mock(PromiseRepository.class);
        timelapseService = new DefaultTimelapseService();
    }

    @Nested
    @DisplayName("DefaultTimelapseService incomingPromises unit tests")
    class IncomingPromises {

        private IncomingPromisesRequest defaultRequest;

        @BeforeEach
        public void prepareTests() {
            defaultRequest = new IncomingPromisesRequest("User");
        }

        @Test
        @DisplayName("Should return non null response")
        public void shouldReturnNonNullResponse() {
            IncomingPromisesResponse response = timelapseService.incomingPromises(defaultRequest);

            Assertions.assertThat(response).isNotNull();
        }

        @Test
        @DisplayName("Should return not null promises list")
        public void shouldReturnNotNullPromisesList() {
            IncomingPromisesResponse response = timelapseService.incomingPromises(defaultRequest);

            Assertions.assertThat(response.getPromises()).isNotNull();
        }

        @Test
        @DisplayName("Should get promises only for current user")
        public void shouldGetPromisesOnlyForCurrentUser() {
            Mockito.when(promiseRepository.findAllNullKeptPromisesForUserWithDeadlineBefore(
                    "User", ArgumentMatchers.any(), ArgumentMatchers.any()))
                    .thenReturn(new ArrayList<>());

            timelapseService.incomingPromises(defaultRequest);

            Mockito.verify(promiseRepository, Mockito.times(1))
                    .findAllNullKeptPromisesForUserWithDeadlineBefore(
                            "User", ArgumentMatchers.any(), ArgumentMatchers.any());
        }
    }

}