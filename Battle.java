package pokemon.game.pokemon;

import java.util.Scanner;

/**
 * Battle — core game engine.
 *
 * Data Structures wired in here:
 *   Stack        (MoveStack)    → battle log
 *   Queue        (BattleQueue)  → enemy queue
 *   SLL          (PokedexList)  → pokedex
 *   DLL          (MoveSelector) → move cursor
 *   CLL          (TypeCycle)    → type advantages
 *   Iterative    (IterativeUtils) → party scan
 *   Recursive    (RecursiveUtils) → combo/strongest
 */
public class Battle {

    // ── Data Structures ──────────────────────
    private MoveStack    battleLog;     // Stack
    private BattleQueue  enemyQueue;    // Queue
    private PokedexList  pokedex;       // SLL
    private MoveSelector moveSelector;  // DLL
    private TypeCycle    typeCycle;     // CLL

    // ── Game State ────────────────────────────
    private Pokemon    player;
    private Pokemon    enemy;
    private PokemonNode partyHead;      // used by recursive/iterative utils
    private int        turnCount;

    private final Scanner scanner;

    // ─────────────────────────────────────────
    public Battle(Scanner scanner) {
        this.scanner      = scanner;
        this.battleLog    = new MoveStack();
        this.enemyQueue   = new BattleQueue();
        this.pokedex      = new PokedexList();
        this.moveSelector = new MoveSelector();
        this.typeCycle    = TypeChart.buildTypeCycle();
        this.turnCount    = 0;

        initPokedex();
        initEnemyQueue();
    }

    // ─────────────────────────────────────────
    //  Init
    // ─────────────────────────────────────────

    private void initPokedex() {
        pokedex.addPokemon(new Pokemon("Pikachu", 100, "Electric", 25, new Move[]{
            new Move("Thunderbolt", 90,  "Electric", 15),
            new Move("Quick Attack", 40, "Normal",   30),
            new Move("Thunder",    110,  "Electric", 10),
            new Move("Iron Tail",  100,  "Normal",   15)
        }));
        pokedex.addPokemon(new Pokemon("Charizard", 130, "Fire", 36, new Move[]{
            new Move("Flamethrower", 95,  "Fire",   15),
            new Move("Wing Attack",  60,  "Normal", 35),
            new Move("Fire Blast",  120,  "Fire",    5),
            new Move("Slash",        70,  "Normal", 20)
        }));
        pokedex.addPokemon(new Pokemon("Blastoise", 120, "Water", 36, new Move[]{
            new Move("Hydro Pump", 110, "Water",   5),
            new Move("Water Gun",   65, "Water",  25),
            new Move("Skull Bash",  80, "Normal", 15),
            new Move("Bite",        60, "Normal", 25)
        }));
        pokedex.addPokemon(new Pokemon("Venusaur", 120, "Grass", 36, new Move[]{
            new Move("Solar Beam", 120, "Grass",  10),
            new Move("Razor Leaf",  75, "Grass",  25),
            new Move("Tackle",      40, "Normal", 35),
            new Move("Vine Whip",   45, "Grass",  25)
        }));
        pokedex.addPokemon(new Pokemon("Geodude", 80, "Rock", 15, new Move[]{
            new Move("Rock Throw",   50, "Rock",   15),
            new Move("Tackle",       40, "Normal", 35),
            new Move("Magnitude",    70, "Ground", 20),
            new Move("Defense Curl",  0, "Normal", 30)
        }));
    }

    private void initEnemyQueue() {
        // Clone from Pokedex so the originals stay untouched
        Pokemon g = pokedex.search("Geodude");
        Pokemon v = pokedex.search("Venusaur");

        if (g != null)
            enemyQueue.enqueue(new Pokemon("Wild Geodude",  g.maxHp, g.type, g.level, copyMoves(g.moves)));
        if (v != null)
            enemyQueue.enqueue(new Pokemon("Wild Venusaur", v.maxHp, v.type, v.level, copyMoves(v.moves)));

        enemyQueue.enqueue(new Pokemon("Wild Charizard", 130, "Fire", 36, new Move[]{
            new Move("Flamethrower", 95, "Fire",   15),
            new Move("Wing Attack",  60, "Normal", 35)
        }));
    }

    /** Deep-copy a move array so enemies have independent PP. */
    private Move[] copyMoves(Move[] src) {
        Move[] copy = new Move[src.length];
        for (int i = 0; i < src.length; i++) {
            if (src[i] != null)
                copy[i] = new Move(src[i].name, src[i].damage, src[i].type, src[i].maxPp);
        }
        return copy;
    }

    // ─────────────────────────────────────────
    //  Pokemon Selection
    // ─────────────────────────────────────────

