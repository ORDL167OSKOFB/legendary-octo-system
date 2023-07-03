import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Database {

    private static Random random = new Random();

    private Connection conn;
    private ArrayList<Card> communityCards;
    private ArrayList<Card> chanceCards;
    private ArrayList<Square> squares;

    public Database() {
        communityCards = new ArrayList<Card>();
        chanceCards = new ArrayList<Card>();
        squares = new ArrayList<Square>();
        
        createConnection();
        generateSquares();
        generateCards();
        
        
    }

    /**
     * Open a connection to the SQLite database
     */
    public void createConnection() {
        conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:database/survivology.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection established");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Generate the Square objects from the database
     */
    public void generateSquares() {
        String sql = "SELECT * FROM Square";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                String name = rs.getString("Name");
                int cost = rs.getInt("Cost");
                AreaType area = AreaType.fromString(rs.getString("Area").trim());
                int campCost = rs.getInt("campCost");
                int sanctCost = rs.getInt("sanctCost");
                int rentZero = rs.getInt("rentZero");
                int rentOne = rs.getInt("rentOne");
                int rentTwo = rs.getInt("rentTwo");
                int rentThree = rs.getInt("rentThree");
                int rentFour = rs.getInt("rentFour");
                int rentSanct = rs.getInt("rentSanct");
                boolean isInactive = rs.getBoolean("isInactive");
                boolean isChanceCard = rs.getBoolean("isChanceCard");
                boolean isCommunityCard = rs.getBoolean("isCommunityCard");

                Square newSquare = new Square(name, cost, area);
                newSquare.setValues(campCost, sanctCost, rentZero, rentOne, rentTwo, rentThree, rentFour, rentSanct, isInactive, isChanceCard, isCommunityCard);
                
                squares.add(newSquare);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    /**
     * Generate the Card objects from the database
     */
    public void generateCards() {
        String sql = "SELECT * FROM Card";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                String cardText = rs.getString("Description");
                int moveTo = rs.getInt("moveTo");
                int loc = rs.getInt("Location");
                int manpower = rs.getInt("Manpower");
                boolean isChance = rs.getBoolean("isChance");

                if(communityCards.size() == 7) {
                    cardText += " " + random.nextInt(((8-1) + 1) + 1) + ".";
                }
                Card newCard = new Card(cardText, moveTo, loc, manpower, isChance);

                if(isChance) {
                    chanceCards.add(newCard);
                    continue;
                }
                else if(!isChance) {
                    communityCards.add(newCard);
                    continue;
                }
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    /**
     * Getter for the community cards
     * @return ArrayList containing all community cards
     */
    public ArrayList<Card> getCommunityCards() {
        return communityCards;
    }

    /**
     * Getter for the chance cards
     * @return ArrayList containing all chance cards
     */
    public ArrayList<Card> getChanceCards() {
        return chanceCards;
    }

    /**
     * Getter for the list of Square objects
     * @return ArrayList of Square objects
     */
    public ArrayList<Square> getSquares() {
        return squares;
    }

    /**
     * Close the database connection
     * @return true if closed successfully, else false
     */
    public boolean closeDatabase() {
        try {
            conn.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
