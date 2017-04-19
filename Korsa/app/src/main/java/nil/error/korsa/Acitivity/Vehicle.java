package nil.error.korsa.Acitivity;

/**
 * Created by nilerror on 19/4/17.
 */

public class Vehicle {

    String brandName;
    String modelName;
    String vehiNumber;
    Boolean licene;
    Boolean puc;

    public Vehicle(){

    }

    public Vehicle(String brandName,
            String modelName,
            String vehiNumber,
            Boolean licene,
            Boolean puc){

        this.brandName = brandName;
        this.modelName = modelName;
        this.vehiNumber = vehiNumber;
        this.licene = licene;
        this.puc = puc;

    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getVehiNumber() {
        return vehiNumber;
    }

    public void setVehiNumber(String vehiNumber) {
        this.vehiNumber = vehiNumber;
    }

    public Boolean getLicene() {
        return licene;
    }

    public void setLicene(Boolean licene) {
        this.licene = licene;
    }

    public Boolean getPuc() {
        return puc;
    }

    public void setPuc(Boolean puc) {
        this.puc = puc;
    }

}
