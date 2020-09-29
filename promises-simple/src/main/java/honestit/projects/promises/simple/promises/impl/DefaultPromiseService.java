package honestit.projects.promises.simple.promises.impl;

import honestit.projects.promises.simple.friends.*;
import honestit.projects.promises.simple.friends.domain.Friend;
import honestit.projects.promises.simple.promises.*;
import honestit.projects.promises.simple.promises.domain.Promise;
import honestit.projects.promises.simple.promises.domain.PromiseRepository;
import honestit.projects.promises.simple.users.domain.User;
import honestit.projects.promises.simple.users.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j @RequiredArgsConstructor
public class DefaultPromiseService implements PromiseService {

    private final PromiseRepository promiseRepository;
    private final FriendService friendService;
    private final UserRepository userRepository;

    @Override
    public MakePromiseResponse makePromise(MakePromiseRequest request) {
        log.debug("Data to register promise: {}", request);
        Promise promise = new Promise();
        promise.setTitle(request.getPromiseTitle());
        promise.setTillDay(request.getPromiseDeadline().toLocalDate());
        promise.setTillTime(request.getPromiseDeadline().toLocalTime());

        CheckFriendRequest friendRequest = new CheckFriendRequest(request.getFriendName(), request.getUsername());
        CheckFriendResponse response = friendService.checkFriend(friendRequest);
        Friend friend = new Friend();
        User user = userRepository.getByUsername(request.getUsername());

        if(!response.getAlreadyFriend()){
            MakeFriendRequest makeFriendRequest = new MakeFriendRequest(request.getFriendName(), request.getUsername());
            MakeFriendResponse makeFriendResponse = friendService.makeFriend(makeFriendRequest);
            friend.setId(makeFriendResponse.getFriendId());
        } else {
            friend.setId(response.getFriendId());

        }

        promise.setWhom(friend);
        promise.setWho(user);
        log.debug("Promise to save {}", promise);
        promiseRepository.save(promise);
        log.debug("Saved promise: {}", promise);
        return new MakePromiseResponse(promise.getId());
    }

    @Override
    public MakePromisesResponse makePromises(MakePromiseRequest request, MakePromiseRequest... requests) {
        return null;
    }

    @Override
    public KeptPromiseResponse keptPromise(KeptPromiseRequest request) {
        return null;
    }
}
