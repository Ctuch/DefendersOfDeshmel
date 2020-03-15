package ui;

import model.Action;
import model.Board;
import model.Enemy;
import model.Person;

import java.util.ArrayList;
import java.util.Random;

//Manages interaction with enemies by players and enemy actions
public class EnemyInteractionController {

    private Random random;
    private Board board;
    private ArrayList<Person> players;
    private ArrayList<Enemy> enemies;

    //creates a new random, and assigns game variables (board, players, enemies)
    public EnemyInteractionController(Board board, ArrayList<Person> players, ArrayList<Enemy> enemies) {
        random = new Random();
        this.board = board;
        this.players = players;
        this.enemies = enemies;
    }

    //REQUIRES: enemies is not empty
    //MODIFIES: this
    //EFFECTS: Selects and enemy action and performs it,
    //         either: adds enemy to the board in a random unoccupied location
    //                 attacks a player in range
    //                 moves the enemy toward a player if possible
    public void enemyTurn() {
        Enemy enemy = enemies.get(random.nextInt(enemies.size()));
        Action action = enemy.decideAction(board);
        if (action == Action.ADD) {
            board.findEmptySquare(enemy);
            System.out.println("Enemy " + enemy.getName() + " has been added to the board.");
        } else if (action == Action.ATTACK) {
            Person defender = enemy.canAttackPerson(board);
            attack(enemy, defender);
        } else {
            enemyMoveInDirection(action, enemy);
        }
    }

    //MODIFIES: board, enemies or players if defender dies
    //EFFECTS: has defender take damage from attacker if in range, and removes defender from game if dead
    private Boolean attack(Person attacker, Person defender) {
        if (board.isInWeaponRange(attacker, defender)) {
            defender.takeDamage(attacker.getAttackPower());
            System.out.println(defender.getName() + " has lost " + attacker.getAttackPower() + " health");
            if (defender.isDead()) {
                System.out.println(defender.getName() + " is dead");
                board.removeDeadDefender(defender, enemies, players);
            }
            return true;
        } else {
            System.out.println("You are out of range to make that attack");
            return false;
        }
    }


    //MODIFIES: board, enemies or players if defender dies
    //EFFECTS: gets the characters on squareFrom and squareTo, and attacks if legal
    //                          (neither are null and the attacker is not an enemy)
    //         attack - has defender take damage from attacker if in range, and removes defender from game if dead
    public Boolean attack(int squareFrom, int squareTo) {
        Person attacker = board.getBoard().get(squareFrom);
        Person defender = board.getBoard().get(squareTo);
        if (attacker == null || defender == null && !attacker.isEnemy()) {
            return false;
        } else {
            return attack(attacker, defender);
        }
    }

    //EFFECTS: determines whether the game should end if there are no enemies or no players remaining
    public boolean checkGameOver() {
        return enemies.isEmpty() || players.isEmpty();
    }

    //MODIFIES: board
    //EFFECTS: moves enemy in the direction specified by the action
    private void enemyMoveInDirection(Action action, Enemy enemy) {
        String statement = enemy.getName() + " has moved ";
        if (action == Action.MOVE_LEFT) {
            board.moveCharacter(Board.LEFT, enemy);
            System.out.println(statement + "left");
        } else if (action == Action.MOVE_RIGHT) {
            board.moveCharacter(Board.RIGHT, enemy);
            System.out.println(statement + "right");
        } else if (action == Action.MOVE_UP) {
            board.moveCharacter(Board.UP, enemy);
            System.out.println(statement + "up");
        } else if (action == Action.MOVE_DOWN) {
            board.moveCharacter(Board.DOWN, enemy);
            System.out.println(statement + "down");
        }
    }
}