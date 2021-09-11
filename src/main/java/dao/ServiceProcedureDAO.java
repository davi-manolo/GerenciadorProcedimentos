package dao;

import java.util.List;
import model.ServiceProcedureModel;

public interface ServiceProcedureDAO {
    
    public boolean addNewServiceProcedure(ServiceProcedureModel procedureModel);
    
    public boolean removeServiceProcedure(List<ServiceProcedureModel> proceduresModel);
    
    public boolean updateServiceProcedure(ServiceProcedureModel procedureModel);
    
    public ServiceProcedureModel getServiceProcedureById(int id);
    
    public List<ServiceProcedureModel> getServiceProceduresByPeriod(String yearMonth);
    
    public List<ServiceProcedureModel> getServiceProcedures();
    
}
