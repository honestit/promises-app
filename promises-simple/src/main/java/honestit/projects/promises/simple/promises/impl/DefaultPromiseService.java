package honestit.projects.promises.simple.promises.impl;

import honestit.projects.promises.simple.promises.*;
import honestit.projects.promises.simple.promises.domain.PromiseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j @RequiredArgsConstructor
public class DefaultPromiseService implements PromiseService {

    private final PromiseRepository promiseRepository;

    @Override
    public MakePromiseResponse makePromise(MakePromiseRequest request) {
        return null;
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
