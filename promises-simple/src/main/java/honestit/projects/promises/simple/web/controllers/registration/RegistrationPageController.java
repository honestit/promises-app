package honestit.projects.promises.simple.web.controllers.registration;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import honestit.projects.promises.simple.users.UserRegisterRequest;
import honestit.projects.promises.simple.users.UserRegisterResponse;
import honestit.projects.promises.simple.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/register")
@Slf4j
@RequiredArgsConstructor
public class RegistrationPageController {

	private final UserService userService;

	@GetMapping
	public String prepareRegistrationPage(Model model) {
		model.addAttribute("form", new RegistrationForm());
		return "/registration/form";
	}

	@PostMapping
	public String processRegistrationPage(@ModelAttribute("form") @Valid RegistrationForm form, BindingResult results) {
		log.debug("Registration data: {}", form);
		if (results.hasErrors()) {
			return "/registration/form";
		}

		try {
			UserRegisterRequest request = new UserRegisterRequest(form.getUsername(), null, null, form.getPassword(),
					form.getEmail());
			UserRegisterResponse response = userService.register(request);
			log.debug("Response from UserService: {}", response);
			return "redirect:/login";
		} catch (RuntimeException ex) {
			results.reject(null, ex.getLocalizedMessage());
			return "/registration/form";
		}
	}

}
