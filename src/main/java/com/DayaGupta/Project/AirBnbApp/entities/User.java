package com.DayaGupta.Project.AirBnbApp.entities;

import com.DayaGupta.Project.AirBnbApp.entities.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
@Table(name="app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(unique = true,nullable=false)
    private String email;

    @Column(nullable=false)
    private String password; // saving password encoded

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

//    @OneToMany(mappedBy="user")
//    private Set<Guest> guests;

}
