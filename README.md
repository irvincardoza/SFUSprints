# SFUSprints
# SFU Sprints - Final Project

## Introduction
SFU Sprints is a tile-based 2D grid game where players navigate the SFU campus map to collect coins, avoid enemies, and reach the bus stop before time runs out. The project implements core mechanics, AI-driven enemies, collision handling, and a complete UI system.






<img width="1340" alt="Screenshot 2025-03-17 at 6 39 12 PM" src="https://github.com/user-attachments/assets/cce54648-f834-46d5-b9c9-3d9712c1d973" />


## Build Automation

## 🧱 Requirements

- Java 17 or higher
- Apache Maven 3.6+

---

## 🛠️ Build Instructions

To compile and generate all artifacts:

```bash
mvn clean package
```

This produces:
- `target/sfusprint.jar` — Executable game file

- Packaged JavaDocs

- Full HTML Javadoc site

---

## ▶️ Run the Game

```bash
java -jar target/sfusprint-1.0-SNAPSHOT.jar
```

This will launch the game if the manifest is correctly set.

---



## Features

### 1. Player Movement & Controls
- Navigate using **WASD/Arrow keys**, with movement restricted by walls and map boundaries.
- Discrete tile-based movement across a **17x17 grid**.
- Interacts with game elements (coins, puddles, compass card, enemies) based on position.

### 2. Game Map & Rendering
- **17x17 grid** represented by a 2D array in `GameMap`.
- Dynamic rendering of tiles:
  - `0`: Walkable tile
  - `1`: Wall
  - `2`: Coin
  - `3`: Puddle
  - `4`: Compass Card
  - `5`: Exit (Bus Stop)

### 3. Collision Detection & Object Interaction
- Player cannot move through walls.
- Collectibles (coins, compass card) are removed from the grid upon collection.
- Enemies capture the player on contact.
- **Puddle mechanic**:
  - Immobilizes the player for **1.5 seconds** and deducts **1 point**.
  - Puddle disappears after activation.

### 4. Collectibles & Scoring
- **10 coins** scattered on the map (+1 point each).
- **Compass Card** bonus (+10 points), spawns randomly at game start.

### 5. Enemy AI - Security Guards & Professors
- Both enemies use **A\* pathfinding** with unique heuristics to avoid overlapping.
- Enemies spawn at separate locations and chase the player.
- Can move through puddles and collectables but not through walls.

### 6. Game States & Conditions
- **Timer**: Limited time to collect coins and escape.
- **Win**: Collect all coins and reach the bus stop before time runs out.
- **Loss**: 
  - Caught by an enemy.
  - Timer reaches zero.
  - Player’s score is negative.
- **Pause Menu**: `ESC` to pause, `P/ESC` to resume, `Q` to quit.

## Additional Details

### Design Choices
- Multithreaded architecture for smoother gameplay:
  - `playerThread`, `enemyThread`, `repaintThread`.
- Embedded collision handling directly into movement logic.
- Grid-based object management reduces system complexity.
- Integrated rendering inside `GamePanel` for a unified UI system.

### Team Contributions
- **Angelina**: UI panels, game layout, score and timer rendering.
- **Darine**: Enemy AI logic, puddle mechanics, thread management.
- **Irvin**: Coin system, map and object placement, player controls.
- **Viktor**: Game art, rendering logic, collision detection.

### Libraries Used
- `javax.swing`, `java.awt`, `java.awt.event` for UI and input.
- `java.util` for data structures and A* implementation.
- `java.imageio` for sprite loading.
- `java.util.Random` for randomizing placements.

### Challenges
- Balancing AI to be challenging but fair.
- Preventing enemy overlap using heuristic adjustments.
- Ensuring collision and rendering logic synced correctly.
- Implementing a pause system that halts gameplay threads.


## 🧪 Testing Overview

Comprehensive unit tests were written using **JUnit** to ensure the stability and correctness of all core components in the game.

### ✅ Tested Modules

- **Game Logic**: Win/loss conditions, timer, scoring, and interaction outcomes were validated.
- **Player Movement**: Directional movement using **WASD/Arrow keys**, wall collisions, puddle effects, and score changes tested.
- **Enemy AI**: A* pathfinding verified for both Security Guards and Professors with proper heuristic execution and player tracking.
- **Keyboard Input**: Input handling for movement, pausing (`ESC`), resuming (`P`), and quitting (`Q`) tested under active game states.
- **Multithreading**: Verified stable execution of game loop, enemy behavior, and UI rendering across multiple threads.
- **Game Map**: Validated 17x17 grid generation, object placement (coins, puddles, compass), and tile interactions.
- **UI and Game State Management**: Confirmed correct rendering and switching between `MainPg`, `gameRunning`, and `gamePaused` states.

### ✅ Test Results
All JUnit tests passed successfully, covering functional behavior, boundary conditions, and game flow interactions.

---

Test files are located in:  
`src/test/java/com/sfusprint/`  
Each test targets a corresponding game class to maintain modular and maintainable test coverage.


  ## Getting Started

### Prerequisites
- Java 17 or higher installed
- A Java IDE (e.g., IntelliJ, Eclipse) or terminal/command line

### Running the Game

#### Using an IDE:
1. Open the project in your preferred IDE.
2. Navigate to the `Main.java` file located in the `com.sfusprint` package.
3. Run `Main.java` — this will launch the game window.

## Conclusion
SFU Sprints delivers a complete, playable game with real-time AI, core mechanics, and a dynamic user interface. Ongoing unit tests are being implemented to ensure system reliability and robustness.
