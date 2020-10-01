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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

        User user = userRepository.getByUsername(request.getUsername());
        promise.setWho(user);

        CheckFriendRequest friendRequest = new CheckFriendRequest(request.getFriendName(), request.getUsername());
        CheckFriendResponse checkFriendResponse = friendService.checkFriend(friendRequest);
        Friend friend = new Friend();

        if(!checkFriendResponse.getAlreadyFriend()){
            MakeFriendRequest makeFriendRequest = new MakeFriendRequest(request.getFriendName(), request.getUsername());
            MakeFriendResponse makeFriendResponse = friendService.makeFriend(makeFriendRequest);
            friend.setId(makeFriendResponse.getFriendId());
        } else {
            friend.setId(checkFriendResponse.getFriendId());
        }

        promise.setWhom(friend);
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
        Promise promiseById = promiseRepository.getOne(request.getPromiseId());

        if(promiseById.getKept() != null){
            throw new IllegalStateException("Promise with this id is already kept");
        }

        User user = promiseById.getWho();
        String username = user.getUsername();
        String requestUsername = request.getUsername();

        if(!username.equals(requestUsername)){
            throw new IllegalStateException("This promise belongs to another user");
        }

        promiseById.setKeptDate(LocalDateTime.now());

        LocalDate tillDay = promiseById.getTillDay();
        LocalTime tillTime = promiseById.getTillTime();
        LocalDateTime tillDayAndTime = tillDay.atTime(tillTime);

        if(tillDayAndTime.isAfter(promiseById.getKeptDate())) {
            promiseById.setKept(true);
        } else {
            promiseById.setKept(false);
        }

        promiseRepository.save(promiseById);

        return new KeptPromiseResponse(!promiseById.getKept());
    }
}
