package honestit.projects.promises.simple.web.controllers.friends;

import honestit.projects.promises.simple.friends.FriendService;
import honestit.projects.promises.simple.friends.MakeFriendRequest;
import honestit.projects.promises.simple.friends.MakeFriendResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriUtils;

import javax.validation.Valid;

@Controller
@RequestMapping("/create-friend")
@Slf4j
@RequiredArgsConstructor
public class CreateFriendPageController {

    private final FriendService friendService;

    @GetMapping
    public String prepareCreateFriendPage(Model model) {
        model.addAttribute("form", new CreateFriendForm());
        return "/friends/form";
    }

    @PostMapping
    public String processCreateFriendPage(@ModelAttribute("form") @Valid CreateFriendForm createFriendForm, BindingResult bindingResult, Authentication authentication) {
        log.debug("Create friend data: {}", createFriendForm);
        if (bindingResult.hasErrors()) {
            return "/friends/form";
        }

        try {
            MakeFriendRequest request = new MakeFriendRequest(createFriendForm.getFriendName(), authentication.getName());
            MakeFriendResponse makeFriendResponse = friendService.makeFriend(request);
            log.debug("Response from FriendService: {}", makeFriendResponse);
            return "redirect:/create-friend?msg=" + UriUtils.encode("Friend successfully created", "UTF-8");
        } catch (RuntimeException ex) {
            log.warn("Error while creating friend", ex);
            bindingResult.reject(null, ex.getMessage());
            return "/friends/form";
        }
    }
}
