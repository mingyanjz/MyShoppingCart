package onlineShop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomePageController {

	// handle /index, get request, return a ModelAndView "index" to DispatcherServelet
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String sayIndex() {
		return "index";
	}

	// handle /login
	@RequestMapping(value = "/login")
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
		ModelAndView modelAndView = new ModelAndView();
		// set modelAndView as login
		modelAndView.setViewName("login");

		// check login failure
		if (error != null) {
			modelAndView.addObject("error", "Invalid email and password");
		}
		// check logout
		if (logout != null) {
			modelAndView.addObject("logout", "Successfully logged out");
		}

		return modelAndView;
	}
	
	//handle about us
	@RequestMapping(value = "/aboutus", method = RequestMethod.GET)
	public String sayAboutUs() {
		return "aboutUs";
	}
	
//	//handle getAllProducts
//		@RequestMapping(value = "/getAllProducts", method = RequestMethod.GET)
//		public String sayGetAllProduct() {
//			return "aboutUs";
//		}
}
