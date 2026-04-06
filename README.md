# Pokemon Battle Simulator
### Data Structures Project — Pure Java Console

---

## HOW TO RUN IN VSCODE

### Step 1 — Install the Java extension pack
In VSCode → Extensions (Ctrl+Shift+X) → search:
  "Extension Pack for Java"  (publisher: Microsoft) → Install

### Step 2 — Open the project folder
  File → Open Folder → select the `PokemonBattle` folder
  (the one that contains `src/` and `.vscode/`)

### Step 3 — Run
  Open `src/pokemon/Main.java`
  Click the ▷ Run button above `public static void main`
  — or press  F5

The integrated terminal will open and the game starts.

---

## PROJECT STRUCTURE

```
PokemonBattle/
├── .vscode/
│   ├── launch.json          ← tells VSCode: mainClass = pokemon.Main
│   └── settings.json        ← tells VSCode: sourcePath = src/
└── src/
    └── pokemon/
        ├── Main.java                        ← entry point
        ├── datastructures/
        │   ├── MoveNode.java                ← node for Stack
        │   ├── MoveStack.java               ← STACK
        │   ├── PokemonNode.java             ← node for Queue + SLL
        │   ├── BattleQueue.java             ← QUEUE
        │   ├── PokedexList.java             ← SLL
        │   ├── MoveSelectNode.java          ← node for DLL
        │   ├── MoveSelector.java            ← DLL
        │   ├── TypeNode.java                ← node for CLL
        │   └── TypeCycle.java               ← CLL
        ├── game/
        │   ├── Move.java
        │   ├── Pokemon.java
        │   ├── TypeChart.java               ← builds the CLL
        │   └── Battle.java                  ← all game logic
        └── utils/
            ├── IterativeUtils.java          ← loop algorithms
            └── RecursiveUtils.java          ← recursive algorithms
```

---

## DATA STRUCTURES MAP

| Structure  | Class            | Role in game                              |
|------------|------------------|-------------------------------------------|
| Stack      | MoveStack        | Battle log — every move pushed (LIFO)     |
| Queue      | BattleQueue      | Enemy line-up — dequeue next (FIFO)       |
| SLL        | PokedexList      | All Pokemon, iterative name/type search   |
| DLL        | MoveSelector     | Move cursor: < and > navigate left/right  |
| CLL        | TypeCycle        | Type loop: Fire>Grass>Water>Fire...       |
| Iterative  | IterativeUtils   | countAlive, searchPokedex, partyStatus    |
| Recursive  | RecursiveUtils   | calcComboDamage, findStrongest, countByType|

---

## GAMEPLAY DEMO GUIDE (for presentation)

1. Main Menu → 1 (Start Battle)
2. Type `Pikachu` to choose your Pokemon
3. Battle starts vs Wild Geodude
4. Action **1 (Attack)** → shows DLL move cursor, use `<` `>` to navigate
5. Action **2 (Battle Log)** → shows Stack contents (LIFO order)
6. Action **3 (Pokedex)** → shows SLL, type `Fire` to filter
7. Action **4 (Type Chart)** → shows CLL cycle, check Fire vs Grass
8. Action **5 (Recursive Stats)** → shows all recursive + iterative demos
