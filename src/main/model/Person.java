package model;

public class Person {

    private String name;
    private Weapon weapon;
    private int attackPower;
    private int health;
    //private int moveSpeed;

    public Person(String name, Weapon weapon, int attackPower, int health) {
        this.name = name;
        this.weapon = weapon;
        this.attackPower = attackPower;
        this.health = health;
        //this.moveSpeed = moveSpeed;
    }


    public void gainHealth(int health) {
        this.health += health;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    public boolean isDead() {
        return this.health <= 0;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setAttributes(String name) {
        if (name.equals("Ice Sorcerer")) {
            createIceSorcerer();
        } else if (name.equals("Fire Sorceress")) {
            createFireSorceress();
        } else if (name.equals("Foot Soldier")) {
            createFootSoldier();
        }
    }

    private void createIceSorcerer() {
        this.weapon = new Weapon("Freeze Ray", 4);
        this.attackPower = 5;
        this.health = 8;
    }

    private void createFireSorceress() {
        this.weapon = new Weapon("Fire Beam", 5);
        this.attackPower = 3;
        this.health = 10;
    }

    private void createFootSoldier() {
        this.weapon = new Weapon("Great Sword", 1);
        this.attackPower = 6;
        this.health = 12;
    }
}
