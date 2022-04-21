package ru.common.micro.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.common.micro.social.dao.UserRepository;

@Component
@RepositoryEventHandler(User.class) // <1>
public class SpringDataRestEventHandler {

	private final AdminRepository adminRepository;

	@Autowired
	public SpringDataRestEventHandler(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}

	@HandleBeforeCreate
	@HandleBeforeSave
	public void applyUserInformationUsingSecurityContext(User user) {

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Admin admin = this.adminRepository.findByName(name);
		if (admin == null) {
			Admin newAdmin = new Admin();
			newAdmin.setName(name);
			newAdmin.setRoles(new String[]{"ROLE_MANAGER"});
			admin = this.adminRepository.save(newAdmin);
		}
		user.setAdmin(admin);
	}
}
