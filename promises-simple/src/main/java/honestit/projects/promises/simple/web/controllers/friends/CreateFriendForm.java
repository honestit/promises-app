package honestit.projects.promises.simple.web.controllers.friends;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CreateFriendForm {

    @NotBlank @Size(min = 3, max = 255)
    private String friendName;
}
