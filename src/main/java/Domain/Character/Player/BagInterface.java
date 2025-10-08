package Domain.Character.Player;

import Domain.Item.ItemInterface;
import java.util.Iterator;
import java.util.List;

public interface BagInterface {

  void putItem(ItemInterface itemInterface);

  List<ItemInterface> getItems();

  void setItems(List<ItemInterface> itemInterfaces);

  boolean removeOne(Class<?> type);

  void clear();

  boolean removeItem(ItemInterface item);
}
