package ru.common.micro.social;

import static ru.common.micro.social.WebSocketConfiguration.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(User.class)
public class EventHandler {

	private final SimpMessagingTemplate websocket;

	private final EntityLinks entityLinks;

	@Autowired
	public EventHandler(SimpMessagingTemplate websocket, EntityLinks entityLinks) {
		this.websocket = websocket;
		this.entityLinks = entityLinks;
	}

	@HandleAfterCreate
	public void newUser(User user) {
		this.websocket.convertAndSend(
				MESSAGE_PREFIX + "/newUser", getPath(user));
	}

	@HandleAfterDelete
	public void deleteUser(User user) {
		this.websocket.convertAndSend(
				MESSAGE_PREFIX + "/deleteUser", getPath(user));
	}

	@HandleAfterSave
	public void updateUser(User user) {
		this.websocket.convertAndSend(
				MESSAGE_PREFIX + "/updateUser", getPath(user));
	}

	/**
	 * Take an {@link User} and get the URI using Spring Data REST's {@link EntityLinks}.
	 *
	 * @param user
	 */
	private String getPath(User user) {
		return this.entityLinks.linkForItemResource(user.getClass(),
				user.getId()).toUri().getPath();
	}

}
