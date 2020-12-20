# Gameplay Instructions

You spawn in with 3 lives.

Left Arrow key to move left
Right Arrow key to move right
Spacebar to shoot

Each invader is worth 10 pts with the occassional invader giving 300 pts.
Each bunker has 10 hits before breaking.
To end the game prematurely press Escape. This will take you to a screen that will reset the game.

## Space Invaders

Space Invaders is a two-dimensional fixed shooter game in which the player
controls a laser cannon by moving it horizontally across the bottom of the
screen and firing at descending aliens (space invaders). The aim is to defeat
the rows of aliens. The player defeats an alien, and earns points, by shooting
it with the laser cannon.

The aliens attempt to destroy the cannon by firing at it while they approach
the bottom of the screen. If they reach the bottom, the alien invasion
is successful and the game ends. Also, if the cannon is destroyed, the player
loses a life. If the player is out of lives, the game ends.

Occasionally, a mystery ship will appear and move across the top of
the screen (usually right-to-left). Once it makes it to the other side,
it usually dissapears.

The laser cannon is partially protected by several stationary defense
bunkers that are gradually destroyed by a numerous amount of blasts from the
aliens or player.

![Space Invaders](http://imgur.com/sXBK1Dk.png)

### Game Setup

The game must start with five rows of eleven aliens. The number of defense
bunkers should be eight. The player starts off with three lives.

### Scoring

Destroying a regular space invader should award 10 points.
Destroying a mystery ship should award 100 points.

### Level Mechanism

Your implementation of this game should have a mechanism whereby the game
increases in difficulty over time. The reccommended way of doing this is
to have levels. The level of the game increases after a certain number
of enemies are cleared. Each level should cause the space invaders to move
at a faster rate than at the previous level. If all enemies are cleared,
a new wave should appear, optionally restoring the defense bunkers.


## Build System

For this project, we will be using the Simple Build System (sbt). If you clone 
the project from the GitHub repository then everything will be setup for you. In 
order to compile your project, you can use the following command:

    $ ./sbt compile

To run your project, use the following command:

    $ ./sbt run

In order to clean your project (remove compiled code), use the following command:

    $ ./sbt clean
