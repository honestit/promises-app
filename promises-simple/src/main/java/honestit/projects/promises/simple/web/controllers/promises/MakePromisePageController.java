package honestit.projects.promises.simple.web.controllers.promises;

import honestit.projects.promises.simple.promises.MakePromiseRequest;
import honestit.projects.promises.simple.promises.MakePromiseResponse;
import honestit.projects.promises.simple.promises.PromiseService;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
@RequestMapping("/make-promise")
@Slf4j
@RequiredArgsConstructor
public class MakePromisePageController {

    private final PromiseService promiseService;

    @GetMapping
    public String prepareMakePromisePage(Model model){
        model.addAttribute("form", new MakePromiseForm());
        return "/promises/form";
    }

    @PostMapping
    public String processMakePromisePage(@ModelAttribute("form") @Valid MakePromiseForm form, BindingResult result, Authentication authentication){
        log.debug("Make promise data: {}", form);
        if(result.hasErrors()) {
            return "/promises/form";
        }

        try {
            MakePromiseRequest makePromiseRequest = new MakePromiseRequest();
            makePromiseRequest.setFriendName(form.getFriendName());
            makePromiseRequest.setPromiseTitle(form.getTitle());

            makePromiseRequest.setUsername(authentication.getName());

            LocalDate tillDate = form.getTillDate();
            LocalTime tillTime = form.getTillTime();
            LocalDateTime promiseDeadLine = tillDate.atTime(tillTime);
            makePromiseRequest.setPromiseDeadline(promiseDeadLine);

            MakePromiseResponse makePromiseResponse = promiseService.makePromise(makePromiseRequest);
            log.debug("Response from MakePromiseService : {}", makePromiseResponse);
            return"redirect:/make-promise?msg=" + UriUtils.encode("Promise successfully created", "UTF-8");

        } catch (RuntimeException ex) {
            log.warn("Error while making friend", ex);
            result.reject(null, ex.getMessage());
            return "/promises/form";
        }
    }
}
