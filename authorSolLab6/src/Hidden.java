import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.TestWatcher;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

@ExtendWith(Hidden.ScoreTracker.class)
public class Hidden {

    // =========================================================================
    // MARKS ALLOCATION TABLE (Total: 10.0 Marks)
    // =========================================================================
    private static final Map<String, Double> MARKS = new LinkedHashMap<>();
    private static final Map<String, Boolean> RESULTS = new LinkedHashMap<>();
    private static boolean reportPrinted = false;

    static {
        // Wrapper & Boolean parsing (1.6)
        MARKS.put("testBooleanValueOfTrap", 0.4);
        MARKS.put("testConstructorValueOfNullThrows", 0.4);
        MARKS.put("testTitanFormParseIntNullThrows", 0.4);
        MARKS.put("testTitanFormParseIntInvalidStringThrows", 0.4);

        // Autoboxing / NullPointerException (0.7)
        MARKS.put("testAssessCombatReadinessNullThrows", 0.7);

        // Integer Caching Trap (1.9)
        MARKS.put("testIntegerCachingBoundary128", 1.0);
        MARKS.put("testIntegerCachingBoundary127", 0.3);
        MARKS.put("testIntegerCachingLargeID", 0.3);
        MARKS.put("testIntegerCachingNegativeBoundary", 0.3);

        // Radix Parsing (0.8)
        MARKS.put("testDecodeSignalInvalidBinaryThrows", 0.4);
        MARKS.put("testDecodeSignalInvalidHexThrows", 0.4);

        // 2D Array Traversal & Nulls (1.0)
        MARKS.put("testDistrictConfirmedKillsWithNullCells", 0.6);
        MARKS.put("testDistrictConfirmedKillsEmptyGrid", 0.4);

        // Varargs & Squad Logic (1.2)
        MARKS.put("testScoutSquadVarargsZeroMembers", 0.4);
        MARKS.put("testScoutSquadBoostPowerUnboxing", 0.4);
        MARKS.put("testDeploySquadUnitsVarargsSum", 0.4);

        // Inner & Static Nested Classes (0.6)
        MARKS.put("testInnerClassStaticFinalConstant", 0.3);
        MARKS.put("testCoordinateCannotBeTriggeredByHostileTitan", 0.3);

        // Defense Orchestration (1.2)
        MARKS.put("testBattleOfWallRoseHostileEngagementWithFriendlySurge", 0.8);
        MARKS.put("testBattleOfWallRoseEmptyDefenseFailure", 0.4);

        // String Reversal Algorithm (1.0)
        MARKS.put("testReverseDistressSignalWithSpaces", 1.0);
    }

    // =========================================================================
    // JUNIT 5 SCORE TRACKER EXTENSION
    // =========================================================================
    public static class ScoreTracker implements TestWatcher, AfterAllCallback {
        @Override
        public void testSuccessful(ExtensionContext context) {
            context.getTestMethod().ifPresent(m -> RESULTS.put(m.getName(), Boolean.TRUE));
        }

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            context.getTestMethod().ifPresent(m -> RESULTS.put(m.getName(), Boolean.FALSE));
        }

