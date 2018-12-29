package pl.gd;

import java.util.Arrays;
import java.util.List;

public enum Papierosy {
    light, bezFiltra, cygaro, mentolowe, fajka;

    public static List<Papierosy> rest() {
        return Arrays.asList(light, bezFiltra, cygaro, mentolowe);
    }
}
