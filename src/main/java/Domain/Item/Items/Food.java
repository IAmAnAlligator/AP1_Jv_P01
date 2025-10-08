package Domain.Item.Items;

import Domain.Item.Item;
import Domain.Item.ItemType;
import Domain.Item.ItemUser;

public class Food extends Item {

  private final int healthBoost;

  public Food(int healthBoost) {
    super("F", ItemType.FOOD, healthBoost);
    this.healthBoost = healthBoost;
  }

  @Override
  public void useItem(ItemUser player) {
    if (player.getHealth() < player.getMaxHealth()) {
      int newHealth = player.getHealth() + healthBoost;

      // ограничиваем значение, чтобы не превышало максимум
      if (newHealth > player.getMaxHealth()) {
        newHealth = player.getMaxHealth();
      }

      player.setHealth(newHealth);
      player.setFoodEaten(player.getFoodEaten() + 1);
    }
  }


  @Override
  public boolean isFood() {
    return true;
  }

}
