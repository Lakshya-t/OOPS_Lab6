/**
 * Represents a Titan Shifter in the military intelligence tracking system.
 * Contains both an inner class {@link TitanForm} and a static nested class {@link CoordinatePower}.
 * 
 * @author Survey Corps HQ
 */
public class TitanShifter {

    /** Name of the shifter (e.g. "Eren Yeager", "Reiner Braun"). */
    private String name;

    /** Combat power rating of the shifter. */
    private Integer powerLevel;

    /** Titan height in meters. */
    private Double heightMeters;

    /** Hostility status toward humanity. */
    private Boolean isHostile;

    /**
     * Constructor 1: Parses raw field report intelligence strings into wrapper types.<br>
     * <br>
     * <b>Implementation Instructions:</b><br>
     * 1. Assign {@code this.name = name}.<br>
     * 2. Parse {@code powerStr} using {@code Integer.valueOf(powerStr)}.<br>
     * 3. Parse {@code heightStr} using {@code Double.valueOf(heightStr)}.<br>
     * 4. Parse {@code hostileStr} using {@code Boolean.valueOf(hostileStr)}.<br>
     * <br>
     * <b>Conceptual Notes &amp; Edge Cases:</b><br>
     * - {@code Boolean.valueOf(String)} only evaluates to {@code true} for case-insensitive "true". 
     *   Strings like "yes", "1", or null evaluate to {@code false}.<br>
     * - Null or non-numeric {@code powerStr} or {@code heightStr} throws {@code NumberFormatException}.
     *
     * @param name        shifter name
     * @param powerStr    power level as string
     * @param heightStr   height in meters as string
     * @param hostileStr  hostility as string ("true"/"false")
     * @throws NumberFormatException if powerStr or heightStr is invalid
     */
    public TitanShifter(String name, String powerStr, String heightStr, String hostileStr) {
        this.name = name;
        this.powerLevel = Integer.valueOf(powerStr);
        this.heightMeters = Double.valueOf(heightStr);
        this.isHostile = Boolean.valueOf(hostileStr);
    }

    /**
     * Constructor 2 (Overloaded): Directly initializes fields with typed values.
     *
     * @param name         shifter name
     * @param powerLevel   power level as Integer
     * @param heightMeters height in meters as Double
     * @param isHostile    hostility as Boolean
     */
    public TitanShifter(String name, Integer powerLevel, Double heightMeters, Boolean isHostile) {
        this.name = name;
        this.powerLevel = powerLevel;
        this.heightMeters = heightMeters;
        this.isHostile = isHostile;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getPowerLevel() { return powerLevel; }
    public void setPowerLevel(Integer powerLevel) { this.powerLevel = powerLevel; }

    public Double getHeightMeters() { return heightMeters; }
    public void setHeightMeters(Double heightMeters) { this.heightMeters = heightMeters; }

    public Boolean getIsHostile() { return isHostile; }
    public void setIsHostile(Boolean isHostile) { this.isHostile = isHostile; }

    /**
     * Non-static inner class representing the titan transformation form.<br>
     * Has direct access to outer {@link TitanShifter}'s instance fields.
     */
    public class TitanForm {

        /** Maximum transformations allowed before stamina depletion. */
        public static final int MAX_TRANSFORM_LIMIT = 3;

        /** Number of times the shifter has transformed. */
        private Integer transformCount;

        /**
         * Constructor 1: Parses transformation count from string using {@code Integer.parseInt()}.
         *
         * @param transformCountStr transformation count as string
         * @throws NumberFormatException if transformCountStr is null or non-numeric
         */
        public TitanForm(String transformCountStr) {
            this.transformCount = Integer.parseInt(transformCountStr);
        }

        /**
         * Constructor 2 (Overloaded): Directly assigns integer count.
         *
         * @param transformCount count as int
         */
        public TitanForm(int transformCount) {
            this.transformCount = transformCount;
        }

        public Integer getTransformCount() { return transformCount; }
        public void setTransformCount(Integer transformCount) { this.transformCount = transformCount; }

        /**
         * Calculates power surge during titan form transformation.<br>
         * Formula: {@code powerLevel * transformCount * 2}<br>
         * Directly reads outer class's {@code powerLevel} field.
         *
         * @return combat power surge as Integer
         */
        public Integer getPowerSurge() {
            return powerLevel * transformCount * 2;
        }
    }

    /**
     * Static nested class representing the mysterious Coordinate (Founding Titan) ability.<br>
     * Does NOT have implicit access to enclosing instance fields; requires an explicit object reference.
     * Static members of the enclosing class can be accessed directly.
     */
    public static class CoordinatePower {

        /** Number of pure titans currently commanded. */
        private static Integer activeTitansControlled = 0;

        public static Integer getActiveTitansControlled() {
            return activeTitansControlled;
        }

        public static void setActiveTitansControlled(Integer count) {
            activeTitansControlled = count;
        }

        /**
         * Triggers the Coordinate power using an encrypted binary signal.<br>
         * Parses {@code commandSignal} with radix 2 (binary).<br>
         * If the shifter is friendly (not hostile) and signal &gt; 0, controls titans.<br>
         * Sets {@code activeTitansControlled = decodedSignal * 10} and returns true.
         *
         * @param shifter       the shifter attempting to scream/trigger coordinate
         * @param commandSignal binary signal string (e.g. "1010")
         * @return true if coordinate successfully commanded pure titans, false otherwise
         */
        public static Boolean triggerCoordinate(TitanShifter shifter, String commandSignal) {
            if (shifter == null || Boolean.TRUE.equals(shifter.getIsHostile())) {
                return false;
            }
            int decoded = Integer.parseInt(commandSignal, 2);
            if (decoded > 0) {
                activeTitansControlled = decoded * 10;
                return true;
            }
            return false;
        }
    }

    /**
     * Assesses a shifter's combat readiness via a 4-step boxing/unboxing sequence.<br>
     * 1. {@code power++} (Auto-unboxes, increments int, re-boxes into Integer)<br>
     * 2. {@code Integer score = power + (power * 3);} (Unboxes, computes, re-boxes)<br>
     * 3. {@code int rawScore = power + (power * 3);} (Unboxes, computes, NO re-boxing)<br>
     * 4. Returns {@code new int[]{score, rawScore}}
     *
     * @param power initial power rating as Integer
     * @return int array with [score, rawScore]
     * @throws NullPointerException if power is null
     */
    public static int[] assessCombatReadiness(Integer power) {
        power++;
        Integer score = power + (power * 3);
        int rawScore = power + (power * 3);
        return new int[]{score, rawScore};
    }
}
