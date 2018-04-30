package sw.arch.sessionmanagement.service;

import java.util.List;

import sw.arch.sessionmanagement.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
	List<User> getAllUsers();
}
