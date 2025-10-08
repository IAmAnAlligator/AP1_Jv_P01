package Datalayer.DTO;

public class EnemyDTO {
    private int health;
    private int agility;
    private int strength;
    private boolean isAlive;
    private int hostility;
    private String type;
    private boolean isFirstHit;
    private int restCounter;
    private boolean isVisible;
    private PositionDTO position;

    public int getHealth(){
        return health;
    }
    public int getAgility() {
        return agility;
    }
    public int getStrength(){
        return strength;
    }
    public boolean isAlive(){
        return isAlive;
    }
    public int getHostility(){
        return hostility;
    }
    public String getType(){
        return type;
    }
    public boolean isFirstHit(){
        return isFirstHit;
    }
    public int getRestCounter(){
        return restCounter;
    }
    public boolean isVisible(){
        return isVisible;
    }
    public PositionDTO getPosition() {
        return position;
    }

    public void setHealth(int health){
        this.health = health;
    }
    public void setAgility(int agility){
        this.agility = agility;
    }
    public void setStrength(int strength){
        this.strength = strength;
    }
    public void setAlive(boolean alive){
        this.isAlive = alive;
    }
    public void setHostility(int hostility){
        this.hostility = hostility;
    }
    public void setType(String type){
        this.type = type;
    }
    public void setFirstHit(boolean firstHit){
        this.isFirstHit = firstHit;
    }
    public void setRestCounter(int restCounter){
        this.restCounter = restCounter;
    }
    public void setVisible(boolean visible){
        this.isVisible = visible;
    }
    public void setPosition(PositionDTO position){
        this.position = position;
    }
}