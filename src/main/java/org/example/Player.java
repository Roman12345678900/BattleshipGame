package org.example;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;


class Player {
     private final String playerName;
     private final Scanner scanner = new Scanner(System.in);
     private final int[][] battlefield = new int[16][16];

    public Player(String playerName) {
        this.playerName = playerName;
    }
    public void placeShipsRandomly(BattleshipGame game) {
        Random random = new Random();
        int[] shipCounts = {6, 5, 4, 3, 2, 1}; // Количество кораблей каждого размера
        for (int deck = 0; deck < shipCounts.length; deck++) {
            int count = shipCounts[deck];
            while (count > 0) {
                int x = random.nextInt(16); // Случайная координата x
                int y = random.nextInt(16); // Случайная координата y
                int direction = random.nextInt(2) + 1; // Случайное направление

                if (!game.isAvailable(this, x, y, deck + 1, direction)) {
                    continue; // Если корабль не может быть размещен здесь, пропустить итерацию
                }

                game.markShip(this, x, y, deck + 1, direction);
                count--;
            }
        }
    }
    public void placeShips(BattleshipGame game) {
        int[] shipCounts = {6, 5, 4, 3, 2, 1}; // Количество кораблей каждого размера
        for (int deck = 0; deck < shipCounts.length; deck++) {
            int count = shipCounts[deck];
            while (count > 0) {
                System.out.println();
                System.out.println(playerName + ", разместите ваш " + (deck + 1) + "-палубный корабль на поле боя:");
                System.out.println();

                BattleshipGame.drawField(battlefield);

                System.out.println("Пожалуйста, введите координаты (например, A6 или G15):");
                try {
                    String input = scanner.nextLine().toUpperCase(); // Преобразуем введенную строку в верхний регистр
                    // Добавляем проверку на пустую строку
                    if (input.isEmpty()) {
                        continue; // Возвращаемся к началу цикла
                    }
                    int x = input.charAt(0) - 'A'; // Получаем первый символ, который должен быть буквой
                    int y = Integer.parseInt(input.substring(1))-1; // Получаем остальную часть строки, которая должна быть числом

                    System.out.println("Выберите направление:");
                    System.out.println("1. Вертикальное.");
                    System.out.println("2. Горизонтальное.");
                    int direction = scanner.nextInt();
                    if (!game.isAvailable(this, x, y, deck + 1, direction)) {
                        System.out.println("Неверные координаты! Попробуйте снова.");
                        scanner.nextLine(); // Очищаем буфер ввода
                        continue;
                    }
                    game.markShip(this, x, y, deck + 1, direction);
                    count--;
                    BattleshipGame.clearScreen();
                } catch (NumberFormatException | StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Недопустимый ввод. Попробуйте снова.");
                    scanner.nextLine(); // Очищаем буфер ввода
                }
            }
        }
    }

    public void makeTurn(Player opponent, Scanner scanner) {
        FileWriter writer = null;
        try {
            writer = new FileWriter("moves.txt", true); // Открываем файл для добавления записей (true)

            while (true) {
                System.out.println(playerName + ", сделайте ваш ход.");
                BattleshipGame.drawField(battlefield);
                System.out.println("Пожалуйста, введите координаты (например, A6 или G15):");
                String input = scanner.nextLine().toUpperCase(); // Преобразуем введенную строку в верхний регистр

                int x = input.charAt(0) - 'A'; // Получаем первый символ, который должен быть буквой
                int y = Integer.parseInt(input.substring(1)) - 1; // Получаем остальную часть строки, которая должна быть числом

                // Запись хода в файл
                String move = playerName + " сделал ход: " + input;
                if (opponent.battlefield[x][y] == 1) {
                    move += " - Попал!";
                    battlefield[x][y] = 2;
                } else {
                    move += " - Промах!";
                    battlefield[x][y] = 1;
                }
                writer.write(move + "\n");
                writer.flush(); // Сбрасываем буфер, чтобы гарантировать запись в файл

                if (opponent.battlefield[x][y] == 1) {
                    System.out.println("Попадание! Сделайте ваш ход еще раз!");
                } else {
                    System.out.println("Промах! Ход вашего противника!");
                    break;
                }
                BattleshipGame.clearScreen();
            }
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        } finally {
            try {
                if (writer != null) {
                    writer.close(); // Закрываем поток FileWriter в блоке finally
                }
            } catch (IOException e) {
                System.out.println("Ошибка при закрытии файла: " + e.getMessage());
            }
        }
    }

    public int countShips() {
        int counter = 0;
        for (int i = 0; i < battlefield.length; i++) {
            for (int j = 0; j < battlefield[i].length; j++) {
                if (battlefield[i][j] == 2) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public int[][] getBattlefield() {
        return battlefield;
    }
}
