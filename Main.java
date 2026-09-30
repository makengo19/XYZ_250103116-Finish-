
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("OMNIHOME SMART CONTROLLER: SYSTEM STARTUP");
        System.out.println("=========================================");

        LegacyBulb rawBulb = new LegacyBulb();
        LegacyThermostat rawThermostat = new LegacyThermostat();
        System.out.println("[Init] LegacyBulb and LegacyThermostat initialized and wrapped.");

        BulbAdapter bulbAdapter = new BulbAdapter(rawBulb);
        ThermostatAdapter thermostatAdapter = new ThermostatAdapter(rawThermostat);

        List<SmartDevice> deviceList = List.of(bulbAdapter, thermostatAdapter);
        System.out.println("[Hub] Registering " + deviceList.size() + " adapted devices into ModernHub...");


        ModernHub hub = new ModernHub(deviceList);

        System.out.println("\n--- OPERATION: ACTIVATE ALL DEVICES ---");
        hub.activateAll();
        System.out.println("[Action] ModernHub.activateAll() invoked.");
        System.out.println("-> BulbAdapter: isON = " + bulbAdapter.isOn());
        System.out.println("-> ThermostatAdapter: isON = " + thermostatAdapter.isOn());
        System.out.println("[Status] All devices reported active: true");

        System.out.printf("[Power] Fleet Average Power Usage: %.2f%%\n", hub.calculateAveragePowerUsage());

        System.out.println("\n--- AUDIT: HARDWARE FAULT INJECTION (STAGE 4) ---");

        System.out.println("[Fault 1] Filament physically severed on LegacyBulb...");
        rawBulb.breakFilament();
        System.out.println("-> BulbAdapter.isOn(): " + bulbAdapter.isOn());
        System.out.println("-> BulbAdapter.getPowerPercent(): " + bulbAdapter.getPowerPercent() + "%");

        System.out.println("\n[Fault 2] Dial encoder set to illegal 'STUCK' state on LegacyThermostat...");
        rawThermostat.rotateDial("STUCK");
        System.out.println("-> ThermostatAdapter.isOn(): " + thermostatAdapter.isOn());
        System.out.println("-> ThermostatAdapter.getPowerPercent(): " + thermostatAdapter.getPowerPercent());

        System.out.println("\n--- OPERATION: EMERGENCY SHUTDOWN ---");
        hub.emergencyShutdown();
        System.out.println("[Action] ModernHub.emergencyShutdown() invoked.");
        System.out.printf("[Power] Fleet Average Power Usage: %.2f%%\n", hub.calculateAveragePowerUsage());

        System.out.println("\n=========================================");
        System.out.println("ALL INTEGRATION TESTS PASSED");
        System.out.println("=========================================");
    }
}