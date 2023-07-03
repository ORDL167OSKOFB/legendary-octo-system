import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SurvivologyApp {

    private static Board board = new Board();

    public static void main(String[] args) {
        mainMenu();
    }

    
    /**
     * Create the main menu object, get the user choice
     */
    private static void mainMenu() {

        String menuOptions[] = { "Start Game", "Add a Player", "Remove a Player", "Quit" };
        Menu menu = new Menu("Welcome to Survivology", menuOptions);
        
        int choice = -1;
        do {
            choice = menu.getChoice();
            if (choice != menuOptions.length) {
                processChoice(choice);
            }
            else if(choice == menuOptions.length) {
                System.exit(0);
            }
        } while (choice != menuOptions.length);

        System.out.println();
    }

    /**
     * Process the choice input in menu object
     * @param choice user choice input to menu object in mainMenu()
     */
    private static void processChoice(int choice) {
        switch (choice) {
            case 1:
                startGame();
                break;
            case 2:
                addPlayer();
                break;
            case 3:
                removePlayer();
                break;
            case 4:
                System.exit(0);
                break;
            case 5:
                debugMenu();
                break;
            default:
                System.out.println("Invalid choice.");
                mainMenu();
                break;
        }
    }

    /**
     * Add a player to the game using the board object's addPlayer() method
     */
    private static void addPlayer() {
        System.out.println("\n\tAdding a Player");
        System.out.println("\t+++++++++++++++++\n");
        
        String username;
        do {
            if(board.getCurrentPlayers().size() > 0) {
                System.out.print("Current players: ");
                System.out.println(board.getCurrentPlayers().toString().substring(1, board.getCurrentPlayers().toString().length()-1));
            }
            System.out.print("> Who would you like to add? Type 0 to finish: ");
            username = GetInput.getString();
            if(username.equals("0")) {
                break;
            }
            System.out.println();
            if(board.addPlayer(username)) {
                continue;
            }
        } while (!username.equals("0"));
    }

    /**
     * Remove player from board
     */
    private static void removePlayer() {
        System.out.println("\t\nRemoving a Player");
        System.out.println("\t+++++++++++++++++\n");

        // If trying to remove player with only 2 players left, end game ------------- I THINK THIS HAPPENS ----------
        //TODO Reallocate owned items when a player is removed
        Player toRemove = selectPlayer();
        if(toRemove != null) {
            board.removePlayer(toRemove);
            System.out.printf("\nPlayer %s has been removed from the game.\n", toRemove.getName());
        }
    }

    private static Player selectPlayer() {
        for (int i = 0; i < board.getCurrentPlayers().size(); i++) {
            System.out.printf("%d: %s\n", i+1, board.getCurrentPlayers().get(i));
        }
        System.out.print("\n\n> Enter player number: ");

        int choice = GetInput.getIntInput();
        if(choice > board.getCurrentPlayers().size() || choice < 1) {
            System.out.println("Invalid input");
            selectPlayer();
        }
        return board.getCurrentPlayers().get(choice-1);
    }

    /**
     * Starts the game by calling board.startGame() which sets the
     * current player and position
     */
    private static void startGame() {

        if(board.getCurrentPlayers().size() < 2) {
            System.out.println("! Please add at least two players before starting the game !");
            return;
        }

        System.out.println("\n\tSurvivology");
        System.out.println("\t+++++++++++");

        board.startGame();
        // calling playerOptions() which processes player's choices
        gameplay();
    }

    private static void displayPlayerDetails() {
        Player currentPlayer = board.getCurrentPlayer();
        System.out.printf("\n\tIt's %s's turn!\n", currentPlayer.getName());
        System.out.printf("%s currently has %d manpower at their disposal.\n\n", currentPlayer.getName(), currentPlayer.getManpower());
    }

    /**
     * Main gameplay method
     */
    private static void gameplay() {
        Player currentPlayer = board.getCurrentPlayer();

        board.setCurrentSquare(board.getSquareById(currentPlayer.getPosition()));
        
        int prevPos = currentPlayer.getPosition();
        if(board.getCurrentPlayer().isOutOfJail()) {
            displayPlayerDetails();
        }
     
        else if(!board.getCurrentPlayer().isOutOfJail()) {
            jailOptions();
            finalOptions();
        }

        int diceIndex = 1;
        
        int[] diceRoll = board.rollDice();
        board.movePlayer(diceRoll[0] + diceRoll[1]);
        
        while(diceRoll[0] == diceRoll[1] && diceIndex < 3) {
            System.out.printf("%s rolled a %d and a %d, totalling to %d. You rolled a double. Roll again!\n", board.getCurrentPlayer().getName(), diceRoll[0], diceRoll[1], (diceRoll[0] + diceRoll[1]));
            diceRoll = board.rollDice();
            board.setCurrentSquare(board.getSquareById(currentPlayer.getPosition()));
            diceIndex++;
        }
        if(diceIndex > 2) {
        	System.out.printf("%s rolled a %d and a %d, totalling to %d. Since you rolled 3 doubles you are being sent to the Arctic Circle!\n", board.getCurrentPlayer().getName(), diceRoll[0], diceRoll[1], (diceRoll[0] + diceRoll[1]));
        	putInJail();
            finalOptions();
        }
    	diceIndex = 1;

        // This function does not take into account chance cards sending you in an opposite direction. Therefore,
        // if the position of the player is less then a previous position, it means they've circumnavigated the globe.
        
        if (currentPlayer.getPosition() < prevPos)
        {
        	 System.out.println("You have circumnavigated the globe!");
             System.out.println("Collect 20 manpower.");
             currentPlayer.addManpower(20);
        }
       
        System.out.printf("%s rolled a %d and a %d, totalling to %d, and landed at", currentPlayer.getName(), diceRoll[0], diceRoll[1], (diceRoll[0] + diceRoll[1]));
        
        performSquareOperations();

        finalOptions();
    }

    /**
     * Giving player options when they are in jail
     */
    private static void jailOptions() {
        Player currentPlayer = board.getCurrentPlayer();
        System.out.println("You're currently stuck at Antarctica. Due to the south pole being tilted away from the sun "
        + "for half the year, no transportation is available.\n");
        System.out.printf("%s is currently stuck in Antarctica for %d more turns!\n\n", currentPlayer.getName(), currentPlayer.getJailTimeLeft());
        if(!currentPlayer.isOutOfJail()) {
            if(currentPlayer.getGetOutOfJailCard() > 0) {
                System.out.println("You have a GET OUT OF JAIL card! Would you like to use it now? Enter Y/N");
                boolean jailChoice = GetInput.getYesNo();
                if(jailChoice) {
                    currentPlayer.setOutOfJail(true);
                    System.out.println("You are now out of jail!");
                } else {
                    System.out.println("You are still in jail.");
                }
            }
            currentPlayer.decrementJailTimeLeft();
            if(currentPlayer.getJailTimeLeft() < 1) {
            	currentPlayer.setOutOfJail(true);
            }
        }
        else {
            currentPlayer.setOutOfJail(false);
        }
    }

    /**
     * Performs operations based on the square a player lands on
     */
    private static void performSquareOperations() {
        Player currentPlayer = board.getCurrentPlayer();
        switch (board.getCurrentSquare().getArea()) {
            case CHANCE:
                //NOTE: Using string formatting here even though it's probably better to hard code the string e.g. "A Chance Square!"
                // This is because if, for example we add more tax squares, they don't all have to be called climate emergency
                System.out.printf(" a %s! A Chance Card will be drawn...\n", board.getCurrentSquare().getName());
                Card newCard = board.drawChanceCard();
                System.out.println(newCard.getDesc());

                //TODO is this correct?
                if(!currentPlayer.isOutOfJail()) {
                    jailOptions();
                }
                board.calculateDrawnCard(newCard);
                break;
            case COMMUNITY:
                System.out.printf(" a %s! A Community Card will be drawn...\n", board.getCurrentSquare().getName());
                newCard = board.drawCommunityCard();
                System.out.println(newCard.getDesc());
                //TODO Community Card Stuff
                board.calculateDrawnCard(newCard);
                break;
            case JAIL:
                //TODO Better way to check if square landed on is transportation breakdown?
                if(board.getCurrentSquare().getName().toUpperCase().equals("TRANSPORTATION BREAKDOWN")) {
                    System.out.printf(" %s!\n", board.getCurrentSquare().getName());
                    System.out.printf("%s is to go to the Antarctic Circle immediately! If you pass go, do not collect 20 manpower.\n\n", currentPlayer.getName());
                }
                else {
                    System.out.printf(" the %s!\n\n", board.getCurrentSquare().getName());
                }
                putInJail();
                jailOptions();
                finalOptions();

                break;
            case TAX:
                System.out.printf(" a %s!\n\n", board.getCurrentSquare().getName());

                //TODO Somehow get the actual name of the region the tax is in?
                System.out.printf("Due to the current climate in %s, you are required to send %d manpower to the government" + 
                " to assist their endeavours in protecting endangered species!\n\n", board.getCurrentSquare().getArea().toString(), board.getCurrentSquare().getCost());

                currentPlayer.removeManpower(board.getCurrentSquare().getCost());
                System.out.printf("%s manpower has been sent to the government.\n\n", board.getCurrentSquare().getCost());
                break;

            case START:
                System.out.printf(" the %s!\n", board.getCurrentSquare().getName());
                break;
            case UTILITY:
            	System.out.printf(" a %s!\n", board.getCurrentSquare().getName());
                getPlayerOptions();
            	break;
            case ZOO:
            	System.out.printf(" the %s!\n", board.getCurrentSquare().getName());
                getPlayerOptions();
            	break;
            default:
                System.out.printf(" the home of the %s!\n\n", board.getCurrentSquare().getName());
                if(board.getCurrentSquare().isOwned() && board.getCurrentSquare().getOwner() != currentPlayer) {
                    System.out.printf("This square is already under the protection of %s." +
                    " You must send them %d manpower to help in their fight for endangered species!\n\n", board.getCurrentSquare().getOwner(), board.getCurrentSquare().getRent());
                    board.getCurrentSquare().getOwner().addManpower(board.getCurrentSquare().getRent());
                    currentPlayer.removeManpower(board.getCurrentSquare().getRent());
                }
                else {
                    getPlayerOptions();
                }
                break;
        }
    }

    /**
     * Set player to jail
     */
    private static void putInJail() {
        board.getCurrentPlayer().setOutOfJail(false);
    }

    /**
     * Give the player options when they have performed other operations e.g. protecting a square
     */
    private static void finalOptions() {
        displayPlayerDetails();
        System.out.println("1. Trade with another player (Animals, Protectors)");
        System.out.println("2. End Turn");
        System.out.println("3. Remove Player");

        System.out.print("\n> What would you like to do? Choose the corresponding number: ");
        int input = GetInput.getIntInput();
        System.out.println();
        switch(input) {
            case 1:
                trade();
                finalOptions();
                break;
            case 2:
                endTurn();
                break;
            case 3:
                removePlayer();
                finalOptions();
                break;
        }
    }

    /**
     * Present player with options when they first roll the dice
     */
    private static void getPlayerOptions() {
        Square currentSquare = board.getCurrentSquare();
        	int input = 0;
            int displayChoice = 1;

            if (!currentSquare.isOwned())
            {
                switch(currentSquare.getArea()) {
                    case ZOO:
                    case UTILITY:
                        System.out.printf("%d. Protect the %s for %d manpower\n", displayChoice, currentSquare.getName(), currentSquare.getCost());
                        displayChoice++;
                        break;
                    default:
                        System.out.printf("%d. Protect the home of the %s for %d manpower\n", displayChoice, currentSquare.getName(), currentSquare.getCost());
                        displayChoice++;
                        break;
                }
                
            }
            if(currentSquare.isOwned() && currentSquare.getOwner().getPlayerId() == board.getCurrentPlayer().getPlayerId()) {
                System.out.print("You protect this square! ");
                if(currentSquare.getCampCount() > 0) {
                    System.out.printf("You have built %d camps");
                    if(currentSquare.getSanctCount() > 0) {
                        System.out.print(" and a sanctuary.");
                    }
                    else {
                        System.out.println(".");
                    }
                }
            }

            System.out.printf("%d. Trade with another player (Animals, Protectors)\n", displayChoice++);
            System.out.printf("%d. Buy Camps or Sanctuaries.\n", displayChoice++);
            System.out.printf("%d. End Turn\n", displayChoice++);
            System.out.printf("%d. Remove a Player\n", displayChoice++);
            

            System.out.printf("%d. End Game\n", displayChoice++);
            System.out.print("\n> What would you like to do? Choose the corresponding number: ");
            input = GetInput.getIntInput();
            System.out.println();
        
            if (displayChoice == 7)
            {
                switch(input)
                {
                    case 1:
                    protectSquare();
                    break;
                    case 2:
                    trade();
                    break;
                    case 3:
                    campSanctuaryChoice();
                    break;
                    case 4:
                    endTurn();
                    break;
                    case 5:
                    removePlayer();
                    break;
                    case 6:
                    endGame();
                    break;
               }
            }
            else if (displayChoice == 6)
            {
                switch(input)
                {
                    case 1:
                    trade();
                    break;
                    case 2:
                    campSanctuaryChoice();
                    break;
                    case 3:
                    endTurn();
                    break;
                    case 4:
                    removePlayer();
                    break;
                    case 5:
                    endGame();
                    break;
                }
            }
      
        }
    
        /**
         * Used when a player wishes to buy a camp/sanctuary
         */
        private static void campSanctuaryChoice() {
            System.out.println("Do you want to purchase a camp or sanctuary?");
            System.out.println("1. Buy camp(s)\n2. Buy a Sanctuary\n3. Cancel");
            int choice = -1;
            choice = GetInput.getIntInput();
            switch(choice) {
                case 1:
                    buyCamp();
                    break;
                case 2:
                    buySanct();
                    break;
                case 3:
                    System.out.println("You have chosen to cancel.");
                    getPlayerOptions();
                    break;
            }
        }

    /**
     * Method called when a player's turn ends
     * Calls board.nextTurn() which increments current turn count 
     * and sets the current player to the next player
     */
    private static void endTurn() {
        
        for (int i = 0; i < board.getCurrentPlayers().size(); i++) {
            if(board.getCurrentPlayers().get(i).getManpower() <= 0) {
                board.removePlayer(board.getCurrentPlayers().get(i));
                System.out.printf("%s has been removed due to having no manpower!\n\n", board.getCurrentPlayers().get(i));
            }
        }
        if(board.getCurrentPlayers().size() < 2) {
            endGame();
        }
        board.endTurn();
        
        int pos = board.getCurrentPlayer().getPosition();
        board.setCurrentSquare(board.getSquareById(pos));
        
        
        gameplay();
    }

    /**
     * Method called when the game ends
     * Displays player ranking and details about their protection
     */
    private static void endGame() {
        board.endGame();
        long completionTime = (board.getEndTime() - board.getStartTime()) / 1000000000;
        System.out.printf("This game lasted %s seconds\n\n", completionTime);
        ArrayList<Player> rankedPlayers = board.getRankedPlayers();
        HashMap<String, Integer> scores = new HashMap<String, Integer>();

        System.out.println("Player Ranking");
        System.out.println("++++++++++++++++++++++\n\n");
        String s = "%d. Player %s ended the game having gained %d manpower in total, and having lost %d manpower, finishing the game with %d manpower.\n";

        for (int i = 0; i < rankedPlayers.size(); i++) {
            Player chosenPlayer = rankedPlayers.get(i);
            scores.put(chosenPlayer.getName(), chosenPlayer.getManpower());
            System.out.print(String.format(s, i+1, chosenPlayer.getName(), chosenPlayer.getTotalManpowerGained(), chosenPlayer.getTotalManpowerLost(), chosenPlayer.getManpower()));
            ArrayList<Square> ownedSquares = chosenPlayer.getOwnedSquares();
            if(ownedSquares.size() == 0) {
                System.out.printf("%s did not protect any animals.\n\n", chosenPlayer.getName());
                continue;
            }
            System.out.printf("%s protected: ", chosenPlayer.getName());
            for (int j = 0; j < ownedSquares.size(); j++) {
                if(ownedSquares.size() == 1) {
                    System.out.println(ownedSquares.get(j).getName());
                    break;
                }
                if(j != ownedSquares.size()-1) {
                    System.out.printf("%s, ", ownedSquares.get(j).getName());
                }
                else {
                    System.out.printf("%s", ownedSquares.get(j));
                }
                
            }
            System.out.println();
            
            
            try {
            	chosenPlayer.getFinalCredits().StoreCSV();
				chosenPlayer.getFinalCredits().creditRetrieve();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        String winner = getMaxEntryInMapBasedOnValue(scores);
        System.out.printf("\n-----\nCongratulations %s! You won the game with a total manpower of %d\n-----\n", winner, scores.get(winner));
        
        System.out.print("\n> Enter any input to continue... ");
        String input = GetInput.getString();

        for (int i = 0; i < board.getAllPlayers().size(); i++) {
            board.getAllPlayers().get(i).reset();
        }
        mainMenu();
    }

    /**
     * Method for managing trading properties
     * @param initiator player who initiated trade
     * @param trader player who the initiator wishes to trade with
     * @return ArrayList of properties to trade
     */
    private static ArrayList<Square> tradeChooseProperties(Player initiator, Player trader) {
        ArrayList<Square> chosenProperties = new ArrayList<Square>();
        System.out.printf("%s, Which squares would you like to request from %s?\n\n", initiator.getName(), trader.getName());
        int choice = -1;
        do {
            if(chosenProperties.size() > 0) {
                System.out.print("Currently chosen squares: ");
                System.out.println(chosenProperties.toString().substring(1, chosenProperties.toString().length()-1));
                System.out.println();
            } 
            for (int i = 0; i < trader.getOwnedSquares().size(); i++) {
                System.out.printf("%d. %s\n", i+1, trader.getOwnedSquares().get(i).getName());
            }
            System.out.printf("\n> Which squares do you want to protect from %s? Type 0 to cancel: ", trader.getName());
            choice = GetInput.getIntInput();
            if(choice > trader.getOwnedSquares().size()) {
                System.out.println("Invalid Choice; please enter a valid number.");
                continue;
            }
            if(choice == 0) {
                return chosenProperties;
            }
            chosenProperties.add(trader.getOwnedSquares().get(choice-1));
            
        } while (choice != 0);
        return chosenProperties;
    }

    /**
     * Manage the amount of manpower that will be sent through a trade
     * @param initiator player initiating the trade
     * @param trader player the initiator wishes to trade with
     * @return amount of manpower that will be transferred
     */
    private static int tradeSelectManpower(Player initiator, Player trader) {
        int manpowerToReceive = -1;

        do {
            System.out.printf("> Enter the manpower you'd like to receive from %s: ", initiator.getName());
            manpowerToReceive = GetInput.getIntInput();
            if(manpowerToReceive < 0) {
                System.out.println("Please enter a valid amount.");
                continue;
            }
            else if(manpowerToReceive >= initiator.getManpower()) {
                System.out.printf("%s does not have this much manpower.\n", initiator.getName());
                System.out.print("> Would you like to renegotiate? Y to renegotiate, N to cancel the trade: ");
                boolean renegotiate = GetInput.getYesNo();
                if(renegotiate) {
                    continue;
                }
                else {
                    System.out.printf("%s has cancelled the trade.\n", trader.getName());
                    //TODO Check this works properly
                    return -1;
                }
            }
        } while (manpowerToReceive >= initiator.getManpower());
        return manpowerToReceive;
    }

    /**
     * Method to manage the trading process
     */
    private static void trade() {	
    	
        Player currentPlayer = board.getCurrentPlayer();
    	
    	System.out.println("> Who would you like to trade with?");
    	Player trader = selectPlayer();

    	ArrayList<Square> chosenInitiatorProperties = tradeChooseProperties(currentPlayer, trader);
        ArrayList<Square> chosenTraderProperties = new ArrayList<Square>();
        
        System.out.printf("\n%s, %s would like to protect: %s", trader.getName(), currentPlayer.getName(), 
            chosenInitiatorProperties.toString().substring(1, chosenInitiatorProperties.toString().length()-1));

        System.out.println();
        int manpowerToSend = tradeSelectManpower(currentPlayer, trader);
        int manpowerToReceive = -1;
        
        System.out.printf("> %s, would you like to request properties from %s? Y/N: ", trader.getName(), currentPlayer.getName());
        boolean takeOver = GetInput.getYesNo();
        System.out.println();
        if(takeOver) {
            if(currentPlayer.getOwnedSquares().size() <= 0) {
                System.out.printf("%s does not own any squares.\n", currentPlayer.getName());
            }
            else {
                chosenTraderProperties = tradeChooseProperties(trader, currentPlayer);
                manpowerToReceive = tradeSelectManpower(trader, currentPlayer);
            }
        }

        System.out.println("- Trading Overview -");
        System.out.printf("\n%s would like to protect: %s", currentPlayer.getName(), 
        chosenInitiatorProperties.toString().substring(1, chosenInitiatorProperties.toString().length()-1));
        System.out.println();

        System.out.printf("%s wants to receive %d manpower for this trade.\n\n", trader.getName(), manpowerToSend);

        if(chosenTraderProperties.size() > 0) {
            System.out.printf("\n%s would like to protect: %s", trader.getName(),
            chosenTraderProperties.toString().substring(1, chosenTraderProperties.toString().length()-1));

            System.out.printf("%s wants to receive %d manpower for this trade.", currentPlayer.getName(), manpowerToReceive);
        }

        System.out.printf("> %s, do you accept the terms of this trade? Y/N: ", trader.getName());
        boolean tradeAcceptInitiator = GetInput.getYesNo();
        System.out.println();
        if(tradeAcceptInitiator) {
            System.out.printf("> %s, do you accept the terms of this trade? Y/N: ", currentPlayer.getName());
            boolean tradeAcceptTrader = GetInput.getYesNo();
            System.out.println();
            if(tradeAcceptTrader) {
                System.out.println("Trade confirmed!");
                board.trade(chosenInitiatorProperties, trader, currentPlayer, manpowerToSend);
                
             
                // create trade method
                if(chosenTraderProperties.size() > 0) {
                	
        
                    board.trade(chosenTraderProperties, currentPlayer, trader, manpowerToReceive);
                }
            }
        }
    }
	

    /**
     * Method to call when buying a camp
     * Makes use of Square's buyCamp() method
     */
    private static void buyCamp() {
    	if (board.getCurrentPlayer().getOwnedSquares().size() == 0) {
    		System.out.println("You have no properties to build camps on.");
    		getPlayerOptions();
    		return;
    	}
    	System.out.println("Which property do you wish to build camps on? (0 to cancel)");
    	int choice = -1;
    	boolean valid = true;
    	do {
    		for (int i = 0; i < board.getCurrentPlayer().getOwnedSquares().size(); i++) {
    			System.out.printf("%d. %s\n", i+1, board.getCurrentPlayer().getOwnedSquares().get(i).getName());
    		}
    		choice = GetInput.getIntInput();
    		if (choice > board.getCurrentPlayer().getOwnedSquares().size() || choice < 0) {
    			System.out.println("Invalid choice. Please select a valid number");
    			valid = false;
    			continue;
    		}
    	}
    	while (!valid);
    	if (choice == 0) {
    		System.out.println("You have not chosen a property. Building camps has been cancelled.");
    		return;
    	}
    	Square chosenSquare = board.getCurrentPlayer().getOwnedSquares().get(choice-1);
        System.out.printf("How many camps would you like to buy? (max 4):");
        int amount = GetInput.getIntInput();
        System.out.println();
        System.out.printf("It will take %d manpower to build %d camps. Do you want to continue? (Y/N)\n", chosenSquare.getCampCost() * amount, amount);
        boolean confirmation = GetInput.getYesNo();
        if (!confirmation) {
        	System.out.println("Purchase cancelled.");
        	getPlayerOptions();
        	return;
        }
        if(chosenSquare.buyCamp(amount)) {
            System.out.printf("You built %d camp(s) to aid in the protection of the %s!\n", amount, chosenSquare.getName());
            System.out.printf("This square has a total of %d camps.\n", chosenSquare.getCampCount());
        }
        else if(chosenSquare.getCampCost() > board.getCurrentPlayer().getManpower()) {
            System.out.println("You don't have enough funds to construct a camp.");
        }
        else if(chosenSquare.getCampCount() == 4) {
            System.out.println("You already have the maximum amount of camps built on this square. Maybe try building a sanctuary?");
        }
    }


    /**
     * Method to call when buying a sanctuary
     * Makes use of Square's buySanct() method.
     */
    private static void buySanct() {
    	if (board.getCurrentPlayer().getOwnedSquares().size() == 0) {
    		System.out.println("You have no properties to build a sanctuary on.");
    		getPlayerOptions();
    		return;
    	}
    	System.out.println("Which property do you wish to build a sanctuary on? (0 to cancel)");
    	int choice = -1;
    	boolean valid = true;
    	do {
    		for (int i = 0; i < board.getCurrentPlayer().getOwnedSquares().size(); i++) {
    			System.out.printf("%d. %s\n", i+1, board.getCurrentPlayer().getOwnedSquares().get(i).getName());
    		}
    		choice = GetInput.getIntInput();
    		if (choice > board.getCurrentPlayer().getOwnedSquares().size() || choice < 0) {
    			System.out.println("Invalid choice. Please select a valid number");
    			valid = false;
    			continue;
    		}
    	}
    	while (!valid);
    	if (choice == 0) {
    		System.out.println("You have not chosen a property. Building a sanctuary has been cancelled.");
    		return;
    	}
    	Square chosenSquare = board.getCurrentPlayer().getOwnedSquares().get(choice-1);
    	if (chosenSquare.getCampCount() < 4) {
    		System.out.println("You do not have enough camps to build a sanctuary! Purchase cancelled.");
    		return;
    	}
    	System.out.printf("It will take %d manpower to build a sanctuary here. Do you want to continue? (Y/N)\n", chosenSquare.getSanctCost());
        boolean confirmation = GetInput.getYesNo();
        if (!confirmation) {
        	System.out.println("Purchase cancelled.");
        	getPlayerOptions();
        	return;
        }
        if(chosenSquare.buySanctuary()) {
            System.out.printf("You built a sanctuary to aid in the protection of the %s!\n", board.getCurrentSquare().getName());
        }
        else if(!chosenSquare.buySanctuary()) {
            System.out.println("You don't have enough manpower to construct a sanctuary.");
        }
    }

    /**
     * Method to buy the current square
     */
    private static void protectSquare() {
        if(board.purchaseCurrentSquare()) {
            switch(board.getCurrentSquare().getArea()) {
                case ZOO:
                case UTILITY:
                    System.out.printf("\nYou used %d to protect the %s.\n", board.getCurrentSquare().getCost(), board.getCurrentSquare().getName());
                    System.out.printf("The %s is now under the protection of %s!\n", board.getCurrentSquare().getName(), board.getCurrentPlayer().getName());  
                    break;
                default:
                    System.out.printf("\nYou used %d manpower to protect the home of the %s.\n", board.getCurrentSquare().getCost(), board.getCurrentSquare().getName());
                    System.out.printf("The home of the %s is now under the protection of %s!\n", board.getCurrentSquare().getName(), board.getCurrentPlayer().getName());
                    break;
            }
        }
        else if(board.getCurrentSquare().getCost() > board.getCurrentPlayer().getManpower()){
            System.out.println("You don't have enough funds to purchase this square.");
        }
        else if(board.getCurrentSquare().isOwned()) {
            System.out.printf("This square is already under the protection of %s.\n", board.getCurrentSquare().getOwner().getName());
        }
        endTurn();
    }
    

    // Find the entry with highest value (winner)
    private static <K, V extends Comparable<V> > String getMaxEntryInMapBasedOnValue(Map<K, V> map)
    {
        // To store the result
        Map.Entry<K, V> entryWithMaxValue = null;
  
        // Iterate in the map to find the required entry
        for (Map.Entry<K, V> currentEntry : map.entrySet()) {
  
            if (
                // If this is the first entry, set result as this
                entryWithMaxValue == null
  
                // If this entry's value is more than the max value
                // Set this entry as the max
                || currentEntry.getValue()
                           .compareTo(entryWithMaxValue.getValue())
                       > 0) {
  
                entryWithMaxValue = currentEntry;
            }
        }
        
        return (String) entryWithMaxValue.getKey();
    }

    /**
     * !NOTE THAT ALL METHODS BELOW THIS ARE FOR DEBUGGING
     */

    private static void debugMenu() {
        String options[] = {"Display Board Debug Details (ADD PLAYERS AND START GAME FIRST)", "Add Test Player", "Start Game", "Set currentPlayer", "Pick a Chance Card", 
        "Pick a Community Card", "Display All Players", "Show Current Player Details", "Move to Square", "End Game", "Back"};
        Menu menu = new Menu("Debug Menu", options);
        int choice = -1;
        do {
            choice = menu.getChoice();
            if (choice != options.length) {
                processDebugChoice(choice);
            }
        } while (choice != options.length);
    }

    private static void processDebugChoice(int choice) {
    	
    	
        switch (choice) {
            case 1:
                System.out.println(board.getDebugDetails());
                System.out.println("");
                break;
            case 2:
                addPlayer();
                break;
            case 3:
                board.startGame();
                System.out.println("Game has been started.");
                break;
            case 4:
                Player testPlayer = selectPlayer();
                board.setCurrentPlayer(testPlayer);
                System.out.printf("currentPlayer set to: %s\n", board.getCurrentPlayer().getName());
                break;
            case 5:
                System.out.println("List of Chance Cards");
                System.out.println("-------------\n");
                for (int i = 0; i < board.getChanceCards().size(); i++) {
                    Card currentCard = board.getChanceCards().get(i);
                    System.out.printf("%d: %s\nrelPos: %d\npos: %d\nManpower: %d\n", i, currentCard.getDesc(), 0, 0, currentCard.getManpower());
                }
                System.out.println("> Which card would you like to draw: ");
                int input = GetInput.getIntInput();
                
                Card newChanceCard = board.drawSelectedCard(input);
                System.out.println(newChanceCard.getDesc());
                System.out.println(board.getCurrentPlayer().getPosition());

                break;
            case 6:
                System.out.println("List of Community Cards");
                System.out.println("-------------\n");
                for (int i = 0; i < board.getCommunityCards().size(); i++) {
                    Card currentCard = board.getCommunityCards().get(i);
                    System.out.printf("%d: %s\nrelPos: %d\npos: %d\nManpower: %d\n", i, currentCard.getDesc(), 0, 0, currentCard.getManpower());
                }
                System.out.println("> Which card would you like to draw: ");
                int input2 = GetInput.getIntInput();
                
                Card newCommunityCard = board.getCommunityCards().get(input2);
                System.out.println(newCommunityCard.getDesc());
                //int communityCardOps[] = newCommunityCard.operations();
                // board.movePlayer(communityCardOps[0]);
                // board.movePlayerTo(communityCardOps[1]);
                // board.getCurrentPlayer().addManpower(communityCardOps[2]);
                break;
            case 7:
                for (int i = 0; i < board.getCurrentPlayers().size(); i++) {
                    System.out.println(board.getCurrentPlayers().get(i).getDebugDetails());
                }
                break;
            case 8:
                System.out.println(board.getCurrentPlayer().getDebugDetails());
                break;
            case 9:
                System.out.print("> Enter square number: ");
                int squareNo = GetInput.getIntInput();
                board.movePlayerTo(squareNo);
                System.out.printf("\nPlayer has been moved to square %d, %s", board.getCurrentSquare().getSquareId(), board.getCurrentSquare().getName());
                break;
            case 10:
                endGame();
            case 11:
                break;
            default:
                break;
        }
    }

    
    
}
