package model;

public class Weapon {

    private String name;
    private int range;

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

    @Override
    public String toString() {
        return "Name: " + name + ", Range: " + range;
    }
}
