package Datalayer.Converter;

import Datalayer.DTO.ItemDTO;
import Datalayer.DTO.PositionDTO;
import Domain.Item.ItemInterface;
import Domain.Item.Items.Elixir;
import Domain.Item.Items.Food;
import Domain.Item.Items.Scroll;
import Domain.Item.Items.Treasure;
import Domain.Item.Items.Weapon;
import Domain.Item.Property;
import Domain.Navigator.Position;
import Domain.Navigator.PositionInterface;

import java.util.logging.Logger;

public class ItemConverter {
    private static final Logger logger = Logger.getLogger(ItemConverter.class.getName());

    public static ItemDTO toDTO(ItemInterface item) {
        if (item == null) return null;
        ItemDTO dto = new ItemDTO();
        dto.setItemType(item.getType().name());
        dto.setValue(item.getValue());

        PositionInterface position = item.getPosition();
        if (position != null) {
            PositionDTO positionDTO = new PositionDTO();
            positionDTO.setX(position.getX());
            positionDTO.setY(position.getY());
            dto.setPosition(positionDTO);
        }

        return dto;
    }

    public static ItemInterface fromDTO(ItemDTO dto) {
        if (dto == null) return null;

        ItemInterface item = null;

        String itemType = dto.getItemType();
        try {
            switch (itemType) {
                case "TREASURE" -> item = new Treasure(dto.getValue());
                case "WEAPON" -> item = new Weapon(dto.getValue());
                case "SCROLL" -> item = new Scroll(Property.MAX_HEALTH, dto.getValue());
                case "FOOD" -> item = new Food(dto.getValue());
                case "ELIXIR" -> item = new Elixir(Property.AGILITY, dto.getValue(), 2);
            }
            PositionDTO positionDTO = dto.getPosition();
            if (positionDTO != null) {
                PositionInterface position = new Position();
                position.setX(positionDTO.getX());
                position.setY(positionDTO.getY());
                assert item != null; // fixed
                item.setPosition(position);
            }
            return item;
        } catch (Exception ex) {
            logger.severe("Fail while convert property -> " + ex.getMessage());
        }
        return null;
    }
}
