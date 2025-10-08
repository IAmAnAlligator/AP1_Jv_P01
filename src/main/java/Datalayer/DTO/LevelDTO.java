package Datalayer.DTO;

import java.util.List;

public class LevelDTO {
    private List<RoomDTO> rooms;
    private List<CorridorDTO> corridors;
    private int level;
    private boolean isLastLevel;
    private PositionDTO exitPosition;
    private List<PositionDTO> path;

    public List<RoomDTO> getRooms(){
        return rooms;
    }
    public List<CorridorDTO> getCorridors(){
        return corridors;
    }
    public int getLevel() {
        return level;
    }
    public boolean isLastLevel() {
        return isLastLevel;
    }
    public PositionDTO getExitPosition() {
        return exitPosition;
    }
    public List<PositionDTO> getPath() {
        return path;
    }

    public void setRooms(List<RoomDTO> rooms) {
        this.rooms = rooms;
    }
    public void setCorridors(List<CorridorDTO> corridors) {
        this.corridors = corridors;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public void setLastLevel(boolean lastLevel) {
        isLastLevel = lastLevel;
    }
    public void setExitPosition(PositionDTO exitPosition) {
        this.exitPosition = exitPosition;
    }
    public void setPath(List<PositionDTO> path) {
        this.path = path;
    }
}
