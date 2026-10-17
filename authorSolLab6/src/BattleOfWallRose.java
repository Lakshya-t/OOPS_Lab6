/**
 * Master Tactical Command system coordinating the defense of Wall Rose.
 * Orchestrates interaction between {@link WallDistrict}, {@link ScoutSquad}, 
 * and {@link TitanShifter} (including its inner classes).
 * 
 * @author Commander Erwin Smith &amp; Survey Corps Command
 */
public class BattleOfWallRose {

    /** Maximum districts along Wall Rose. */
    private static final int MAX_DISTRICTS = 5;

    /** Maximum squads in the regiment. */
    private static final int MAX_SQUADS = 10;

    /** Maximum shifters tracked. */
    private static final int MAX_TITANS = 10;

    /** Array of registered wall districts. */
    private WallDistrict[] districts;

    /** Array of active Scout squads. */
    private ScoutSquad[] activeSquads;

    /** Array of encountered Titan Shifters. */
    private TitanShifter[] encounteredTitans;

    /** Current registered counts. */
    private int districtCount;
    private int squadCount;
    private int titanCount;

    /** Overall defense readiness and combat score achieved by humanity. */
    private Integer totalHumanityDefenseScore;

    /**
     * Initialise all three arrays to their max sizes; set all counters and
     * totalHumanityDefenseScore to 0.
     */
    public BattleOfWallRose() {
        this.districts = new WallDistrict[MAX_DISTRICTS];
        this.activeSquads = new ScoutSquad[MAX_SQUADS];
        this.encounteredTitans = new TitanShifter[MAX_TITANS];
        this.districtCount = 0;
        this.squadCount = 0;
        this.titanCount = 0;
        this.totalHumanityDefenseScore = Integer.valueOf(0);
    }

    /**
     * Add the given object to its respective array if capacity is not exceeded and the object is not null;
     * return true on success.
     *
     * @param district the district to add
     * @return true if capacity is not exceeded and the object is not null, false otherwise
     */
    public boolean addDistrict(WallDistrict district) {
        if (district != null && districtCount < MAX_DISTRICTS) {
            districts[districtCount++] = district;
            return true;
        }
        return false;
    }

    /**
     * Add the given object to its respective array if capacity is not exceeded and the object is not null;
     * return true on success.
     *
     * @param squad the squad to register
     * @return true if capacity is not exceeded and the object is not null, false otherwise
     */
    public boolean registerSquad(ScoutSquad squad) {
        if (squad != null && squadCount < MAX_SQUADS) {
            activeSquads[squadCount++] = squad;
            return true;
        }
        return false;
    }

    /**
     * Add the given object to its respective array if capacity is not exceeded and the object is not null;
     * return true on success.
     *
     * @param titan the shifter encountered
     * @return true if capacity is not exceeded and the object is not null, false otherwise
     */
    public boolean registerTitan(TitanShifter titan) {
        if (titan != null && titanCount < MAX_TITANS) {
            encounteredTitans[titanCount++] = titan;
            return true;
        }
        return false;
    }

    // ---- Getters and setters (provided - already implemented; nothing to change) ----

    /**
     * Gets the total defense and combat score accumulated by humanity.
     *
     * @return total defense score as Integer
     */
    public Integer getTotalHumanityDefenseScore() {
        return totalHumanityDefenseScore;
    }

    /**
     * Gets the number of registered wall districts.
     *
     * @return registered district count
     */
    public int getDistrictCount() {
        return districtCount;
    }

    /**
     * Gets the number of registered Scout squads.
     *
     * @return registered squad count
     */
    public int getSquadCount() {
        return squadCount;
    }

    /**
     * Gets the number of encountered Titan Shifters.
     *
     * @return registered titan count
     */
    public int getTitanCount() {
        return titanCount;
    }

    /**
     * Finds a friendly (non-hostile) shifter among the encountered titans.
     * Searches encounteredTitans for a shifter whose isHostile is false.
     *
     * @return friendly TitanShifter, or null if none available
     */
    public TitanShifter findFriendlyShifter() {
        for (int i = 0; i < titanCount; i++) {
            if (Boolean.FALSE.equals(encounteredTitans[i].getIsHostile())) {
                return encounteredTitans[i];
            }
        }
        return null;
    }

    /**
     * Deploys a single squad unit of the specified size.
     * Demonstrates exact-match method overloading over varargs.
     *
     * @param squadSize unit size
     * @return deployed squad size directly
     */
    public static int deploySquadUnits(int squadSize) {
        return squadSize;
    }

    /**
     * Deploys multiple squad units across the battle sectors using varargs.
     * Overloads deploySquadUnits(int).
     *
     * @param firstSquad  size of primary unit
     * @param otherSquads sizes of additional units (varargs)
     * @return sum of all unit sizes
     */
    public static int deploySquadUnits(int firstSquad, int... otherSquads) {
        int total = firstSquad;
        if (otherSquads != null) {
            for (int size : otherSquads) {
                total += size;
            }
        }
        return total;
    }

