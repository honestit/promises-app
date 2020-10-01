package honestit.projects.promises.simple.timelapse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class IncomingPromisesResponse {

    private List<PromiseData> promises = new ArrayList<>();

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class PromiseData {

        private String friendName;
        private String title;
        private LocalDate tillDate;
        private LocalTime tillTime;

    }

}
