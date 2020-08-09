package onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onlineShop.dao.OrderDao;
import onlineShop.dataBaseModel.Order;

@Service
public class OrderService {
	@Autowired
	OrderDao orderDao;
	
	public void addOrder(Order order) {
		orderDao.addOrder(order);
	}
}
