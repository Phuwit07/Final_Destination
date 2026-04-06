package pokemon.game.pokemon;

/**
 * ════════════════════════════════════════
 *  DATA STRUCTURE : DLL (MoveSelector)
 * ════════════════════════════════════════
 *  Used for: Move cursor during battle
 *
 *  Doubly Linked List: each node has
 *  both next AND prev pointers.
 *  Allows bidirectional navigation.
 *
 *  Operations:
 *    addMove(m)    → append        O(n)
 *    moveNext()    → cursor right  O(1)
 *    movePrev()    → cursor left   O(1)
 *    getSelected() → current move  O(1)
 */
public class MoveSelector {

    private MoveSelectNode head;
    private MoveSelectNode tail;
    private MoveSelectNode current;
    private int            size;

    public MoveSelector() {
        head = tail = current = null;
        size = 0;
    }

    /** Append a move to the end of the DLL. */
    public void addMove(Move m) {
        MoveSelectNode node = new MoveSelectNode(m);
        if (head == null) {
            head = tail = current = node;
        } else {
            tail.next  = node;
            node.prev  = tail;
            tail       = node;
        }
        size++;
    }

    /** Move cursor forward (wraps to head). */
    public void moveNext() {
        if (current == null) return;
        current = (current.next != null) ? current.next : head;
    }

    /** Move cursor backward (wraps to tail). */
    public void movePrev() {
        if (current == null) return;
        current = (current.prev != null) ? current.prev : tail;
    }

    /** Return the move the cursor is on. */
    public Move getSelected() {
        return (current != null) ? current.data : null;
    }

    /** Reset cursor back to the first move. */
    public void resetCursor() {
        current = head;
    }

    /** Load a new set of moves (replaces existing list). */
    public void loadMoves(Move[] moves) {
        head = tail = current = null;
        size = 0;
        for (Move m : moves) {
            if (m != null) addMove(m);
        }
    }

    /** Print all moves; marks the currently selected one. */
    public void display() {
        System.out.println("  --- Choose Move [DLL cursor] ---");
        MoveSelectNode cur = head;
        int idx = 1;
        while (cur != null) {
            String arrow  = (cur == current) ? "  <-- selected" : "";
            String noPp   = (cur.data.pp <= 0) ? " [NO PP]" : "";
            System.out.println("  " + idx + ". " + cur.data + noPp + arrow);
            cur = cur.next;
            idx++;
        }
    }

    public boolean isEmpty() { return head == null; }
    public int     size()    { return size; }
}
