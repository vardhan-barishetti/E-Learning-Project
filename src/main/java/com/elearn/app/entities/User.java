package com.elearn.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    private String userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String about;
    private boolean active;
    private boolean emailVerified;
    private boolean smsVerified;
    private Date createAt;
    private String profilePath;
    private String recentOTP;

}
