package pokemon.game.pokemon;

public class Move {
    public String name;
    public int damage;
    public String type;
    public int pp;
    public int maxPp;

    public Move(String name, int damage, String type, int pp) {
        this.name   = name;
        this.damage = damage;
        this.type   = type;
        this.pp     = pp;
        this.maxPp  = pp;
    }

    @Override
    public String toString() {
        return name + " (" + type + ", " + damage + " dmg, PP:" + pp + "/" + maxPp + ")";
    }
}
