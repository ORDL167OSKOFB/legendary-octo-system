public enum AreaType {
    START(0),
    MADAGASCAR(1),
    TAX(2),
    ZOO(3),
    MICRONESIA(4),
    COMMUNITY(5),
    CHANCE(6),
    JAIL(7),
    BORNEO(8),
    UTILITY(9),
    ANDES(10),
    SKYISLANDS(11),
    MEDDITERRANEAN(12),
    ARCTIC(13),
    AMAZON(14),
    DEFAULT(15);

    private int value;

    private String[] areaString = {"Starting Area", "Madagascar", "Tax", "Zoo", "Micronesia", "Community Area", 
        "Chance Area", "Jail", "Borneo", "Utility", "Tropical Andes", "Sky Islands", "Medditerranean Basin", "Arctic", "Amazon", "Default"};

    private AreaType(int value) {
        this.value = value;
    }

    /**
     * Return an AreaType corresponding to the String value
     * @param str the string value
     * @return AreaType for the input String
     */
    public static AreaType fromString(String str) {
        switch (str) {
            case "start":
                return START;

            case "madagascar":
                return MADAGASCAR;
        
            case "micronesia":
                return MICRONESIA;
        
            case "jail":
                return JAIL;
        
            case "borneo":
                return BORNEO;
        
            case "tropicalAndes":
                return ANDES;
        
            case "skyIslands":
                return SKYISLANDS;
        
            case "medditerraneanBasin":
                return MEDDITERRANEAN;
        
            case "arctic":
                return ARCTIC;

            case "amazon":
                return AMAZON;
            
            case "zoos":
                return ZOO;

            case "tax":
                return TAX;
            
            case "chance":
                return CHANCE;
            
            case "community":
                return COMMUNITY;

            case "utility":
                return UTILITY;
        
            default:
                break;
        }
        return DEFAULT;
    }

    /**
     * Convert AreaType to String
     */
    public String toString() {
        return areaString[value];
    }
}
