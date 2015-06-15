package com.free.agent.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by antonPC on 15.06.15.
 */
@Entity
@Table(name = "SPORT")
public class Sport implements Serializable {

    @Id
    @Column(name = "SPORT_ID")
    @GeneratedValue
    private Long id;
    @Column(name = "NAME")
    private String name;
    @ManyToMany(mappedBy="SPORTS")
    private Set<User> users = new HashSet<User>();

    public Sport(String name, Set<User> users) {
        this.name = name;
        this.users = users;
    }

    public Sport() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
