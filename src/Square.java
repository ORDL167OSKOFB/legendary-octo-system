public class Square {
    private String name;
    private int squareId;
    private static int nextId = 1;
    private AreaType area; // ! edited from string to enum
    private int cost;

    private int campCost;
    private int sanctCost;
    private int mortgage;
    
    private int rentZero;
    private int rentOne;
    private int rentTwo;
    private int rentThree;
    private int rentFour;
    private int rentSanct;

    private boolean isBought = false;
    private Player owner;
    private int campCount = 0;
    private int sanctCount = 0;



    private boolean isChanceCard;
    private boolean isCommunityCard;

    public Square(String name, int cost, AreaType area) {
        this.name = name;
        this.cost = cost;
        this.area = area;
        this.mortgage = cost/2;
        this.squareId = nextIdNum();
    }

    private static int nextIdNum() {
        return nextId++;
    }

    /**
    * Getter for Square ID
    * @return Square ID
    */
    public int getSquareId() {
        return this.squareId;
    }

    /**
     * Setter for the values a Square has
     * @param campBuy
     * @param SanctBuy
     * @param rentZero
     * @param rentOne
     * @param rentTwo
     * @param rentThree
     * @param rentFour
     * @param rentSanct
     * @param isInactive
     * @param isChanceCard
     * @param isCommunityCard
     */
    public void setValues(int campBuy, int SanctBuy, int rentZero, int rentOne, int rentTwo, int rentThree, int rentFour, int rentSanct, boolean isInactive, boolean isChanceCard, boolean isCommunityCard)
	{
		this.campCost = campBuy;
		this.sanctCost = SanctBuy;
		this.rentZero = rentZero;
		this.rentOne = rentOne;
		this.rentTwo = rentTwo;
		this.rentThree = rentThree;
		this.rentFour = rentFour;
        this.rentSanct = rentSanct;
        this.isChanceCard = isChanceCard;
        this.isCommunityCard = isCommunityCard;
	}

    /**
     * Setter for owner of this Square
     * @param owner Player to be new owner
     */
    public void setOwner(Player owner) {
        this.owner = owner;
        owner.getFinalCredits().addTransaction(getCost(), name, area.toString());
        isBought = true;
    }

    /**
     * Method to purchase a camp on this Square
     * @param amount number of camps to purchase
     * @return true if successfully purchased else false
     */
    public boolean buyCamp(int amount) {
        if(owner.getManpower() >= campCost * amount) {
            if(amount - campCount >= 0 && campCount + amount <= 4) {
                campCount += amount;
                owner.removeManpower(campCost * amount);
                return true;
            }
        } 
        return false;
    }

    /**
     * Method to purhcase a sanctuary on this Square
     * @return true if successfully purchased else false
     */
    public boolean buySanctuary() {
        if(sanctCount < 1 && owner.getManpower() >= sanctCost) {
            sanctCount = 1;
            campCount = 0;
            return true;
        }
        return false;
    }

    /**
     * Getter for currently built camp count
     * @return camp count
     */
    public int getCampCount() {
        return this.campCount;
    }
    
    /**
     * Getter for currently built sanctuary count
     * @return sanctuary count
     */
    public int getSanctCount()
    {
    	return this.sanctCount;
    }

    /**
     * Reset this square to default values
     */
    public void resetSquare() {
        isBought = false;
        owner = null;
        campCount = 0;
        sanctCount = 0;
    }

    /**
     * Reset this Square to a new owner
     * @param newOwner
     */
    public void reset(Player newOwner) {
        isBought = true;
        owner = newOwner;
    }

    /**
     * Setter for if the Square is owned
     * @return true if successfully set else false
     */
    public boolean isOwned() {
        if(owner != null) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Getter for Square owner
     * @return Player object
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Getter for AreaType
     * @return AreaType for this Square
     */
    //TODO Change to getAreaType()?
    public AreaType getArea() {
        return area;
    }

    /**
     * Getter for name
     * @return name
     */
    public String getName()
	{
		return name;
	}
	
    /**
     * Getter for Square cost
     * @return Square cost
     */
	public int getCost()
	{
		return cost;
	}
	
    /**
     * Getter for camp cost
     * @return camp cost
     */
	public int getCampCost()
	{
		return campCost;
	}
	
    /**
     * Getter for sanctuary cost
     * @return sanctuary cost
     */
	public int getSanctCost()
	{
		return sanctCost;
    }

    /**
     * Getter for rent
     * @return rent based on amount of camps
     */
    public int getRent() {
        switch (campCount) {
            case 0:
                if(sanctCount >= 1) {
                    return rentSanct;
                } 
                return rentZero;
            case 1:
                return rentOne;
            case 2:
                return rentTwo;
            case 3:
                return rentThree;
            case 4:
                return rentFour;
            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Checks if a Square is a chance square
     * @return true if a chance square else false
     */
    public boolean isChanceCard() {
        return this.isChanceCard;
    }

        /**
     * Checks if a Square is a community square
     * @return true if a community square else false
     */
    public boolean isCommunityCard() {
        return this.isCommunityCard;
    }
    
}
