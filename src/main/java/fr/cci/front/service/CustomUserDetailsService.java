//package fr.cci.front.service;
//
//import java.util.ArrayList;
//import java.util.List;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import fr.cci.front.datalayer.UserProxy;
//import fr.cci.front.model.UserModel;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//	private UserProxy userProxy;
//
//	public CustomUserDetailsService(UserProxy userProxy) {
//		this.userProxy = userProxy;
//	}
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		UserModel user = this.userProxy.getUserByUsername(username);
//
//		if(user == null) {
//			throw new UsernameNotFoundException(username);
//		}
//
//		return new User(user.getUsername(),
//						user.getPwd(),
//						getGrantedAuthorities(user.getRoles()));
//	}
//
//	private List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
//		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//		roles.forEach(role -> {
//			authorities.add(new SimpleGrantedAuthority("ROLE_"+ role));
//		});
//		return authorities;
//	}
//
//}
