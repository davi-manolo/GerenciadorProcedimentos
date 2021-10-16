package controller;

import dao.ProcedureTypeDAO;
import dao.ProcedureTypeImp;
import java.util.List;
import utils.SessionMode;
import model.ProcedureTypeModel;
import service.ProcedureTypeService;
import mockup.dao.ProcedureTypeMockupImp;
import view.ManagerTypeDialog;

public class ManagerTypeController {
    
    private ProcedureTypeDAO procedureType;
    private ProcedureTypeService service;
    private ManagerTypeDialog manager;
    
    public ManagerTypeController(ManagerTypeDialog manager) {
        this.manager = manager;
        service = new ProcedureTypeService(manager);
        procedureType = SessionMode.getValue() ?
                new ProcedureTypeMockupImp() : new ProcedureTypeImp();    
    }
    
    public boolean updateProcedereType(int id) {
        ProcedureTypeModel spEdited = service.createProcedureType();
        spEdited.setId(id);
        return procedureType.updateType(spEdited);
    }
    
    public boolean addNewType() {
        return procedureType.addNewType(service.createProcedureType());
    }
    
    public boolean removeType() {
        return procedureType.removeType(manager.getProcedureTypeTable()
                .getSelectionModel().getSelectedItems());
    }
    
    public List<ProcedureTypeModel> getTypes() {
        return procedureType.getTypes();
    }
    
}
