package pl.gd.itstartup.core.cards;

public enum CardType {
    PROGRAMMER("Pracownik - DEV"),
    SCRUM_MASTER("Pracownik - Agile Coach"),
    HR("Pracownik - HR"),
    KNOWLEDGE("Wiedza"),
    ACTON("Karta Akcji");
    private String polishName;

    CardType(String polishName) {
        this.polishName = polishName;
    }

    public String getPolishName() {
        return polishName;
    }
}
