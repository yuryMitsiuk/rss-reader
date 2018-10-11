package com.example.rssreader.service;

import com.example.rssreader.model.ItemNews;
import com.example.rssreader.repository.ItemRepository;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ServiceReader {

    private final ItemRepository itemRepository;

    public ServiceReader(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * The method reads news from a source and saves it in a database(H2).
     * The method runs every two minutes. To change the period you need to change "fixedRate".
     * To change the source, change "url".
     */
    @Scheduled(fixedRate = 120000)
    public void readRss() {

        List<ItemNews> itemList = new ArrayList<>();

        try {
            String url = "https://www.nytimes.com/svc/collections/v1/publish/https://www.nytimes.com/section/world/rss.xml";

            try (XmlReader reader = new XmlReader(new URL(url))) {
                SyndFeed feed = new SyndFeedInput().build(reader);
                for (SyndEntry entry : feed.getEntries()) {
                    LocalDateTime localDateTime = entry.getPublishedDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();
                    ItemNews item = new ItemNews();
                    item.setTitle(entry.getTitle());
                    item.setAuthor(entry.getAuthor());
                    item.setLink(entry.getLink());
                    item.setDescription(entry.getDescription().getValue());
                    item.setDateTime(localDateTime);
                    modifyItem(item);
                    itemList.add(item);
                }
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }

        if (!itemList.isEmpty()) {
            Collections.sort(itemList , Comparator.comparing(ItemNews::getDateTime));
            saveItems(itemList);
        }
    }

    /**
     * The method saves data to the database(H2) if it's not there.
     * @param itemList
     */
    private void saveItems(List<ItemNews> itemList) {
        Optional<ItemNews> firstByOrderByDateTimeDesc = itemRepository.findFirstByOrderByDateTimeDesc();
        if (firstByOrderByDateTimeDesc.isPresent()) {
            itemRepository.saveAll(itemList.stream()
                    .filter(o -> o.getDateTime().isAfter(firstByOrderByDateTimeDesc.get().getDateTime()))
                    .collect(Collectors.toList()));
        } else {
            itemRepository.saveAll(itemList);
        }
    }

    /**
     * The method modifies the input data.
     * @param itemNews
     * @return modified itemNews
     */
    private ItemNews modifyItem(ItemNews itemNews) {
        itemNews.setTitle("Title: " + itemNews.getTitle());
        if (itemNews.getAuthor() != null && itemNews.getAuthor() != "") {
            if (itemNews.getAuthor().contains("By "))
                itemNews.setAuthor(itemNews.getAuthor().replace("By ", "").trim());
        }
        return itemNews;
    }
}

