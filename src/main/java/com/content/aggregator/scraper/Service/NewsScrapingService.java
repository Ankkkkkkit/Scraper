package com.content.scraper.aggregator.Service;

import com.content.scraper.aggregator.Entity.NewsArticle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class NewsScrapingService {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void scrapeAndSaveNews(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements newsElements = doc.select("div.news-item"); // Adjust selector as per target website

        for (Element element : newsElements) {
            String title = element.select("h2.title").text();
            String author = element.select("span.author").text();
            LocalDateTime publishDate = LocalDateTime.parse(element.select("time").attr("datetime"));
            String content = element.select("div.content").text();
            String sourceUrl = url; // Assuming all articles are from the same source URL

            NewsArticle newsArticle = new NewsArticle();
            newsArticle.setTitle(title);
            newsArticle.setAuthor(author);
            newsArticle.setPublishDate(publishDate);
            newsArticle.setContent(content);
            newsArticle.setSourceUrl(sourceUrl);

            entityManager.persist(newsArticle);
        }
    }
}
