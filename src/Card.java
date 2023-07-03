public class Card {
    private int cardId;
    private static int nextId = 0;
    private String desc;
    private int moveTo;
    private int location;
    private int manpower;
    private boolean isChance;

    public Card(String desc, int moveTo, int location, int manpower, boolean isChance) {
        this.desc = desc;
        this.moveTo = moveTo;
        this.location = location;
        this.manpower = manpower;
        this.isChance = isChance;
    }

    private static int nextIdNum() {
        return nextId++;
    }

    /**
     * Get String of Card description
     * @return Card description
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Setter for Card description
     * @param desc new description
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Setter for the Square a Card moves a Player to
     * @param squareId
     */
    public void setMoveTo(int squareId) {
        this.moveTo = squareId;
    }

    /**
     * Getter for the Square a Card moves a Player to
     * @return Square number
     */
    public int getMoveTo() {
        return this.moveTo;
    }

    /**
     * Getter for amount Card adjusts manpower
     * @return manpower amount
     */
    public int getManpower() {
        return manpower;
    }

    /**
     * Setter for amount Card adjusts manpower
     * @param manpower new manpower amount
     */
    public void setManPower(int manpower) {
        this.manpower = manpower;
    }

    @Override
    public String toString() {
        return "Card [desc=" + desc + ", isChance=" + isChance + ", location=" + location + ", manpower=" + manpower
                + ", moveTo=" + moveTo + "]";
    }

    /**
     * Getter for Card ID
     * @return Card ID
     */
    public int getCardId() {
        return this.cardId;
    }
}