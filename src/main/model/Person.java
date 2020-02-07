package model;

import java.util.ArrayList;

public class Person {

    private String name;
    private Weapon weapon;
    private int attackPower;
    private int health;
    private boolean available;
    //private int moveSpeed;

    private static ArrayList<String> unusedCharacterList = new ArrayList<>();

    public Person(String name) {
        this.name = name;
        this.available = true;
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

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
