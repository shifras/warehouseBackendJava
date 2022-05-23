package lt.bit.sandelys.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lt.bit.sandelys.entities.User;
import lt.bit.sandelys.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=this.findByUsername(username);
		if (user==null) {
			throw new UsernameNotFoundException("Toks vartotojas neegzistuoja.");
		}
		return new SecUserDetails(user);
	}
}
