import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

//TODO Trade needs to be added

public class Board {
    private ArrayList<Player> currentPlayers;
    private ArrayList<Player> allPlayers;
    private ArrayList<Area> areas;

    private static Random random = new Random();

    private int playerCount = 0;
    private int maxPlayerCount = 8;
    private int currentTurn = 0;
    private Player currentPlayer;
    private Square currentSquare;

    private int maxPlayers = 8;

    private ArrayList<Card> communityCards;
    private ArrayList<Card> chanceCards;
    private long startTime;
    private long endTime;

    private Database db;

    public Board() {
        currentPlayers = new ArrayList<Player>();
        allPlayers = new ArrayList<Player>();
        areas = new ArrayList<Area>();
        db = new Database();
        currentTurn = 0;
        communityCards = db.getCommunityCards();
        chanceCards = db.getChanceCards();
        generateAreas();
    }

    
    /**
     * Method to process the end of a turn
     */
    public void endTurn() {
        currentTurn++;
        if(currentTurn == playerCount) {
            currentTurn = 0;
        }
        
       // conditon for endGame, can be any player.
       if (playerCount <= 2 && endTime != 0.00)
       	currentPlayer = currentPlayers.get(0);
       
        currentPlayer = currentPlayers.get(currentTurn);
    }

    /**
     * Method for setting up the game on start
     */
    public void startGame() {
        shufflePlayers();
        currentPlayer = currentPlayers.get(currentTurn);
        currentSquare = getSquareById(1);
        genChanceCards();
        startTime = System.nanoTime();
    }

    /**
     * Method to generate the Area objects and their contained Square objects
     */
    private void generateAreas() {
        Area start = new Area("Starting Area", AreaType.START);
        Area madagascar = new Area("Madagascar", AreaType.MADAGASCAR);
        Area micronesia = new Area("Micronesia", AreaType.MICRONESIA);
        Area antarcticCircle = new Area("Antarctic Circle", AreaType.JAIL);
        Area borneo = new Area("Borneo", AreaType.BORNEO);
        Area tropicalAndes = new Area("Tropical Andes", AreaType.ANDES);
        Area skyIslands = new Area("Sky Islands", AreaType.SKYISLANDS);
        Area medditerraneanBasin = new Area("Medditerranean Basin", AreaType.MEDDITERRANEAN);
        Area arctic = new Area("Arctic", AreaType.ARCTIC);
        Area amazon = new Area("Amazon", AreaType.AMAZON);
        Area zoos = new Area("Zoos", AreaType.ZOO);
        Area tax = new Area("Tax", AreaType.TAX);
        Area chance = new Area("Chance", AreaType.CHANCE);
        Area community = new Area("Community", AreaType.CHANCE);
        //Area jail = new Area("Jail");
        Area utility = new Area("Utility", AreaType.UTILITY);

        ArrayList<Square> squares = db.getSquares();
        

        for (int i = 0; i < squares.size(); i++) {
            Square squareToAdd = squares.get(i);
            switch (squareToAdd.getArea()) {
                case START:
                    start.addSquare(squareToAdd);
                    break;

                case MADAGASCAR:
                    madagascar.addSquare(squareToAdd);
                    break;
            
                case MICRONESIA:
                    micronesia.addSquare(squareToAdd);
                    break;
            
                case JAIL:
                    antarcticCircle.addSquare(squareToAdd);
                    break;
            
                case BORNEO:
                    borneo.addSquare(squareToAdd);
                    break;
            
                case ANDES:
                    tropicalAndes.addSquare(squareToAdd);
                    break;
            
                case SKYISLANDS:
                    skyIslands.addSquare(squareToAdd);
                    break;
            
                case MEDDITERRANEAN:
                    medditerraneanBasin.addSquare(squareToAdd);
                    break;
            
                case ARCTIC:
                    arctic.addSquare(squareToAdd);
                    break;

                case AMAZON:
                    amazon.addSquare(squareToAdd);
                    break;
                
                case ZOO:
                    zoos.addSquare(squareToAdd);
                    break;

                case TAX:
                    tax.addSquare(squareToAdd);
                    break;
                
                case CHANCE:
                    chance.addSquare(squareToAdd);
                    break;
                
                case COMMUNITY:
                    community.addSquare(squareToAdd);
                    break;

                case UTILITY:
                    utility.addSquare(squareToAdd);
                    break;
            
                default:
                    break;
            }
        }

        addArea(start);
        addArea(madagascar);
        addArea(micronesia);
        addArea(antarcticCircle);
        addArea(borneo);
        addArea(tropicalAndes);
        addArea(skyIslands);
        addArea(medditerraneanBasin);
        addArea(arctic);
        addArea(amazon);
        addArea(zoos);
        addArea(tax);
        addArea(chance);
        addArea(community);
        //addArea(jail);
        addArea(utility);

        currentSquare = getSquareById(0);
    }

