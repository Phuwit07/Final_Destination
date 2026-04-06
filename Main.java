package pokemon.game.pokemon;

import java.util.Scanner;

/**
 * +============================================================+
 * |       POKEMON BATTLE SIMULATOR — DATA STRUCTURES           |
 * +------------------------------------------------------------+
 * |  Stack      MoveStack       Battle log (LIFO)              |
 * |  Queue      BattleQueue     Enemy order (FIFO)             |
 * |  SLL        PokedexList     Pokedex search                 |
 * |  DLL        MoveSelector    Move cursor navigation         |
 * |  CLL        TypeCycle       Type advantages (looping)      |
 * |  Iterative  IterativeUtils  countAlive, searchPokedex      |
 * |  Recursive  RecursiveUtils  comboDamage, findStrongest      |
 * +============================================================+
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        printTitle();

        boolean running = true;
        while (running) {
            System.out.println("\n+----------------------------------+");
            System.out.println("|           MAIN MENU              |");
            System.out.println("|  1. Start New Battle             |");
            System.out.println("|  2. Data Structures Map          |");
            System.out.println("|  3. Exit                         |");
            System.out.println("+----------------------------------+");
            System.out.print("> ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    Battle battle = new Battle(scanner);
                    battle.choosePokemon();
                    battle.startBattle();

                    // Chain battles until player faints or queue is empty
                    while (!battle.playerFainted() && !battle.enemyQueueEmpty()) {
                        System.out.print("\nContinue to next battle? (Y/N): ");
                        String cont = scanner.nextLine().trim();
                        if (cont.equalsIgnoreCase("Y")) {
                            battle.startBattle();
                        } else {
                            break;
                        }
                    }
                    break;

                case "2":
                    printDsMap();
                    System.out.print("  [Press ENTER to continue]");
                    scanner.nextLine();
                    break;

                case "3":
                    System.out.println("Thanks for playing! Gotta code 'em all!");
                    System.out.println("--------------------------------------");
                    System.out.println("| Phuwit Jumnongsirisak - 6831501101 |");
                    System.out.println("| Ratthaphum Mekkharach - 6831501105 |");
                    System.out.println("| Rachan Chuthongrat - 6831501107    |");
                    System.out.println("| Witchakon Nokthiang - 6831501112   |");
                    System.out.println("| Ei Nyein Thu - 6831501143          |");
                    System.out.println("--------------------------------------");

                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Enter 1, 2, or 3.");
            }
        }

        scanner.close();
    }

    // ─────────────────────────────────────────

    private static void printTitle() {
        System.out.println();
        System.out.println("  ====================================================");
        System.out.println("    POKEMON BATTLE SIMULATOR  —  Data Structures");
        System.out.println("    Stack | Queue | SLL | DLL | CLL | Iter | Recur");
        System.out.println("  ====================================================");
        System.out.println();
    }

    private static void printDsMap() {
        System.out.println();
        System.out.println("  +------------------------------------------------------------+");
        System.out.println("  |              DATA STRUCTURES MAP                          |");
        System.out.println("  +---------------+------------------+------------------------+");
        System.out.println("  | Structure     | Class            | Role                   |");
        System.out.println("  +---------------+------------------+------------------------+");
        System.out.println("  | Stack         | MoveStack        | Battle log (LIFO)      |");
        System.out.println("  | Queue         | BattleQueue      | Enemy queue (FIFO)     |");
        System.out.println("  | SLL           | PokedexList      | All Pokemon, search    |");
        System.out.println("  | DLL           | MoveSelector     | Move cursor (</>)      |");
        System.out.println("  | CLL           | TypeCycle        | Type loop (Fire>Grass) |");
        System.out.println("  | Iterative     | IterativeUtils   | countAlive, search     |");
        System.out.println("  | Recursive     | RecursiveUtils   | comboDmg, findStrongest|");
        System.out.println("  +---------------+------------------+------------------------+");
    }
}
