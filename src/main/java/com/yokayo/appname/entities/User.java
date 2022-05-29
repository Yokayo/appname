package com.yokayo.appname.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "user")
public class User {

    public User() {}
    
    @Id
    @Column
    public String id;
    
    @Column
    public String username;
    
    @Column(name = "avatar")
    public String avatarURI;
    
    @Column
    public String email;
    
    @Column
    public String city;
    
    @Column(name = "real_name")
    public String realName;
    
    @Column(name = "birth_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Date birthDate;
    
    public enum Status {
        ONLINE, OFFLINE
    }
    
    @Transient
    public Status status = Status.OFFLINE;

}