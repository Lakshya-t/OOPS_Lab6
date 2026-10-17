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
     * Constructor 1: Initializes the shifter by parsing raw field report intelligence strings.
     * <br>
     * Parses the string arguments using wrapper class methods:
     * <ul>
     *   <li>{@code powerStr} parsed to Integer using {@code Integer.valueOf(powerStr)}</li>
     *   <li>{@code heightStr} parsed to Double using {@code Double.valueOf(heightStr)}</li>
     *   <li>{@code hostileStr} parsed to Boolean using {@code Boolean.valueOf(hostileStr)}</li>
     * </ul>
     *
     * @param name        shifter name
     * @param powerStr    power level as string
     * @param heightStr   height in meters as string
     * @param hostileStr  hostility as string ("true"/"false")
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

    // ---- Getters and setters (provided - already implemented; nothing to change) ----

    /**
     * Gets the shifter's name.
     *
     * @return shifter name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the shifter's name.
     *
     * @param name shifter name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the combat power level.
     *
     * @return power level as Integer
     */
    public Integer getPowerLevel() {
        return powerLevel;
    }

    /**
     * Sets the combat power level.
     *
     * @param powerLevel power level as Integer
     */
    public void setPowerLevel(Integer powerLevel) {
        this.powerLevel = powerLevel;
    }

    /**
     * Gets the titan height in meters.
     *
     * @return height in meters as Double
     */
    public Double getHeightMeters() {
        return heightMeters;
    }

    /**
     * Sets the titan height in meters.
     *
     * @param heightMeters height in meters as Double
     */
    public void setHeightMeters(Double heightMeters) {
        this.heightMeters = heightMeters;
    }

    /**
     * Gets whether the shifter is hostile toward humanity.
     *
     * @return hostility status as Boolean
     */
    public Boolean getIsHostile() {
        return isHostile;
    }

    /**
     * Sets whether the shifter is hostile toward humanity.
     *
     * @param isHostile hostility status as Boolean
     */
    public void setIsHostile(Boolean isHostile) {
        this.isHostile = isHostile;
    }

    /**
     * Non-static inner class representing the titan transformation form.<br>
     * Has direct access to outer {@link TitanShifter}'s instance fields.<br>
     * <br>
     * <b>This class is already fully implemented and provided. Do NOT change anything in it.</b>
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

        /**
         * Gets the transformation count.
         *
         * @return number of transformations as Integer
         */
        public Integer getTransformCount() {
            return transformCount;
        }

        /**
         * Sets the transformation count.
         *
         * @param transformCount count as Integer
         */
        public void setTransformCount(Integer transformCount) {
            this.transformCount = transformCount;
        }

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
     * Static nested class representing the Coordinate (Founding Titan) ability.<br>
     * Its getter and setter are provided (nothing to change there); only
     * {@code triggerCoordinate} needs to be implemented.
     */
    public static class CoordinatePower {

        /** Private constructor to prevent instantiation of static nested utility class. */
        private CoordinatePower() {}

        /** Number of pure titans currently commanded. */
        private static Integer activeTitansControlled = 0;

        /**
         * Gets the number of pure titans currently commanded.
         *
         * @return active controlled titan count as Integer
         */
        public static Integer getActiveTitansControlled() {
            return activeTitansControlled;
        }

        /**
         * Sets the number of pure titans currently commanded.
         *
         * @param count count as Integer
         */
        public static void setActiveTitansControlled(Integer count) {
            activeTitansControlled = count;
        }

        /**
         * Triggers the Coordinate power using an encrypted binary signal.
         * Decodes commandSignal using base 2.
         * If the shifter is friendly (not hostile) and decoded signal is greater than 0,
         * sets activeTitansControlled = decodedSignal * 10 and returns true.
         *
         * @param shifter       the shifter attempting to trigger coordinate
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
     * Assesses a shifter's combat readiness from its initial power rating.
     * <p>
     * First increase the power rating by 1. Using this updated power, compute:
     * </p>
     * <ul>
     *   <li>{@code score} (stored as an {@code Integer}) = power + (power * 3)</li>
     *   <li>{@code rawScore} (stored as an {@code int}) = power + (power * 3)</li>
     * </ul>
     * Return both values in the order [score, rawScore].
     *
     * @param power initial power rating as Integer
     * @return int array with [score, rawScore]
     */
    public static int[] assessCombatReadiness(Integer power) {
        power++;                                  // unbox, increment, re-box
        Integer score = power + (power * 3);      // unbox, compute, re-box
        int rawScore = power + (power * 3);       // unbox, compute, no re-boxing
        return new int[]{score, rawScore};
    }
}
