package model;

public class ProcedureTypeModel {
    
    private int id;
    private String name;
    private double porcent;
    
    public ProcedureTypeModel(){}
    
    public ProcedureTypeModel(int id, String name, double porcent) {
        this.id = id;
        this.name = name;
        this.porcent = porcent;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPorcent(double porcent) {
        this.porcent = porcent;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPorcent() {
        return porcent;
    }

    @Override
    public String toString() {
        return name;
    }
    
    
    
}
