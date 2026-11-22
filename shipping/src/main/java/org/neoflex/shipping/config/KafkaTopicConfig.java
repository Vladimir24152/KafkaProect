package org.neoflex.shipping.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${KAFKA_PARTITION_COUNT:4}")
    private int partitionCount;

    @Bean
    public NewTopic sentOrdersTopic() {
        return TopicBuilder.name("sent_orders")
                .partitions(partitionCount).replicas(1).build();
    }

    @Bean
    public NewTopic sentOrdersDlt() {
        return TopicBuilder.name("sent_orders.DLT")
                .partitions(partitionCount).replicas(1).build();
    }
}