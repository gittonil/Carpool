package nil.error.korsa.Acitivity;

import android.support.annotation.Keep;

/**
 * Created by nilerror on 13/4/17.
 */

public class Offerride {

    public static final String mapsource = "nil.error.korsa.Acitivity.source";
    public static final String mapdest = "nil.error.korsa.Acitivity.dest";
    public String uid;
    public String source;
    public String destination;
    public String startDate;
    public String startTime;
    public String seatVacancy;
    public Boolean genderMale;
    public Boolean genderFemale;
    public Boolean genderOther;

    public Offerride(){

    }

    @Keep
    public Offerride(String uid, String source, String destination, String startDate, String startTime,
                     String seatVacancy, Boolean genderMale, Boolean genderFemale, Boolean genderOther){

        this.uid = uid;
        this.source = source;
        this.destination = destination;
        this.startDate = startDate;
        this.startTime = startTime;
        this.seatVacancy = seatVacancy;
        this.genderMale = genderMale;
        this.genderFemale = genderFemale;
        this.genderOther = genderOther;

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

   /* public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
*/
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

    public String getSeatVacancy() {
        return seatVacancy;
    }

    public void setSeatVacancy(String seatVacancy) {
        this.seatVacancy = seatVacancy;
    }

    public Boolean getGenderMale() {
        return genderMale;
    }

    public void setGenderMale(Boolean genderMale) {
        this.genderMale = genderMale;
    }

    public Boolean getGenderFemale() {
        return genderFemale;
    }

    public void setGenderFemale(Boolean genderFemale) {
        this.genderFemale = genderFemale;
    }

    public Boolean getGenderOther() {
        return genderOther;
    }

    public void setGenderOther(Boolean genderOther) {
        this.genderOther = genderOther;
    }

}
