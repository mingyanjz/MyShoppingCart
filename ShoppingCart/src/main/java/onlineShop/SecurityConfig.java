package onlineShop;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {

	@Autowired
	private DataSource dataSource;
	
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			//set authorities requirement
			.authorizeRequests()
			.antMatchers("/get*/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
			.antMatchers("/cart/**").hasAuthority("ROLE_USER")
			.antMatchers("/admin*/**").hasAuthority("ROLE_ADMIN")
			.anyRequest().permitAll().and()
			//set login URL is required, default login failure is /login?error
			//redirect to previous page if login successfully
			.formLogin().loginPage("/login").and()
			//set URL the URL that will invoke logout.
			//default logout successful URL id /login?logout
			.logout().logoutUrl("/logout");
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//add in Memory authentication
		auth.inMemoryAuthentication()
			.withUser("mingyan94@yahoo.com")
			.password("123")
			.authorities("ROLE_ADMIN");
		
		//add JDBC authentication
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("SELECT emailId, password, enabled from users WHERE emailId = ?")
			.authoritiesByUsernameQuery("SELECT emailId, authorities FROM authorities WHERE emailId = ?");
		
	}

    @SuppressWarnings("deprecation")
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}


		
}
