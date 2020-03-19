package model;

import ui.Sound;

import java.util.ArrayList;
import java.util.Objects;

/*
Represents a character on the board with a name, weapon, attack power, health, and availability (dependent on if
already in game or dead). A person can be an enemy or a player character, and occupies one space on the board. A
person's move speed is one (for now), and can take move or attack actions on their turn against rivals
*/
public class Person {

    private String name;
    private Weapon weapon;

    private int attackPower;
    private int health;
    private boolean available;
    private String characterCode;
    protected boolean isEnemy;
    private int numSpecialActionCharges;
    private String specialActionString;
    private int locationX;
    private int locationY;
    private Sound attackSound;

    //EFFECTS: creates a new person that is available to put on the map and sets their attributes
    public Person(String name) {
        this.name = name;
        this.available = true;
        this.isEnemy = false;
        setAttributes(name);
    }


    //REQUIRES: health > 0
    //MODIFIES: this
    //EFFECTS: adds health to this character
    public void gainHealth(int health) {
        this.health += health;
    }

    //REQUIRES: damage > 0
    //MODIFIES: this
    //EFFECTS: removed health from character by amount damage
    public void takeDamage(int damage) {
        this.health -= damage;
    }

    //EFFECTS: returns true if character's health is zero or less
    public boolean isDead() {
        return this.health <= 0;
    }

