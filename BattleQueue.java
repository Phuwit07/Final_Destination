package pokemon.game.pokemon;

/**
 * ══════════════════════════════════════
 *  DATA STRUCTURE : QUEUE (BattleQueue)
 * ══════════════════════════════════════
 *  Used for: Enemy Turn Order
 *
 *  FIFO — First In, First Out.
 *  The first enemy added is the first to battle.
 *
 *  Internally: a singly linked list with
 *  both a front pointer and a rear pointer.
 *
 *  Operations:
 *    enqueue(p) → add to rear    O(1)
 *    dequeue()  → remove front   O(1)
 *    peek()     → read front     O(1)
 */
public class BattleQueue {

    private PokemonNode front;
    private PokemonNode rear;
    private int         size;

    public BattleQueue() {
        front = null;
        rear  = null;
        size  = 0;
    }

    /** Add a Pokemon to the rear of the queue. */
    public void enqueue(Pokemon p) {
        PokemonNode node = new PokemonNode(p);
        if (isEmpty()) {
            front = rear = node;
        } else {
            rear.next = node;
            rear      = node;
        }
        size++;
    }

    /** Remove and return the front Pokemon. */
    public Pokemon dequeue() {
        if (isEmpty()) return null;
        Pokemon data = front.data;
        front = front.next;
        if (front == null) rear = null;
        size--;
        return data;
    }

    /** Return the front Pokemon without removing it. */
    public Pokemon peek() {
        if (isEmpty()) return null;
        return front.data;
    }

    public boolean isEmpty() { return front == null; }
    public int     size()    { return size; }

    /** Print the enemy queue from front to rear. */
    public void display() {
        if (isEmpty()) {
            System.out.println("  [Enemy queue is empty]");
            return;
        }
        System.out.print("  Enemy Queue: ");
        PokemonNode cur = front;
        while (cur != null) {
            System.out.print("[" + cur.data.name + "]");
            if (cur.next != null) System.out.print(" -> ");
            cur = cur.next;
        }
        System.out.println();
    }
}
