import javax.swing.text.Style;
enum FlightStages implements Trackable {GROUNDED, LAUNCH, CRUISE, DATA_COLLECTION;

    @Override
    public void track() {
        if (this != GROUNDED) {
            System.out.println("Monitoring " + this);
        }
    }

    public FlightStages getNextStage() {
        FlightStages[] allStages = values();
        return allStages[(ordinal() + 1) % allStages.length]; // Understand this code
    }

}

record DragonFly(String name, String type) implements FlightEnabled {

    @Override
    public void takeOff() {

    }

    @Override
    public void land() {

    }

    @Override
    public void fly() {

    }
}

class Satellite implements OrbitEarth {

    FlightStages stages = FlightStages.GROUNDED;

    @Override
    public void achieveOrbit() {
        System.out.println("Orbit achieved!");
    }

    @Override
    public void takeOff() {
        transition("Taking Off");

    }

    @Override
    public void land() {
        transition("Landing");
    }

    @Override
    public void fly() {
        transition("Orbit Achieved");
    }

    @Override
    public void track() {

    }

    public void transition(String description) {
        System.out.println(description);
        stages = transition(stages);
        stages.track();
    }
}

// An interface can extend another Interface, but it cannot implement it
interface OrbitEarth extends FlightEnabled, Trackable {
    void achieveOrbit();
    private static void log(String description) {
        var today = new java.util.Date();
        System.out.println(today + ": " + description);

    }

    private void logStage(FlightStages stage, String description) {
        description = stage + description;
        log(description);

    }

    @Override
    default FlightStages transition(FlightStages stage) {
        FlightStages nextStage = FlightEnabled.super.transition(stage);
        logStage(stage, "Beginning Transition to " + nextStage);
        return nextStage;
    }
}

interface FlightEnabled {
    // Defining Fields, they can't be private and should be final because it's an interface
    double MILES_TO_KM = 1.60934;
    public static final double KM_TO_MILES = 0.621371; // Same as variable above


    public abstract void takeOff();
    abstract void land();
    void fly(); // Here, public and abstract declaration are implied


    // If we omit an access modifier on a class member, it's implicitly package private
    // If we omit an access modifier on an interface member, it's implicitly public

    default FlightStages transition(FlightStages stage) {
        // "default" lets you add methods without breaking other classes implementing the interface

//        System.out.println("transition not implemented on " + getClass().getName());
//        return null;
        FlightStages nextStage = stage.getNextStage();
        System.out.println("Transitioning from " + stage + " to " + nextStage);
        return nextStage;
    }

}

// Interfaces also forces methods that implement them to override their methods

interface Trackable {
    void track();

}

public abstract class Animal {
    public abstract void move();
}
