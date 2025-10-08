package Datalayer.Converter;

import Datalayer.DTO.PositionDTO;
import Domain.Navigator.Position;
import Domain.Navigator.PositionInterface;

public class PositionConverter {
    public static PositionDTO toDTO(PositionInterface position){
        if (position == null) return null;

        PositionDTO dto = new PositionDTO();
        dto.setX(position.getX());
        dto.setY(position.getY());

        return dto;
    }

    public static Position fromDTO(PositionDTO dto){
        if (dto == null) return null;

        Position position = new Position();

        position.setX(dto.getX());
        position.setY(dto.getY());
        return position;
    }
}