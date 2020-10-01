package honestit.projects.promises.simple.timelapse.impl;

import honestit.projects.promises.simple.timelapse.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j @RequiredArgsConstructor
public class DefaultTimelapseService implements TimelapseService {

    @Override
    public OutdatedPromisesResponse outdatedPromises(OutdatedPromisesRequest request) {
        return null;
    }

    @Override
    public IncomingPromisesResponse incomingPromises(IncomingPromisesRequest request) {
        return null;
    }
}
