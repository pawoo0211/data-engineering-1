package com.example.demo.rw;

import com.example.demo.entity.WebLog;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class WebLogItemWriter implements ItemWriter<WebLog> {
    private final KafkaTemplate<String, WebLog> kafkaTemplate;

    @Override
    public void write(Chunk<? extends WebLog> items) throws Exception {

        for (WebLog webLog : items) {
            kafkaTemplate.send("web_log_topic", webLog);
        }
    }
}
