package com.microservice.tags.service;

import com.microservice.tags.dto.TagAssignmentDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TagAssignmentConsumer {

    @KafkaListener(topics = "tagassignment")
    public void consume(TagAssignmentDTO dto) {
        System.out.println("Event received: " + dto);
    }
}