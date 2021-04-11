package com.vicras.model.leaderboard;

import com.vicras.model.engine.field.FieldState;
import com.vicras.model.player.Player;
import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Builder
public class RecordImpl implements GameRecord {

    private final Player player1;
    private final Player player2;
    private final Player winner;
    private final Duration duration;
    private final LocalDateTime gameTime;
    private final FieldState gameResult;

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        var ans =new StringBuilder();
        ans.append("Result of game:\n");
        ans.append("Player1 name:\t" + player1.getName() +"\n");
        ans.append("Player2 name:\t" + player2.getName() +"\n");
        ans.append("Duration (sec):\t" + duration +"\n");
        ans.append("Game Date:\t" + gameTime + "\n");
        if(gameResult == FieldState.DRAW){
            ans.append("Game result:\tDraw\n");
        }else{
            ans.append("Game result:\t"+ gameResult+"\n");
            ans.append("Winner:\t"+((gameResult==FieldState.CROSSES_WIN)?player1.getName():player2.getName())+"\n");
        }
        return ans.toString();
    }
}
