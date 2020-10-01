package honestit.projects.promises.simple.timelapse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class IncomingPromisesResponse {

    private List<PromiseData> promises = new ArrayList<>();

    public static class PromiseData {

    }

}
