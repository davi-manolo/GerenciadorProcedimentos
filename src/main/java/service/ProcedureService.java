package service;

import java.text.NumberFormat;
import view.ManagerDialog;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import utils.SessionMode;
import model.ServiceProcedureModel;

public class ProcedureService {
    
    private ManagerDialog manager;

    private ServiceProcedureModel sp = new ServiceProcedureModel();
    
    public ProcedureService(ManagerDialog manager) {
        this.manager = manager;
    }

    public ServiceProcedureModel createServiceProcedure(ManagerDialog manager) {
        sp.setPerformedIn(Date.from(manager.getDatePicker().getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        sp.setClient(formaterName(manager.getClientField().getText()));
        sp.setPrice(Double.valueOf(manager.getPriceField().getText().replace(",", ".")));
        sp.setType(manager.getProceduresTypesBox().getSelectionModel().getSelectedItem());
        sp.setReceived((sp.getPrice() / 100) * sp.getType().getPorcent());
        if (SessionMode.getValue()) {
            sp.setId(new Date().hashCode());
            sp.setRegisteredDate(new Date());
        }
        return sp;
    }
    
    public void defineReceivedTotalValue() {
        double value = 0;
        List<ServiceProcedureModel> spList = manager.getServiceProceduresTable().getItems();
        for (ServiceProcedureModel serviceProcedureModel : spList) {
            value += serviceProcedureModel.getReceived();
        }
        manager.setTotalReceivedLabel(NumberFormat.getCurrencyInstance().format(value));
    }

    private static String formaterName(String givenString) {
        String[] tokens = givenString.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String t : tokens) {
            sb.append(Character.toUpperCase(t.charAt(0))).append(t.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }

}
