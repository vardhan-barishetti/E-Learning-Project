package com.elearn.app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    private String roleId;

    private String roleName;


    @ManyToMany
    @JoinTable(name = "roles users")
    private Set<User> users = new HashSet<>();


}
