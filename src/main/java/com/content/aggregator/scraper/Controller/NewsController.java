package com.content.aggregator.scraper.Controller;

import com.content.aggregator.scraper.service.NewsScrapingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsScrapingService newsScrapingService;

    @PostMapping("/scrape")
    public String scrapeNews(@RequestBody String url) throws IOException {
        newsScrapingService.scrapeAndSaveNews(url);
        return "News scraped and saved successfully!";
    }
}
