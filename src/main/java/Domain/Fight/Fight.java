package Domain.Fight;

import Domain.Character.Character;
import Domain.Character.Enemy.Enemy;
import Domain.Character.Player.Player;
import Domain.Dungeon.Level;
import Domain.Item.ItemUser;
import Domain.Item.Items.Treasure;

public interface Fight {

  void attack(Character player, Character enemy);


}
