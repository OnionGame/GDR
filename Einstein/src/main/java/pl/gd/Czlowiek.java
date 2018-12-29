package pl.gd;

public class Czlowiek implements Comparable<Czlowiek> {

    private Narodowosc narodowosc;

    private Integer nrDomuField;
    private Napoj napojField;
    private Kolor kolorField;
    private Papierosy papierosyField;
    private Zwierze zwierzeField;

    public Czlowiek(Narodowosc narodowosc) {
        this.narodowosc = narodowosc;
    }

    public boolean spelniaZasady() {
        return zasada6() && zasada8() && zasada10() && zasada14() && zasada15();
    }

    private boolean zasada6() {
        if (getKolorField() == Kolor.zolty) {
            return papierosyField == Papierosy.cygaro;
        }
        return true;
    }

    private boolean zasada8() {
        if (getNrDomuField().equals(3)) {
            return napojField == Napoj.mleko;
        }
        return true;
    }

    private boolean zasada10() {
        if (getPapierosyField() == Papierosy.bezFiltra) {
            return zwierzeField == Zwierze.ptaki;
        }
        return true;
    }

    private boolean zasada14() {
        if (getPapierosyField() == Papierosy.mentolowe) {
            return napojField == Napoj.piwo;
        }
        return true;
    }

    private boolean zasada15() {
        if (getKolorField() == Kolor.zielony) {
            return napojField == Napoj.kawa;
        }
        return true;
    }


    public void setNrDomuField(Integer nrDomuField) {
        this.nrDomuField = nrDomuField;

    }

    public void setNapojField(Napoj napojField) {
        this.napojField = napojField;

    }

    public void setKolorField(Kolor kolorField) {
        this.kolorField = kolorField;

    }

    public void setPapierosyField(Papierosy papierosyField) {
        this.papierosyField = papierosyField;
    }

    public void setZwierzeField(Zwierze zwierzeField) {
        this.zwierzeField = zwierzeField;
    }

    public Integer getNrDomuField() {
        return nrDomuField;
    }

    public Napoj getNapojField() {
        return napojField;
    }

    public Kolor getKolorField() {
        return kolorField;
    }

    public Narodowosc getNarodowoscField() {
        return narodowosc;
    }


    public Papierosy getPapierosyField() {
        return papierosyField;
    }

    public Zwierze getZwierzeField() {
        return zwierzeField;
    }


    @Override
    public String toString() {
        return getNarodowoscField() + "{" +
                " " + nrDomuField +
                ", " + napojField +
                ", " + kolorField +
                ", " + papierosyField +
                ", " + zwierzeField +
                '}';
    }

    @Override
    public int compareTo(Czlowiek o) {
        return nrDomuField.compareTo(o.nrDomuField);
    }
}
