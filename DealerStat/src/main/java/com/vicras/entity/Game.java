package com.vicras.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Game extends BaseEntity{

    @Column(name = "title" , nullable = false)
    String title;

    @ManyToMany(mappedBy = "games")
    private List<GameObject> gameObjects;
}
