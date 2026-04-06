package pokemon.game.pokemon;

/** Node used by MoveStack (Stack). */
public class MoveNode {
    public Move     data;
    public MoveNode next;

    public MoveNode(Move data) {
        this.data = data;
        this.next = null;
    }
}
