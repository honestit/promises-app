package honestit.projects.promises.simple.timelapse.impl;

import honestit.projects.promises.simple.friends.domain.Friend;
import honestit.projects.promises.simple.promises.domain.Promise;
import honestit.projects.promises.simple.promises.domain.PromiseRepository;
import honestit.projects.promises.simple.timelapse.*;
import honestit.projects.promises.simple.users.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j @RequiredArgsConstructor
public class DefaultTimelapseService implements TimelapseService {

    private final PromiseRepository promiseRepository;

    @Override
    public OutdatedPromisesResponse outdatedPromises(OutdatedPromisesRequest request) {
        return null;
    }

//    {
//        IncomingPromisesResponse.PromiseData promiseData = IncomingPromisesResponse.PromiseData.builder()
//                .title("Abc").friendName("Joe").tillDate(LocalDate.now()).tillTime(LocalTime.now()).build();
//    }

    @Override
    public IncomingPromisesResponse incomingPromises(IncomingPromisesRequest request) {



        return new IncomingPromisesResponse();
    }
}
