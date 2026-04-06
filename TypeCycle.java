package pokemon.game.pokemon;

/**
 * ══════════════════════════════════════
 *  DATA STRUCTURE : CLL (TypeCycle)
 * ══════════════════════════════════════
 *  Used for: Type advantage (Fire > Grass > Water > Fire ...)
 *
 *  Circular Linked List: the last node's
 *  next pointer points back to head.
 *  There is NO null terminator — the list
 *  loops forever.
 *
 *  Operations:
 *    addType(...)          → O(n)
 *    findType(name)        → O(n) scan
 *    getMultiplier(a, d)   → O(n) lookup
 *    nextType()            → O(1) advance cursor
 */
public class TypeCycle {

    private TypeNode head;
    private TypeNode current;
    private int      size;

    public TypeCycle() {
        head    = null;
        current = null;
        size    = 0;
    }

    /** Add a type to the circular list. */
    public void addType(String typeName, String strongAgainst, String weakAgainst) {
        TypeNode node = new TypeNode(typeName, strongAgainst, weakAgainst);
        if (head == null) {
            head       = node;
            head.next  = head;  // points to itself (circular, size=1)
            current    = head;
        } else {
            // Walk to the last node (the one whose next == head)
            TypeNode last = head;
            while (last.next != head) last = last.next;
            last.next  = node;
            node.next  = head;  // close the circle
        }
        size++;
    }

    /** Advance and return the next type node. */
    public TypeNode nextType() {
        if (current == null) return null;
        current = current.next;
        return current;
    }

    /** Find a TypeNode by name. Scans at most `size` nodes. */
    public TypeNode findType(String typeName) {
        if (head == null) return null;
        TypeNode cur = head;
        for (int i = 0; i < size; i++) {
            if (cur.typeName.equalsIgnoreCase(typeName)) return cur;
            cur = cur.next;
        }
        return null;
    }

    /**
     * Return the damage multiplier for atkType attacking defType.
     *   2.0  = super effective
     *   0.5  = not very effective
     *   1.0  = normal
     */
    public double getMultiplier(String atkType, String defType) {
        TypeNode node = findType(atkType);
        if (node == null) return 1.0;
        if (node.strongAgainst.equalsIgnoreCase(defType)) return 2.0;
        if (node.weakAgainst.equalsIgnoreCase(defType))   return 0.5;
        return 1.0;
    }

    /** Print the cycle visually: [Fire] -> [Grass] -> [Water] -> [Fire] (wraps) */
    public void displayCycle() {
        if (head == null) return;
        System.out.print("  Type Cycle [CLL]: ");
        TypeNode cur = head;
        for (int i = 0; i < size; i++) {
            System.out.print("[" + cur.typeName + "] -> ");
            cur = cur.next;
        }
        System.out.println("[" + head.typeName + "] ... (infinite loop)");
    }

    public int size() { return size; }
}
