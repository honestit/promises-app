package honestit.projects.promises.simple.timelapse.impl;

import honestit.projects.promises.simple.friends.domain.Friend;
import honestit.projects.promises.simple.promises.domain.Promise;
import honestit.projects.promises.simple.promises.domain.PromiseRepository;
import honestit.projects.promises.simple.timelapse.IncomingPromisesRequest;
import honestit.projects.promises.simple.timelapse.IncomingPromisesResponse;
import honestit.projects.promises.simple.timelapse.IncomingPromisesResponse.PromiseData;
import honestit.projects.promises.simple.users.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("DefaultTimelapseService unit tests")
class DefaultTimelapseServiceTest {

    private DefaultTimelapseService timelapseService;
    private PromiseRepository promiseRepository;

    @BeforeEach
    public void prepareTests() {
        promiseRepository = mock(PromiseRepository.class);
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

            assertThat(response).isNotNull();
        }

        @Test
        @DisplayName("Should return not null promises list")
        public void shouldReturnNotNullPromisesList() {
            IncomingPromisesResponse response = timelapseService.incomingPromises(defaultRequest);

            assertThat(response.getPromises()).isNotNull();
        }

        @Test
        @DisplayName("Should get promises only for current user")
        public void shouldGetPromisesOnlyForCurrentUser() {
            when(promiseRepository.findAllNullKeptPromisesForUserWithDeadlineBefore(
                    eq(defaultRequest.getUsername()), any(), any()))
                    .thenReturn(new ArrayList<>());

            timelapseService.incomingPromises(defaultRequest);

            verify(promiseRepository, times(1))
                    .findAllNullKeptPromisesForUserWithDeadlineBefore(
                            eq(defaultRequest.getUsername()), any(), any());
        }

        @Test
        @DisplayName("Should get all promises with valid data")
        public void shouldGetPromisesWithValidData() {

            when(promiseRepository.findAllNullKeptPromisesForUserWithDeadlineBefore(
                    anyString(), any(), any()))
                    .thenReturn(List.of(defaultPromise, defaultPromise, defaultPromise));

            IncomingPromisesResponse response = timelapseService.incomingPromises(defaultRequest);

            assertThat(response.getPromises()).hasSize(3);
            assertThat(response.getPromises())
                    .extracting(PromiseData::getFriendName)
                    .containsOnly(defaultPromise.getWhom().getName());
            assertThat(response.getPromises())
                    .extracting(PromiseData::getTitle)
                    .containsOnly(defaultPromise.getTitle());
            assertThat(response.getPromises())
                    .extracting(PromiseData::getTillDate)
                    .containsOnly(defaultPromise.getTillDay());
            assertThat(response.getPromises())
                    .extracting(PromiseData::getTillTime)
                    .containsOnly(defaultPromise.getTillTime());

        }
    }

}