package Datalayer.DTO;

public class GameDTO {
    private int id;
    private PlayerDTO player;
    private LevelDTO level;

    public int getId() {
        return id;
    }

    public PlayerDTO getPlayer() {
        return player;
    }

    public LevelDTO getLevel(){
        return level;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlayer(PlayerDTO player){
        this.player = player;
    }

    public void setLevel(LevelDTO level) {
        this.level = level;
    }
}