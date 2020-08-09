package onlineShop.dao;

import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import onlineShop.dataBaseModel.Cart;
import onlineShop.dataBaseModel.CartItem;

@Repository
public class CartDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	public Cart getCartByCartId(int cartId) {
		Cart cart = null;	
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			cart = (Cart) session.get(Cart.class, cartId);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cart;
	}
	
	public Cart validateCart(int cartId) throws IOException {
		Cart cart = getCartByCartId(cartId);
		if (cart == null || cart.getCartItem().size() == 0) {
			throw new IOException(cartId + "");
		}
		updateTotalPrice(cart);
		return cart;
	}
	
	private void updateTotalPrice(Cart cart) {
		double totalPrice = getSalesOrderTotal(cart);
		cart.setTotalPrice(totalPrice);
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.saveOrUpdate(cart);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	private double  getSalesOrderTotal(Cart cart) {
		double totalPrice = 0;
		for (CartItem cartItem : cart.getCartItem()) {
			totalPrice += cartItem.getPrice();
		}
		return totalPrice;
	}
}
