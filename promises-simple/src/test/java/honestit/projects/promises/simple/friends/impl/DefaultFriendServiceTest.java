package honestit.projects.promises.simple.friends.impl;

import honestit.projects.promises.simple.friends.MakeFriendRequest;
import honestit.projects.promises.simple.friends.MakeFriendResponse;
import honestit.projects.promises.simple.friends.domain.Friend;
import honestit.projects.promises.simple.friends.domain.FriendRepository;
import honestit.projects.promises.simple.users.domain.User;
import honestit.projects.promises.simple.users.domain.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

class DefaultFriendServiceTest {

    private DefaultFriendService friendService;
    private UserRepository userRepository;
    private FriendRepository friendRepository;

    @BeforeEach
    public void prepareTest() {
        friendRepository = Mockito.mock(FriendRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        friendService = new DefaultFriendService(friendRepository, userRepository);
    }

    @Test
    @DisplayName("Make new friend")
    public void makeNewFriend() {
        Mockito.when(friendRepository.save(ArgumentMatchers.any(Friend.class))).thenAnswer(invocation -> {
            invocation.getArgument(0, Friend.class).setId(1L);
            return invocation.getArgument(0, Friend.class);
        });

        MakeFriendRequest request = new MakeFriendRequest("Joe", "User");
        MakeFriendResponse response = friendService.makeFriend(request);

        assertThat(response).isNotNull();
        assertThat(response).extracting("friendId").isNotNull().isEqualTo(1L);
    }

    @Test
    @DisplayName("Make new friend should be connected to user")
    public void makeNewFriendWithConnectUser() {
        Friend friend = new Friend();
        friend.setId(1L);
        User user = new User();
        user.setId(1L);
        user.setUsername("User");
        Mockito.when(friendRepository.save(ArgumentMatchers.any())).thenReturn(friend);
        Mockito.when(userRepository.getByUsername("User")).thenReturn(user);

        MakeFriendRequest request = new MakeFriendRequest("Joe", "User");
        friendService.makeFriend(request);

        ArgumentCaptor<Friend> argumentCaptor = ArgumentCaptor.forClass(Friend.class);
        Mockito.verify(friendRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isNotNull();
        assertThat(argumentCaptor.getValue().getOwner()).isNotNull();
        assertThat(argumentCaptor.getValue().getOwner().getUsername()).isEqualTo("User");
    }
}