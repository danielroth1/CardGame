# CardGame
A simple network card game based on the rules of Mau-Mau

## Installation
```
git clone https://github.com/danielroth1/CardGame.git
cd CardGame/src
javac main/Main.java
java main.Main
```

## Rules

To play a game, first start a server by pressing on the "Host" button.
Then, 2 or more clients can log in to that server over the before specified port by pressing the button "Join".

To start a local game, start a server and connect to it with the local ip "127.0.0.1".
Then start another client and do the same.

The game starts after each party ticked "ready" in the bottom right corner.

How to play:
Basic rules of Mau-Mau without any special cards, i.e. no draw 2 when placing 7 or wait a turn when placing 8.
Possible actions at your turn:

- Play a card from your hand if it matches the type or symbol of the card on the board.
- Draw a card from the deck.
- After executing an action the turn is automatically passed to the next player.

The goal is to play all cards in your hand.

## Gameplay
![auswahl_181](https://user-images.githubusercontent.com/34305776/33761494-79bc43a8-dc09-11e7-88a9-fbb52259c8df.png)
