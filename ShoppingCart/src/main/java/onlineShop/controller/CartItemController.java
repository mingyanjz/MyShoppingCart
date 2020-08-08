package onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import onlineShop.dataBaseModel.Cart;
import onlineShop.dataBaseModel.CartItem;
import onlineShop.dataBaseModel.Customer;
import onlineShop.dataBaseModel.Product;
import onlineShop.service.CartItemService;
import onlineShop.service.CartService;
import onlineShop.service.CustomerService;
import onlineShop.service.ProductService;

@Controller
public class CartItemController {
	@Autowired
	CartItemService cartItemService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CartService cartService;
	
	@RequestMapping(value = "/cart/add/{productId}")
	@ResponseStatus(value = HttpStatus.CREATED)
	public void addCartItem(@PathVariable(value = "productId") int productId) {
		Product product = productService.getProductByProductId(productId);
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		String emailId = user.getName();
		Customer customer = customerService.getCustomerByEmailId(emailId);
		Cart cart = customer.getCart();
		
		//check if cartItem exist
		for (CartItem cartItem : cart.getCartItem()) {
			if (cartItem.getProduct().getId() == productId) {
				cartItem.setQuantity(cartItem.getQuantity() + 1);
				cartItem.setPrice(product.getProductPrice() * cartItem.getQuantity());
				cartItemService.updateCartItem(cartItem);
				return;
			}
		}
		
		//cartItem does not exist, create new cartItem
		CartItem cartItem = new CartItem();
		cartItem.setCart(cart);
		cartItem.setPrice(product.getProductPrice());
		cartItem.setQuantity(1);
		cartItem.setProduct(product);
		cartItemService.addCartItem(cartItem);		
	}
	
	@RequestMapping(value = "/cart/removeCartItem/{cartItemId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeCartItem(@PathVariable(value = "cartItemId") int cartItemId) {
   	 cartItemService.deleteCartItem(cartItemId);
    }

    @RequestMapping(value = "/cart/removeAllItems/{cartId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeAllCartItems(@PathVariable(value = "cartId") int cartId) {
   	 Cart cart = cartService.getCartByCartId(cartId);
   	 cartItemService.deleteAllCartItem(cart);
    }

	
	
}
