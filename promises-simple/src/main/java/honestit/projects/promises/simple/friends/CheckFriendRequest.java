package honestit.projects.promises.simple.friends;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CheckFriendRequest {

    private String friendName;
    private String username;
}
