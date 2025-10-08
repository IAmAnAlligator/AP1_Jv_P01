package Datalayer.Converter;

import Datalayer.DTO.GameDTO;
import Domain.GameSession.GameFacade;
import Domain.GameSession.GameFacadeInterface;

public class GameConverter {

  public static GameDTO toDTO(GameFacadeInterface game) {
      if (game == null) {
          return null;
      }
    GameDTO dto = new GameDTO();
    dto.setPlayer(PlayerConverter.toDTO(game.getPlayer()));
    dto.setLevel(LevelConverter.toDTO(game.getLevel()));

    return dto;
  }

  public static GameFacadeInterface fromDTO(GameDTO dto) {
      if (dto == null) {
          return null;
      }
    GameFacadeInterface game = new GameFacade();

    game.setPlayer(PlayerConverter.fromDTO(dto.getPlayer()));
    game.setLevel(LevelConverter.fromDTO(dto.getLevel()));

    return game;
  }

}
