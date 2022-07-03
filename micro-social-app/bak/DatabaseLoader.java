import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.common.micro.social.dao.UserRepository;
import ru.common.micro.social.dao.dto.User;

@Component
public class DatabaseLoader implements CommandLineRunner {

	private final UserRepository users;
	private final UserRepository managers;

	@Autowired
	public DatabaseLoader(UserRepository userRepository,
						  UserRepository adminRepository) {

		this.users = userRepository;
		this.managers = adminRepository;
	}

	@Override
	public void run(String... strings) throws Exception {

		User greg = this.managers.save(new User("manager", "manager",
							"ROLE_ADMIN"));
		User oliver = this.managers.save(new User("oliver", "gierke",
							"ROLE_ADMIN"));

		SecurityContextHolder.getContext().setAuthentication(
			new UsernamePasswordAuthenticationToken("manager", "doesn't matter",
				AuthorityUtils.createAuthorityList("ROLE_MANAGER")));

		this.users.save(new User("Frodo", "Baggins", "ring bearer", greg));
		this.users.save(new User("Bilbo", "Baggins", "burglar", greg));
		this.users.save(new User("Gandalf", "the Grey", "wizard", greg));

		SecurityContextHolder.getContext().setAuthentication(
			new UsernamePasswordAuthenticationToken("oliver", "doesn't matter",
				AuthorityUtils.createAuthorityList("ROLE_MANAGER")));

		this.users.save(new User("Samwise", "Gamgee", "gardener", oliver));
		this.users.save(new User("Merry", "Brandybuck", "pony rider", oliver));
		this.users.save(new User("Peregrin", "Took", "pipe smoker", oliver));

		SecurityContextHolder.clearContext();
	}
}