package com.example.rssreader.repository;

import com.example.rssreader.model.ItemNews;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends CrudRepository<ItemNews, Long> {

    List<ItemNews> findFirst10ByOrderByDateTimeDesc();
    Optional<ItemNews> findFirstByOrderByDateTimeDesc();
}
