package pokemon.game.pokemon;

/**
 * ══════════════════════════════════════════
 *  DATA STRUCTURE : SLL (PokedexList)
 * ══════════════════════════════════════════
 *  Used for: Pokedex — full list of all Pokemon
 *
 *  Singly Linked List: each node has one
 *  pointer (next), forming a one-way chain.
 *
 *  Operations:
 *    addPokemon(p)      → append to tail  O(n)
 *    search(name)       → iterative scan  O(n)
 *    displayByType(t)   → filtered print  O(n)
 *    displayAll()       → print all       O(n)
 */

public class PokedexList {

    private PokemonNode head;
    private int         size;

    public PokedexList() {
        head = null;
        size = 0;
    }

    public void addPokemon(Pokemon p) {
        PokemonNode node = new PokemonNode(p);
        if (head == null) {
            head = node;
            head.idx = size + 1;
        } else {
            PokemonNode cur = head;
            while (cur.next != null) cur = cur.next;
            cur.next = node;
            cur.idx = size + 1;
        }
        size++;
    }

    /**
     * Iterative search by name (case-insensitive).
     * Traverses the list one node at a time.
     */
    public Pokemon search(String name) {
        PokemonNode cur = head;
        while (cur != null) {
            if (cur.data.name.equalsIgnoreCase(name)) return cur.data;
            cur = cur.next;
        }
        return null;
    }

    /** Print all Pokemon that match the given type. */
    public void displayByType(String type) {
        System.out.println("  --- " + type + "-type Pokemon in Pokedex ---");
        PokemonNode cur   = head;
        boolean     found = false;
        while (cur != null) {
            if (cur.data.type.equalsIgnoreCase(type)) {
                System.out.println("  -> " + cur.data);
                found = true;
            }
            cur = cur.next;
        }
        if (!found) System.out.println("  (none found)");
    }

    /** Print every Pokemon in the Pokedex. */
    public void displayAll() {
        System.out.println("  ===== POKEDEX [SLL] =====");
        PokemonNode cur = head;
        int idx = 1;
        while (cur != null) {
            System.out.println("  " + idx + ". " + cur.data);
            cur = cur.next;
            idx++;
        }
        if (size == 0) System.out.println("  (empty)");
    }

    public int         size()    { return size; }
    public PokemonNode getHead() { return head; }
}
