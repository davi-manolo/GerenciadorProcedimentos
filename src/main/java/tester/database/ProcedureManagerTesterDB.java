package tester.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ProcedureTypeModel;
import model.ServiceProcedureModel;
import tester.InsertDataTests;

public abstract class ProcedureManagerTesterDB {
    
    static {
        System.out.println("Aplicação executada em Sessão de Testes!");
    }
    
    private static ObservableList<ProcedureTypeModel> typeModelList = FXCollections.observableArrayList();
    private static ObservableList<ServiceProcedureModel> serviceProcedureModelList = FXCollections.observableArrayList();
    private static InsertDataTests inserts = new InsertDataTests();

    public static ObservableList<ServiceProcedureModel> getServiceProcedureModelList() {
        return serviceProcedureModelList;
    }

    public static ObservableList<ProcedureTypeModel> getTypeModelList() {
        return typeModelList;
    }
    
}
