package Datalayer.DTO;

import java.util.ArrayList;
import java.util.List;

public class RoomDTO {
    private PositionDTO position;
    private int width;
    private int height;
    // Инициализируем сразу пустыми коллекциями
    private List<ItemDTO> items = new ArrayList<>();
    private List<EnemyDTO> enemies = new ArrayList<>();
    private List<PositionDTO> walls = new ArrayList<>();
    private List<PositionDTO> path = new ArrayList<>();
    private boolean isStart;
    private boolean isEnd;

    public PositionDTO getPosition() {
        return position;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public List<EnemyDTO> getEnemies() {
        return enemies;
    }

    public List<PositionDTO> getWalls() {
        return walls;
    }

    public List<PositionDTO> getPath() {
        return path;
    }

    public boolean isStart() {
        return isStart;
    }

    public boolean isEnd() {
        return isEnd;
    }


    public void setPosition(PositionDTO position) {
        this.position = position;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }

    public void setEnemies(List<EnemyDTO> enemies) {
        this.enemies = enemies;
    }

    public void setWalls(List<PositionDTO> walls) {
        this.walls = walls;
    }

    public void setPath(List<PositionDTO> path) {
        this.path = path;
    }

    public void setStart(boolean start) {
        this.isStart = start;
    }

    public void setEnd(boolean end) {
        this.isEnd = end;
    }
}
