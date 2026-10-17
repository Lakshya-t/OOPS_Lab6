import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class Visible {

    // 0.25 marks: Constructor 1 String parsing & getters
    @Test
    void testTitanShifterConstructorAndGetters() {
        TitanShifter eren = new TitanShifter("Eren", "150", "15.0", "false");
        assertEquals("Eren", eren.getName());
        assertEquals(150, eren.getPowerLevel());
        assertEquals(15.0, eren.getHeightMeters());
        assertFalse(eren.getIsHostile());
    }

    // 0.25 marks: Constructor 2 Overloaded with typed parameters
    @Test
    void testTitanShifterOverloadedConstructor() {
        TitanShifter armin = new TitanShifter("Armin", 120, 60.0, false);
        assertEquals("Armin", armin.getName());
        assertEquals(120, armin.getPowerLevel());
        assertEquals(60.0, armin.getHeightMeters());
        assertFalse(armin.getIsHostile());
    }

    // 0.25 marks: Inner class TitanForm creation and power surge reading outer field
    @Test
    void testTitanFormInnerClassPowerSurge() {
        TitanShifter eren = new TitanShifter("Eren", 100, 15.0, false);
        TitanShifter.TitanForm form = eren.new TitanForm("2");
        assertEquals(2, form.getTransformCount());
        // outer power (100) * transformCount (2) * 2 = 400
        assertEquals(400, form.getPowerSurge());
    }

    // 0.25 marks: Static nested class CoordinatePower
    @Test
    void testCoordinatePowerStaticNestedClass() {
        TitanShifter eren = new TitanShifter("Eren", 100, 15.0, false);
        // binary 1010 = 10 -> controls 10 * 10 = 100 titans
        assertTrue(TitanShifter.CoordinatePower.triggerCoordinate(eren, "1010"));
        assertEquals(100, TitanShifter.CoordinatePower.getActiveTitansControlled());
    }

    // 0.25 marks: assessCombatReadiness boxing/unboxing sequence
    @Test
    void testAssessCombatReadiness() {
        int[] result = TitanShifter.assessCombatReadiness(100);
        // power becomes 101 after ++
        // score = 101 + (101 * 3) = 101 + 303 = 404
        assertEquals(404, result[0]);
        assertEquals(404, result[1]);
    }

    // 0.25 marks: ScoutSquad varargs constructor & full roster
    @Test
    void testScoutSquadVarargsConstructorAndRoster() {
        ScoutSquad squad = new ScoutSquad("Levi", "Eren", "Mikasa", "Armin");
        assertEquals("Levi", squad.getSquadLeader());
        assertEquals(3, squad.getMembers().length);
        // power = 3 * 50 + 100 = 250
        assertEquals(250, squad.getSquadPower());

        String[] roster = squad.getFullRoster();
        assertEquals(4, roster.length);
        assertEquals("Levi", roster[0]);
        assertEquals("Armin", roster[3]);
    }

    // 0.25 marks: Interlinked method: squad engaging hostile titan
    @Test
    void testScoutSquadCanEngage() {
        ScoutSquad leviSquad = new ScoutSquad("Levi", "Eren", "Mikasa"); // power = 2 * 50 + 100 = 200
        TitanShifter reiner = new TitanShifter("Reiner", 180, 15.0, true);
        TitanShifter bertholdt = new TitanShifter("Bertholdt", 300, 60.0, true);

        assertTrue(leviSquad.canEngage(reiner));        // 200 >= 180 and hostile
        assertFalse(leviSquad.canEngage(bertholdt));   // 200 < 300
    }

    // 0.25 marks: WallDistrict calculateTotalConfirmedKills with nested loops
    @Test
    void testWallDistrictConfirmedKillsBasic() {
        Integer[][] matrix = {{5, 3}, {2, 4}};
        WallDistrict trost = new WallDistrict("Trost", "90", matrix);
        assertEquals(14, trost.calculateTotalConfirmedKills());
    }

    // 0.25 marks: WallDistrict decodeSignal with radix 2 and 16
    @Test
    void testWallDistrictDecodeSignal() {
        WallDistrict trost = new WallDistrict("Trost");
        int[] signals = trost.decodeSignal("1010", "FF");
        assertEquals(10, signals[0]);  // binary 1010 = 10
        assertEquals(255, signals[1]); // hex FF = 255
    }

    // 0.25 marks: Method overloading resolution (exact match vs varargs)
    @Test
    void testDeploySquadUnitsOverloadExactMatch() {
        assertEquals(8, BattleOfWallRose.deploySquadUnits(8));
        assertEquals(15, BattleOfWallRose.deploySquadUnits(5, 4, 6));
    }

    // 1.0 marks: The Master Coordinator Method linking all classes together
    @Test
    void testBattleOfWallRoseDefenseOperationSuccess() {
        BattleOfWallRose battle = new BattleOfWallRose();

        Integer[][] kills = {{10, 15}, {20, 25}};
        WallDistrict trost = new WallDistrict("Trost", "80", kills);
        TitanShifter eren = new TitanShifter("Eren", 150, 15.0, false);
        trost.setSpottedTitan(eren);

        battle.addDistrict(trost);
        battle.registerTitan(eren);

        ScoutSquad levi = new ScoutSquad("Levi", "Mikasa", "Armin");
        battle.registerSquad(levi);

        // Executes total functioning of the defense operation
        // binary "0100" = 4, hex "20" = 32
        // Kills: 70, Signals: 36, Coordinate controlled: 40 -> Total: 146 >= 100
        boolean result = battle.executeDefenseOperation("0100", "20");
        assertTrue(result);
        assertTrue(battle.getTotalHumanityDefenseScore() >= 100);
    }
}
