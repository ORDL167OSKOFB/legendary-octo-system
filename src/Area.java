import java.util.ArrayList;

public class Area {
    private String name;
    private ArrayList<Square> squares;
    private boolean allSquaresBought = false;
    private boolean canBuild = false;
    private int squareCount;
    private static int totalSquareCount = 0;
    private AreaType areaType;

    public Area(String name, AreaType areaType) {
        this.name = name;
        this.squares = new ArrayList<Square>();
        this.areaType = areaType;
    }

    /**
     * Add a square to the area
     * @param newSquare square to be added
     * @return true if square added successfully, else false
     */
    public boolean addSquare(Square newSquare) {
    	
    	if (squares.contains(newSquare))
    	{
    		System.out.println("duplicate square.");
    		return false;
    	}
    	
        squares.add(newSquare);
        totalSquareCount++;
        squareCount++;
        return true;
    }

    /**
     * Getter for total (static) square count
     * @return current count of squares
     */
    public static int getTotalSquareCount() {
        return totalSquareCount;
    }

    /**
     * Getter for square count within this Area object
     * @return current count of squares this Area object contains
     */
    public int getSquareCount() {
        return squareCount;
    }

    /**
     * Checks whether a player can build on a square
     * @return
     */
    public boolean canBuild() {
        Player owner = null;
        
        //remove null clause because no square can be removed from area.
        for (int i = 0; i < squares.size(); i++) {
            if(squares.get(i) != null && squares.get(i).getOwner() != null ) {
                owner = squares.get(i).getOwner();
                continue;
            }
            if(squares.get(i).getOwner() != owner) {
                return false;
            }
        }
        //TODO Change canBuild if square is traded
        canBuild = true;
        return true;
    }

    /**
     * Gets the details about a square
     * @return details
     */
    public String getDetails() {
        String res = String.format("Area Name: %s, allSquaresBought: %s, canBuild: %s, squareCount: %d", name, allSquaresBought, canBuild, squareCount);
        return res;
    }

    /**
     * Getter for the squares this Area object contains
     * @return ArrayList of Square objects
     */
    public ArrayList<Square> getSquares() {
        return squares;
    }

    /**
     * Search for a square by ID
     * @param id the ID of the square to search for
     * @return the Square object if found
     */
    public Square getSquareById(int id) {
        for (int i = 0; i < squares.size(); i++) {
            if(squares.get(i).getSquareId() == id) {
                return squares.get(i);
            }
        }
        return null;
    }

    /**
     * Getter for the AreaType of this Area
     * @return AreaType
     */
    public AreaType getAreaType() {
        return this.areaType;
    }

    /**
     * Getter for Area name
     * @return area name
     */
    public String getName() {
        return this.name;
    }
}
