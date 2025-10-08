package Datalayer.DTO;

public class ItemDTO {
    private String itemType;
    private int value;
    private PositionDTO position;
    private String property;

    public String getItemType() {
        return itemType;
    }

    public int getValue() {
        return value;
    }

    public PositionDTO getPosition() {
        return position;
    }
    public String getProperty() {
        return property;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setPosition(PositionDTO position) {
        this.position = position;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
