/**
 * Represents an elite combat squad within the Survey Corps (e.g. Squad Levi, Squad Hange).
 * Uses varargs constructors and encapsulates squad readiness logic.
 * 
 * @author Survey Corps HQ
 */
public class ScoutSquad {

    /** Name of the squad leader (e.g. "Levi Ackerman"). */
    private String squadLeader;

    /** Array of squad members under the leader. */
    private String[] members;

    /** Total combat power rating of the squad. */
    private Integer squadPower;

    /** Whether the squad is currently deployed in active combat. */
    private Boolean deployed;

    /**
     * Constructor 1 (Overloaded): Creates a squad with only the leader.
     * Calls the second constructor using {@code this(...)}:
     * <pre>
     *     // Hint: Constructor delegation allows calling another constructor in the same class:
     *     // e.g., this(param1, defaultValueForParam2);
     * </pre>
     * (Calling one constructor from another in the same class using {@code this(...)} 
     * is known as constructor delegation).
     *
     * @param leader squad leader's name
     */
    public ScoutSquad(String leader) {
        // TODO: delegate to Constructor 2 passing leader and an empty String array
    }

    /**
     * Constructor 2 (Varargs Overloaded): Assembles a squad with a leader and squad members.
     * The varargs parameter may be empty (no squad members).
     * Computes squadPower as members.length * 50 + 100, stored as an Integer.
     * Sets deployed to the Boolean representation of false (the squad is not deployed initially).
     *
     * @param leader  squad leader's name
     * @param members squad members (varargs; may be empty)
     */
    public ScoutSquad(String leader, String... members) {
        // TODO: implement this constructor
    }

    // ---- Getters and setters (provided - already implemented; nothing to change) ----

    /**
     * Gets the squad leader's name.
     *
     * @return leader's name
     */
    public String getSquadLeader() {
        return squadLeader;
    }

    /**
     * Sets the squad leader's name.
     *
     * @param squadLeader leader's name
     */
    public void setSquadLeader(String squadLeader) {
        this.squadLeader = squadLeader;
    }

    /**
     * Gets the squad members.
     *
     * @return array of member names
     */
    public String[] getMembers() {
        return members;
    }

    /**
     * Sets the squad members.
     *
     * @param members array of member names
     */
    public void setMembers(String[] members) {
        this.members = members;
    }

    /**
     * Gets the squad's combat power rating.
     *
     * @return squad power as Integer
     */
    public Integer getSquadPower() {
        return squadPower;
    }

    /**
     * Sets the squad's combat power rating.
     *
     * @param squadPower squad power as Integer
     */
    public void setSquadPower(Integer squadPower) {
        this.squadPower = squadPower;
    }

    /**
     * Gets whether the squad is deployed in active combat.
     *
     * @return deployment status as Boolean
     */
    public Boolean isDeployed() {
        return deployed;
    }

    /**
     * Sets whether the squad is deployed in active combat.
     *
     * @param deployed deployment status as Boolean
     */
    public void setDeployed(Boolean deployed) {
        this.deployed = deployed;
    }

    /**
     * Boosts squad power by a given bonus amount.
     *
     * @param bonus power boost value as Integer
     */
    public void boostSquadPower(Integer bonus) {
        // TODO: implement this method
    }

    /**
     * Determines whether this squad has sufficient combat power to engage a specified titan.
     * Checks if the titan is hostile and squadPower is greater than or equal to the titan's powerLevel.
     *
     * @param titan target TitanShifter
     * @return true if squad can engage and neutralize the titan, false otherwise
     */
    public boolean canEngage(TitanShifter titan) {
        // TODO: implement this method
        return false;
    }

    /**
     * Assembles and returns the full squad roster with the leader at index 0 followed by members.
     *
     * @return String array containing [leader, members[0], members[1], ...]
     */
    public String[] getFullRoster() {
        // TODO: implement this method
        return null;
    }
}
