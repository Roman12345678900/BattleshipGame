package org.example;
import java.util.Scanner;

public class Battleship {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (true) {
            System.out.println("Выберите режим игры:");
            System.out.println("1. Одиночная игра");
            System.out.println("2. Игра с напарником");
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {
                GameBot gameBot = new GameBot();
                gameBot.placeShipsRandomlyBot();

            } else if (choice == 2) {
                // Игра против другого игрока
                BattleshipGame battleshipGame = new BattleshipGame();
                battleshipGame.playGame();
            } else {
                System.out.println("Неверный выбор. Пожалуйста, повторите попытку.");
            }
        }
    }
}