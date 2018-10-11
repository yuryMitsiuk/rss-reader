package com.example.rssreader.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class ItemNews {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String link;
    private String author;
    private LocalDateTime dateTime;
    @Lob
    private String description;
}
