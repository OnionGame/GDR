package pl.gd.itstartup.cards;

public enum CardType {

    ACTON("Karta Akcji"),
    PROGRAMMER("Pracownik - DEV"),
    SCRUM_MASTER("Pracownik - Agile Coach"),
    HR("Pracownik - HR"),
    KNOWNLAGE("Wiedza");
    private String polishName;

    CardType(String polishName) {
        this.polishName = polishName;
    }

    public String getPolishName() {
        return polishName;
    }
}
