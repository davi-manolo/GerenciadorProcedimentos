package controller;

import dao.ProcedureTypeDAO;
import dao.ProcedureTypeImp;
import dao.ServiceProcedureDAO;
import dao.ServiceProcedureImp;
import database.ProcedureManagerDB;
import java.util.List;
import utils.SessionMode;
import model.ProcedureTypeModel;
import model.ServiceProcedureModel;
import service.ProcedureService;
import mockup.dao.ProcedureTypeMockupImp;
import mockup.dao.ServiceProcedureMockupImpl;
import view.ManagerDialog;

public class ServiceProcedureController {
    
    private ManagerDialog manager;
    private ProcedureTypeDAO procedureType;
    private ServiceProcedureDAO serviceProcedure;
    private ProcedureService service;
    
    public ServiceProcedureController(ManagerDialog manager) {
        service = new ProcedureService(manager);
        this.manager = manager;
    }
    
    public void startSession() {
        if (!SessionMode.getValue()) {
            manager.getTestText().setVisible(false);
            //ProcedureManagerDB.connect(); //Conexão default.
            ProcedureManagerDB.connect( //Conexão customizada (banco de dados, usuário e senha).
                    "jdbc:mysql://localhost:3306/procedures_db",
                    "procedure_user",
                    "procedure");
            procedureType = new ProcedureTypeImp();
            serviceProcedure = new ServiceProcedureImp();
            return;
        }
        manager.getTestText().setVisible(true);
        procedureType = new ProcedureTypeMockupImp();
        serviceProcedure = new ServiceProcedureMockupImpl();
    }
    
    public void defineReceivedTotalValue() {
        service.defineReceivedTotalValue();
    }

    public List<ProcedureTypeModel> getProcedureTypeList() {
        return procedureType.getTypes();
    }
    
    public boolean updateServiceProcedure(int id) {
        ServiceProcedureModel spEdited = service.createServiceProcedure(manager);
        spEdited.setId(id);
        return serviceProcedure.updateServiceProcedure(spEdited);
    }
    
    public boolean addNewServiceProcedure() {
        return serviceProcedure.addNewServiceProcedure(service.createServiceProcedure(manager));
    }
    
    public boolean removeServiceProcedure(List<ServiceProcedureModel> models) {
        return serviceProcedure.removeServiceProcedure(models);
    }
    
    public List<ServiceProcedureModel> getServiceProcedureModels(String yearMonth) {
        return serviceProcedure.getServiceProceduresByPeriod(yearMonth);
    }
    
    public void closeConnection() {
        if (!SessionMode.getValue()) {
            ProcedureManagerDB.closeConnection();
        }
    }
    
}
