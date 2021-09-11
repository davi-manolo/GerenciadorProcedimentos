package model;

import java.util.Date;

public class ServiceProcedureModel {
    
    private int id;
    private Date performedIn;
    private String client;
    private double price;
    private ProcedureTypeModel type;
    private double received;
    private Date registeredDate;
    
    public ServiceProcedureModel() {}
    
    public ServiceProcedureModel(int id, Date performedIn, String client,
            double price, ProcedureTypeModel type, double received, Date registeredDate) {
        this.id = id;
        this.performedIn = performedIn;
        this.client = client;
        this.price = price;
        this.type = type;
        this.received = received;
        this.registeredDate = registeredDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPerformedIn(Date performedIn) {
        this.performedIn = performedIn;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setType(ProcedureTypeModel type) {
        this.type = type;
    }

    public void setReceived(double received) {
        this.received = received;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public int getId() {
        return id;
    }

    public Date getPerformedIn() {
        return performedIn;
    }

    public String getClient() {
        return client;
    }

    public double getPrice() {
        return price;
    }

    public ProcedureTypeModel getType() {
        return type;
    }

    public double getReceived() {
        return received;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }
    
}