    public void choosePokemon() {
        System.out.println("\n+----------------------------------+");
        System.out.println("|      CHOOSE YOUR POKEMON         |");
        System.out.println("+----------------------------------+");
        pokedex.displayAll();

        System.out.print("\nEnter Pokemon name: ");
        String  name  = scanner.nextLine().trim();
        Pokemon found = pokedex.search(name);   // SLL iterative search

        if (found == null) {
            System.out.println("Not found! Defaulting to Pikachu.");
            found = pokedex.search("Pikachu");
        }

        // Clone so the Pokedex entry is never modified
        player = new Pokemon(found.name, found.maxHp, found.type, found.level, copyMoves(found.moves));

        // Build the party SLL (single-node for now; expandable)
        partyHead = new PokemonNode(player);

        // Load player's moves into the DLL cursor
        moveSelector.loadMoves(player.moves);

        System.out.println("\nYou chose: " + player);
    }

    // ─────────────────────────────────────────
    //  Battle Loop
    // ─────────────────────────────────────────

    public void startBattle() {
        if (enemyQueue.isEmpty()) {
            System.out.println("\nNo more enemies! You cleared them all!");
            return;
        }

        enemy = enemyQueue.dequeue();           // Queue: FIFO — get next enemy
        System.out.println("\n+----------------------------------+");
        System.out.printf( "|  A wild %-24s|%n", enemy.name + " appeared!");
        System.out.println("+----------------------------------+");
        System.out.println("  Enemies still waiting: " + enemyQueue.size());
        enemyQueue.display();
        pause();

        while (!player.isFainted() && !enemy.isFainted()) {
            turnCount++;
            printBattleScreen();
            playerTurn();
            if (!enemy.isFainted()) enemyTurn();
        }

        endBattle();
    }

    // ─────────────────────────────────────────
    //  Player Turn
    // ─────────────────────────────────────────

    private void playerTurn() {
        boolean acted = false;
        while (!acted) {
            System.out.println("\nActions:");
            System.out.println("  1. Attack");
            System.out.println("  2. View Battle Log  [Stack]");
            System.out.println("  3. View Pokedex     [SLL]");
            System.out.println("  4. View Type Chart  [CLL]");
            System.out.println("  5. Recursive Stats  [Recursive + Iterative]");
            System.out.println("  6. Run");
            System.out.print("> ");

            String input = scanner.nextLine().trim();
            switch (input) {
                case "1": acted = attackMenu();     break;
                case "2": viewBattleLog();           break;
                case "3": viewPokedex();             break;
                case "4": viewTypeChart();           break;
                case "5": viewRecursiveStats();      break;
                case "6":
                    System.out.println("You ran away!");
                    player.takeDamage(player.hp);   // set HP to 0 to exit loop
                    acted = true;
                    break;
                default:
                    System.out.println("  Invalid. Enter 1-6.");
            }
        }
    }

    // ─────────────────────────────────────────
    //  Attack / Move Menu  (DLL navigation)
    // ─────────────────────────────────────────

