/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.common.micro.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.common.micro.social.data.UserRepository;

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