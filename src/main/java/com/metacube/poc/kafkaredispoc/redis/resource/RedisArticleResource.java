package com.metacube.poc.kafkaredispoc.redis.resource;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.metacube.poc.kafkaredispoc.redis.dao.RedisArticleDAO;
import com.metacube.poc.kafkaredispoc.redis.entity.RedisArticle;

@RestController
@RequestMapping("redis")
public class RedisArticleResource {
	
	@Autowired
	private RedisArticleDAO articleDAO;
	
	@GetMapping("article/{id}")
	public ResponseEntity<RedisArticle> getArticleByKey(@PathVariable("id") Integer id) {
		RedisArticle article = articleDAO.getArticleInMap(id);
		System.out.println("found article" + article);
		return new ResponseEntity<RedisArticle>(article, HttpStatus.OK);
	}
	
	@GetMapping("articles")
	public ResponseEntity<Map<Integer, RedisArticle>> getRedisArticles() {
		return new ResponseEntity<Map<Integer, RedisArticle>>(articleDAO.getAllArticlesInMap(), HttpStatus.OK);
	}
	
	@GetMapping("articles/count")
	public ResponseEntity<Long> getRedisArticlesCount() {
		return new ResponseEntity<Long>(articleDAO.getNumberOfArticlesInMap(), HttpStatus.OK);
	}

	@PostMapping("article")
	public ResponseEntity<Void> addRedisArticle(@RequestBody RedisArticle article, UriComponentsBuilder builder) {
/*		System.out.println("Inside addRedisArticle");
		if(!articleDAO.isArticleInMap(article)){
			System.out.println("I am here");
			articleDAO.updateArticleInMap(article);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(builder.path("/article/{id}").buildAndExpand(article.getArticleId()).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.OK);
		}*/
		articleDAO.addOrUpdateArticleInMap(article);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/article/{id}").buildAndExpand(article.getArticleId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}
	
	@PutMapping("article")
	public ResponseEntity<RedisArticle> updateRedisArticle(@RequestBody RedisArticle article) {
		articleDAO.updateArticleInMap(article);
		return new ResponseEntity<RedisArticle>(article, HttpStatus.OK);
	}

	@DeleteMapping("article/{id}")
	public ResponseEntity<Void> deleteRedisArticle(@PathVariable("id") Integer id) {
		articleDAO.deleteArticlesInMap(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	public static void main(String[] args) {	
		
		/*System.out.println("--Example of RedisTemplate for ListOperations--");
		
		RedisArticle p1 = new RedisArticle(1L, "TestA", "TEST_A");
		articleDAO.addRedisArticle(p1);
		RedisArticle p2 = new RedisArticle(2L, "TestB", "TEAS_B");
		articleDAO.addRedisArticle(p2);
		System.out.println("Number of articles: " + articleDAO.getNumberOfRedisArticles());
		System.out.println(articleDAO.getRedisArticleAtIndex(1));
		articleDAO.removeRedisArticle(p2);
		System.out.println(articleDAO.getRedisArticleAtIndex(1)); //It will return null, because value is deleted.
*/		
	}

}
