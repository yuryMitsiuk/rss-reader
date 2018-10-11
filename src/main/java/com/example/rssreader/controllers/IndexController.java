package com.example.rssreader.controllers;

import com.example.rssreader.model.ItemNews;
import com.example.rssreader.service.ItemNewsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {

    private final ItemNewsService itemNewsService;

    public IndexController(ItemNewsService itemNewsService) {
        this.itemNewsService = itemNewsService;
    }

    @RequestMapping("/")
    public List<ItemNews> getLast10News() {
        return itemNewsService.getLast10News();
    }
}
