package honestit.projects.promises.simple.promises;

public interface PromiseService {
	
	MakePromiseResponse makePromise(MakePromiseRequest request);
	
	MakePromisesResponse makePromises(MakePromiseRequest request, MakePromiseRequest... requests);
	
	KeptPromiseResponse keptPromise(KeptPromiseRequest request);
}
