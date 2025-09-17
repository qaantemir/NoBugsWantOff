package clean_code.patterns_java.facade_door;

public class DoorProcessingFacade {
    private DoorOpener doorOpener;
    private DoorLocker doorLocker;
    private DoorCloser doorCloser;

    public DoorProcessingFacade(DoorOpener doorOpener, DoorLocker doorLocker, DoorCloser doorCloser) {
        this.doorOpener = new DoorOpener();
        this.doorLocker = new DoorLocker();
        this.doorCloser = new DoorCloser();
    }

    public void openDoor() {
        doorOpener.openDoor();
    }

    public void closeDoor() {
        doorCloser.closeDoor();
    }

    public void lockDoor() {
        doorLocker.lockDoor();
    }

    public void lockAndCloseDoor() {
        doorCloser.closeDoor();
        doorLocker.lockDoor();
    }
}
