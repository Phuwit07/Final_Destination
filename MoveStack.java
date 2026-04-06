package pokemon.game.pokemon;

/**
 * ══════════════════════════════════════
 *  DATA STRUCTURE : STACK (MoveStack)
 * ══════════════════════════════════════
 *  Used for: Battle Log / Move History
 *
 *  LIFO — Last In, First Out.
 *  The most recent move is always on top.
 *
 *  Internally: a singly linked list where
 *  the head IS the top of the stack.
 *
 *  Operations:
 *    push(m)  → add to top      O(1)
 *    pop()    → remove from top O(1)
 *    peek()   → read top        O(1)
 */
public class MoveStack {

    private MoveNode top;
    private int      size;

    public MoveStack() {
        top  = null;
        size = 0;
    }

    /** Push a move onto the top of the stack. */
    public void push(Move m) {
        MoveNode node = new MoveNode(m);
        node.next = top;   // new node points to old top
        top       = node;  // top is now the new node
        size++;
    }

    /** Remove and return the top move. Returns null if empty. */
    public Move pop() {
        if (isEmpty()) return null;
        Move data = top.data;
        top = top.next;
        size--;
        return data;
    }

    /** Return the top move without removing it. */
    public Move peek() {
        if (isEmpty()) return null;
        return top.data;
    }

    public boolean isEmpty() { return top == null; }
    public int     size()    { return size; }

    /** Print all moves from top (most recent) to bottom. */
    public void display() {
        if (isEmpty()) {
            System.out.println("  [Battle log is empty]");
            return;
        }
        MoveNode cur = top;
        int idx = 1;
        while (cur != null) {
            System.out.println("  " + idx + ". " + cur.data.name
                + " (" + cur.data.type + ", " + cur.data.damage + " dmg)");
            cur = cur.next;
            idx++;
        }
    }
}
