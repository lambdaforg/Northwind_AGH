package com.northwind.entities;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "icon")
public class Icon {

    @Transient
    public static final String SEQUENCE_NAME = "icon_sequence";
    @Id
    @BsonProperty("id")
    public int id;

    public String iconStr;
}
