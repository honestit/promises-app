package honestit.projects.promises.simple.web.controllers.promises;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

@Data @AllArgsConstructor @NoArgsConstructor
public class MakePromiseForm {

    @NotBlank
    @Size(min = 3, max = 12)
    private String friendName;
    @NotBlank
    @Size(min = 3)
    private String title;
    private String content;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tillDate;
    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime tillTime;
}
