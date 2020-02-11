package model;

//creates a weapon to be wielded by a character with a name and a range
public class Weapon {

    private String name;
    private int range;

    //REQUIRES: range must be 1 - 4
    //EFFECTS: creates a new weapon with a name and a range
    public Weapon(String name, int range) {
        this.name = name;
        this.range = range;
    }

    public int getRange() {
        return range;
    }

    public String getName() {
        return name;
    }

    //EFFECTS: produces information about the weapon
    @Override
    public String toString() {
        return "Name: " + name + ", Range: " + range;
    }
}
