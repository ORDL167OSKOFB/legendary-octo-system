import java.util.ArrayList;

public class Player {
    private int playerId;
    private String name;
    private int manpower;
    private int getOutOfJailCard = 0;
    private static int nextId = 1;
    private int position = 1;
    private boolean outOfJail = true;
    private int jailTimeLeft;
    private int totalManpowerGained;
    private int totalManpowerLost;
    private ArrayList<Square> ownedSquares;
    private Credits myCredits;
    
    public Player(String name) {
        this.name = name;
        this.manpower = 150;
        this.playerId = nextIdNum();
        ownedSquares = new ArrayList<Square>();
        myCredits = new Credits(name);
    }

    /**
     * Reset a Player's values
     */
    public void reset() {
        this.manpower = 150;
        this.getOutOfJailCard = 0;
        this.position = 1;
        this.outOfJail = true;
        this.jailTimeLeft = 0;
        this.totalManpowerGained = 0;
        this.totalManpowerLost = 0;
        this.ownedSquares.clear();
    }

    private static int nextIdNum() {
        return nextId++;
    }

    /**
     * Get details regarding a Player object
     * @return String of details
     */
    public String getDetails() {
        String res = String.format("%s currently has %d employees, %d properties and %d escape cards.", name, manpower, ownedSquares.size(), getOutOfJailCard);
        return res;
    }

    /**
     * Getter for Player name
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for Player ID
     * @return
     */
    public int getPlayerId() {
        return this.playerId;
    }

    /**
     * Setter for Player position
     * @param position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Setter for out of jail
     * @param bool true if not in jail, else false
     */
    public void setOutOfJail(boolean bool) {
        if(!bool) {
            jailTimeLeft = 3;
        }
        else {
            jailTimeLeft = 0;
            //TODO Check why this is being decremented

        }
        outOfJail = bool;
    }

    /**
     * Getter for Player position
     * @return current position on board
     */
    public int getPosition() {
        return position;
    }

    /**
     * Method to remove manpower from Player
     * @param decrement amount to remove
     * @return true if Player has enough manpower to remove, else false
     */
    public boolean removeManpower(int decrement) {
        if(this.manpower >= decrement) {
            this.manpower -= decrement;
            this.totalManpowerLost += decrement;
            return true;
        }
        return false;
    }

    /**
     * Method add manpower to Player
     * @param increment amount to add
     */
    public void addManpower(int increment) {
        if(increment > 0) {
            this.manpower += increment;
            this.totalManpowerGained += increment;
        }
    }

    /**
     * Getter for manpower
     * @return manpower
     */
    public int getManpower() {
        return this.manpower;
    }

    /**
     * Getter for out of jail
     * @return true if not in jail, else false
     */
    public boolean isOutOfJail() {
        return outOfJail;
    }

    /**
     * Remove one turn from Player's jail time
     */
    public void decrementJailTimeLeft() {
        jailTimeLeft--;
    }

    public int getJailTimeLeft() {
        return this.jailTimeLeft;
    }

    /**
     * Give Player a get out of jail free card
     */
    public void giveGetOutOfJailCard() {
        getOutOfJailCard++;
    }

    /**
     * Getter for amount of get out of jail free cards
     * @return amount of cards
     */
    public int getGetOutOfJailCard() {
        return getOutOfJailCard;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Total manpower gained throughout the game
     * @return manpower gained
     */
    public int getTotalManpowerGained() {
        return totalManpowerGained;
    }

    /**
     * Total manpower lost throughout the game
     * @return manpower lost
     */
    public int getTotalManpowerLost() {
        return totalManpowerLost;
    }

    /**
     * Add a Square to a Player's owned squares
     * @param square
     */
    public void addSquare(Square square) {
        this.ownedSquares.add(square);
    }

    /**
     * Remove a Square from the Player's owned squares
     * @param square
     * @return true if successfully removed else false
     */
    public boolean removeSquare(Square square) {
        for (int i = 0; i < ownedSquares.size(); i++) {
            if(ownedSquares.get(i).getSquareId() == square.getSquareId()) {
                this.ownedSquares.remove(ownedSquares.get(i));
                return true;
            }
        }
        return false;
    }

    /**
     * Getter for Credits on this Player
     * @return Credits object
     */
    public Credits getFinalCredits()
    {
    	return myCredits;
    }

    /**
     * Getter for owned squares
     * @return ArrayList of owned squares
     */
    public ArrayList<Square> getOwnedSquares() {
        return this.ownedSquares;
    }
    
    public String getDebugDetails() {
        return String.format("Name: %s\nID: %d\nManpower: %d\nPosition: %d", name, playerId, manpower, position);
    }
}
