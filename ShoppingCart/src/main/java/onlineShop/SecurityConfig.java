package onlineShop;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {

	@Autowired
	private DataSource dataSource;
	
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		//redirect to "/login?error" for authentication failure
			.formLogin().loginPage("/login")
			;
	}
		
}
