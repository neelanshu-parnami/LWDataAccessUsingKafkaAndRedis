package com.metacube.poc.kafkaredispoc.redis.dao;

import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metacube.poc.kafkaredispoc.redis.entity.RedisArticle;

@Repository
@Transactional
public class RedisArticleDAO {
	  private static final String KEY = "POC_ARTICLES";
	  
	  // 2 types of template - StringRedisTemplate and the object one
	  
	  // 1. ValueOperations<K,V> opsForValue()
	  @Autowired
	  private StringRedisTemplate stringRedisTemplate;
	  //ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue(); 
	  // Find some of ValueOperations methods. 
	  /*setIfAbsent(K key, V value): Sets key to hold the string value if key is absent. 
	  set(K key, V value): Sets value for key. 
	  get(Object key): Fetches the value of key.*/ 
	  
	  // 2. List operations - ListOperations<K,V> opsForList()
	  @Autowired
	  private RedisTemplate<String, RedisArticle> redisTemplate;
	  
	  public void addArticle(RedisArticle article) {
		  redisTemplate.opsForList().leftPush(KEY, article);
	  }
	  public long getNumberOfArticles() {
		  return redisTemplate.opsForList().size(KEY);
	  }
	  public RedisArticle getArticleAtIndex(Integer index) {
		  return redisTemplate.opsForList().index(KEY, index);
	  }
	  public void removeArticle(RedisArticle p) {
		  redisTemplate.opsForList().remove(KEY, 1, p);
	  }
	  
	  // Set Operations - SetOperations<K,V> opsForSet()
	  @Resource(name="redisTemplate")
	  private SetOperations<String, RedisArticle> setOps;	  
	  
	  public void addArticles(RedisArticle... redisArticles) {
		  setOps.add(KEY, redisArticles);  	
	  }
	  public Set<RedisArticle> getArticles() {
		  return setOps.members(KEY);
	  }
	  public long getNumberOfArticlesSet() {
		  return setOps.size(KEY);
	  }
	  public long removeArticlesSet(RedisArticle... redisArticles) {
		  return setOps.remove(KEY, (Object[])redisArticles);
	  }	
	  
	  // Hash Operations - HashOperations<K,HK,HV> opsForHash()
	  @Resource(name="redisTemplate")
	  private HashOperations<String, Integer, RedisArticle> hashOps;	 
	  
	  public void addOrUpdateArticleInMap(RedisArticle redisArticle) {
		  System.out.println("hashOps is NULL " + hashOps == null);
		  hashOps.put(KEY, redisArticle.getArticleId(), redisArticle);
	  }
	  public Boolean isArticleInMap(RedisArticle redisArticle) {
		  System.out.println("isArticleInMap ***************************************" + hashOps == null);
		  return hashOps.hasKey(KEY, redisArticle.getArticleId());
	  }
	  public void updateArticleInMap(RedisArticle redisArticle) {
		  hashOps.put(KEY, redisArticle.getArticleId(), redisArticle);
	  }	  
	  public RedisArticle getArticleInMap(Integer id) {
		  RedisArticle article = hashOps.get(KEY, id);
		  System.out.println("on Redis DAO ::  " + article);
		  return article;
		  //return hashOps.get(KEY, id);
	  }
	  public long getNumberOfArticlesInMap() {
		  return hashOps.size(KEY);
	  }
	  public Map<Integer, RedisArticle> getAllArticlesInMap() {
		  return hashOps.entries(KEY);
	  }
	  public long deleteArticlesInMap(Integer... ids) {
		  return hashOps.delete(KEY, (Object)ids);
	  }	
	  
}
