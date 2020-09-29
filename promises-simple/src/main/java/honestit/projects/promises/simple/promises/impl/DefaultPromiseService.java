package honestit.projects.promises.simple.promises.impl;

import honestit.projects.promises.simple.friends.CheckFriendRequest;
import honestit.projects.promises.simple.friends.CheckFriendResponse;
import honestit.projects.promises.simple.friends.FriendService;
import honestit.projects.promises.simple.promises.*;
import honestit.projects.promises.simple.promises.domain.Promise;
import honestit.projects.promises.simple.promises.domain.PromiseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j @RequiredArgsConstructor
public class DefaultPromiseService implements PromiseService {

    private final PromiseRepository promiseRepository;
    private final FriendService friendService;

    @Override
    public MakePromiseResponse makePromise(MakePromiseRequest request) {
        log.debug("Data to register promise: {}", request);
        Promise promise = new Promise();
        promise.setTitle(request.getPromiseTitle());
        promise.setTillDay(request.getPromiseDeadline().toLocalDate());
        promise.setTillTime(request.getPromiseDeadline().toLocalTime());

        CheckFriendRequest friendRequest = new CheckFriendRequest(request.getFriendName(), request.getUsername());
        CheckFriendResponse response = friendService.checkFriend(friendRequest);

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
