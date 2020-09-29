package honestit.projects.promises.simple.timelapse;

public interface TimelapseService {
	
	OutdatedPromisesResponse outdatedPromises(OutdatedPromisesRequest request);
	
	IncomingPromisesResponse incomingPromises(IncomingPromisesRequest request);
}
