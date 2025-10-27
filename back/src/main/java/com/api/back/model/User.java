package com.api.back.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import java.util.Date;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String imageUrl;
    private String status;
    private Date createdAt;
    private Date lastSeen;

    // Getters and Setters

}
