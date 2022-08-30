package ru.common.micro.social.ru.common.rest;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.common.micro.social.dto.HotNewsMessage;

import java.util.Date;

@Controller
public class WsNewsController {


  @MessageMapping("/hotnews")
  @SendTo("/topic/hotnews")
  public HotNewsMessage getHotNews(String arg) throws Exception {
    Thread.sleep(1000); // simulated delay
    return new HotNewsMessage("Message title "+System.currentTimeMillis(), "Message text!", new Date().toString());
  }

}