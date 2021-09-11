package dao;

import java.util.List;
import model.ProcedureTypeModel;

public interface ProcedureTypeDAO {
    
    public boolean addNewType(ProcedureTypeModel typeModel);
    
    public boolean removeType(List<ProcedureTypeModel> typeModel);
    
    public boolean updateType(ProcedureTypeModel typeModel);
    
    public ProcedureTypeModel getTypeById(int id);
    
    public List<ProcedureTypeModel> getTypes();
    
}