        @Override
        public void afterAll(ExtensionContext context) {
            printReport();
        }
    }

    @AfterAll
    public static void onAfterAll() {
        printReport();
    }

    /**
     * Prints the formatted score report to the console.
     */
    public static synchronized void printReport() {
        if (reportPrinted) return;
        reportPrinted = true;

        System.out.println("\n================================================================================");
        System.out.println("                     HIDDEN TEST CASES EVALUATION REPORT");
        System.out.println("================================================================================");
        double totalEarned = 0.0;
        double maxTotal = 0.0;

        for (Map.Entry<String, Double> entry : MARKS.entrySet()) {
            String testName = entry.getKey();
            double weight = entry.getValue();
            maxTotal += weight;
            boolean passed = RESULTS.getOrDefault(testName, Boolean.FALSE);

            if (passed) {
                totalEarned += weight;
                System.out.printf("  [PASS] %-55s : %.1f / %.1f\n", testName, weight, weight);
            } else {
                System.out.printf("  [FAIL] %-55s : 0.0 / %.1f (Failed)\n", testName, weight);
            }
        }

        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("  TOTAL SCORE: %.1f / %.1f MARKS\n", totalEarned, maxTotal);
        System.out.println("================================================================================\n");
    }

    // =========================================================================
    // 1. BOOLEAN & WRAPPER PARSING EDGE CASES (1.6 Marks)
    // =========================================================================

    // 0.4 marks: Boolean.valueOf("yes") evaluates to false!
    @Test
    void testBooleanValueOfTrap() {
        TitanShifter reiner = new TitanShifter("Reiner", "200", "15.0", "yes");
        assertFalse(reiner.getIsHostile());

        TitanShifter bertholdt = new TitanShifter("Bertholdt", "250", "60.0", "TRUE");
        assertTrue(bertholdt.getIsHostile());
    }

    // 0.4 marks: Constructor with null throws NumberFormatException
    @Test
    void testConstructorValueOfNullThrows() {
        assertThrows(NumberFormatException.class, () -> {
            new TitanShifter("Mystery", null, "15.0", "false");
        });
    }

    // 0.4 marks: TitanForm parseInt with null
    @Test
    void testTitanFormParseIntNullThrows() {
        TitanShifter eren = new TitanShifter("Eren", 100, 15.0, false);
        assertThrows(NumberFormatException.class, () -> {
            eren.new TitanForm(null);
        });
    }

    // 0.4 marks: TitanForm parseInt with non-numeric string
    @Test
    void testTitanFormParseIntInvalidStringThrows() {
        TitanShifter eren = new TitanShifter("Eren", 100, 15.0, false);
        assertThrows(NumberFormatException.class, () -> {
            eren.new TitanForm("invalid");
        });
    }

    // =========================================================================
    // 2. AUTOBOXING / UNBOXING / NULL POINTER (0.7 Marks)
    // =========================================================================

    // 0.7 marks: Unboxing null in assessCombatReadiness triggers NullPointerException
    @Test
    void testAssessCombatReadinessNullThrows() {
        assertThrows(NullPointerException.class, () -> {
            TitanShifter.assessCombatReadiness(null);
        });
    }

    // =========================================================================
    // 3. INTEGER CACHING (THE TRAP) (1.9 Marks)
    // =========================================================================

    // 1.0 marks: ID 128 is outside Integer cache [-128, 127] -> must use .equals()
    @Test
    void testIntegerCachingBoundary128() {
        WallDistrict district = new WallDistrict("Krolva");
        // If student used ==, this will FAIL because separate heap objects are allocated
        assertTrue(district.verifyTitanIdentification("128", "128"));
    }

    // 0.3 marks: Boundary at 127 (still inside cache)
    @Test
    void testIntegerCachingBoundary127() {
        WallDistrict district = new WallDistrict("Krolva");
        assertTrue(district.verifyTitanIdentification("127", "127"));
    }

    // 0.3 marks: Large ID (outside cache)
    @Test
    void testIntegerCachingLargeID() {
        WallDistrict district = new WallDistrict("Krolva");
        assertTrue(district.verifyTitanIdentification("1000", "1000"));
    }

    // 0.3 marks: Negative boundary outside cache (-129)
    @Test
    void testIntegerCachingNegativeBoundary() {
        WallDistrict district = new WallDistrict("Krolva");
        assertTrue(district.verifyTitanIdentification("-129", "-129"));
    }

    // =========================================================================
    // 4. RADIX PARSING EDGE CASES (0.8 Marks)
    // =========================================================================

    // 0.4 marks: Invalid binary digit '2' throws NumberFormatException
    @Test
    void testDecodeSignalInvalidBinaryThrows() {
        WallDistrict district = new WallDistrict("Trost");
        assertThrows(NumberFormatException.class, () -> {
            district.decodeSignal("1234", "FF");
        });
    }

    // 0.4 marks: Invalid hex digit 'G' throws NumberFormatException
    @Test
    void testDecodeSignalInvalidHexThrows() {
        WallDistrict district = new WallDistrict("Trost");
        assertThrows(NumberFormatException.class, () -> {
            district.decodeSignal("1010", "GG");
        });
    }

    // =========================================================================
    // 5. NESTED LOOPS & NULL CELLS (1.0 Marks)
    // =========================================================================

    // 0.6 marks: Null cells inside 2D matrix must be safely skipped
    @Test
    void testDistrictConfirmedKillsWithNullCells() {
        Integer[][] matrix = {{3, null, 2}, {null, 5, 1}};
        WallDistrict district = new WallDistrict("Ehrmich", "70", matrix);
        // 3 + 2 + 5 + 1 = 11
        assertEquals(11, district.calculateTotalConfirmedKills());
    }

    // 0.4 marks: Empty kill grid
    @Test
    void testDistrictConfirmedKillsEmptyGrid() {
        WallDistrict district = new WallDistrict("EmptyDistrict");
        assertEquals(0, district.calculateTotalConfirmedKills());
    }

    // =========================================================================
    // 6. VARARGS & SQUAD LOGIC (1.2 Marks)
    // =========================================================================

    // 0.4 marks: Squad with zero varargs members
    @Test
    void testScoutSquadVarargsZeroMembers() {
        ScoutSquad squad = new ScoutSquad("Erwin");
        assertEquals("Erwin", squad.getSquadLeader());
        assertEquals(0, squad.getMembers().length);
        assertEquals(100, squad.getSquadPower()); // 0 * 50 + 100
        assertEquals(1, squad.getFullRoster().length);
    }

    // 0.4 marks: Boost squad power unboxing/reboxing
    @Test
    void testScoutSquadBoostPowerUnboxing() {
        ScoutSquad squad = new ScoutSquad("Hange", "Moblit");
        squad.boostSquadPower(50);
        assertEquals(200, squad.getSquadPower()); // (1 * 50 + 100) + 50 = 200
    }

    // 0.4 marks: Varargs unit deployment sum
    @Test
    void testDeploySquadUnitsVarargsSum() {
        assertEquals(30, BattleOfWallRose.deploySquadUnits(10, 5, 5, 10));
    }

    // =========================================================================
    // 7. INNER CLASS & STATIC NESTED CLASS (0.6 Marks)
    // =========================================================================

    // 0.3 marks: Non-static inner class static final constant
    @Test
    void testInnerClassStaticFinalConstant() {
        assertEquals(3, TitanShifter.TitanForm.MAX_TRANSFORM_LIMIT);
    }

    // 0.3 marks: Hostile titan cannot trigger Coordinate power
    @Test
    void testCoordinateCannotBeTriggeredByHostileTitan() {
        TitanShifter reiner = new TitanShifter("Reiner", 200, 15.0, true);
        assertFalse(TitanShifter.CoordinatePower.triggerCoordinate(reiner, "1010"));
    }

    // =========================================================================
    // 8. ADVANCED COORDINATOR WORKFLOW (1.2 Marks)
    // =========================================================================

    // 0.8 marks: Battle with hostile titan requiring friendly shifter power surge
    @Test
    void testBattleOfWallRoseHostileEngagementWithFriendlySurge() {
        BattleOfWallRose battle = new BattleOfWallRose();

        // Hostile Titan with power 300
        TitanShifter reiner = new TitanShifter("Reiner", 300, 15.0, true);
        WallDistrict krolva = new WallDistrict("Krolva", "95", new Integer[][]{{10}});
        krolva.setSpottedTitan(reiner);
        battle.addDistrict(krolva);

        // Friendly shifter Eren (power 100)
        TitanShifter eren = new TitanShifter("Eren", 100, 15.0, false);
        battle.registerTitan(eren);

        // Squad Levi with base power 150 (cannot beat Reiner alone: 150 < 300)
        ScoutSquad levi = new ScoutSquad("Levi", "Soldier1"); // power = 150
        battle.registerSquad(levi);

        // executeDefenseOperation mobilizes Eren's power surge (100 * 2 * 2 = 400),
        // boosts Levi's squad (150 + 400 = 550), and assesses combat readiness!
        boolean success = battle.executeDefenseOperation("0101", "0A");
        assertTrue(success);
        assertTrue(battle.getTotalHumanityDefenseScore() > 500);
    }

    // 0.4 marks: Battle with null signals safely fails
    @Test
    void testBattleOfWallRoseEmptyDefenseFailure() {
        BattleOfWallRose battle = new BattleOfWallRose();
        assertFalse(battle.executeDefenseOperation(null, "FF"));
        assertFalse(battle.executeDefenseOperation("1010", null));
    }

    // =========================================================================
    // 9. STRING REVERSAL ALGORITHM (1.0 Mark)
    // =========================================================================

    // 1.0 marks: Decodes inverted distress signal with extra spaces (LeetCode 151)
    @Test
    void testReverseDistressSignalWithSpaces() {
        String input = "  breach at wall rose  ";
        String expected = "rose wall at breach";
        assertEquals(expected, ScoutSquad.reverseDistressSignal(input));

        String input2 = "eren  protect  must";
        String expected2 = "must protect eren";
        assertEquals(expected2, ScoutSquad.reverseDistressSignal(input2));
    }

    // =========================================================================
    // STANDALONE MAIN RUNNER (If executed as Java Application)
    // =========================================================================
    public static void main(String[] args) {
        Hidden instance = new Hidden();
        for (Method m : Hidden.class.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Test.class)) {
                try {
                    m.invoke(instance);
                    RESULTS.put(m.getName(), Boolean.TRUE);
                } catch (Throwable t) {
                    RESULTS.put(m.getName(), Boolean.FALSE);
                }
            }
        }
        printReport();
    }
}
