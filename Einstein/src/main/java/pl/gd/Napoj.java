package pl.gd;

import java.util.Arrays;
import java.util.List;

public enum Napoj {
    piwo, mleko, woda, kawa, herbata;

    public static List<Napoj> rest() {
        return Arrays.asList(piwo, mleko, woda, kawa);
    }
}
