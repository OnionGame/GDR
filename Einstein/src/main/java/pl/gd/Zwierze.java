package pl.gd;

import java.util.Arrays;
import java.util.List;

public enum Zwierze {
    ptaki, ryby, koty, konie, psy;

    public static List<Zwierze> rest() {
        return Arrays.asList(ptaki, ryby, koty, konie);
    }
}
