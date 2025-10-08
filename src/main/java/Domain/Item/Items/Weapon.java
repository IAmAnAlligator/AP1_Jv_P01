package Domain.Item.Items;

import Domain.Item.Item;
import Domain.Item.ItemType;
import Domain.Item.ItemUser;

public class Weapon extends Item {

  public Weapon(int damage) {
    super("W", ItemType.WEAPON, damage);
  }

  @Override
  public void useItem(ItemUser player) {

    int bonus = player.getStrength() + getValue();

    player.setStrength(Math.min(bonus, 30));
    player.useWeapon(true);

  }


  @Override
  public boolean isWeapon() {
    return true;
  }


}
