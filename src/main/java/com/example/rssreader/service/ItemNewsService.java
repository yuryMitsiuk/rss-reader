package com.example.rssreader.service;

import com.example.rssreader.model.ItemNews;

import java.util.List;

public interface ItemNewsService {
    List<ItemNews> getLast10News();
}
