package pokemon.game.pokemon;

public class Pokemon {
    public String name;
    public int    hp;
    public int    maxHp;
    public String type;
    public Move[] moves;
    public int    level;

    public Pokemon(String name, int hp, String type, int level, Move[] moves) {
        this.name   = name;
        this.hp     = hp;
        this.maxHp  = hp;
        this.type   = type;
        this.level  = level;
        this.moves  = moves;
    }

    public boolean isFainted() {
        return hp <= 0;
    }

    public void takeDamage(int dmg) {
        hp = Math.max(0, hp - dmg);
    }

    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount);
    }

    /** Returns a 10-character HP bar, e.g. [====------] 40/100 */
    public String hpBar() {
        int bars = (int) ((double) hp / maxHp * 10);
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < 10; i++) sb.append(i < bars ? "=" : "-");
        sb.append("] ").append(hp).append("/").append(maxHp);
        return sb.toString();
    }

    @Override
    public String toString() {
        return name + " (Lv." + level + ", " + type + ", HP:" + hp + "/" + maxHp + ")";
    }
}
