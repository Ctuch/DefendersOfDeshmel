package model;

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
    //private int moveSpeed;

    //EFFECTS: creates a new person that is available to put on the map and sets their attributes
    public Person(String name) {
        this.name = name;
        this.available = true;
        this.isEnemy = false;
        setAttributes(name);
        //this.moveSpeed = moveSpeed;
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
    }

    //MODIFIES: this
    //EFFECTS: adds weapon, attackPower and health attributes for an ice sorcerer
    private void createIceSorcerer() {
        this.weapon = new Weapon("Freeze Ray", 3);
        this.attackPower = 5;
        this.health = 8;
        this.characterCode = "IC";
    }

    //MODIFIES: this
    //EFFECTS: adds weapon, attackPower and health attributes for an fire sorceress
    private void createFireSorceress() {
        this.weapon = new Weapon("Fire Beam", 4);
        this.attackPower = 3;
        this.health = 10;
        this.characterCode = "FR";
    }

    //MODIFIES: this
    //EFFECTS: adds weapon, attackPower and health attributes for an foot soldier
    private void createFootSoldier() {
        this.weapon = new Weapon("Great Sword", 1);
        this.attackPower = 6;
        this.health = 12;
        this.characterCode = "FS";
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
        return ts;
    }

    public boolean isEnemy() {
        return isEnemy;
    }
}
