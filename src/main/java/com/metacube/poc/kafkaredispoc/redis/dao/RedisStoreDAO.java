package com.metacube.poc.kafkaredispoc.redis.dao;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metacube.poc.kafkaredispoc.redis.entity.RedisArticle;

//NOT USED ATM
@Repository
@Transactional
public class RedisStoreDAO {
	  private static final String KEY = "myStore";
	  
	  @Resource(name="redisTemplate")
	  private SetOperations<String, RedisArticle> setOps;	  
	  
	  public void addFamilyMembers(RedisArticle... articles) {
		  setOps.add(KEY, articles);  	
	  }
	  public Set<RedisArticle> getFamilyMembers() {
		  return setOps.members(KEY);
	  }
	  public long getNumberOfFamilyMembers() {
		  return setOps.size(KEY);
	  }
	  public long removeFamilyMembers(RedisArticle... articles) {
		  return setOps.remove(KEY, (Object[])articles);
	  }	 
}
