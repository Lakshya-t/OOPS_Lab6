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
     * Initializes the tactical defense command system with empty arrays and zeroed counters.
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

    // Registration Methods
    public boolean addDistrict(WallDistrict district) {
        if (district != null && districtCount < MAX_DISTRICTS) {
            districts[districtCount++] = district;
            return true;
        }
        return false;
    }

    public boolean registerSquad(ScoutSquad squad) {
        if (squad != null && squadCount < MAX_SQUADS) {
            activeSquads[squadCount++] = squad;
            return true;
        }
        return false;
    }

    public boolean registerTitan(TitanShifter titan) {
        if (titan != null && titanCount < MAX_TITANS) {
            encounteredTitans[titanCount++] = titan;
            return true;
        }
        return false;
    }

    // Getters
    public Integer getTotalHumanityDefenseScore() { return totalHumanityDefenseScore; }
    public int getDistrictCount() { return districtCount; }
    public int getSquadCount() { return squadCount; }
    public int getTitanCount() { return titanCount; }

    /**
     * Deploys a single squad unit of the specified size.<br>
     * Demonstrates exact-match method overloading over varargs.
     *
     * @param squadSize unit size
     * @return deployed squad size directly
     */
    public static int deploySquadUnits(int squadSize) {
        return squadSize;
    }

    /**
     * Deploys multiple squad units across the battle sectors using varargs.<br>
     * Overloads {@link #deploySquadUnits(int)}.
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
     * Finds a friendly (non-hostile) shifter among the encountered titans.
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
     * <b>Master Execution Method:</b> Coordinates the comprehensive military operation 
     * to defend Wall Rose against the titan assault.<br>
     * <br>
     * <b>Operational Workflow:</b><br>
     * 1. <b>Signal Intelligence:</b> For each registered district, decodes the radio signals 
     *    using {@code district.decodeSignal(commandSignal, hexIntel)}. Adds the decoded urgency level 
     *    and titan count to {@code totalHumanityDefenseScore}.<br>
     * 2. <b>District Kill Matrices:</b> Tallies confirmed kills using 
     *    {@code district.calculateTotalConfirmedKills()} (skipping null cells via nested loops) 
     *    and adds to {@code totalHumanityDefenseScore}.<br>
     * 3. <b>Titan Engagement:</b> Checks if the district has a {@code spottedTitan}:<br>
     *    &bull; If the spotted titan is <b>hostile</b>:<br>
     *      - Searches for an undeployed squad in {@code activeSquads} that can engage the titan 
     *        ({@code squad.canEngage(spottedTitan)}).<br>
     *      - If a squad can engage, marks it deployed ({@code squad.setDeployed(Boolean.TRUE)}) 
     *        and adds its {@code squadPower} to {@code totalHumanityDefenseScore}.<br>
     *      - If no squad can engage alone, calls on a friendly shifter (e.g. Eren):<br>
     *        Creates an inner class transformation: {@code TitanShifter.TitanForm form = friendly.new TitanForm("2");}<br>
     *        Retrieves the power surge via {@code form.getPowerSurge()} and boosts the first squad's power.<br>
     *        Assesses combat readiness via {@code TitanShifter.assessCombatReadiness(friendly.getPowerLevel())} 
     *        and adds the calculated readiness score.<br>
     *    &bull; If the spotted titan is <b>friendly</b>:<br>
     *      - Triggers the Coordinate ability via static nested class: 
     *        {@code TitanShifter.CoordinatePower.triggerCoordinate(spottedTitan, commandSignal)}.<br>
     *      - If activated, adds the controlled titans count to {@code totalHumanityDefenseScore}.<br>
     * 4. <b>Outcome Assessment:</b> Returns {@code true} if {@code totalHumanityDefenseScore >= 100} 
     *    (Wall Rose successfully secured), otherwise {@code false}.
     *
     * @param commandSignal binary operational command signal (e.g. "1010")
     * @param hexIntel      hexadecimal intel report (e.g. "FF")
     * @return true if humanity's defense succeeds (score &gt;= 100), false otherwise
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

                            if (squadCount > 0 && activeSquads[0] != null) {
                                activeSquads[0].boostSquadPower(surge);
                                activeSquads[0].setDeployed(Boolean.TRUE);
                                this.totalHumanityDefenseScore += activeSquads[0].getSquadPower();
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
