package Download;

import sun.security.provider.PolicySpiFile;

import java.net.URL;
import java.util.Observable;

/**
 * Created by ravneet on 23/6/17.
 */
public class Download extends Observable implements Runnable {

    public static final int Max_Buffer_Size = 1024;
    public static final String STATUSES[] = {"Downloading", "Paused","Completed","Cancelled","Error"};

    public static final int DOWNLOADING = 0;
    public static final int PAUSED = 1;
    public static final int COMPLETED = 2;
    public static final int CANCELLED = 3;
    public static final int ERROR = 4;

    private URL url;
    private int size;
    private int downloaded;
    private int status;

    public Download(URL url) {
        this.url = url;
        size = -1;
        status = DOWNLOADING;

        downlaod();
    }

    public String getUrl() {

        return url.toString();
    }
    public int getSize(){
        return size;
    }

    public float getProgress(){
        return ((float)downloaded/size)*100;
    }

    public int getStatus() {
        return status;
    }

    public void pause(){
        status = PAUSED;
        stateChanged();
    }

    public void resume(){
        status = DOWNLOADING;
        stateChanged();
    }

    public void cancel(){
        status = CANCELLED;
        statechanged();
    }

    public void error(){
        status = ERROR;
        stateChanged();
    }

    public void downlaod(){
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {

    }
}
