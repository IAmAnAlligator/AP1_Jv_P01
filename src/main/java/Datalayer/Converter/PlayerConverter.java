package Datalayer.Converter;

import Datalayer.DTO.PlayerDTO;
import Datalayer.DTO.PositionDTO;
import Domain.Character.Player.Bag;
import Domain.Character.Player.Player;
import Domain.Item.Property;
import Domain.Navigator.Position;

import java.util.stream.Collectors;

public class PlayerConverter {

  public static PlayerDTO toDTO(Player player) {
    PlayerDTO dto = new PlayerDTO();
    dto.setDisplayName(player.getDisplayName());

    dto.setMaxHealth(player.getHealth());

    dto.setGold(player.getGold());
    dto.setBaseAgility(player.getBaseAgility());
    dto.setBaseStrength(player.getBaseStrength());


    dto.setBaseMaxHealth(player.getMaxHealth());

    dto.setElixirProperty(
        player.getElixirProperty() != null ? player.getElixirProperty().toString() : null);

    dto.setElixirTurnsLeft(player.getElixirTurnsLeft());
    dto.setSleepTurnsLeft(player.getSleepTurnsLeft());
    dto.setElixirUse(player.getElixirUse());
    dto.setFoodEaten(player.getFoodEaten());
    dto.setMonsterKills(player.getMonsterKills());
    dto.setCellMove(player.getCellMove());

    dto.setPack(player.getBag().getItems().stream()
        .map(ItemConverter::toDTO)
        .collect(Collectors.toList()));

    dto.setPosition(new PositionDTO(player.getPosition().getX(), player.getPosition().getY()));

    dto.setAlive(player.isAlive());

    return dto;
  }

  public static Player fromDTO(PlayerDTO dto) {
    Player player = new Player(
        dto.getMaxHealth(),
        dto.getBaseAgility(),
        dto.getBaseStrength(),
        dto.isAlive(),
        new Position(dto.getPosition().getX(), dto.getPosition().getY()),
        new Bag()
    );

    player.setGold(dto.getGold());

    if (dto.getElixirProperty() != null) {
      player.setElixirProperty(Property.valueOf(dto.getElixirProperty()));
    }

    player.setElixirTurnsLeft(dto.getElixirTurnsLeft());
    player.setSleepTurnsLeft(dto.getSleepTurnsLeft());
    player.setElixirUse(dto.getElixirUse());
    player.setFoodEaten(dto.getFoodEaten());
    player.setMonsterKills(dto.getMonsterKills());
    player.setCellMove(dto.getCellMove());
    player.setDisplayName(dto.getDisplayName());

    // Восстанавливаем предметы в сумке
    player.getBag().setItems(dto.getPack().stream()
        .map(ItemConverter::fromDTO)
        .collect(Collectors.toList()));

    return player;
  }

}
