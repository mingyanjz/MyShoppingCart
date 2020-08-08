package onlineShop.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import onlineShop.dataBaseModel.Authorities;
import onlineShop.dataBaseModel.Cart;
import onlineShop.dataBaseModel.Customer;
import onlineShop.dataBaseModel.User;

@Repository
public class CustomerDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void addCustomer(Customer customer) {
		//set enable, emailId, password in user
		customer.getUser().setEnabled(true);

		//set authorities
		Authorities authorities = new Authorities();
		authorities.setAuthorities("ROLE_USER");
		authorities.setEmailId(customer.getUser().getEmailId());
		
		//set cart
		Cart cart = new Cart();
		cart.setCustomer(customer);
		customer.setCart(cart);
		
		Session session = null;
		
		try {
			//upload to DB
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(customer);
			session.save(authorities);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			//rollback if error occurs
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}		
		
	}
	
	public Customer getCustomerByEmailId(String emailId) {
		User user = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
			Root<User> root = criteriaQuery.from(User.class);
			criteriaQuery.select(root).where(builder.equal(root.get("emailId"), emailId));
			user = session.createQuery(criteriaQuery).getSingleResult();
            session.getTransaction().commit();			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		if (user != null) {
			return user.getCustomer();
		}
		return null;
	}
}
