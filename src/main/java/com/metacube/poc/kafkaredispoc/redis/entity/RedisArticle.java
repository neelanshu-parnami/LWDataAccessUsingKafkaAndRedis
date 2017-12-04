package com.metacube.poc.kafkaredispoc.redis.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.metacube.poc.kafkaredispoc.rest.entity.Article;

import lombok.Data;

@Data
public class RedisArticle implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int articleId;
	private String title;
	private String category;
	
	public RedisArticle() {
		
	}

	public RedisArticle(int articleId, String title, String category) {
		super();
		this.articleId = articleId;
		this.title = title;
		this.category = category;
	}
	
	public RedisArticle(Article article) {
		super();
		this.articleId = article.getArticleId();
		this.title = article.getTitle();
		this.category = article.getCategory();
	}
	
	public RedisArticle(String articleJson) {
		
        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(articleJson);  
            int articleId = Integer.valueOf((String) jsonObject.get("id"));
            String title = (String) jsonObject.get("title");
            String category = (String) jsonObject.get("category");
            
            this.articleId = articleId;
    		this.title = title;
    		this.category = category;
        } catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		final RedisArticle article = (RedisArticle) obj;
		if (this == article) {
			return true;
		} else {
			return (this.title.equals(article.title) && this.category == article.category);
		}
	}
	
	public Map<String, String> loadMapData() {
		Map<String,String> myMap = new HashMap<String,String>();
        myMap.put("id", this.articleId + "");
        myMap.put("title", this.title);
        myMap.put("category", this.category);
        return myMap;
	}
	
	@Override
	public int hashCode() {
		int hashno = 7;
		hashno = 13 * hashno + (title == null ? 0 : title.hashCode());
		return hashno;
	}

	@Override
	public String toString() {
		return "RedisArticle [articleId=" + articleId + ", title=" + title + ", category=" + category + "]";
	}

}
