package onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import onlineShop.dao.CartItemDao;
import onlineShop.dataBaseModel.Cart;
import onlineShop.dataBaseModel.CartItem;

@Service
public class CartItemService {
	@Autowired
	CartItemDao cartItemDao;
	
	public void addCartItem(CartItem cartItem) {
		cartItemDao.addCartItem(cartItem);
	}
	
	public void updateCartItem(CartItem cartItem) {
		cartItemDao.updateCartItem(cartItem);
	}
	
	public void deleteCartItem(int cartItemId) {
		cartItemDao.deleteCartItemByCartItemId(cartItemId);
	}
	
	public void deleteAllCartItem(Cart cart) {
		cartItemDao.deleteAllCartItem(cart);
	}
}
