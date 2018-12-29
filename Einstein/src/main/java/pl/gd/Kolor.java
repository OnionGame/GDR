package pl.gd;

import java.util.Arrays;
import java.util.List;

public enum Kolor {
    zielony, zolty, bialy, niebieski, czerwony;

    public static List<Kolor> rest() {
        return Arrays.asList(zielony, zolty, bialy, niebieski);
    }
}
