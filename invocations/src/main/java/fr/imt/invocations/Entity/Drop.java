package fr.imt.invocations.Entity;

public enum Drop {
    MONSTRE_1(1, 30),  // 30% de chance
    MONSTRE_2(2, 60),  // 30% de chance
    MONSTRE_3(3, 90),  // 30% de chance
    MONSTRE_4(4, 100); // 10% de chance

    private final int id;
    private final int probabilite;

    Drop(int id, int probabilite) {
        this.id = id;
        this.probabilite = probabilite;
    }

    public int getId() {
        return id;
    }

    public int getProbabilite() {
        return probabilite;
    }
}