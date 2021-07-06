package uk.ac.ebi;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class KafkaConsumerListener {

    @Value(value = "${websocket.messages-prod.in}")
    private String destinationMessagesInProd;

    @Value(value = "${websocket.messages-prod.out}")
    private String destinationMessagesOutProd;

    @Value(value = "${websocket.messages-cons.in}")
    private String destinationMessagesInCons;

    @Value(value = "${websocket.messages-cons.out}")
    private String destinationMessagesOutCons;

    @Value(value = "${websocket.messages-humidity.in}")
    private String destinationMessagesInHumidity;

    @Value(value = "${websocket.messages-humidity.out}")
    private String destinationMessagesOutHumidity;

    @Value(value = "${websocket.messages-humidity-min.in}")
    private String destinationMessagesInHumidityMin;

    @Value(value = "${websocket.messages-humidity-min.out}")
    private String destinationMessagesOutHumidityMin;

    @Value(value = "${websocket.messages-humidity-average.in}")
    private String destinationMessagesInHumidityAverage;

    @Value(value = "${websocket.messages-humidity-average.out}")
    private String destinationMessagesOutHumidityAverage;



    @Autowired
    private SimpMessagingTemplate template;

    @KafkaListener(topics = "${kafka.topic-prod.in}", containerFactory = "rawKafkaListenerContainerFactory")
    public void listenTopicInProd(ConsumerRecord<String, String> cr) throws Exception {

        template.convertAndSend(destinationMessagesInProd, cr.key() + " " + cr.value());
    }

    @KafkaListener(topics = "${kafka.topic-prod.out}", containerFactory = "rawKafkaListenerContainerFactory")
    public void listenTopicOutProd(ConsumerRecord<String, String> cr) throws Exception {

        template.convertAndSend(destinationMessagesOutProd, cr.key() + " " + cr.value());
    }

    @KafkaListener(topics = "${kafka.topic-cons.in}", containerFactory = "rawKafkaListenerContainerFactory")
    public void listenTopicInCons(ConsumerRecord<String, String> cr) throws Exception {

        template.convertAndSend(destinationMessagesInCons, cr.key() + " " + cr.value());
    }

    @KafkaListener(topics = "${kafka.topic-cons.out}", containerFactory = "rawKafkaListenerContainerFactory")
    public void listenTopicOutCons(ConsumerRecord<String, String> cr) throws Exception {

        template.convertAndSend(destinationMessagesOutCons, cr.key() + " " + cr.value());
    }

    @KafkaListener(topics = "${kafka.topic-humidity.in}", containerFactory = "rawKafkaListenerContainerFactory")
    public void listenTopicInHumidity(ConsumerRecord<String, String> cr) throws Exception {

        template.convertAndSend(destinationMessagesInHumidity, cr.key() + " " + cr.value());
    }

    @KafkaListener(topics = "${kafka.topic-humidity.out}", containerFactory = "rawKafkaListenerContainerFactory")
    public void listenTopicOutHumidity(ConsumerRecord<String, String> cr) throws Exception {

        template.convertAndSend(destinationMessagesOutHumidity, cr.key() + " " + cr.value());
    }

    @KafkaListener(topics = "${kafka.topic-humidity-min.in}", containerFactory = "rawKafkaListenerContainerFactory")
    public void listenTopicInHumidityMin(ConsumerRecord<String, String> cr) throws Exception {

        template.convertAndSend(destinationMessagesInHumidityMin, cr.key() + " " + cr.value());
    }

    @KafkaListener(topics = "${kafka.topic-humidity-min.out}", containerFactory = "rawKafkaListenerContainerFactory")
    public void listenTopicOutHumidityMin(ConsumerRecord<String, String> cr) throws Exception {

        template.convertAndSend(destinationMessagesOutHumidityMin, cr.key() + " " + cr.value());
    }

    @KafkaListener(topics = "${kafka.topic-humidity-average.in}", containerFactory = "rawKafkaListenerContainerFactory")
    public void listenTopicInHumidityAverage(ConsumerRecord<String, String> cr) throws Exception {

        template.convertAndSend(destinationMessagesInHumidityAverage, cr.key() + " " + cr.value());
    }

    @KafkaListener(topics = "${kafka.topic-humidity-average.out}", containerFactory = "rawKafkaListenerContainerFactory")
    public void listenTopicOutHumidityAverage(ConsumerRecord<String, String> cr) throws Exception {

        template.convertAndSend(destinationMessagesOutHumidityAverage, cr.key() + " " + cr.value());
    }
}
