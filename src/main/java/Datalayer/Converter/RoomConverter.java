package Datalayer.Converter;

import Datalayer.DTO.RoomDTO;
import Datalayer.DTO.ItemDTO;
import Datalayer.DTO.EnemyDTO;
import Datalayer.DTO.PositionDTO;
import Domain.Character.Enemy.Enemy;
import Domain.Dungeon.Room;
import Domain.Item.ItemInterface;
import Domain.Navigator.PositionInterface;

import java.util.ArrayList;
import java.util.List;

public class RoomConverter {
    public static RoomDTO toDTO(Room room){
        if (room == null) return null;
        RoomDTO dto = new RoomDTO();
        dto.setPosition(PositionConverter.toDTO(room.getPosition()));
        dto.setWidth(room.getWidth());
        dto.setHeight(room.getHeight());

        List<ItemDTO> itemDTOS = new ArrayList<>();
        for (ItemInterface item : room.getItems()){
            itemDTOS.add(ItemConverter.toDTO(item));
        }
        dto.setItems(itemDTOS);

        List<EnemyDTO> enemyDTOS = new ArrayList<>();
        for (Enemy enemy : room.getEnemies()){
            enemyDTOS.add(EnemyConverter.toDTO(enemy));
        }
        dto.setEnemies(enemyDTOS);

        List<PositionDTO> wallDTOS = new ArrayList<>();
        for (PositionInterface wall : room.getWalls()) {
            wallDTOS.add(PositionConverter.toDTO(wall));
        }
        dto.setWalls(wallDTOS);

        List<PositionDTO> pathDTOS = new ArrayList<>();
        for (PositionInterface position : room.getDoors()){
            pathDTOS.add(PositionConverter.toDTO(position));
        }
        dto.setPath(pathDTOS);
        dto.setStart(room.isStart());
        dto.setEnd(room.isEnd());

        return dto;
    }

    public static Room fromDTO(RoomDTO dto){
        if (dto == null) return null;
        Room room = new Room(
                PositionConverter.fromDTO(dto.getPosition()),
                dto.getWidth(),
                dto.getHeight()
        );

        List<ItemInterface> items = new ArrayList<>();
        for (ItemDTO itemDTO : dto.getItems()){
            items.add(ItemConverter.fromDTO(itemDTO));
        }
        room.setItems(items);

        List<Enemy> enemies = new ArrayList<>();
        for (EnemyDTO enemyDTO : dto.getEnemies()){
            enemies.add(EnemyConverter.fromDTO(enemyDTO));
        }
        room.setEnemies(enemies);

        List<PositionInterface> walls = new ArrayList<>();
        for (PositionDTO wallDTO : dto.getWalls()){
            walls.add(PositionConverter.fromDTO(wallDTO));
        }
        room.setWalls(walls);

        List<PositionInterface> path = new ArrayList<>();
        for (PositionDTO positionDTO : dto.getPath()) {
            path.add(PositionConverter.fromDTO(positionDTO));
        }
        room.setDoors(path);

        room.setStart(dto.isStart());
        room.setEnd(dto.isEnd());

        return room;
    }
}
