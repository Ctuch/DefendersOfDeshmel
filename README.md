# Defenders of Deshmel

## A turn-based strategy game

Darkness has overrun Deshmel, leaving your band of spellcasters and warriors to take back the land. 
In this application, players control a group of heroes by taking actions on their turn, to slay enemies
on the board. The application will load a board and indicate the **location of the players** and enemies.
It will also control the **movement of the enemies** and keep **track of health levels** of all characters.
The application will display the rules to the players when they trigger the **help function**, and display a character's
stats when they call the help function on that character. Players can see the number of enemies remaining and the
number of good characters remaining through asking for a **stats display**. Later functionality will include multiple 
game board shapes and wall formations, as well as varying amounts of enemies based on whether easy, medium or hard mode 
is selected.

### This application will be used by:
- People who like to play board games in digital format
- Audience ages 10 - 25
- Solo players
- Collaborative game players as a team

### My interest:
This project is interesting to me because I love strategy based board games, and have always wanted to be able to create
my own game. Additionally, it allows for a very simple beginning implementation that has a large amount of opportunity 
for expansion of bonus features such as AI, character customization or action feedback, allowing the creation of a
base feature set that can be continuously expanded or delivered in that state. This expansion capability is really 
exciting for me because I can play and enjoy the game as I go, while making it even better in each iteration.

## User Stories

As a user I want to be able to:
- move my character around on the board, but not through walls
- add a new character to the board, and have enemies be added to the board
- attack an enemy with my character's weapon
- display the help and stats views
- remove a character when their health drops below 0
- be able to save their game with all character location and stats preserved
- be able to reload their previous game and start playing where they left off
- clear the users save when they finish a game

## Instructions for Grader

- You can generate the first required event by first clicking on the blue circle representing the character you want to 
  add to the board from the lower panel, then clicking on the square you would like to add them to, and finally clicking 
  on the 'Add Character' button in the game panel.
- You can generate the second required event by deciding to move a character you have added to the board. To do this, 
  first click on the character you want to move, then on the square you want them to move to, then finally clicking
  the 'Move Character' button.
- You can trigger my audio component by taking any in game action successfully. Each is linked to a thematic sound 
  effect. You can also trigger an audio component by finishing a game.
- You can save the state of my application by clicking the 'Save and Quit' button on the game menu.
- You can reload the state of my application by clicking the 'load previous game' button on the main menu.
    
    *Note if this button is greyed out, there is no save to load (because you either just finished a game or it is your
    first time playing). If this is the case, you must start a new game, save it, and then you will be able to use this
     functionality.
     
Additional game instructions can be found by clicking the 'Display help' button on the game menu

## Phase 4: Task 2

I have chosen to test and design a class that is robust. My Enemy.java class in the model class is robust, with particular focus on the moveTowardTarget method which throws a NoViableDirectionException if the enemy cannot move, and the findClosestPerson method which throws a PersonNotOnBoardException. You can find corresponding tests in the EnemyTest.java file in the test module.
