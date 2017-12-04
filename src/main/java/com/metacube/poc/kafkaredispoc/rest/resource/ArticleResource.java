package com.metacube.poc.kafkaredispoc.rest.resource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

import com.metacube.poc.kafkaredispoc.rest.entity.Article;
import com.metacube.poc.kafkaredispoc.rest.service.IArticleService;

@RestController
@RequestMapping("rest")
public class ArticleResource {
	
	@Autowired
	private IArticleService articleService;

	@GetMapping("article/{id}")
	public ResponseEntity<Article> getArticleById(@PathVariable("id") Integer id) {
		Article article = articleService.getArticleById(id);
		return new ResponseEntity<Article>(article, HttpStatus.OK);
	}

	@GetMapping("articles")
	public ResponseEntity<List<Article>> getAllArticles() {
		List<Article> list = StreamSupport.stream(articleService.getAllArticles().spliterator(), false)
                .collect(Collectors.toList());
		return new ResponseEntity<List<Article>>(list, HttpStatus.OK);
	}

	@PostMapping("article")
	public ResponseEntity<Void> addArticle(@RequestBody Article article, UriComponentsBuilder builder) {
		System.out.println("addArticle:POST");
		System.out.println("article:"+article);
		boolean flag = articleService.saveArticle(article);
		if (flag == false) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/article/{id}").buildAndExpand(article.getArticleId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@PutMapping("article")
	public ResponseEntity<Article> updateArticle(@RequestBody Article article) {
		System.out.println("updateArticle:PUT");
		System.out.println("article:"+article);
		articleService.saveArticle(article);
		return new ResponseEntity<Article>(article, HttpStatus.OK);
	}

	@DeleteMapping("article/{id}")
	public ResponseEntity<Void> deleteArticle(@PathVariable("id") Integer id) {
		articleService.deleteArticle(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
