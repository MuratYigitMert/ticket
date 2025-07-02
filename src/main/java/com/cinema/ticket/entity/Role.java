package com.cinema.ticket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @JsonManagedReference
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<User> users;

    @Override
    public String toString() {
        return "Role{id=" + id + ", name='" + name + "'}";
    }
}
