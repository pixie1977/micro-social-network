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
package ru.common.micro.social.data;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.common.micro.social.User;

@PreAuthorize("hasRole('ROLE_admin')") // <1>
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	@Override
	@PreAuthorize("#user?.admin == null or #user?.admin?.name == authentication?.name")
	User save(@Param("user") User user);

	@Override
	@PreAuthorize("@userRepository.findById(#id)?.admin?.name == authentication?.name")
	void deleteById(@Param("id") Long id);

	@Override
	@PreAuthorize("#user?.admin?.name == authentication?.name")
	void delete(@Param("user") User user);

}
