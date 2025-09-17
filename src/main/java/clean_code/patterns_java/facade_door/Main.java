package clean_code.patterns_java.facade_door;

public class Main {
    public static void main(String[] args) {
        DoorProcessingFacade doorProcessingFacade = new DoorProcessingFacade(
                new DoorOpener(),
                new DoorLocker(),
                new DoorCloser());

        doorProcessingFacade.openDoor();
        doorProcessingFacade.lockDoor();
        doorProcessingFacade.closeDoor();

        doorProcessingFacade.lockAndCloseDoor();

    }
}
