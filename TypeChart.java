package pokemon.game.pokemon;

/** Builds the Circular Linked List of type advantages. */
public class TypeChart {

    public static TypeCycle buildTypeCycle() {
        TypeCycle cycle = new TypeCycle();
        // addType( typeName, strongAgainst, weakAgainst )
        cycle.addType("Fire",     "Grass",    "Water");
        cycle.addType("Water",    "Fire",     "Electric");
        cycle.addType("Grass",    "Water",    "Fire");
        cycle.addType("Electric", "Water",    "Ground");
        cycle.addType("Ground",   "Electric", "Grass");
        cycle.addType("Rock",     "Fire",     "Water");
        cycle.addType("Normal",   "None",     "None");
        return cycle;
    }
}
