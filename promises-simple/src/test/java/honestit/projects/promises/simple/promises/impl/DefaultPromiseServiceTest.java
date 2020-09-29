package honestit.projects.promises.simple.promises.impl;

import honestit.projects.promises.simple.friends.CheckFriendResponse;
import honestit.projects.promises.simple.friends.FriendService;
import honestit.projects.promises.simple.friends.MakeFriendResponse;
import honestit.projects.promises.simple.promises.MakePromiseRequest;
import honestit.projects.promises.simple.promises.MakePromiseResponse;
import honestit.projects.promises.simple.promises.domain.Promise;
import honestit.projects.promises.simple.promises.domain.PromiseRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.time.LocalDateTime;

@DisplayName("Promises Service Tests")
class DefaultPromiseServiceTest {

    private DefaultPromiseService promiseService;
    private PromiseRepository promiseRepository;
    private FriendService friendService;

    @BeforeEach
    public void prepareTest() {
        promiseRepository = Mockito.mock(PromiseRepository.class);
        friendService = Mockito.mock(FriendService.class);
        promiseService = new DefaultPromiseService(promiseRepository, friendService);
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

            MakePromiseResponse response = promiseService.makePromise(defaultRequest);

            Assertions.assertThat(response).isNotNull();
            Assertions.assertThat(response).extracting("promiseId").isNotNull();
        }

        @Test
        @DisplayName("When make promise friend should be checked")
        public void whenMakePromiseFriendShouldBeChecked() {
            Mockito.when(friendService.checkFriend(ArgumentMatchers.any())).thenReturn(new CheckFriendResponse());

            promiseService.makePromise(defaultRequest);

            Mockito.verify(friendService, Mockito.times(1)).checkFriend(ArgumentMatchers.any());
        }

        @Test
        @DisplayName("When make promise to new friend should make friend")
        public void whenMakePromiseToNewFriendShouldMakeFriend() {
            Mockito.when(friendService.checkFriend(ArgumentMatchers.any())).thenReturn(new CheckFriendResponse(false, null));

            promiseService.makePromise(defaultRequest);

            Mockito.verify(friendService, Mockito.times(1)).makeFriend(ArgumentMatchers.any());
        }
    }


}