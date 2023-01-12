package com.example.springsecurityjwtdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER_TBL")
@Entity
public class User {

    @Id
    private int id;
    private String username;
    private String password;
    private String email;


}
