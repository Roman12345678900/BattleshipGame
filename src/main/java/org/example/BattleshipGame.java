package org.example;
import java.io.IOException;
import java.util.Scanner;

class BattleshipGame {

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Игрок 1, введите ваше имя:");
        String playerName1 = scanner.nextLine();
        System.out.println("Игрок 2, введите ваше имя:");
        String playerName2 = scanner.nextLine();

        Player player1 = new Player(playerName1);
        Player player2 = new Player(playerName2);

        player1.placeShips(this);
        player2.placeShips(this);

        player1.placeShipsRandomly(this);
        player2.placeShipsRandomly(this);

        while (true) {
            player1.makeTurn(player2, scanner);
            if (isWinCondition(player1)) {
                break;
            }
            player2.makeTurn(player1, scanner);
            if (isWinCondition(player2)) {
                break;
            }
        }
        scanner.close();
    }


    private boolean isWinCondition(Player player) {
        return player.countShips() >= 10;
    }

    public boolean isAvailable(Player player, int x, int y, int deck, int rotation) {
        int[][] battlefield = player.getBattlefield();

        // Проверка границ
        if (rotation == 1) {
            if (y + deck > battlefield.length) {
                return false;
            }
        }
        if (rotation == 2) {
            if (x + deck > battlefield[0].length) {
                return false;
            }
        }

        //проверка соседей без диагоналей
        while (deck != 0) {
            for (int i = 0; i < deck; i++) {
                int xi = 0;
                int yi = 0;
                if (rotation == 1) {
                    yi = i;
                } else {
                    xi = i;
                }

                if (x + 1 + xi < battlefield.length && x + 1 + xi >= 0) {
                    if (battlefield[x + 1 + xi][y + yi] != 0) {
                        return false;
                    }
                }
                if (x - 1 + xi < battlefield.length && x - 1 + xi >= 0) {
                    if (battlefield[x - 1 + xi][y + yi] != 0) {
                        return false;
                    }
                }
                if (y + 1 + yi < battlefield.length && y + 1 + yi >= 0) {
                    if (battlefield[x + xi][y + 1 + yi] != 0) {
                        return false;
                    }
                }
                if (y - 1 + yi < battlefield.length && y - 1 + yi >= 0) {
                    if (battlefield[x + xi][y - 1 + yi] != 0) {
                        return false;
                    }
                }
            }
            deck--;
        }
        return true;
    }

    public void markShip(Player player, int x, int y, int deck, int rotation) {
        int[][] battlefield = player.getBattlefield();

        for (int i = 0; i < deck; i++) {
            if (rotation == 1) {
                battlefield[x][y + i] = 1;
            } else {
                battlefield[x + i][y] = 1;
            }
        }
    }

    public static void drawField(int[][] battlefield) {
        System.out.print("  ");
        for (int i = 0; i < battlefield.length; i++) {
            char colChar = (char) ('A' + i);
            System.out.print(colChar + " ");
        }
        System.out.println();

        for (int i = 0; i < battlefield.length; i++) {
            System.out.printf("%2d", i + 1);
            for (int j = 0; j < battlefield[1].length; j++) {
                if (battlefield[j][i] == 0) {
                    System.out.print(" -");
                } else if (battlefield[j][i] == 1) {
                    System.out.print(" *"); // Корабль
                } else if (battlefield[j][i] == 2) {
                    System.out.print(" X"); // Попадание
                } else {
                    System.out.print(" ."); // Промах
                }
            }
            System.out.println();
        }
    }

    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}