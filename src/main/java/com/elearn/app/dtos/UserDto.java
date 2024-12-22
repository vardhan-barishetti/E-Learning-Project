package com.elearn.app.dtos;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import com.elearn.app.dtos.RoleDto;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {


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


    private Set<RoleDto> roles = new HashSet<>();

}
