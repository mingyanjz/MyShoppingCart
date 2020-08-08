package onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import onlineShop.dataBaseModel.Customer;
import onlineShop.service.CustomerService;



@Controller
public class RegistrationController {
	
	@Autowired
	private CustomerService customerService;
	
	//return a registration Form
	@RequestMapping(value = "/customer/registration", method = RequestMethod.GET)
	public ModelAndView registrationForm() {
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName("register");
		Customer customer = new Customer();
		modelAndView.addObject("customer", customer);
		return modelAndView;
	}
	
	@RequestMapping(value = "/customer/registration", method = RequestMethod.POST)
	public ModelAndView register(@ModelAttribute(value = "customer") Customer customer, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView();		
		//has error or used emailId
		if (result.hasErrors() || customerService.getCustomerByEmailId(customer.getUser().getEmailId()) != null) {			
			modelAndView.setViewName("register");
			return modelAndView;
		}
		//valid customer
		customerService.addCustomer(customer);
		modelAndView.setViewName("login");
		modelAndView.addObject("registrationSuccess", "Registered Successfully. Please login with username and password");
		return modelAndView;
	}

}
