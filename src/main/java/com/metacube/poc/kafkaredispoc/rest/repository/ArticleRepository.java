package com.metacube.poc.kafkaredispoc.rest.repository;

import org.springframework.data.repository.CrudRepository;
import com.metacube.poc.kafkaredispoc.rest.entity.Article;

public interface ArticleRepository extends CrudRepository<Article, Integer> {

}