package onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onlineShop.dao.CustomerDao;
import onlineShop.dataBaseModel.Customer;

@Service
public class CustomerService {
	@Autowired
	private CustomerDao customerDao;
	
	public void addCustomer(Customer customer) {
		customerDao.addCustomer(customer);
	}
	
	public Customer getCustomerByEmailId(String emailId) {
		return customerDao.getCustomerByEmailId(emailId);
	}
}
