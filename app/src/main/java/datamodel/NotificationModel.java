package datamodel;

/**
 * Created by Hopewell Mutanda on 6/27/2015.
 */
public class NotificationModel {
    public String NotificationId;
    public String SchoolId;
    public String SchoolName;
    public String SchoolImage;
    public String NotificationSubject;
    public String NotificationDetail;
    public String NotificationDate;

    public NotificationModel(String NotificationId, String SchoolId, String SchoolName, String SchoolImage, String NotificationSubject, String NotificationDetail, String NotificationDate){
        this.SchoolId = SchoolId;
        this.NotificationId = NotificationId;
        this.NotificationSubject = NotificationSubject;
        this.NotificationDetail = NotificationDetail;
        this.NotificationDate = NotificationDate;
        this.SchoolName = SchoolName;
        this.SchoolImage = SchoolImage;
    }
}
