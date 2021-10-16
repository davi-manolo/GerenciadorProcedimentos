package mockup.dao;

import dao.ProcedureTypeDAO;
import mockup.database.ProcedureManagerMockupDB;
import java.util.List;
import javafx.collections.ObservableList;
import model.ProcedureTypeModel;

public class ProcedureTypeMockupImp implements ProcedureTypeDAO {
    
    private ObservableList<ProcedureTypeModel> list = ProcedureManagerMockupDB.getTypeModelList();

    @Override
    public boolean addNewType(ProcedureTypeModel typeModel) {
        if(typeModel != null) {
            return list.add(typeModel);
        }
        return false;
    }

    @Override
    public boolean removeType(List<ProcedureTypeModel> typeModel) {
        return list.removeAll(typeModel);
    }

    @Override
    public boolean updateType(ProcedureTypeModel typeModel) {
        boolean result = false;
        for(ProcedureTypeModel type: list) {
            if(type.getId() == typeModel.getId()) {
                type.setName(typeModel.getName());
                type.setPorcent(typeModel.getPorcent());
                result = true;
            }
        }
        return result;
    }

    @Override
    public ProcedureTypeModel getTypeById(int id) {
        ProcedureTypeModel typeRequest = null;
        for(ProcedureTypeModel type: list) {
            if(type.getId() == id) {
                typeRequest = type;
            }
        }
        return typeRequest;
    }

    @Override
    public List<ProcedureTypeModel> getTypes() {
        return list;
    }
    
}
