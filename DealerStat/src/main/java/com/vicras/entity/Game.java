package com.vicras.entity;

import com.vicras.dto.GameDTO;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@Getter
@Builder(access = AccessLevel.PUBLIC)
public class Game extends BaseEntity{

    @Column(name = "title" , nullable = false)
    String title;

    @ManyToMany(mappedBy = "games")
    private List<GameObject> gameObjects;

    public Game() {
    }

    public GameDTO convert2DTO(){
        return new GameDTO(id, title);
    }
}
