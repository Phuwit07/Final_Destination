package pokemon.game.pokemon;

/**
 * ══════════════════════════════════════════════
 *  RECURSIVE ALGORITHMS (RecursiveUtils)
 * ══════════════════════════════════════════════
 *  Every method here calls itself.
 *  Pattern:
 *    1. Base case  → stop condition (null node)
 *    2. Recursive  → call self with smaller input
 */
public class RecursiveUtils {

    /**
     * Calculate total combo damage from a chain of MoveNodes.
     * Each move is multiplied by a decreasing multiplier.
     *
     *  Base case : node == null → return 0
     *  Recursive : damage(node) + calcComboDamage(node.next, multiplier-1)
     */
    public static int calcComboDamage(MoveNode node, int multiplier) {
        if (node == null) return 0;
        int thisDamage = node.data.damage * Math.max(1, multiplier);
        return thisDamage + calcComboDamage(node.next, multiplier - 1);
    }

    /**
     * Find the Pokemon with the highest current HP in a list.
     *
     *  Base case : node == null → return best found so far
     *  Recursive : compare current vs best, pass winner to next call
     */
    public static Pokemon findStrongest(PokemonNode node, Pokemon best) {
        if (node == null) return best;
        Pokemon current = node.data;
        if (best == null || current.hp > best.hp) best = current;
        return findStrongest(node.next, best);
    }

    /**
     * Count Pokemon of a given type in a list.
     *
     *  Base case : node == null → return 0
     *  Recursive : (1 if match, else 0) + countByType(node.next, type)
     */
    public static int countByType(PokemonNode node, String type) {
        if (node == null) return 0;
        int match = node.data.type.equalsIgnoreCase(type) ? 1 : 0;
        return match + countByType(node.next, type);
    }

    /**
     * Sum the current HP of all Pokemon in a list.
     *
     *  Base case : node == null → return 0
     *  Recursive : node.hp + totalPartyHp(node.next)
     */
    public static int totalPartyHp(PokemonNode node) {
        if (node == null) return 0;
        return node.data.hp + totalPartyHp(node.next);
    }
}