    private boolean attackMenu() {
        moveSelector.resetCursor();
        while (true) {
            System.out.println();
            moveSelector.display();
            System.out.println("  [ Enter 1-4 to pick | < prev | > next | B = back ]");
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equals("<"))          { moveSelector.movePrev(); continue; }
            if (input.equals(">"))          { moveSelector.moveNext(); continue; }
            if (input.equalsIgnoreCase("B")){ return false; }

            int idx;
            try {
                idx = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input.");
                continue;
            }

            if (idx < 1 || idx > moveSelector.size()) {
                System.out.println("  No move at index " + idx + ".");
                continue;
            }

            // Position cursor at chosen index
            moveSelector.resetCursor();
            for (int i = 1; i < idx; i++) moveSelector.moveNext();

            Move chosen = moveSelector.getSelected();
            if (chosen == null) { System.out.println("  No move selected."); continue; }
            if (chosen.pp <= 0) { System.out.println("  No PP left for " + chosen.name + "!"); continue; }

            useMove(player, enemy, chosen);
            return true;
        }
    }

    // ─────────────────────────────────────────
    //  Move Execution
    // ─────────────────────────────────────────

    private void useMove(Pokemon attacker, Pokemon defender, Move move) {
        move.pp--;

        double multiplier = typeCycle.getMultiplier(move.type, defender.type); // CLL lookup
        int    damage     = (move.damage > 0) ? Math.max(1, (int)(move.damage * multiplier)) : 0;

        defender.takeDamage(damage);
        battleLog.push(move);           // Stack: push to battle log

        System.out.println("\n  " + attacker.name + " used " + move.name + "!");

        if      (move.damage == 0)      System.out.println("  (Status move — no damage)");
        else if (multiplier >= 2.0)     System.out.println("  Super effective!  (x2.0)");
        else if (multiplier <= 0.5)     System.out.println("  Not very effective...  (x0.5)");

        if (damage > 0)
            System.out.println("  " + defender.name + " took " + damage + " damage!");

        System.out.println("  " + defender.name + " HP: " + defender.hpBar());
    }

    // ─────────────────────────────────────────
    //  Enemy Turn
    // ─────────────────────────────────────────

    private void enemyTurn() {
        Move pick = null;
        for (Move m : enemy.moves) {
            if (m != null && m.pp > 0) { pick = m; break; }
        }
        if (pick == null) {
            System.out.println("\n  " + enemy.name + " has no moves left!");
            return;
        }
        useMove(enemy, player, pick);
        pause();
    }

    // ─────────────────────────────────────────
    //  Info Screens
    // ─────────────────────────────────────────

    private void printBattleScreen() {
        System.out.println("\n============== TURN " + turnCount + " ==============");
        System.out.println("  " + player.name + "  HP: " + player.hpBar());
        System.out.println("  " + enemy.name  + "  HP: " + enemy.hpBar());
        System.out.println("=========================================");
    }

    private void viewBattleLog() {
        System.out.println("\n--- Battle Log  [STACK — LIFO] ---");
        System.out.println("  Most recent move is at the TOP.");
        battleLog.display();
        System.out.println("  Total moves in stack: " + battleLog.size());
        waitForEnter();
    }

    private void viewPokedex() {
        System.out.println();
        pokedex.displayAll();
        System.out.print("  Filter by type? (Fire/Water/Grass/Electric/Rock/Normal) or ENTER to skip: ");
        String t = scanner.nextLine().trim();
        if (!t.isEmpty()) pokedex.displayByType(t);
        waitForEnter();
    }

    private void viewTypeChart() {
        System.out.println("\n--- Type Chart  [CLL — Circular Linked List] ---");
        typeCycle.displayCycle();
        System.out.print("\n  Attacker type: ");
        String atk = scanner.nextLine().trim();
        System.out.print("  Defender type: ");
        String def  = scanner.nextLine().trim();
        double mult = typeCycle.getMultiplier(atk, def);
        String desc = mult >= 2.0 ? "Super Effective!" : mult <= 0.5 ? "Not Very Effective" : "Normal";
        System.out.println("  Result: " + mult + "x  (" + desc + ")");
        waitForEnter();
    }

    private void viewRecursiveStats() {
        System.out.println("\n--- Recursive & Iterative Stats ---");

        // 1. Recursive: combo damage from top of Stack
        MoveNode chain = buildChainFromStack(3);
        int combo = RecursiveUtils.calcComboDamage(chain, 3);
        System.out.println("  [Recursive] calcComboDamage (top 3 log moves): " + combo);

        // 2. Recursive: strongest pokemon in party
        Pokemon strongest = RecursiveUtils.findStrongest(partyHead, null);
        System.out.println("  [Recursive] findStrongest: "
            + (strongest != null ? strongest.name + " (" + strongest.hp + " HP)" : "none"));

        // 3. Recursive: count Fire-types in Pokedex
        int fireCount = RecursiveUtils.countByType(pokedex.getHead(), "Fire");
        System.out.println("  [Recursive] countByType(Fire) in Pokedex: " + fireCount);

        // 4. Recursive: total HP of party
        int totalHp = RecursiveUtils.totalPartyHp(partyHead);
        System.out.println("  [Recursive] totalPartyHp: " + totalHp);

        // 5. Iterative: alive count
        int alive = IterativeUtils.countAlive(partyHead);
        System.out.println("  [Iterative] countAlive: " + alive);

        // 6. Iterative: party status display
        IterativeUtils.displayPartyStatus(partyHead);

        waitForEnter();
    }

    /**
     * Temporarily pop up to `limit` moves from the Stack,
     * build a MoveNode chain for recursive functions, then push them back.
     */
    private MoveNode buildChainFromStack(int limit) {
        if (battleLog.isEmpty()) return null;

        Move[] temp  = new Move[limit];
        int    count = 0;
        while (!battleLog.isEmpty() && count < limit) {
            temp[count++] = battleLog.pop();
        }
        // Restore stack (re-push in reverse so order is preserved)
        for (int i = count - 1; i >= 0; i--) battleLog.push(temp[i]);

        // Build temporary linked chain
        MoveNode head = null;
        MoveNode tail = null;
        for (int i = 0; i < count; i++) {
            MoveNode n = new MoveNode(temp[i]);
            if (head == null) { head = tail = n; }
            else              { tail.next = n; tail = n; }
        }
        return head;
    }

    // ─────────────────────────────────────────
    //  End of Battle
    // ─────────────────────────────────────────

    private void endBattle() {
        System.out.println("\n=========================================");
        if (enemy.isFainted()) {
            System.out.println("  " + enemy.name + " fainted! You win!");
            if (!enemyQueue.isEmpty()) {
                System.out.println("  Next challenger: " + enemyQueue.peek().name);
                System.out.println("  Remaining in queue: " + enemyQueue.size());
            } else {
                System.out.println("  Enemy queue is empty — no more challengers!");
            }
        } else {
            System.out.println("  " + player.name + " fainted or fled!");
        }
        System.out.println("  Turns played : " + turnCount);
        System.out.println("  Moves in log : " + battleLog.size());
        System.out.println("=========================================");
    }

    // ─────────────────────────────────────────
    //  Helpers
    // ─────────────────────────────────────────

    private void pause() {
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
    }

    private void waitForEnter() {
        System.out.print("\n  [Press ENTER to continue]");
        scanner.nextLine();
    }

    public boolean playerFainted()    { return player != null && player.isFainted(); }
    public boolean enemyQueueEmpty()  { return enemyQueue.isEmpty(); }
}
