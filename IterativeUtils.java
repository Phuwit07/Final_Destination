package pokemon.game.pokemon;

/**
 * ══════════════════════════════════════════════
 *  ITERATIVE ALGORITHMS (IterativeUtils)
 * ══════════════════════════════════════════════
 *  Every method here uses a loop (while/for).
 *  No recursion — step through nodes manually.
 */
public class IterativeUtils {

    /**
     * Iterative search for a Pokemon by name.
     * Walks the SLL one node at a time until found or end.
     */
    public static Pokemon searchPokedex(PokemonNode head, String name) {
        PokemonNode cur = head;
        while (cur != null) {
            if (cur.data.name.equalsIgnoreCase(name)) return cur.data;
            cur = cur.next;
        }
        return null;
    }

    /**
     * Iteratively count how many Pokemon are NOT fainted.
     */
    public static int countAlive(PokemonNode head) {
        int count = 0;
        PokemonNode cur = head;
        while (cur != null) {
            if (!cur.data.isFainted()) count++;
            cur = cur.next;
        }
        return count;
    }

    /**
     * Iteratively find the first alive (non-fainted) Pokemon.
     */
    public static Pokemon findFirstAlive(PokemonNode head) {
        PokemonNode cur = head;
        while (cur != null) {
            if (!cur.data.isFainted()) return cur.data;
            cur = cur.next;
        }
        return null;
    }

    /**
     * Print HP bars for every Pokemon in the party list.
     */
    public static void displayPartyStatus(PokemonNode head) {
        System.out.println("  --- Party Status [Iterative] ---");
        PokemonNode cur = head;
        int idx = 1;
        while (cur != null) {
            String status = cur.data.isFainted() ? " [FAINTED]" : "";
            System.out.println("  " + idx + ". " + cur.data.name
                    + "  HP: " + cur.data.hpBar() + status);
            cur = cur.next;
            idx++;
        }
    }
}
