package onlineShop.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import onlineShop.dataBaseModel.Cart;
import onlineShop.dataBaseModel.CartItem;

@Repository
public class CartItemDao {
	@Autowired
	SessionFactory sessionFactory;
	
	public void addCartItem(CartItem cartItem) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.save(cartItem);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	public void deleteCartItemByCartItemId(int cartItemId) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			CartItem cartItem = (CartItem) session.get(CartItem.class, cartItemId);
			//delete cartItem from cart's list
			cartItem.getCart().getCartItem().remove(cartItem);
			session.delete(cartItem);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	public void updateCartItem(CartItem cartItem) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(cartItem);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	public void deleteAllCartItem(Cart cart) {
		for (CartItem cartItem : cart.getCartItem()) {
			deleteCartItemByCartItemId(cartItem.getId());
		}
	}
}
