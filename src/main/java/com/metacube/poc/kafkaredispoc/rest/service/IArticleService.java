package com.metacube.poc.kafkaredispoc.rest.service;

import com.metacube.poc.kafkaredispoc.rest.entity.Article;

public interface IArticleService {
	
     Iterable<Article> getAllArticles();
     
     Article getArticleById(int articleId);
     
     boolean saveArticle(Article article);
     
     void deleteArticle(int articleId);
} 
