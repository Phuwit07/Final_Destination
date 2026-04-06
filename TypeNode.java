package pokemon.game.pokemon;

/** Node used by TypeCycle (CLL). */
public class TypeNode {
    public String   typeName;
    public String   strongAgainst;  // deals 2x damage to this type
    public String   weakAgainst;    // deals 0.5x damage to this type
    public TypeNode next;           // points to next type (circular — never null)

    public TypeNode(String typeName, String strongAgainst, String weakAgainst) {
        this.typeName      = typeName;
        this.strongAgainst = strongAgainst;
        this.weakAgainst   = weakAgainst;
        this.next          = null;
    }
}
