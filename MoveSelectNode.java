package pokemon.game.pokemon;

/** Node used by MoveSelector (DLL). Has both next and prev pointers. */
public class MoveSelectNode {
    public Move           data;
    public MoveSelectNode next;
    public MoveSelectNode prev;

    public MoveSelectNode(Move data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}
