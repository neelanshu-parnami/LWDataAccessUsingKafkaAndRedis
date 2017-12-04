package com.metacube.poc.kafkaredispoc.kafka;

import java.net.URI;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.metacube.poc.kafkaredispoc.redis.entity.RedisArticle;

//@Service
public class KafkaConsumerService {

	private Properties properties;
	private static KafkaConsumer<String, String> consumer;
	
//	@Autowired
//	private ApplicationContext context;
//	
//	@Autowired
//	private RedisArticleDAO articleDAO;

	public KafkaConsumerService() {
		
		//articleDAO = context.getBean(RedisArticleDAO.class);
    	properties = new Properties();
    	
    	// kafka bootstrap server
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());

        properties.setProperty("group.id", "poc_group");
        properties.setProperty("enable.auto.commit", "false");
        // properties.setProperty("auto.commit.interval.ms", "1000");
        properties.setProperty("auto.offset.reset", "earliest");
        
        consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Collections.singletonList("poc_topic"));
        /*try {
        	System.out.println("Starting the kafka Consmer altogether YASH");
        	
			runConsumer();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	public void runConsumer() throws InterruptedException {
        final int giveUp = 100;
        int noRecordsCount = 0;
        while (true) {
            final ConsumerRecords<String, String> consumerRecords = consumer.poll(600000);
            if (consumerRecords.count()==0) {
                noRecordsCount++;
                if (noRecordsCount > giveUp) break;
                else continue;
            }
            consumerRecords.forEach(record -> {
            	System.out.println("Partition: " + record.partition() +
                        ", Offset: " + record.offset() +
                        ", Key: " + record.key() +
                        ", Value: " + record.value());
            	
            	// 1.
                //articleDAO.addArticleInMap(new RedisArticle(10, "title", "category"));
                
                // 2. Using client
                addArticleToRedis(new RedisArticle(record.value()));
            });
            consumer.commitAsync();
        }
        consumer.close();
        System.out.println("DONE");
    }
	
	public void addArticleToRedis(RedisArticle redisArticle) {
		System.out.println("Consuming article:"+redisArticle);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/redis/article";
		HttpEntity<RedisArticle> requestEntity = new HttpEntity<RedisArticle>(redisArticle, headers);
		URI uri = restTemplate.postForLocation(url, requestEntity);
		System.out.println(uri.getPath());
	}
	
	public static void main(String[] args) throws InterruptedException {
		new KafkaConsumerService().runConsumer();
	}

}
