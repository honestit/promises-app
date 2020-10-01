package honestit.projects.promises.simple.timelapse.impl;

import honestit.projects.promises.simple.friends.domain.Friend;
import honestit.projects.promises.simple.promises.domain.Promise;
import honestit.projects.promises.simple.promises.domain.PromiseRepository;
import honestit.projects.promises.simple.timelapse.IncomingPromisesRequest;
import honestit.projects.promises.simple.timelapse.IncomingPromisesResponse;
import honestit.projects.promises.simple.users.domain.User;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.assertj.core.internal.Conditions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalMatchers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@DisplayName("DefaultTimelapseService unit tests")
class DefaultTimelapseServiceTest {

    private DefaultTimelapseService timelapseService;
    private PromiseRepository promiseRepository;

    @BeforeEach
    public void prepareTests() {
        promiseRepository = Mockito.mock(PromiseRepository.class);
        timelapseService = new DefaultTimelapseService(promiseRepository);
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
                    ArgumentMatchers.eq(defaultRequest.getUsername()), ArgumentMatchers.any(), ArgumentMatchers.any()))
                    .thenReturn(new ArrayList<>());

            timelapseService.incomingPromises(defaultRequest);

            Mockito.verify(promiseRepository, Mockito.times(1))
                    .findAllNullKeptPromisesForUserWithDeadlineBefore(
                            ArgumentMatchers.eq(defaultRequest.getUsername()), ArgumentMatchers.any(), ArgumentMatchers.any());
        }

        @Test
        @DisplayName("Should get all promises with valid data")
        public void shouldGetPromisesWithValidData() {

            Mockito.when(promiseRepository.findAllNullKeptPromisesForUserWithDeadlineBefore(
                    ArgumentMatchers.anyString(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                    .thenReturn(List.of(defaultPromise, defaultPromise, defaultPromise));

            IncomingPromisesResponse response = timelapseService.incomingPromises(defaultRequest);

            Assertions.assertThat(response.getPromises()).hasSize(3);
            Assertions.assertThat(response.getPromises())
                    .extracting(IncomingPromisesResponse.PromiseData::getFriendName)
                    .containsOnly(defaultPromise.getWhom().getName());
            Assertions.assertThat(response.getPromises())
                    .extracting(IncomingPromisesResponse.PromiseData::getTitle)
                    .containsOnly(defaultPromise.getTitle());
            Assertions.assertThat(response.getPromises())
                    .extracting(IncomingPromisesResponse.PromiseData::getTillDate)
                    .containsOnly(defaultPromise.getTillDay());
            Assertions.assertThat(response.getPromises())
                    .extracting(IncomingPromisesResponse.PromiseData::getTillTime)
                    .containsOnly(defaultPromise.getTillTime());

        }
    }

}