package onlineShop.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import onlineShop.dataBaseModel.Product;
import onlineShop.dataBaseModel.User;

@Repository
public class ProductDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void addProduct(Product product) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			//save() method will fail if the primary key is already persistent
			session.save(product);
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
	
	public void updateProduct(Product product) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			//saveOrUpdate can either INSERT or UPDATE based upon existence of record
			session.saveOrUpdate(product);
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
	
	public void deleteProductByProductId(int productId) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Product product = (Product) session.get(Product.class, productId);
			session.delete(product);
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
	
	public Product getProductByProductId(int productId) {
		Product product = null;
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			product = (Product) session.get(Product.class, productId);
            session.getTransaction().commit();		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return product;
	}
	
	public List<Product> getAllProducts() {
		List<Product> products = new ArrayList<>();
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();			
			CriteriaQuery<Product> criteriaQuery = builder.createQuery(Product.class);
			Root<Product> root = criteriaQuery.from(Product.class);
			criteriaQuery.select(root);
			products = session.createQuery(criteriaQuery).getResultList();
            session.getTransaction().commit();		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;		
	}
}
