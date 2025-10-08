package Datalayer.DTO;

import java.util.List;

public class PlayerDTO {
    private int maxHealth;
    private int gold;
    private int baseAgility;
    private int baseStrength;
    private int baseMaxHealth;
    private String elixirProperty;
    private int elixirTurnsLeft;
    private int sleepTurnsLeft;
    private int elixirUse;
    private int foodEaten;
    private int monsterKills;
    private int cellMove;
    private List<ItemDTO> pack;
    private PositionDTO position;
    private boolean isAlive;
    private String displayName;


    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getBaseAgility() {
        return baseAgility;
    }

    public void setBaseAgility(int baseAgility) {
        this.baseAgility = baseAgility;
    }

    public int getBaseStrength() {
        return baseStrength;
    }

    public void setBaseStrength(int baseStrength) {
        this.baseStrength = baseStrength;
    }

    public int getBaseMaxHealth() {
        return baseMaxHealth;
    }

    public void setBaseMaxHealth(int baseMaxHealth) {
        this.baseMaxHealth = baseMaxHealth;
    }

    public String getElixirProperty() {
        return elixirProperty;
    }

    public void setElixirProperty(String elixirProperty) {
        this.elixirProperty = elixirProperty;
    }

    public int getElixirTurnsLeft() {
        return elixirTurnsLeft;
    }

    public void setElixirTurnsLeft(int elixirTurnsLeft) {
        this.elixirTurnsLeft = elixirTurnsLeft;
    }

    public int getSleepTurnsLeft() {
        return sleepTurnsLeft;
    }

    public void setSleepTurnsLeft(int sleepTurnsLeft) {
        this.sleepTurnsLeft = sleepTurnsLeft;
    }

    public int getElixirUse() {
        return elixirUse;
    }

    public void setElixirUse(int elixirUse) {
        this.elixirUse = elixirUse;
    }

    public int getFoodEaten() {
        return foodEaten;
    }

    public void setFoodEaten(int foodEaten) {
        this.foodEaten = foodEaten;
    }

    public int getMonsterKills() {
        return monsterKills;
    }

    public void setMonsterKills(int monsterKills) {
        this.monsterKills = monsterKills;
    }

    public int getCellMove() {
        return cellMove;
    }

    public void setCellMove(int cellMove) {
        this.cellMove = cellMove;
    }

    public List<ItemDTO> getPack() {
        return pack;
    }

    public void setPack(List<ItemDTO> pack) {
        this.pack = pack;
    }

    public PositionDTO getPosition() {
        return position;
    }

    public void setPosition(PositionDTO position) {
        this.position = position;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}