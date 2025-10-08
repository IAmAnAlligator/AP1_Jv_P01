package Datalayer.Converter;

import Datalayer.DTO.EnemyDTO;
import Datalayer.DTO.PositionDTO;
import Domain.Character.Enemy.Enemy;
import Domain.Character.Enemy.Ghost;
import Domain.Character.Enemy.Mimik;
import Domain.Character.Enemy.Ogre;
import Domain.Character.Enemy.Snake;
import Domain.Character.Enemy.Vampire;
import Domain.Character.Enemy.Zombie;
import Domain.Navigator.Position;
import Domain.Navigator.PositionInterface;

public class EnemyConverter {

  public static EnemyDTO toDTO(Enemy enemy) {
      if (enemy == null) {
          return null;
      }
    EnemyDTO dto = new EnemyDTO();
    dto.setAgility(enemy.getAgility());
    dto.setHealth(enemy.getHealth());
    dto.setAlive(enemy.isAlive());
    dto.setFirstHit(enemy.isFirstHit());
    dto.setHostility(enemy.getHostility());
    dto.setRestCounter(enemy.getRestCounter());
    dto.setVisible(enemy.isVisible());
    dto.setStrength(enemy.getStrength());
    dto.setType(enemy.getType().name());
    PositionInterface pos = enemy.getPosition();

    if (pos != null) {
      PositionDTO posDTO = new PositionDTO();
      posDTO.setX(pos.getX());
      posDTO.setY(pos.getY());
      dto.setPosition(posDTO);
    }
    return dto;
  }

  public static Enemy fromDTO(EnemyDTO dto) {
      if (dto == null) {
          return null;
      }
    Enemy enemy = null;
    String type = dto.getType();
    switch (type) {
      case "ZOMBIE" ->
          enemy = new Zombie(dto.getHealth(), dto.getAgility(), dto.getStrength(), true,
              dto.getHostility(), new Position(dto.getPosition().getX(), dto.getPosition().getY()));
      case "OGRE" -> enemy = new Ogre(dto.getHealth(), dto.getAgility(), dto.getStrength(), true,
          dto.getHostility(), new Position(dto.getPosition().getX(), dto.getPosition().getY()));
      case "VAMPIRE" ->
          enemy = new Vampire(dto.getHealth(), dto.getAgility(), dto.getStrength(), true,
              dto.getHostility(), new Position(dto.getPosition().getX(), dto.getPosition().getY()));
      case "GHOST" -> enemy = new Ghost(dto.getHealth(), dto.getAgility(), dto.getStrength(), true,
          dto.getHostility(), new Position(dto.getPosition().getX(), dto.getPosition().getY()));
      case "MIMIK" -> enemy = new Mimik(dto.getHealth(), dto.getAgility(), dto.getStrength(), true,
          dto.getHostility(), new Position(dto.getPosition().getX(), dto.getPosition().getY()));
      case "SNAKE" -> enemy = new Snake(dto.getHealth(), dto.getAgility(), dto.getStrength(), true,
          dto.getHostility(), new Position(dto.getPosition().getX(), dto.getPosition().getY()));
    }
    if (enemy != null) {
      enemy.setFirstHit(dto.isFirstHit());
      enemy.setRestCounter(dto.getRestCounter());
      enemy.setVisible(dto.isVisible());
      Position pos = new Position(dto.getPosition().getX(), dto.getPosition().getY());
      enemy.setPosition(pos);
    }
    return enemy;
  }
}
