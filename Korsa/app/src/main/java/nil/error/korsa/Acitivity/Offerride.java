package nil.error.korsa.Acitivity;

import android.support.annotation.Keep;

/**
 * Created by nilerror on 13/4/17.
 */

public class Offerride {

    public String uid;
    public String source;
    public String destination;
    public String startDate;
    public String startTime;

    public Offerride() {

    }

    @Keep
    public Offerride(String uid, String source, String destination, String startDate, String startTime) {
        this.uid = uid;
        this.source = source;
        this.destination = destination;
        this.startDate = startDate;
        this.startTime = startTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

}
