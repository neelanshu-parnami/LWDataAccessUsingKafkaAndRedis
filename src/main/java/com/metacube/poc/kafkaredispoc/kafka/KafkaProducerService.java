package com.metacube.poc.kafkaredispoc.kafka;

import java.util.Properties;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.metacube.poc.kafkaredispoc.redis.entity.RedisArticle;

@Service
public class KafkaProducerService {
	
	Properties properties;
	Producer<String, String> producer;
	ProducerRecord<String, String> producerRecord;
	
    public KafkaProducerService() {
    	properties = new Properties();
    	
    	// kafka bootstrap server
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());
        // producer acks
        properties.setProperty("acks", "1");
        properties.setProperty("retries", "3");
        properties.setProperty("linger.ms", "1"); // flush time
        
        producer = new org.apache.kafka.clients.producer.KafkaProducer<String, String>(properties);
	}
	
	public void pushRecord(RedisArticle article) {
		
		String jsonData = JSONObject.toJSONString(article.loadMapData());
		System.out.println("Writing article data to Kafka :: " + jsonData);
        producer.send(new ProducerRecord<String, String>("poc_topic", article.getArticleId() + "", jsonData));
		
	}
	
	@Override
	protected void finalize() throws Throwable {
		producer.close();
	}
}
