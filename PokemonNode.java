package pokemon.game.pokemon;

/** Node used by BattleQueue (Queue) and PokedexList (SLL). */
public class PokemonNode {
    public Pokemon     data;
    public PokemonNode next;
    public int idx = 0;

    public PokemonNode(Pokemon data) {
        this.data = data;
        this.next = null;
    }
}