    /**
     * Get a Square object by it's ID
     * @param id ID of Square to search for
     * @return Square object if found else null
     */
    public Square getSquareById(int id) {
        for (int i = 0; i < areas.size(); i++) {
            if(areas.get(i).getSquareById(id) != null) {
                return areas.get(i).getSquareById(id);
            }
        }
        return null;
    }

    
    /**
     * Method to move current player relatively from their current position 
     * @param amount number of squares to move the player forward by
     */
    public boolean movePlayer(int amount) {
    	boolean circumnav = false;
      
    	if (currentPlayer.isOutOfJail() == true)
    	{
            int currentSquareNo = currentSquare.getSquareId();
            if(currentSquareNo + amount > Area.getTotalSquareCount()) {
                amount -= Area.getTotalSquareCount() - currentSquareNo;
                currentSquareNo = 0;
                currentPlayer.getFinalCredits().adjCircumnavigation();
            }
            currentSquareNo += amount;
            circumnav = true;
            currentSquare = getSquareById(currentSquareNo);
            currentPlayer.setPosition(currentSquareNo);
            return circumnav;
    	}
    	
    	return false;
    }

    /**
     * Move a player absolutely to a square by it's ID
     * @param squareId ID of square to move player to
     */
    public void movePlayerTo(int squareId) {
        currentSquare = getSquareById(squareId);
    }

    /**
     * Method to add a player
     * @param username name of player to add
     * @return true if player added successfully else false
     */
    public boolean addPlayer(String username) {
    	if(currentPlayers.size() >= maxPlayerCount) return false; // limits player count to 8 (can be adjusted in future)
    	
        boolean usernameAvailable = true;
        for (int i = 0; i < currentPlayers.size(); i++) {
            if(currentPlayers.get(i).getName().equals(username)) {
                usernameAvailable = false;
                return usernameAvailable;
            }
        }
        Player newPlayer = new Player(username);
        if(currentPlayers.size() >= maxPlayers) {
            return false;
        }
        currentPlayers.add(newPlayer);
        allPlayers.add(newPlayer);
        playerCount++;
        return true;
    }

    /**
     * Method to remove a player
     * @param player Player object to remove
     * @return true if player removed successfully else false
     */
    public boolean removePlayer(Player player) {
        if(currentPlayers.size() <= 2) { 
            endGame();
        }
        for (int i = 0; i < currentPlayers.size(); i++) {
        	//Changed to comparing players instead of comparing playerIDs.
            if(currentPlayers.get(i) == player) {
            	// Removing properties and resetting squares values
            	for(int j = 0; j < player.getOwnedSquares().size(); j++) {
            		player.getOwnedSquares().get(j).resetSquare();
            		player.getOwnedSquares().remove(j);
            	}
            	
                currentPlayers.remove(i);
                playerCount--;
                return true;
            }
        }
        return false;
    }

