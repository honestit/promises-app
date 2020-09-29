package honestit.projects.promises.simple.promises;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
public class MakePromiseRequest {

    private String username;
    private String friendName;
    private String promiseTitle;
    private LocalDateTime promiseDeadline;

}