    //REQUIRES: name is one of the characters with an assigned create method
    //MODIFIES: this
    //EFFECTS: adds weapon, attackPower and health attributes to the created Person
    private void setAttributes(String name) {
        switch (name) {
            case "Ice Sorcerer":
                createIceSorcerer();
                break;
            case "Fire Sorceress":
                createFireSorceress();
                break;
            case "Ranged Shooter":
                createRangedShooter();
                break;
            case "Sharp Shooter":
                createSharpShooter();
                break;
            case "Warped Knight":
                createWarpedKnight();
                break;
            default:
                createFootSoldier();
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: adds weapon, attackPower and health attributes for an ranged shooter
    private void createRangedShooter() {
        this.weapon = new Weapon("Cross Bow", 3);
        this.attackPower = 4;
        this.health = 12;
        this.characterCode = "RS";
        this.numSpecialActionCharges = 0;
        this.specialActionString = "";
        this.attackSound = Sound.ARCHER;
    }

    //MODIFIES: this
    //EFFECTS: adds weapon, attackPower and health attributes for an ice sorcerer
    private void createIceSorcerer() {
        this.weapon = new Weapon("Freeze Ray", 3);
        this.attackPower = 5;
        this.health = 8;
        this.characterCode = "IC";
        this.numSpecialActionCharges = 2;
        this.specialActionString = "Decrease all enemy attack power by 1";
        this.attackSound = Sound.EXPLOSION;
    }

    //MODIFIES: this
    //EFFECTS: adds weapon, attackPower and health attributes for an fire sorceress
    private void createFireSorceress() {
        this.weapon = new Weapon("Fire Beam", 4);
        this.attackPower = 3;
        this.health = 10;
        this.characterCode = "FR";
        this.numSpecialActionCharges = 2;
        this.specialActionString = "Damage all enemies by 3";
        this.attackSound = Sound.EXPLOSION;
    }

    //MODIFIES: this
    //EFFECTS: adds weapon, attackPower and health attributes for an foot soldier
    private void createFootSoldier() {
        this.weapon = new Weapon("Great Sword", 1);
        this.attackPower = 6;
        this.health = 12;
        this.characterCode = "FS";
        this.numSpecialActionCharges = 0;
        this.specialActionString = "";
        this.attackSound = Sound.SWORD_FIGHT;
    }

    //MODIFIES: this
    //EFFECTS: adds weapon, attackPower and health attributes for an sharp shooter
    private void createSharpShooter() {
        this.weapon = new Weapon("Pistol Crossbow", 2);
        this.attackPower = 5;
        this.health = 9;
        this.characterCode = "SS";
        this.numSpecialActionCharges = 0;
        this.specialActionString = "";
        this.attackSound = Sound.ARCHER;
    }

    //MODIFIES: this
    //EFFECTS: adds weapon, attackPower and health attributes for an foot soldier
    private void createWarpedKnight() {
        this.weapon = new Weapon("Lance", 1);
        this.attackPower = 2;
        this.health = 16;
        this.characterCode = "WN";
        this.numSpecialActionCharges = 0;
        this.specialActionString = "";
        this.attackSound = Sound.SWORD_FIGHT;
    }

    //REQUIRES: board is not null
    //MODIFIES: board
    //EFFECTS: triggers special action associated with this person
    public void specialAction(Board board) {
        switch (name) {
            case "Ice Sorcerer":
                reduceEnemyAttackPower(1, board);
                break;
            case "Fire Sorceress":
                attackAllEnemies(3, board);
                break;
            default:
                break;
        }
        numSpecialActionCharges--;
    }

    //REQUIRES: board is not null, damage > 0
    //MODIFIES: board
    //EFFECTS: damages all enemies on the board by damage
    private void attackAllEnemies(int damage, Board board) {
        for (Person person: board.getBoard()) {
            if (person != null && person.isEnemy()) {
                person.takeDamage(damage);
            }
        }
    }

    //REQUIRES: board is not null, attackPower > 0
    //MODIFIES: board
    //EFFECTS: reduces the attack power of all enemies on the board by attackPower
    private void reduceEnemyAttackPower(int attackPower, Board board) {
        for (Person person: board.getBoard()) {
            if (person != null && person.isEnemy()) {
                person.setAttackPower(person.getAttackPower() - attackPower);
                if (person.getAttackPower() <= 0) {
                    person.setAttackPower(1);
                }
            }
        }
    }

    //EFFECTS: returns true if person has charges of special action remaining
    public boolean hasChargesRemaining() {
        return numSpecialActionCharges > 0;
    }


    public String getSpecialActionString() {
        return specialActionString;
    }

    public void setNumSpecialActionCharges(int numSpecialActionCharges) {
        this.numSpecialActionCharges = numSpecialActionCharges;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getCharacterCode() {
        return characterCode;
    }

    public int getHealth() {
        return health;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setCharacterCode(String characterCode) {
        this.characterCode = characterCode;
    }

    public void setSpecialActionString(String specialActionString) {
        this.specialActionString = specialActionString;
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocation(int locationX, int locationY) {
        setLocationX(locationX);
        setLocationY(locationY);
    }

    private void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    private void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public Sound getAttackSound() {
        return attackSound;
    }

    //MODIFIES: this
    //EFFECTS: clears out players, and adds the persons to players (resets the list to default state)
    public static void addPlayers(ArrayList<Person> players) {
        players.clear();
        players.add(new Person("Fire Sorceress"));
        players.add(new Person("Ice Sorcerer"));
    }

    //EFFECTS: produces the associated character with chrCode or null if doesn't exist/ is already dead
    public static Person selectCharacterByCharacterCode(String chrCode, Board board, ArrayList<Person> players) {
        Person person = board.findPersonByCharacterCode(chrCode);
        if (person != null) {
            return person;
        }
        for (Person player : players) {
            if (player.getCharacterCode().equalsIgnoreCase(chrCode)) {
                return player;
            }
        }
        return null;
    }

    //EFFECTS: produces description of person
    @Override
    public String toString() {
        String ts = "Name: " + name;
        ts += "\nWeapon: " + weapon.toString();
        ts += "\nAttack Power: " + attackPower;
        ts += "\nHealth Status: ";
        if (isDead()) {
            ts += "Dead";
        } else {
            ts += health + " remaining health";
            ts += "\nIn Play: ";
            if (isAvailable()) {
                ts += "false";
            } else {
                ts += "true";
            }
        }
        if (!isEnemy) {
            ts += "\nSpecial Action: Has " + numSpecialActionCharges
                    + " charges remaining. See the help menu for ability.";
        }

        return ts;
    }


    //EFFECTS: produces description of person with html tags
    public String toStringHtml() {
        String ts = "<html>Name: " + name;
        ts += "<br/>Weapon: " + weapon.toString();
        ts += "<br/>Attack Power: " + attackPower;
        ts += "<br/>Health Status: ";
        if (isDead()) {
            ts += "Dead";
        } else {
            ts += health + " remaining health";
            ts += "<br/>In Play: ";
            if (isAvailable()) {
                ts += "false";
            } else {
                ts += "true";
            }
        }
        if (!isEnemy) {
            ts += "<br/>Special Action: Has " + numSpecialActionCharges
                    + " charges remaining. See the help menu for ability.";
        }

        return ts + "</html>";
    }

    //EFFECTS: overrides default equals method for Person objects, returns true if this and o are identical by fields
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        Person person = (Person) o;
        return attackPower == person.attackPower
                && health == person.health
                && available == person.available
                && isEnemy == person.isEnemy
                && numSpecialActionCharges == person.numSpecialActionCharges
                && name.equals(person.name)
                && weapon.equals(person.weapon)
                && characterCode.equals(person.characterCode)
                && specialActionString.equals(person.specialActionString);
    }

    //EFFECTS: overrides default hashcode method for Person objects, produces hashcode for this
    @Override
    public int hashCode() {
        return Objects.hash(name, weapon, attackPower, health, available, characterCode,
                isEnemy, numSpecialActionCharges, specialActionString);
    }
}
