package clean_code.fix_code.DIP;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationService {
    private Sendable sendable;

    public void sendNotification(String message) {
        sendable.send(message);
    }
}
