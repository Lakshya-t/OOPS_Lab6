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
        this(leader, new String[0]);
    }

    /**
     * Constructor 2 (Varargs Overloaded): Assembles a squad with a leader and arbitrary members.
     * Computes squadPower as members.length * 50 + 100.
     * Squad is not deployed initially (deployed is set to false).
     *
     * @param leader  squad leader's name
     * @param members additional squad members (varargs)
     */
    public ScoutSquad(String leader, String... members) {
        this.squadLeader = leader;
        this.members = (members != null) ? members : new String[0];
        this.squadPower = Integer.valueOf(this.members.length * 50 + 100);
        this.deployed = Boolean.FALSE;
    }

    // Getters and Setters
    public String getSquadLeader() { return squadLeader; }
    public void setSquadLeader(String squadLeader) { this.squadLeader = squadLeader; }

    public String[] getMembers() { return members; }
    public void setMembers(String[] members) { this.members = members; }

    public Integer getSquadPower() { return squadPower; }
    public void setSquadPower(Integer squadPower) { this.squadPower = squadPower; }

    public Boolean isDeployed() { return deployed; }
    public void setDeployed(Boolean deployed) { this.deployed = deployed; }

    /**
     * Boosts squad power by a given bonus amount.
     *
     * @param bonus power boost value as Integer
     */
    public void boostSquadPower(Integer bonus) {
        if (bonus != null) {
            this.squadPower += bonus;
        }
    }

    /**
     * Determines whether this squad has sufficient combat power to engage a specified titan.
     * Checks if the titan is hostile and squadPower is greater than or equal to the titan's powerLevel.
     *
     * @param titan target TitanShifter
     * @return true if squad can engage and neutralize the titan, false otherwise
     */
    public boolean canEngage(TitanShifter titan) {
        if (titan == null || !Boolean.TRUE.equals(titan.getIsHostile())) {
            return false;
        }
        return this.squadPower >= titan.getPowerLevel();
    }

    /**
     * Assembles and returns the full squad roster with the leader at index 0 followed by members.
     *
     * @return String array containing [leader, members[0], members[1], ...]
     */
    public String[] getFullRoster() {
        String[] roster = new String[1 + members.length];
        roster[0] = squadLeader;
        for (int i = 0; i < members.length; i++) {
            roster[i + 1] = members[i];
        }
        return roster;
    }

    /**
     * Decodes an inverted distress signal broadcast by scouts during titan ambush.
     * Reverses the order of words in the transmission string.
     * Words should be separated by a single space with no leading or trailing spaces.
     * <br>
     * Useful String methods hint:
     * <ul>
     *   <li><code>"  hello world  ".trim()</code> &rarr; <code>"hello world"</code></li>
     *   <li><code>"hello world".split("\\s+")</code> &rarr; <code>new String[]{"hello", "world"}</code></li>
     * </ul>
     *
     * @param rawSignal original distress transmission string
     * @return decoded signal with words in reversed order separated by a single space
     */
    public static String reverseDistressSignal(String rawSignal) {
        if (rawSignal == null) return null;
        String[] words = rawSignal.trim().split("\\s+");
        String result = "";
        for (int i = words.length - 1; i >= 0; i--) {
            result = result + words[i];
            if (i > 0) {
                result = result + " ";
            }
        }
        return result.trim();
    }
}
