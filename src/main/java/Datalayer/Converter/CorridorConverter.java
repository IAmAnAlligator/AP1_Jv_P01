package Datalayer.Converter;

import Datalayer.DTO.CorridorDTO;
import Datalayer.DTO.PositionDTO;
import Domain.Dungeon.Corridor;
import Domain.Navigator.Position;
import Domain.Navigator.PositionInterface;

import java.util.ArrayList;
import java.util.List;

public class CorridorConverter {

    public static CorridorDTO toDTO(Corridor corridor) {
        if (corridor == null) {
            return null;
        }

        CorridorDTO dto = new CorridorDTO();

        List<PositionDTO> pathDTOs = new ArrayList<>();
        for (PositionInterface position : corridor.getPath()){
            pathDTOs.add(PositionConverter.toDTO(position));
        }
        dto.setPath(pathDTOs);

        List<PositionDTO> doorDTOs = new ArrayList<>();
        for (PositionInterface position : corridor.getDoor()){
            doorDTOs.add(PositionConverter.toDTO(position));
        }
        dto.setDoor(doorDTOs);

        return dto;
    }

    public static Corridor fromDTO(CorridorDTO dto) {
        if (dto == null) return null;

        Position startPos = PositionConverter.fromDTO(dto.getPath().getFirst());
        Position endPos = PositionConverter.fromDTO(dto.getPath().getLast());

        Corridor corridor = new Corridor(startPos, endPos, new ArrayList<>());

        List<PositionInterface> path = new ArrayList<>();
        for (PositionDTO positionDTO : dto.getPath()){
            path.add(PositionConverter.fromDTO(positionDTO));
        }
        corridor.setPath(path);

        List<PositionInterface> doors = new ArrayList<>();
        for (PositionDTO positionDTO : dto.getDoor()){
            doors.add(PositionConverter.fromDTO(positionDTO));
        }
        corridor.setDoor(doors);

        return corridor;
    }
}