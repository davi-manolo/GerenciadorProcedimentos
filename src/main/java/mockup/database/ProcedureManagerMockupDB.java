package mockup.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ProcedureTypeModel;
import model.ServiceProcedureModel;
import mockup.InsertDataMockup;

public abstract class ProcedureManagerMockupDB {
    
    static {
        System.out.println("Aplicação executada em Sessão de Testes!");
    }
    
    private static ObservableList<ProcedureTypeModel> typeModelList = FXCollections.observableArrayList();
    private static ObservableList<ServiceProcedureModel> serviceProcedureModelList = FXCollections.observableArrayList();
    @SuppressWarnings("unused")
    private static InsertDataMockup inserts = new InsertDataMockup();

    public static ObservableList<ServiceProcedureModel> getServiceProcedureModelList() {
        return serviceProcedureModelList;
    }

    public static ObservableList<ProcedureTypeModel> getTypeModelList() {
        return typeModelList;
    }
    
}
