package Domain.Fight;

import Domain.Character.Character;
import Domain.Character.Enemy.Enemy;
import Domain.Character.Player.Player;

import Domain.Dungeon.Level;
import Domain.Item.Items.Treasure;
import java.util.Random;

public class Combat implements Fight {

  private static final Random RANDOM = new Random();
  private final StringBuilder sb = new StringBuilder();
  private final Level level;

  public Combat(Level level) {
    this.level = level;
  }


  @Override
  public void attack(Character player, Character enemy) {
    if (player == null || enemy == null) {
      sb.append("Combat cannot start. One of the participants is missing.\n");
    } else {

      sb.append("Combat started between ").append(player.getDisplayName()).append(" and ")
          .append(enemy.getDisplayName()).append(".\n");

      Character attacker = RANDOM.nextBoolean() ? player : enemy;
      Character defender = (attacker == player) ? enemy : player;

      battleCycle(attacker, defender);
      battleCycle(defender, attacker);

    }
  }

  public void getBounty(Player player, Enemy enemy, Level level) {

    int totalReward = getTotalReward(enemy, level);

    int variation = (int) (totalReward * 0.2);
    totalReward += RANDOM.nextInt(variation * 2 + 1) - variation;

    if (totalReward < 1) {
      totalReward = 1; // reward is always at least 1
    }

    Treasure treasure = new Treasure(totalReward);

    treasure.useItem(player);
  }

  private static int getTotalReward(Enemy enemy, Level level) {

    double weightedStats =
        enemy.getHealth() * 0.5 +
            enemy.getStrength() * 1.5 +
            enemy.getAgility() * 1.0 +
            enemy.getHostility() * 1.2;

    int reward = (int) (5 +
        level.getLevel() * 3 +
        weightedStats);

    // add small random variation ±10%
    int variation = (int) (reward * 0.1);
    reward += RANDOM.nextInt(variation * 2 + 1) - variation;

    // limit reward range
    return Math.max(5, Math.min(100, reward));
  }

  private void battleCycle(Character attacker, Character defender) {
    if (attacker.isAlive() && defender.isAlive()) {
      if (hitSuccess(attacker, defender)) {
        int damage = calculateDamage(attacker);
        defender.applyDamage(damage);

        sb.append(attacker.getDisplayName()).append(" dealt ").append(damage)
            .append(" damage to ").append(defender.getDisplayName())
            .append(". Remaining HP: ").append(defender.getHealth()).append("\n");

        if (attacker instanceof Player) {
          ((Player) attacker).setPlayerHits(((Player) attacker).getPlayerHits() + 1);
        }

        if (!defender.isAlive()) {
          if (attacker instanceof Player && defender instanceof Enemy) {
            getBounty((Player) attacker, (Enemy) defender, level);
          }

          sb.append(attacker.getDisplayName()).append(" killed ")
              .append(defender.getDisplayName())
              .append("!\n");
        }

      } else {
        sb.append(attacker.getDisplayName()).append(" missed ")
            .append(defender.getDisplayName()).append("\n");

        if (attacker instanceof Player) {
          ((Player) attacker).setPlayerMisses(((Player) attacker).getPlayerMisses() + 1);
        }
      }
    }
  }


  private boolean hitSuccess(Character attacker, Character target) {
    int baseChance = 85;
    int minChance = 5;
    int maxChance = 90;
    int diff = attacker.getAgility() - target.getAgility();
    double k = 1.0; // agility influence

    int chance = (int) (baseChance + k * diff);

    chance = Math.max(minChance, Math.min(maxChance, chance));

    return RANDOM.nextInt(100) < chance;
  }

  private int calculateDamage(Character attacker) {
    int damage = attacker.getStrength();

    if (attacker instanceof Player) {
      damage = (int) (damage * 0.5); // reduced damage for balance
      if (RANDOM.nextInt(100) < 10) {
        damage = (int) (damage * 1.0); // moderate critical
      }
    } else if (attacker instanceof Enemy) {
      damage = (int) (damage * 1.5); // enemies deal full damage
    }

    return damage;
  }

  public String getCombatInfo() {
    return sb.toString();
  }

  public void cleanCombatInfo() {
    sb.setLength(0);
  }

}
