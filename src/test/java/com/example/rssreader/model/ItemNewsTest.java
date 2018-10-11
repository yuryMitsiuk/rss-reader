package com.example.rssreader.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemNewsTest {
    private ItemNews itemNews;

    @Test
    public void getId() {
        Long idVal = 4L;
        itemNews = new ItemNews();
        itemNews.setId(idVal);
        assertEquals(idVal, itemNews.getId());
    }

    @Test
    public void getTitle() {
        String title = "New title";
        itemNews = new ItemNews();
        itemNews.setTitle(title);
        assertEquals(title, itemNews.getTitle());
    }

}