package dummy.tracker.objects;

public class Contact {
    protected String MAC;
    protected String noticedTime;

    public Contact(String MAC, String noticedTime) {
        this.MAC = MAC;
        this.noticedTime = noticedTime;
    }

    public String getMAC() {
        return MAC;
    }


    public String getNoticedTime() {
        return noticedTime;
    }
}