    /**
     * Get player ranking
     * @return an ArrayList of Player objects in order of ranking
     */
    public ArrayList<Player> getRankedPlayers() {
        Comparator<Player> playerComparator = Comparator.comparing(Player::getManpower).thenComparing(Player::getTotalManpowerGained).thenComparing(Player::getTotalManpowerLost);
        ArrayList<Player> rankedPlayers = new ArrayList<Player>(allPlayers.stream().sorted(playerComparator.reversed()).collect(Collectors.toList()));
        
        rankedPlayers.get(0).getFinalCredits().StoreCSV();
        //Credits
        try {
			rankedPlayers.get(0).getFinalCredits().creditRetrieve();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        System.out.println(rankedPlayers);
        
        return rankedPlayers;
    }

    /**
     * Method called when the game is ended
     */
    public void endGame() {
        endTime = System.nanoTime();
    }

    /**
     * Method to add areas
     * @param newArea
     */
    public void addArea(Area newArea) {
        areas.add(newArea);
    }

    /**
     * Shuffle the players at the start of the game
     */
    public void shufflePlayers() {
        Collections.shuffle(currentPlayers);
        Collections.shuffle(currentPlayers);
        Collections.shuffle(currentPlayers);
    }

    /**
     * Perform a dice roll
     * @return array of the two rolls
     */
    public int[] rollDice() {
        Random randomTwo = new Random();

        int rollOne = random.nextInt(6 - 1 + 1) + 1;
        int rollTwo = randomTwo.nextInt(6 - 1 + 1) + 1;

        int params[] = {rollOne, rollTwo};
        return params;
    }

    /**
     * Method to purchase a square
     * @return true if purchase was successfull else false
     */
    public boolean purchaseCurrentSquare() {
        if(currentPlayer.getManpower() >= currentSquare.getCost() && currentSquare.getOwner() == null) {
            currentSquare.setOwner(currentPlayer);
            currentPlayer.removeManpower(currentSquare.getCost());
            currentPlayer.addSquare(currentSquare);
            return true;
        }
        return false;
    }

    /**
     * Getter for areas
     * @return ArrayList of Area objects
     */
    public ArrayList<Area> getAreas() {
        return areas;
    }

    /**
     * Getter for current players
     * @return ArrayList of current Player objects
     */
    public ArrayList<Player> getCurrentPlayers() {
        return this.currentPlayers;
    }

    /**
     * Getter for current Player object
     * @return Player object
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Getter for current square
     * @return Square object
     */
    public Square getCurrentSquare() {
        return this.currentSquare;
    }

    /**
     * Getter for list of community cards
     * @return ArrayList of community cards
     */
    public ArrayList<Card> getCommunityCards() {
        return communityCards;
    }

    /**
     * Getter for list of chance cards
     * @return ArrayList of chance cards
     */
    public ArrayList<Card> getChanceCards() {
        return this.chanceCards;
    }
    
    /**
     * Method to draw a chance card
     * @return random Card object
     */
    public Card drawChanceCard() {
    	Random randChance = new Random();
    	int n = randChance.nextInt(17);
        Card newCard = calculateCardDesc(chanceCards.get(n));
    	return newCard;
    }
    
    /**
     * Method to draw a community card
     * @return random Card object
     */
    public Card drawCommunityCard() {
    	Random randComm = new Random();
    	int n = randComm.nextInt(17);
        Card newCard = calculateCardDesc(communityCards.get(n));
    	return newCard;
    }

    /**
     * Method to calculate the card description for computed values e.g. move to <randomAnimal>
     * @param card chosen card
     * @return card with updated values
     */
    public Card calculateCardDesc(Card card) {
        // Using regex to find patterns with any amount of characters enclosed by <>
    	
        Pattern pattern = Pattern.compile("<(\\S+)>");	// Compile string inside <>
        
        Matcher matcher = pattern.matcher(card.getDesc()); // Description of preExisting card
        String replacement = card.getDesc(); // Assign pre-existing description to string with <randomAnimal>, <pos> or <randomArea>. 
        Square randomSquare = null;
        
       
        while(matcher.find()) {
            switch(matcher.group(0)) {
                case "<randomAnimal>":
                    int randInt = random.nextInt(Area.getTotalSquareCount()+1-1) + 1;
                    randomSquare = getSquareById(randInt);
                    while(randomSquare.getArea() == AreaType.START || randomSquare.getArea() == AreaType.JAIL ||
                        randomSquare.getArea() == AreaType.CHANCE || randomSquare.getArea() == AreaType.COMMUNITY || 
                        randomSquare.getArea() == AreaType.DEFAULT) {
                            randInt = random.nextInt(Area.getTotalSquareCount()+1-1) + 1;
                            randomSquare = getSquareById(randInt);
                    }
                    randomSquare.getName();
                    replacement = replacement.replace("<randomAnimal>", randomSquare.getName()); 
                    card.setMoveTo(randInt);
                    break;
                case "<randomArea>":
                    replacement = replacement.replace("<randomArea>", randomSquare.getArea().toString());
                    break;
                case "<randomPlayer>":
                    do {
                        randInt = random.nextInt(currentPlayers.size()+1-0) + 0;
                        if (randInt > currentPlayers.size()-1) randInt = currentPlayers.size()-1;
                    } while (currentPlayers.get(randInt).getPlayerId() == currentPlayer.getPlayerId());
                    replacement = replacement.replace("<randomPlayer>", currentPlayers.get(randInt).getName());
                    break;
                case "<pos>":
                    replacement = replacement.replace("<pos>", currentSquare.getName());
                    break;
            }
        }
        card.setDesc(replacement);
        return card;
    }

    /**
     * Generate the chance cards
     */
    public void genChanceCards()
    {
    	for (Card j: getChanceCards())
    		j = calculateCardDesc(j);
    		
    }

    /**
     * Set the current square
     * @param square new square to set to current
     */
    public void setCurrentSquare(Square square) {
        this.currentSquare = square;
    }

    /**
     * Getter for start time
     * @return start time
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Getter for end time
     * @return end time
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * Get list of all players (different from current players; this contains all players that were ever in the current game)
     * @return
     */
    public ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    /**
     * Method to facilitate trading
     * @param toTrade ArrayList of properties to trade
     * @param oldOwner the old owner
     * @param newOwner the new owner
     * @param tradePrice how much manpower to trade
     * @return true if trade is successful else false
     */
    public boolean trade(ArrayList<Square> toTrade, Player oldOwner, Player newOwner, int tradePrice) {
    	
    	// check if newOwner has sufficient manpower
        if(toTrade.size() > 0 && newOwner.getManpower() >= tradePrice) {
            for (int i = 0; i < toTrade.size(); i++) {
            	//remove square from old
            	 oldOwner.removeSquare(toTrade.get(i));
            	 //set new owner
                toTrade.get(i).setOwner(newOwner);
                // Add square to new owner
                newOwner.addSquare(toTrade.get(i));
                //remove manpower from newOwner
                oldOwner.addManpower(tradePrice / toTrade.size());
                //add manpower to old
                newOwner.removeManpower(tradePrice / toTrade.size());
                
                newOwner.getFinalCredits().addTransaction(tradePrice / toTrade.size(), toTrade.get(i).getName(), toTrade.get(i).getArea().toString());
                return true;
            }
        }
        return false;
    }


     /**
      * Perform operations that a card contains
      * @param drawnCard input card
      */
     public void calculateDrawnCard(Card drawnCard) {
    	
    	// ADVANCE TO NEAREST ZOO
    	if(drawnCard.getDesc().contains("Advance to nearest Zoo")) {
    		int currentPos = currentPlayer.getPosition();
    		if(currentPos > 35 && currentPos <= 40) {
    			movePlayerTo(5);
    		} else {
    			int zooDiff1 = 5 - currentPos;
    			int zooDiff2 = 15 - currentPos;
    			int zooDiff3 = 35 - currentPos;
    			// Finding which is the smallest value (that is > 0. 
    			// It has to be > 0 otherwise it would be going backwards
    			ArrayList<Integer> zooDiffSort = new ArrayList<Integer>();
    			zooDiffSort.add(zooDiff1); zooDiffSort.add(zooDiff2); zooDiffSort.add(zooDiff3);
    			Collections.sort(zooDiffSort);
    			for(int i = 0; i < zooDiffSort.size(); i++) {
    				if (zooDiffSort.get(i) > 0) {
    					movePlayerTo(zooDiffSort.get(i));
    					break;
    				}
    			}
    		}
    	}
    

    	if(drawnCard.getMoveTo() > -1) {
            movePlayerTo(drawnCard.getMoveTo());
        }
        if(drawnCard.getManpower() > 0) {
            currentPlayer.addManpower(drawnCard.getManpower());
        } 
        else if(drawnCard.getManpower() < 0) {
            currentPlayer.removeManpower(drawnCard.getManpower());
    	}
        if (drawnCard.getMoveTo() == 11) {
        	currentPlayer.setOutOfJail(false);
        }
    }

    //!TEST FUNCTIONS
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public String getDebugDetails() {
        String res = String.format("Current Player: %s\nCurrent Square: %s\nPlayer Count: %d\nAreas: ", currentPlayer.getName(), currentSquare.getName(), playerCount);
        for (int i = 0; i < areas.size(); i++) {
            res += "\n\t" + areas.get(i).getDetails();
        }
        return res;
    }

    public Card drawSelectedCard(int id) {
        Card newChanceCard = calculateCardDesc((getChanceCards().get(id)));

        if(newChanceCard.getMoveTo() > -1) {
            movePlayerTo(newChanceCard.getMoveTo());
        }
        if(newChanceCard.getManpower() > 0) {
            currentPlayer.addManpower(newChanceCard.getManpower());
        }
        else if(newChanceCard.getManpower() < 0) {
            currentPlayer.removeManpower(newChanceCard.getManpower());
        }
        return newChanceCard;
    }
    
   
    
   

}
