package sw.arch.sessionmanagement.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import sw.arch.sessionmanagement.model.Role;
import sw.arch.sessionmanagement.model.User;
import sw.arch.sessionmanagement.repository.RoleRepository;
import sw.arch.sessionmanagement.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public User findUserByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setActive(1);
//		Role role = roleRepository.findByRole("ADMIN");
		Role role = roleRepository.findByRole("USER");
		user.setRoles(new HashSet<Role>(Arrays.asList(role)));
		userRepository.save(user);
	}
	
	@Override
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}

}
