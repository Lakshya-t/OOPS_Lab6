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

    // ---- Getters and setters ----

    /**
     * Returns the name of this district.
     *
     * @return the district name
     */
    public String getDistrictName() {
        return districtName;
    }

    /**
     * Updates the name of this district.
     *
     * @param districtName the new district name
     */
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    /**
     * Returns the defense integrity percentage of the outer gates.
     *
     * @return the wall integrity
     */
    public Integer getWallIntegrity() {
        return wallIntegrity;
    }

    /**
     * Updates the defense integrity percentage of the outer gates.
     *
     * @param wallIntegrity the new wall integrity
     */
    public void setWallIntegrity(Integer wallIntegrity) {
        this.wallIntegrity = wallIntegrity;
    }

    /**
     * Returns the 2D grid of confirmed titan kills (rows are sectors, columns are squads).
     *
     * @return the titan kill matrix
     */
    public Integer[][] getTitanKillMatrix() {
        return titanKillMatrix;
    }

    /**
     * Updates the 2D grid of confirmed titan kills (rows are sectors, columns are squads).
     *
     * @param titanKillMatrix the new titan kill matrix
     */
    public void setTitanKillMatrix(Integer[][] titanKillMatrix) {
        this.titanKillMatrix = titanKillMatrix;
    }

    /**
     * Returns the Titan Shifter spotted within this district.
     *
     * @return the spotted TitanShifter, or null if none has been spotted
     */
    public TitanShifter getSpottedTitan() {
        return spottedTitan;
    }

    /**
     * Updates the Titan Shifter spotted within this district.
     *
     * @param spottedTitan the newly spotted TitanShifter
     */
    public void setSpottedTitan(TitanShifter spottedTitan) {
        this.spottedTitan = spottedTitan;
    }

    /**
     * Calculates total confirmed titan kills across all sectors and squads.
     * Traverses the 2D titanKillMatrix using nested loops.
     * Note that some cells may be null where no squad was deployed; safely skip them.
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
     * Verifies whether two outpost sighting reports identify the same titan.
     * Parses reportId1 and reportId2 to Integer.
     * Compares the two parsed values.
     *
     * Hint: In Java, comparing objects using == compares memory references,
     * like comparing whether two array variables point to the same array object.
     * For example, comparing string or number representations like "100" and "100"
     * might behave differently depending on how object comparison is done.
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
     * Decodes binarySignal using base 2 and hexSignal using base 16.
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
