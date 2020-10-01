package honestit.projects.promises.simple.timelapse.impl;

import honestit.projects.promises.simple.friends.domain.Friend;
import honestit.projects.promises.simple.promises.domain.Promise;
import honestit.projects.promises.simple.promises.domain.PromiseRepository;
import honestit.projects.promises.simple.timelapse.IncomingPromisesRequest;
import honestit.projects.promises.simple.timelapse.IncomingPromisesResponse;
import honestit.projects.promises.simple.users.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.internal.verification.api.VerificationData;

import java.time.LocalDate;
import java.time.LocalTime;
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
        private Promise defaultPromise;

        @BeforeEach
        public void prepareTests() {
            defaultRequest = new IncomingPromisesRequest("User");
            defaultPromise = new Promise();
            defaultPromise.setTitle("Test");
            Friend friend = new Friend();
            friend.setName("Joe");
            defaultPromise.setWhom(friend);
            User user = new User();
            user.setUsername("User");
            defaultPromise.setWho(user);
            defaultPromise.setKept(null);
            defaultPromise.setTillDay(LocalDate.now());
            defaultPromise.setTillTime(LocalTime.now());
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

        @Test
        @DisplayName("Should get all promises with valid data")
        public void shouldGetPromisesWithValidData() {

            Mockito.when(promiseRepository.findAllNullKeptPromisesForUserWithDeadlineBefore(
                    "User", ArgumentMatchers.any(), ArgumentMatchers.any()))
                    .thenReturn(List.of(defaultPromise, defaultPromise, defaultPromise));

            IncomingPromisesResponse response = timelapseService.incomingPromises(defaultRequest);

            Assertions.assertThat(response.getPromises()).hasSize(3);
            Assertions.assertThat(response.getPromises())
                    .flatExtracting(IncomingPromisesResponse.PromiseData::getFriendName)
                    .isEqualTo(defaultPromise.getWhom().getName());
            Assertions.assertThat(response.getPromises())
                    .flatExtracting(IncomingPromisesResponse.PromiseData::getTitle)
                    .isEqualTo(defaultPromise.getTitle());
            Assertions.assertThat(response.getPromises())
                    .flatExtracting(IncomingPromisesResponse.PromiseData::getTillDate)
                    .isEqualTo(defaultPromise.getTillDay());
            Assertions.assertThat(response.getPromises())
                    .flatExtracting(IncomingPromisesResponse.PromiseData::getTillTime)
                    .isEqualTo(defaultPromise.getTillTime());

        }
    }

}