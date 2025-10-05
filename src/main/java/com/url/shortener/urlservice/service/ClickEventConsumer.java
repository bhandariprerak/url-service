package com.url.shortener.urlservice.service;


import com.url.shortener.urlservice.config.RabbitMQConfig;
import com.url.shortener.urlservice.dtos.ClickEventRmqDTO;
import com.url.shortener.urlservice.models.ClickEvent;
import com.url.shortener.urlservice.models.UrlMapping;
import com.url.shortener.urlservice.repository.ClickEventRepository;
import com.url.shortener.urlservice.repository.UrlMappingRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ClickEventConsumer {

    private final ClickEventRepository clickEventRepository;
    private final UrlMappingRepository urlMappingRepository;

    public ClickEventConsumer(ClickEventRepository clickEventRepository,
                              UrlMappingRepository urlMappingRepository) {
        this.clickEventRepository = clickEventRepository;
        this.urlMappingRepository = urlMappingRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.CLICK_EVENTS_QUEUE)
    public void receiveClickEvent(ClickEventRmqDTO event) {
        // Resolve UrlMapping by id from the message
        Long mappingId = event.getUrlMappingId();
        Optional<UrlMapping> mappingOpt = urlMappingRepository.findById(mappingId);
        if (mappingOpt.isEmpty()) {
            System.out.println("⚠️ UrlMapping not found for id: " + mappingId + " — skipping click persist.");
            return;
        }

        // Build and save ClickEvent (using relation, not a raw FK field)
        ClickEvent clickEvent = new ClickEvent();
        clickEvent.setUrlMapping(mappingOpt.get());
        clickEvent.setClickDate(event.getClickDate() != null ? event.getClickDate() : LocalDateTime.now());

        clickEventRepository.save(clickEvent);
        System.out.println("Saved click event from queue: " + mappingId);
    }
}
