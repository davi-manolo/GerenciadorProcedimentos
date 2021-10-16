package service;

import view.ManagerTypeDialog;
import utils.SessionMode;
import model.ProcedureTypeModel;

public class ProcedureTypeService {
    
    private ProcedureTypeModel type = new ProcedureTypeModel();
    private ManagerTypeDialog manager;
    
    public ProcedureTypeService(ManagerTypeDialog manager) {
        this.manager = manager;
    }
    
    public ProcedureTypeModel createProcedureType() {
        type.setName(manager.getProcedureField().getText());
        type.setPorcent(Double.valueOf(manager.getPorcentField().getText().replace(",", ".")));
        if (SessionMode.getValue()) {
            type.setId(type.getName().hashCode());
        }
        return type;
    }
    
}
