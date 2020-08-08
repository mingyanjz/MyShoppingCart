package onlineShop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onlineShop.dao.ProductDao;
import onlineShop.dataBaseModel.Product;

@Service
public class ProductService {
	@Autowired
	ProductDao productDao;
	
	public List<Product> getAllProduct() {
		return productDao.getAllProducts();
	}
	
	public Product getProductByProductId(int productId) {
		return productDao.getProductByProductId(productId);
	}
	
	public void deleteProductByProductId(int productId) {
		productDao.deleteProductByProductId(productId);
	}
	
	public void updateProduct(Product product) {
		productDao.updateProduct(product);
	}
	
	public void addProduct(Product product) {
		productDao.addProduct(product);
	}
}
