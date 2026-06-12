package main.models;

public class Subject {
    private final String id;
    private final String name;
    private final int noCredits;

    public Subject(String id, String name, int noCredits) {
        this.id = id;
        this.name = name;
        this.noCredits = noCredits;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNoCredits() {
        return noCredits;
    }

    @Override
    public String toString() {
        return "Subject [id=" + id + ", name=" + name + ", noCredits=" + noCredits + "]";
    }
}
