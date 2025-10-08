package Domain.Character.Player;

import Domain.Character.Character;
import Domain.GameSession.GameSettings;
import Domain.Item.ItemUser;
import Domain.Item.Property;
import Domain.Navigator.Direction;
import Domain.Navigator.PositionInterface;
import Domain.Navigator.ValidateLocation;

public class Player extends Character implements ItemUser {

  private int maxHealth;
  private int gold = 0;
  private final int baseAgility;
  private final int baseStrength;
  private final int baseMaxHealth;
  private Property elixirProperty;

  private int elixirTurnsLeft;
  private int sleepTurnsLeft;
  private int elixirUse = 0;
  private int foodEaten = 0;
  private int monsterKills = 0;
  private int cellMove = 0;
  private int playerHits = 0;
  private int playerMisses = 0;

  private final BagInterface bag;

  public Player(int health, int agility, int strength, boolean isAlive,
      PositionInterface startPosition, BagInterface bag) {

    super(health, agility, strength, isAlive, startPosition, "Player");
    this.maxHealth = GameSettings.MAX_HEALTH;
    this.baseAgility = agility;
    this.baseStrength = strength;
    this.baseMaxHealth = health;
    this.bag = bag;
  }

  public void playerMove(Direction direction, ValidateLocation validateLocation) {
    if (sleepTurnsLeft > 0) {
      sleepTurnsLeft--;
    } else {

      PositionInterface currentPos = getPosition();
      PositionInterface newPos = currentPos.move(direction, 1);

      if (validateLocation.isValid(newPos)) {
        setPosition(newPos);
        setCellMove(getCellMove() + 1);
      }
    }
  }

  public int getCellMove() {
    return cellMove;
  }

  public void setCellMove(int cellMove) {
    this.cellMove = cellMove;
  }

  @Override
  public void addGold(int cost) {
    gold += cost;
  }

  @Override
  public int getGold() {
    return gold;
  }

  @Override
  public int getMaxHealth() {
    return maxHealth;
  }

  @Override
  public void setMaxHealth(int maxHealth) {
    this.maxHealth = maxHealth;
  }

  @Override
  public int getElixirUse() {
    return elixirUse;
  }

  @Override
  public void setElixirUse(int elixirUse) {
    this.elixirUse = elixirUse;
  }

  public int getSleepTurnsLeft() {
    return sleepTurnsLeft;
  }

  public void setSleepTurnsLeft(int sleepTurnsLeft) {
    this.sleepTurnsLeft = sleepTurnsLeft;
  }

  public BagInterface getBag() {
    return bag;
  }

  @Override
  public void setElixirProperty(Property property, int turns) {
    this.elixirProperty = property;
    this.elixirTurnsLeft = turns;
  }

  @Override
  public void updateElixirEffect() {
    if (elixirTurnsLeft > 0) {
      elixirTurnsLeft--;
      if (elixirTurnsLeft == 0) {
        resetElixirEffect();
      }
    }
  }

  @Override
  public void resetElixirEffect() {
    switch (elixirProperty) {
      case MAX_HEALTH -> setMaxHealth(baseMaxHealth);
      case AGILITY -> setAgility(baseAgility);
      case STRENGTH -> setStrength(baseStrength);
    }
  }

  @Override
  public void useWeapon(boolean use) {
    if (!use) {
      resetWeaponBonus();
    }
  }

  private void resetWeaponBonus() {
    setStrength(baseStrength);
  }

  @Override
  public int getFoodEaten() {
    return foodEaten;
  }

  @Override
  public void setFoodEaten(int foodEaten) {
    this.foodEaten = foodEaten;
  }

  public int getMonsterKills() {
    return monsterKills;
  }

  public void setMonsterKills(int monsterKills) {
    this.monsterKills = monsterKills;
  }

  public int getBaseAgility() {
    return baseAgility;
  }

  public int getBaseStrength() {
    return baseStrength;
  }

  public int getBaseMaxHealth() {
    return baseMaxHealth;
  }

  public Property getElixirProperty() {
    return elixirProperty;
  }

  public int getElixirTurnsLeft() {
    return elixirTurnsLeft;
  }

  public void setElixirProperty(Property property) {
    this.elixirProperty = property;
  }

  public void setGold(int gold) {
    this.gold = gold;
  }

  public void setElixirTurnsLeft(int elixirTurnsLeft) {
    this.elixirTurnsLeft = elixirTurnsLeft;
  }

  public int getPlayerHits() {
    return playerHits;
  }

  public void setPlayerHits(int playerHits) {
    this.playerHits = playerHits;
  }

  public int getPlayerMisses() {
    return playerMisses;
  }

  public void setPlayerMisses(int playerMisses) {
    this.playerMisses = playerMisses;
  }

  public void resetStats() {
    // статистика
    this.gold = 0;
    this.elixirUse = 0;
    this.foodEaten = 0;
    this.monsterKills = 0;
    this.cellMove = 0;
    this.playerHits = 0;
    this.playerMisses = 0;

    // боевые параметры
    this.maxHealth = baseMaxHealth;
    this.setHealth(baseMaxHealth); // текущее HP тоже обнуляем на максимум
    this.setAgility(baseAgility);
    this.setStrength(baseStrength);

    // эффекты и бафы
    this.elixirTurnsLeft = 0;
    this.sleepTurnsLeft = 0;
    this.elixirProperty = null;

    resetWeaponBonus();

    if (bag != null) {
      bag.clear();
    }
  }


}
