package honestit.projects.promises.simple.timelapse.impl;

import honestit.projects.promises.simple.promises.domain.Promise;
import honestit.projects.promises.simple.promises.domain.PromiseRepository;
import honestit.projects.promises.simple.timelapse.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j @RequiredArgsConstructor
public class DefaultTimelapseService implements TimelapseService {

    private final PromiseRepository promiseRepository;

    @Override
    public OutdatedPromisesResponse outdatedPromises(OutdatedPromisesRequest request) {
        return null;
    }

    @Override
    public IncomingPromisesResponse incomingPromises(IncomingPromisesRequest request) {

        LocalDate tillDate = LocalDate.now();
        LocalTime tillTime = LocalTime.now();
        List<Promise> promises = promiseRepository.findAllNullKeptPromisesForUserWithDeadlineBefore(request.getUsername(), tillDate, tillTime);

        IncomingPromisesResponse response = new IncomingPromisesResponse();
        promises.stream()
                .map(
                promise -> IncomingPromisesResponse.PromiseData.builder()
                        .title(promise.getTitle())
                        .friendName(promise.getWhom().getName())
                        .tillDate(promise.getTillDay())
                        .tillTime(promise.getTillTime())
                        .build())
                .forEach(response.getPromises()::add);
        return response;
    }
}
