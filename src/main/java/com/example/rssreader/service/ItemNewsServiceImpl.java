package com.example.rssreader.service;

import com.example.rssreader.model.ItemNews;
import com.example.rssreader.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemNewsServiceImpl implements ItemNewsService {

    private final ItemRepository itemRepository;

    public ItemNewsServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<ItemNews> getLast10News() {
        return itemRepository.findFirst10ByOrderByDateTimeDesc();
    }
}
