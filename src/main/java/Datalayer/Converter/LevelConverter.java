package Datalayer.Converter;

import Datalayer.DTO.LevelDTO;
import Datalayer.DTO.RoomDTO;
import Datalayer.DTO.CorridorDTO;
import Datalayer.DTO.PositionDTO;
import Domain.Dungeon.Corridor;
import Domain.Dungeon.Level;
import Domain.Dungeon.Room;
import Domain.Navigator.PositionInterface;

import java.util.ArrayList;
import java.util.List;


public class LevelConverter {
    public static LevelDTO toDTO(Level level){
        if (level == null) return null;

        LevelDTO dto = new LevelDTO();
        dto.setLevel(level.getLevel());
        dto.setLastLevel(level.isLastLevel());

        List<RoomDTO> roomDTOs = new ArrayList<>();
        for (Room room : level.getRooms()) {
            roomDTOs.add(RoomConverter.toDTO(room));
        }
        dto.setRooms(roomDTOs);

        List<CorridorDTO> corridorDTOs = new ArrayList<>();
        for (Corridor corridor : level.getCorridors()) {
            corridorDTOs.add(CorridorConverter.toDTO(corridor));
        }
        dto.setCorridors(corridorDTOs);

        dto.setExitPosition(PositionConverter.toDTO(level.getExitPosition()));

        List<PositionDTO> pathDTOs = new ArrayList<>();
        for (PositionInterface position : level.getPath()) {
            pathDTOs.add(PositionConverter.toDTO(position));
        }
        dto.setPath(pathDTOs);

        return dto;
    }

    public static Level fromDTO(LevelDTO dto) {
        if (dto == null) {
            return null;
        }

        Level level = new Level();
        level.setLevel(dto.getLevel());
        level.setLastLevel(dto.isLastLevel());

        List<Room> rooms = new ArrayList<>();
        for (RoomDTO roomDTO : dto.getRooms()) {
            rooms.add(RoomConverter.fromDTO(roomDTO));
        }
        level.setRooms(rooms);

        List<Corridor> corridors = new ArrayList<>();
        for (CorridorDTO corridorDTO : dto.getCorridors()) {
            corridors.add(CorridorConverter.fromDTO(corridorDTO));
        }
        level.setCorridors(corridors);

        level.setExitPosition(PositionConverter.fromDTO(dto.getExitPosition()));

        List<PositionInterface> path = new ArrayList<>();
        for (PositionDTO positionDTO : dto.getPath()) {
            path.add(PositionConverter.fromDTO(positionDTO));
        }
        level.setPath(path);

        return level;
    }
}