    /**
     * Master Execution Method: Coordinates the comprehensive military operation 
     * to defend Wall Rose against the titan assault.
     *
     * Operational Workflow:
     * 1. Signal Intelligence: For each registered district, decodes the radio signals 
     *    using district.decodeSignal(commandSignal, hexIntel). Adds the decoded urgency level 
     *    and titan count to totalHumanityDefenseScore.
     * 2. District Kill Matrices: Tallies confirmed kills using 
     *    district.calculateTotalConfirmedKills() and adds to totalHumanityDefenseScore.
     * 3. Titan Engagement: Checks if the district has a spottedTitan:
     *    - If the spotted titan is hostile:
     *      - Searches for an undeployed squad in activeSquads that can engage the titan.
     *      - If a squad can engage, marks it deployed (sets deployed to true) 
     *        and adds its squadPower to totalHumanityDefenseScore.
     *      - If no squad can engage alone, calls on a friendly shifter (using findFriendlyShifter()):
     *        Creates an inner class transformation: TitanShifter.TitanForm form = friendly.new TitanForm("2");
     *        Retrieves the power surge via form.getPowerSurge() and boosts the first undeployed squad's power.
     *        Marks that squad as deployed and adds its updated squadPower to totalHumanityDefenseScore.
     *        Assesses combat readiness via TitanShifter.assessCombatReadiness(friendly.getPowerLevel()) 
     *        and adds the calculated readiness score (index 0).
     *    - If the spotted titan is friendly (not hostile):
     *      - Triggers the Coordinate ability via static nested class: 
     *        TitanShifter.CoordinatePower.triggerCoordinate(spottedTitan, commandSignal).
     *      - If activated, adds the controlled titans count to totalHumanityDefenseScore.
     * 4. Outcome Assessment: Returns true if totalHumanityDefenseScore >= 100 
     *    (Wall Rose successfully secured), otherwise false.
     *
     * @param commandSignal binary operational command signal (e.g. "1010")
     * @param hexIntel      hexadecimal intel report (e.g. "FF")
     * @return true if humanity's defense succeeds (score >= 100), false otherwise
     */
    public boolean executeDefenseOperation(String commandSignal, String hexIntel) {
        if (commandSignal == null || hexIntel == null) {
            return false;
        }

        for (int i = 0; i < districtCount; i++) {
            WallDistrict district = districts[i];
            if (district == null) continue;

            // 1. Decode signals
            int[] signalData = district.decodeSignal(commandSignal, hexIntel);
            int urgency = signalData[0];
            int detectedTitans = signalData[1];
            this.totalHumanityDefenseScore += urgency + detectedTitans;

            // 2. Tally confirmed kills (nested loops with null-skip)
            Integer kills = district.calculateTotalConfirmedKills();
            if (kills != null) {
                this.totalHumanityDefenseScore += kills;
            }

            // 3. Handle spotted titan
            TitanShifter spotted = district.getSpottedTitan();
            if (spotted != null) {
                if (Boolean.TRUE.equals(spotted.getIsHostile())) {
                    // Hostile titan spotted - attempt engagement
                    boolean engaged = false;
                    for (int s = 0; s < squadCount; s++) {
                        ScoutSquad squad = activeSquads[s];
                        if (squad != null && !Boolean.TRUE.equals(squad.isDeployed()) && squad.canEngage(spotted)) {
                            squad.setDeployed(Boolean.TRUE);
                            this.totalHumanityDefenseScore += squad.getSquadPower();
                            engaged = true;
                            break;
                        }
                    }

                    // If squad power is insufficient, mobilize friendly shifter
                    if (!engaged) {
                        TitanShifter friendly = findFriendlyShifter();
                        if (friendly != null) {
                            // Non-static inner class instantiation
                            TitanShifter.TitanForm form = friendly.new TitanForm("2");
                            Integer surge = form.getPowerSurge();

                            // Boost the first undeployed squad
                            ScoutSquad target = null;
                            for (int s = 0; s < squadCount; s++) {
                                if (activeSquads[s] != null && !Boolean.TRUE.equals(activeSquads[s].isDeployed())) {
                                    target = activeSquads[s];
                                    break;
                                }
                            }
                            if (target != null) {
                                target.boostSquadPower(surge);
                                target.setDeployed(Boolean.TRUE);
                                this.totalHumanityDefenseScore += target.getSquadPower();
                            }

                            // Combat readiness assessment (autoboxing test)
                            int[] readiness = TitanShifter.assessCombatReadiness(friendly.getPowerLevel());
                            this.totalHumanityDefenseScore += readiness[0];
                        }
                    }
                } else {
                    // Friendly shifter - trigger Coordinate
                    Boolean coordSuccess = TitanShifter.CoordinatePower.triggerCoordinate(spotted, commandSignal);
                    if (Boolean.TRUE.equals(coordSuccess)) {
                        this.totalHumanityDefenseScore += TitanShifter.CoordinatePower.getActiveTitansControlled();
                    }
                }
            }
        }

        return this.totalHumanityDefenseScore >= 100;
    }
}
