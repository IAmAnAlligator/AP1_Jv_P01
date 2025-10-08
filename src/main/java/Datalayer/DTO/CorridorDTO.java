package Datalayer.DTO;

import java.util.List;

public class CorridorDTO {
    private List<PositionDTO> path;
    private List<PositionDTO> door;

    public List<PositionDTO> getPath() {
        return path;
    }

    public List<PositionDTO> getDoor() {
        return door;
    }

    public void setDoor(List<PositionDTO> door) {
        this.door = door;
    }

    public void setPath(List<PositionDTO> path) {
        this.path = path;
    }
}