package com.vicras.dto;

import com.vicras.entity.ApprovedStatus;
import com.vicras.entity.GameObject;
import com.vicras.entity.User;
import com.vicras.service.GameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class GameObjectDTOTest {

    @Mock
    private GameService gameService;


    private final GameObjectDTO gameObjectDTO =
            GameObjectDTO.builder()
            .id(1L)
            .title("Title")
            .description("Description")
            .approvedStatus(ApprovedStatus.APPROVED)
            .build();
    @Test
    void convert2GameObject() {
        User user = User.builder().build();
        GameObject gameObject = gameObjectDTO.convert2GameObject(user, gameService);
        Assertions.assertEquals(ApprovedStatus.SENT, gameObject.getApprovedStatus());
        Assertions.assertEquals(user, gameObject.getOwner());
    }

    @Test
    void updateExistingObject() {
        var existObj = new GameObject();
        existObj.setApprovedStatus(ApprovedStatus.APPROVED);
        GameObject gameObject = gameObjectDTO.updateExistingObject(existObj, gameService);
        Assertions.assertEquals(ApprovedStatus.SENT, gameObject.getApprovedStatus());
    }
}