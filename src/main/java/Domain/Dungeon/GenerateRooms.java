package Domain.Dungeon;

import Domain.Character.Enemy.Enemy;
import Domain.Character.Enemy.Ghost;
import Domain.Character.Enemy.Mimik;
import Domain.Character.Enemy.Ogre;
import Domain.Character.Enemy.Snake;
import Domain.Character.Enemy.Vampire;
import Domain.Character.Enemy.Zombie;
import Domain.GameSession.GameSettings;
import Domain.Item.ItemInterface;
import Domain.Item.Items.Elixir;
import Domain.Item.Items.Food;
import Domain.Item.Items.Scroll;
import Domain.Item.Items.Treasure;
import Domain.Item.Items.Weapon;
import Domain.Item.Property;
import Domain.Navigator.Position;
import Domain.Navigator.PositionInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GenerateRooms {

  private static final Random random = new Random();

  public static List<Room> createRooms(int level) {
    List<Room> rooms = new ArrayList<>();
    int attempts = 0;
    int maxAttempts = GameSettings.COUNT_ROOMS * 500; // лимит попыток

    while (rooms.size() < GameSettings.COUNT_ROOMS && attempts < maxAttempts) {
      attempts++;

      // генерируем размеры комнаты
      int roomWidth = random.nextInt(5) + GameSettings.MIN_ROOM_WIDTH;
      int roomHeight = random.nextInt(4) + GameSettings.MIN_ROOM_HEIGHT;

      // гарантируем, что не выйдем за границы
      int maxX = Math.max(1, GameSettings.GAME_WIDTH - roomWidth);
      int maxY = Math.max(1, GameSettings.GAME_HEIGHT - roomHeight);

      int x = random.nextInt(maxX);
      int y = random.nextInt(maxY);

      PositionInterface roomPosition = new Position(x, y);
      Room room = new Room(roomPosition, roomWidth, roomHeight);

      // добавляем только если не пересекается и не параллельна
      if (!room.isCross(rooms) && !room.isParallel(rooms)) {
        rooms.add(room);
      }
    }

    // если хотя бы одна комната есть — добавляем данные
    if (rooms.size() >= 2) {
      setStartAndEndRooms(rooms);
    }
    if (!rooms.isEmpty()) {
      setItems(rooms, level);
      setEnemies(rooms, level);
    }

    return rooms;
  }


  private static void setStartAndEndRooms(List<Room> rooms) {
    if (!rooms.isEmpty()) {

      // перемешиваем список случайным образом
      Collections.shuffle(rooms, random);

      // первая комната после перемешивания — стартовая
      Room startRoom = rooms.get(0);
      startRoom.setStart(true);

      // если есть хотя бы 2 комнаты — выбираем вторую как конечную
      if (rooms.size() >= 2) {
        Room endRoom = rooms.get(1);
        endRoom.setEnd(true);
      } else {
        // если только одна комната — она и старт, и конец
        startRoom.setEnd(true);
      }
    }
  }


  private static void setItems(List<Room> rooms, int level) {
    for (Room room : rooms) {
      if (!room.isStart()) {

        // количество предметов зависит от уровня
        int maxItems = Math.max(1,
            (int) (GameSettings.MAX_COUNT_ITEM_IN_ROOM / (1 + Math.log(level + 1))));
        int itemCount = random.nextInt(maxItems) + 1;

        for (int i = 0; i < itemCount; i++) {
          ItemInterface item;

          int itemType = random.nextInt(5); // 5 типов предметов
          switch (itemType) {
            case 0: // Food
              item = new Food(25 + level);
              break;
            case 1: // Elixir
              int power = random.nextInt(5) + level;
              Property typeElixir = Property.values()[random.nextInt(Property.values().length)];
              item = new Elixir(typeElixir, power, power / 2);
              break;
            case 2: // Scroll
              int scrollPower = random.nextInt(5) + level;
              Property typeScroll = Property.values()[random.nextInt(Property.values().length)];
              item = new Scroll(typeScroll, scrollPower);
              break;
            case 3: // Treasure
              int value = random.nextInt(10) + level;
              item = new Treasure(value);
              break;
            case 4: // Weapon
              int damage = random.nextInt(5) + level;
              item = new Weapon(damage);
              break;
            default:
              throw new IllegalStateException("Неизвестный тип предмета: " + itemType);
          }

          // назначаем позицию внутри комнаты и добавляем
          item.setPosition(room.getRandomPoint());
          room.addItem(item);
        }
      }
    }
  }


  private static void setEnemies(List<Room> rooms, int level) {
    for (Room room : rooms) {
      if (!room.isStart()) {

        // количество врагов
        int maxEnemy = Math.min(GameSettings.MAX_COUNT_ENEMIES_IN_ROOM,
            1 + (int) Math.log(level + 1));
        int enemyCount = random.nextInt(maxEnemy) + 1;

        for (int i = 0; i < enemyCount; i++) {
          int health = random.nextInt(10) + level * 5;
          int agility = random.nextInt(5) + level * 2;
          int strength = random.nextInt(7) + level * 3;
          int damage = random.nextInt(5) + level * 3;
          boolean isHostile = true;

          PositionInterface randomEnemyPosition = room.getRandomPoint();

          // создаём врага сразу с позицией
          Enemy enemy;
          int enemyType = random.nextInt(6);
          switch (enemyType) {
            case 0 -> enemy = new Ghost(health, agility, strength, isHostile, damage,
                randomEnemyPosition);
            case 1 -> {
              enemy = new Mimik(health, agility, strength, isHostile, damage,
                  randomEnemyPosition);
              enemy.setVisible(false);
            }
            case 2 -> enemy = new Ogre(health, agility, strength, isHostile, damage,
                randomEnemyPosition);
            case 3 -> enemy = new Snake(health, agility, strength, isHostile, damage,
                randomEnemyPosition);
            case 4 -> enemy = new Vampire(health, agility, strength, isHostile, damage,
                randomEnemyPosition);
            case 5 -> enemy = new Zombie(health, agility, strength, isHostile, damage,
                randomEnemyPosition);
            default -> throw new IllegalStateException("Неизвестный тип врага: " + enemyType);
          }

          room.addEnemy(enemy);
        }
      }
    }
  }


}
