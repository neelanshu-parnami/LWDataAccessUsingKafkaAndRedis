package com.metacube.poc.kafkaredispoc.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metacube.poc.kafkaredispoc.kafka.KafkaProducerService;
import com.metacube.poc.kafkaredispoc.redis.entity.RedisArticle;
import com.metacube.poc.kafkaredispoc.rest.entity.Article;
import com.metacube.poc.kafkaredispoc.rest.repository.ArticleRepository;

@Service
public class ArticleService implements IArticleService {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private KafkaProducerService kafkaProducerService;
	
	@Override
	public Article getArticleById(int articleId) {
		Article obj = articleRepository.findOne(articleId);
		return obj;
	}	
	
	@Override
	public Iterable<Article> getAllArticles(){
		return articleRepository.findAll();
	}
	
	@Override
	public synchronized boolean saveArticle(Article article){
    	articleRepository.save(article);
    	kafkaProducerService.pushRecord(new RedisArticle(article));
        return true;
	}
	
	@Override
	public void deleteArticle(int articleId) {
		articleRepository.delete(articleId);
	}
}