package mockup.dao;

import dao.ServiceProcedureDAO;
import mockup.database.ProcedureManagerMockupDB;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import model.ServiceProcedureModel;

public class ServiceProcedureMockupImpl implements ServiceProcedureDAO {
    
    private ObservableList<ServiceProcedureModel> list = ProcedureManagerMockupDB.getServiceProcedureModelList();

    @Override
    public boolean addNewServiceProcedure(ServiceProcedureModel procedureModel) {
        if(procedureModel != null) {
            return list.add(procedureModel);
        }
        return false;
    }

    @Override
    public boolean removeServiceProcedure(List<ServiceProcedureModel> proceduresModel) {
        return list.removeAll(proceduresModel);
    }

    @Override
    public boolean updateServiceProcedure(ServiceProcedureModel procedureModel) {
        boolean result = false;
        for(ServiceProcedureModel serviceProcedure: list) {
            if(serviceProcedure.getId() == procedureModel.getId()) {
                serviceProcedure.setPerformedIn(procedureModel.getPerformedIn());
                serviceProcedure.setClient(procedureModel.getClient());
                serviceProcedure.setPrice(procedureModel.getPrice());
                serviceProcedure.setType(procedureModel.getType());
                serviceProcedure.setReceived(procedureModel.getReceived());
                serviceProcedure.setRegisteredDate(procedureModel.getRegisteredDate());
                result = true;
            }
        }
        return result;
    }

    @Override
    public ServiceProcedureModel getServiceProcedureById(int id) {
        ServiceProcedureModel serviceProcedureRequest = null;
        for(ServiceProcedureModel serviceProcedure: list) {
            if(serviceProcedure.getId() == id) {
                serviceProcedureRequest = serviceProcedure;
            }
        }
        return serviceProcedureRequest;
    }

    @Override
    public List<ServiceProcedureModel> getServiceProceduresByPeriod(String yearMonth) {
        
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM");
        List<ServiceProcedureModel> listByPeriod = new ArrayList<>();
        for (ServiceProcedureModel serviceProcedure : list) {
            String yearMonthRequest = outputFormat.format(serviceProcedure.getRegisteredDate());
            if(yearMonth.equals(yearMonthRequest)) {
                listByPeriod.add(serviceProcedure);
            }
        }
        return listByPeriod;
    }

    @Override
    public List<ServiceProcedureModel> getServiceProcedures() {
        return list;
    }
    
}
