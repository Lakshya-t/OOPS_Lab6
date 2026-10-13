/**
 * Represents a fortified district along Wall Rose (e.g. Trost, Krolva, Ehrmich).
 * Handles district kill matrices, signal decoding, and titan identification.
 * 
 * @author Garrison and Wall Defense Command
 */
public class WallDistrict {

    /** Name of the district (e.g. "Trost", "Krolva", "Ehrmich"). */
    private String districtName;

    /** Defense integrity percentage of the outer gates. */
    private Integer wallIntegrity;

    /**
     * 2D grid of confirmed titan kills across defense sectors.
     * Rows represent defense sectors along the district walls (Sector 0, Sector 1, etc.).
     * Columns represent assigned defense squads (Squad 0, Squad 1, etc.).
     *
     * Example:
     * matrix[0][1] = 5 means Sector 0 had 5 confirmed kills credited to Squad 1.
     * Note: If a squad was not deployed to that sector, the entry may be null.
     */
    private Integer[][] titanKillMatrix;

    /** Hostile or friendly Titan Shifter spotted within this district. */
    private TitanShifter spottedTitan;

    /**
     * Constructor 1 (Overloaded): Creates a district with default 100% integrity and empty matrix.
     *
     * @param name district name
     */
    public WallDistrict(String name) {
        this(name, "100", new Integer[0][0]);
    }

    /**
     * Constructor 2 (Overloaded): Initializes district name, parses integrityStr to Integer,
     * and sets the titanKillMatrix. spottedTitan is initialized to null.
     *
     * @param name         district name
     * @param integrityStr wall integrity as string
     * @param killMatrix   2D array of kill counts (may contain nulls)
     */
    public WallDistrict(String name, String integrityStr, Integer[][] killMatrix) {
        this.districtName = name;
        this.wallIntegrity = Integer.valueOf(integrityStr);
        this.titanKillMatrix = (killMatrix != null) ? killMatrix : new Integer[0][0];
        this.spottedTitan = null;
    }

    // Getters and Setters
    public String getDistrictName() { return districtName; }
    public void setDistrictName(String districtName) { this.districtName = districtName; }

    public Integer getWallIntegrity() { return wallIntegrity; }
    public void setWallIntegrity(Integer wallIntegrity) { this.wallIntegrity = wallIntegrity; }

    public Integer[][] getTitanKillMatrix() { return titanKillMatrix; }
    public void setTitanKillMatrix(Integer[][] titanKillMatrix) { this.titanKillMatrix = titanKillMatrix; }

    public TitanShifter getSpottedTitan() { return spottedTitan; }
    public void setSpottedTitan(TitanShifter spottedTitan) { this.spottedTitan = spottedTitan; }

    /**
     * Calculates total confirmed titan kills across all sectors and squads.
     *
     * @return total confirmed kills as Integer
     */
    public Integer calculateTotalConfirmedKills() {
        Integer total = 0;
        if (titanKillMatrix == null) return total;
        for (int i = 0; i < titanKillMatrix.length; i++) {
            if (titanKillMatrix[i] == null) continue;
            for (int j = 0; j < titanKillMatrix[i].length; j++) {
                if (titanKillMatrix[i][j] != null) {
                    total += titanKillMatrix[i][j];
                }
            }
        }
        return total;
    }

    /**
     * Verifies whether two outpost sighting reports identify the exact same titan.
     *
     * @param reportId1 first titan report ID string
     * @param reportId2 second titan report ID string
     * @return true if both IDs refer to the same titan, false otherwise
     */
    public boolean verifyTitanIdentification(String reportId1, String reportId2) {
        Integer id1 = Integer.valueOf(reportId1);
        Integer id2 = Integer.valueOf(reportId2);
        return id1.equals(id2);
    }

    /**
     * Decodes military radio signals transmitted along the wall.
     * Decodes binarySignal using base-2 radix and hexSignal using base-16 radix.
     *
     * @param binarySignal urgency encoded in binary
     * @param hexSignal    titan count encoded in hexadecimal
     * @return int array [urgencyLevel, titanCount]
     */
    public int[] decodeSignal(String binarySignal, String hexSignal) {
        int urgency = Integer.parseInt(binarySignal, 2);
        int count = Integer.parseInt(hexSignal, 16);
        return new int[]{urgency, count};
    }
}
