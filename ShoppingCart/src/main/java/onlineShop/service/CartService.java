package onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onlineShop.dao.CartDao;
import onlineShop.dataBaseModel.Cart;

@Service
public class CartService {
	@Autowired
	CartDao cartDao;
	public Cart getCartByCartId(int cartId) {
		return cartDao.getCartByCartId(cartId);
	}	
}
