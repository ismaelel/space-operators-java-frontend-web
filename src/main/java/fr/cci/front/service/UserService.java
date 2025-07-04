//package fr.cci.front.service;
//
//import java.util.List;
//
//import fr.cci.front.model.PlayerModel;
//import org.springframework.stereotype.Service;
//
//import fr.cci.front.datalayer.UserProxy;
////import fr.cci.front.model.UserModel;
//
//@Service
//public class UserService {
//
//	private UserProxy userProxy;
//
//	public UserService(UserProxy userProxy) {
//		this.userProxy = userProxy;
//	}
//
//	public List<UserModel> get() {
//		return userProxy.getUsers();
//	}
//
//	public void add(UserModel user) {
//		userProxy.add(user);
//	}
//
//	public String login(UserModel user) {
//		return userProxy.login(user);
//	}
//
//
//
//	public UserModel getUserProfile(String token) {
//		return userProxy.getUserProfile(token);
//	}
//
//	public List<UserModel> getAllUsers() {
//		return userProxy.getUsers();
//	}
//}