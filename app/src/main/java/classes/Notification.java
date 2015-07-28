package classes;

/**
 * Created by Hopewell Mutanda on 6/27/2015.
 */
public class Notification {
    public String NotificationId;
    public String SchoolId;
    public String NotificationSubject;
    public String NotificationDetail;
    public String NotificationDate;

    public Notification(String NotificationId, String SchoolId, String NotificationSubject, String NotificationDetail, String NotificationDate){
        this.SchoolId = SchoolId;
        this.NotificationId = NotificationId;
        this.NotificationSubject = NotificationSubject;
        this.NotificationDetail = NotificationDetail;
        this.NotificationDate = NotificationDate;
    }
}
