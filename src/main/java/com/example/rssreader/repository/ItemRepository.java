package com.example.rssreader.repository;

import com.example.rssreader.model.ItemNews;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<ItemNews, Long> {

    List<ItemNews> findFirst10ByOrderByDateTimeDesc();
    boolean existsByLink(String link);
}
