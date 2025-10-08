package Domain.Character.Player;

import Domain.Item.ItemInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bag implements BagInterface {

  private List<ItemInterface> itemInterfaces = new ArrayList<>();

  public void putItem(ItemInterface itemInterface){
    int maxCapacity = 9;
    if(itemInterfaces.size() < maxCapacity){
      itemInterfaces.add(itemInterface);
    }
  }

  public List<ItemInterface> getItems() {
    return itemInterfaces;
  }

  public void setItems(List<ItemInterface> itemInterfaces) {
    this.itemInterfaces = itemInterfaces;
  }

  public boolean removeOne(Class<?> type) {
    Iterator<ItemInterface> iterator = itemInterfaces.iterator();
    while (iterator.hasNext()) {
      ItemInterface item = iterator.next();

      if (type.isInstance(item)) {
        iterator.remove();
        return true;
      }
    }
    return false;
  }

  @Override
  public void clear() {
    itemInterfaces.clear();
  }

  public boolean removeItem(ItemInterface item) {
    return itemInterfaces.remove(item);
  }

}
