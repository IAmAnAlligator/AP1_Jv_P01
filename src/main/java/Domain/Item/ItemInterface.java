package Domain.Item;

import Domain.Character.Player.Player;
import Domain.Dungeon.Room;
import Domain.Navigator.Position;
import Domain.Navigator.PositionInterface;

public interface ItemInterface {

  ItemType getItemType();

  int getValue();

  void useItem(ItemUser player);

  PositionInterface getPosition();

  void setPosition(PositionInterface pos);

  Property getProperty();

  String getDisplayName();

  ItemType getType();

  boolean onPickup(Player player, PositionInterface endPos);

  default String getWeaponName() {
    return null; // по умолчанию предмет не оружие
  }


  default boolean isFood() {
    return false;
  }

  default boolean isElixir() {
    return false;
  }

  default boolean isScroll() {
    return false;
  }

  default boolean isWeapon() {
    return false;
  }

}
