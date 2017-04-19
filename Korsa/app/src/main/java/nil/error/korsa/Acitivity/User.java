package nil.error.korsa.Acitivity;

/**
 * Created by nilerror on 19/4/17.
 */

public class User {

    String uid;
    String userName;
    String userContact;
    String userDOB;
    String userAge;
    String userProfession;
    String vehiBrand;
    String vehiModel;
    String vehiNumber;
    Boolean licence;
    Boolean puc;

    public User(){

    }

    public User(String uid,
            String userName,
            String userContact,
            String userDOB,
            String userAge,
            String userProfession,
                String vehiBrand,
                String vehiModel,
                String vehiNumber,
                Boolean licence,
                Boolean puc){

        this.uid = uid;
        this.userName = userName;
        this.userContact = userContact;
        this.userDOB = userDOB;
        this.userAge = userAge;
        this.userProfession = userProfession;
        this.vehiBrand = vehiBrand;
        this.vehiModel = vehiModel;
        this.vehiNumber = vehiNumber;
        this.licence = licence;
        this.puc = puc;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getUserDOB() {
        return userDOB;
    }

    public void setUserDOB(String userDOB) {
        this.userDOB = userDOB;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getUserProfession() {
        return userProfession;
    }

    public void setUserProfession(String userProfession) {
        this.userProfession = userProfession;
    }

    public String getVehiBrand() {
        return vehiBrand;
    }

    public void setVehiBrand(String vehiBrand) {
        this.vehiBrand = vehiBrand;
    }

    public String getVehiModel() {
        return vehiModel;
    }

    public void setVehiModel(String vehiModel) {
        this.vehiModel = vehiModel;
    }

    public String getVehiNumber() {
        return vehiNumber;
    }

    public void setVehiNumber(String vehiNumber) {
        this.vehiNumber = vehiNumber;
    }

    public Boolean getLicence() {
        return licence;
    }

    public void setLicence(Boolean licence) {
        this.licence = licence;
    }

    public Boolean getPuc() {
        return puc;
    }

    public void setPuc(Boolean puc) {
        this.puc = puc;
    }
}
