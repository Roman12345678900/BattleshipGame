package org.example;
import java.util.ArrayList;
import java.util.Random;

class GameBot {
    private final Random random;
    private final int[][] battlefield;

    public GameBot() {
        random = new Random();
        battlefield = new int[16][16];
    }

    public void placeShipsRandomlyBot() {
        int[] shipCounts = {6, 5, 4, 3, 2, 1}; // Количество кораблей каждого размера
        for (int deck = 0; deck < shipCounts.length; deck++) {
            int count = shipCounts[deck];
            while (count > 0) {
                int x = random.nextInt(16); // Случайная координата x
                int y = random.nextInt(16); // Случайная координата y
                int direction = random.nextInt(2) + 1; // Случайное направление

                if (!isAvailable(x, y, deck + 1, direction)) {
                    continue; // Если корабль не может быть размещен здесь, пропустить итерацию
                }

                markShip(x, y, deck + 1, direction);
                count--;
            }
        }
    }

    private boolean isAvailable(int x, int y, int size, int direction) {
        if (direction == 1) { // вертикальное
            if (x + size > 16) {
                return false;
            }
            for (int i = x; i < x + size; i++) {
                if (battlefield[i][y] == 1) {
                    return false;
                }
            }
        } else { // горизонтальное
            if (y + size > 16) {
                return false;
            }
            for (int j = y; j < y + size; j++) {
                if (battlefield[x][j] == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    private void markShip(int x, int y, int size, int direction) {
        if (direction == 1) { // вертикальное
            for (int i = x; i < x + size; i++) {
                battlefield[i][y] = 1;
            }
        } else { // горизонтальное
            for (int j = y; j < y + size; j++) {
                battlefield[x][j] = 1;
            }
        }
    }

    public int[][] getBattlefield() {
        return battlefield;
    }

    public void makeRandomMove(Player opponent) {
        int x = random.nextInt(16);
        int y = random.nextInt(16);

        if (opponent.getBattlefield()[x][y] == 1) {
            System.out.println("Бот попал по вашему кораблю в точке " + (char)('A' + x) + (y + 1));
            opponent.getBattlefield()[x][y] = 2; // обозначаем попадание
        } else {
            System.out.println("Бот промахнулся в точке " + (char)('A' + x) + (y + 1));
            opponent.getBattlefield()[x][y] = 3; // обозначаем промах
        }
    }
}
