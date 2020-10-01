package honestit.projects.promises.simple.promises.impl;

import honestit.projects.promises.simple.friends.CheckFriendResponse;
import honestit.projects.promises.simple.friends.FriendService;
import honestit.projects.promises.simple.friends.MakeFriendResponse;
import honestit.projects.promises.simple.friends.domain.Friend;
import honestit.projects.promises.simple.promises.KeptPromiseRequest;
import honestit.projects.promises.simple.promises.KeptPromiseResponse;
import honestit.projects.promises.simple.promises.MakePromiseRequest;
import honestit.projects.promises.simple.promises.MakePromiseResponse;
import honestit.projects.promises.simple.promises.domain.Promise;
import honestit.projects.promises.simple.promises.domain.PromiseRepository;
import honestit.projects.promises.simple.users.domain.User;
import honestit.projects.promises.simple.users.domain.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDateTime;

@DisplayName("Promises Service Tests")
class DefaultPromiseServiceTest {

    private DefaultPromiseService promiseService;
    private PromiseRepository promiseRepository;
    private UserRepository userRepository;
    private FriendService friendService;

    @BeforeEach
    public void prepareTest() {
        promiseRepository = Mockito.mock(PromiseRepository.class);
        friendService = Mockito.mock(FriendService.class);
        userRepository = Mockito.mock(UserRepository.class);
        promiseService = new DefaultPromiseService(promiseRepository, friendService, userRepository);
    }

    @Nested
    @DisplayName("Promises Service Tests - Method make promise tests")
    class MakePromiseTest {

        private MakePromiseRequest defaultRequest = new MakePromiseRequest("User", "Joe", "I will love you forever!", LocalDateTime.now().plusDays(1));

        @Test
        @DisplayName("When make promise then promise should be saved")
        public void whenMakePromiseThenPromiseShouldBeSaved() {
            Mockito.when(promiseRepository.save(ArgumentMatchers.any(Promise.class))).then(invocation -> {
                invocation.getArgument(0, Promise.class).setId(1L);
                return invocation.getArgument(0, Promise.class);
            });
            Mockito.when(friendService.checkFriend(ArgumentMatchers.any())).thenReturn(new CheckFriendResponse(true, 1L));

            MakePromiseResponse response = promiseService.makePromise(defaultRequest);

            Assertions.assertThat(response).isNotNull();
            Assertions.assertThat(response).extracting("promiseId").isNotNull();
        }

        @Test
        @DisplayName("When make promise friend should be checked")
        public void whenMakePromiseFriendShouldBeChecked() {
            Mockito.when(friendService.checkFriend(ArgumentMatchers.any())).thenReturn(new CheckFriendResponse(true, 1L));

            promiseService.makePromise(defaultRequest);

            Mockito.verify(friendService, Mockito.times(1)).checkFriend(ArgumentMatchers.any());
        }

        @Test
        @DisplayName("When make promise to new friend should make friend")
        public void whenMakePromiseToNewFriendShouldMakeFriend() {
            Mockito.when(friendService.checkFriend(ArgumentMatchers.any())).thenReturn(new CheckFriendResponse(false, null));
            Mockito.when(friendService.makeFriend(ArgumentMatchers.any())).thenReturn(new MakeFriendResponse(1L));

            promiseService.makePromise(defaultRequest);

            Mockito.verify(friendService, Mockito.times(1)).makeFriend(ArgumentMatchers.any());
        }

        @Test
        @DisplayName("When make promise then promise should have a friend")
        public void whenMakePromiseThenPromiseShouldHaveAFriend() {
            ArgumentCaptor<Promise> captor = ArgumentCaptor.forClass(Promise.class);
            Mockito.when(promiseRepository.save(captor.capture())).thenReturn(new Promise());
            Mockito.when(friendService.checkFriend(ArgumentMatchers.any())).thenReturn(new CheckFriendResponse(true, 1L));

            MakePromiseResponse response = promiseService.makePromise(defaultRequest);

            Assertions.assertThat(captor.getValue()).isNotNull();
            Assertions.assertThat(captor.getValue().getWhom())
                    .isNotNull()
                    .extracting(Friend::getId)
                    .isNotNull()
                    .isEqualTo(1L);

        }

        @Test
        @DisplayName("When make promise then promise should have a user")
        public void whenMakePromiseThenPromiseShouldHaveAUser() {
            ArgumentCaptor<Promise> captor = ArgumentCaptor.forClass(Promise.class);
            Mockito.when(promiseRepository.save(captor.capture())).thenReturn(new Promise());
            Mockito.when(friendService.checkFriend(ArgumentMatchers.any())).thenReturn(new CheckFriendResponse(true, 1L));
            User user = new User();
            user.setId(1L);
            user.setUsername("User");
            Mockito.when(userRepository.getByUsername("User")).thenReturn(user);

            promiseService.makePromise(defaultRequest);

            Assertions.assertThat(captor.getValue()).isNotNull();
            Assertions.assertThat(captor.getValue().getWho())
                    .isNotNull()
                    .extracting(User::getId)
                    .isNotNull()
                    .isEqualTo(1L);
            Assertions.assertThat(captor.getValue().getWho())
                    .extracting(User::getUsername)
                    .isNotNull()
                    .isEqualTo("User");
        }
    }

    @Nested
    @DisplayName("Promises Service tests - Method kept promise")
    class KeptPromiseTest {

        private final KeptPromiseRequest defaultRequest = new KeptPromiseRequest(1L, "User");

        @Test
        @DisplayName("When kept promise then should get valid response")
        public void whenKeptPromiseThenShouldGetValidResponse() {
            KeptPromiseResponse response = promiseService.keptPromise(defaultRequest);

            Assertions.assertThat(response).isNotNull();
            Assertions.assertThat(response.getOutdated()).isNotNull();

        }

    }
}