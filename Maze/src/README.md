# src

This directory holds all project files for the game.

### Files/Directories in this directory:
| Name | Description |
| ---- | ----------- |
| [Common](Common/README.md) | Common files that are shared between Players and the Referee.|
| [Enumerations](Enumerations) | Holds all basic Enumerations used for the Game |
| [Players](Players/README.md) | Contains the Strategies employed by players and a simple AI Player that uses a strategy to compute actions to take for a given state.|
| [Referee](Referee/README.md) | Files concerned with the Referee and Observer logic for the game. |
| [Remote](Remote/README.md) | Files concerned with the remote interactions logic of the game.|


### Directory Hierarchy
```
src
├── Common
│   ├── Action.java
│   ├── Board.java
│   ├── Coordinate.java
│   ├── JSONConverter.java
│   ├── MoveAction.java
│   ├── Pass.java
│   ├── README.md
│   ├── State.java
│   ├── Tile.java
│   ├── Utils.java
│   └── gems
│       ├── ...
├── Enumerations
│   ├── Direction.java
│   ├── Gem.java
│   ├── Orientation.java
│   ├── ResponseStatus.java
│   └── Shape.java
├── Players
│   ├── AIPlayer.java
│   ├── EuclidStrategy.java
│   ├── IStrategy.java
│   ├── Player.java
│   ├── RiemannStrategy.java
│   └── Strategy.java
├── README.md
├── Referee
│   ├── ColorGenerator.java
│   ├── GameDisplay.java
│   ├── GamePanel.java
│   ├── ListenObserver.java
│   ├── Observer.java
│   ├── README.md
│   └── Referee.java
└── Remote
    ├── ApiOperation.java
    ├── Client
    │   ├── PlayerClient.java
    │   ├── ProxyReferee.java
    │   └── README.md
    ├── README.md
    └── Server
        ├── ProxyPlayer.java
        ├── README.md
        └── RefereeServer.java
```
