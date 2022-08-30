package ru.common.micro.social.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.common.micro.social.dto.HotNewsMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class HotNewsGeneratorAndPusher {

    private static final AtomicInteger counter = new AtomicInteger(0);

    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public HotNewsGeneratorAndPusher(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Scheduled(fixedRate = 5000)
    public void sendMessage() {
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        simpMessagingTemplate.convertAndSend("/topic/hotnews",
          new HotNewsMessage("NEWS TITLE", "hot news number" + counter.getAndIncrement(), time));
    }
    
}