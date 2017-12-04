 DROP SCHEMA public CASCADE;
 CREATE SCHEMA public;

-- Sequence
DROP SEQUENCE IF EXISTS hibernate_sequence;
CREATE SEQUENCE hibernate_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
-- Dumping structure for table concretepage.articles
CREATE TABLE IF NOT EXISTS articles (
  article_id SERIAL,
  title varchar(200) NOT NULL,
  category varchar(100) NOT NULL
);
-- Dumping data for table concretepage.articles: ~3 rows (approximately)
INSERT INTO articles ("article_id", "title", "category") VALUES
	(1, 'Java Concurrency', 'Java'),
	(2, 'Hibernate HQL ', 'Hibernate'),
	(3, 'Spring MVC with Hibernate', 'Spring'); 