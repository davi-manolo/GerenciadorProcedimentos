package mockup;

import dao.ProcedureTypeDAO;
import dao.ServiceProcedureDAO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ProcedureTypeModel;
import model.ServiceProcedureModel;
import mockup.dao.ProcedureTypeMockupImp;
import mockup.dao.ServiceProcedureMockupImpl;

public class InsertDataMockup {

    public InsertDataMockup() {

        ProcedureTypeDAO type = new ProcedureTypeMockupImp();
        ServiceProcedureDAO sp = new ServiceProcedureMockupImpl();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        
        try {
            ProcedureTypeModel typeModel01 = new ProcedureTypeModel();
            typeModel01.setName("Procedimento 1");
            typeModel01.setPorcent(10.00);
            typeModel01.setId(typeModel01.getName().hashCode());
            type.addNewType(typeModel01);
            
            ProcedureTypeModel typeModel02 = new ProcedureTypeModel();
            typeModel02.setName("Procedimento 2");
            typeModel02.setPorcent(20.00);
            typeModel02.setId(typeModel02.getName().hashCode());
            type.addNewType(typeModel02);
            
            ProcedureTypeModel typeModel03 = new ProcedureTypeModel();
            typeModel03.setName("Procedimento 3");
            typeModel03.setPorcent(30.00);
            typeModel03.setId(typeModel03.getName().hashCode());
            type.addNewType(typeModel03);
            
            ProcedureTypeModel typeModel04 = new ProcedureTypeModel();
            typeModel04.setName("Procedimento 4");
            typeModel04.setPorcent(40.00);
            typeModel04.setId(typeModel04.getName().hashCode());
            type.addNewType(typeModel04);
            
            ProcedureTypeModel typeModel05 = new ProcedureTypeModel();
            typeModel05.setName("Procedimento 5");
            typeModel05.setPorcent(50.00);
            typeModel05.setId(typeModel05.getName().hashCode());
            type.addNewType(typeModel05);
            
            ProcedureTypeModel typeModel06 = new ProcedureTypeModel();
            typeModel06.setName("Procedimento 6");
            typeModel06.setPorcent(100.00);
            typeModel06.setId(typeModel06.getName().hashCode());
            type.addNewType(typeModel06);
            
            ServiceProcedureModel spModel = new ServiceProcedureModel();
            spModel.setPerformedIn(format.parse("22/09/2021"));
            spModel.setClient("João");
            spModel.setPrice(640.00);
            spModel.setType(typeModel06);
            spModel.setReceived((spModel.getPrice() / 100) * spModel.getType().getPorcent());
            spModel.setId(spModel.getClient().hashCode());
            spModel.setRegisteredDate(new Date());
            sp.addNewServiceProcedure(spModel);
            
            spModel = new ServiceProcedureModel();
            spModel.setPerformedIn(format.parse("04/09/2021"));
            spModel.setClient("Maria");
            spModel.setPrice(1200.00);
            spModel.setType(typeModel03);
            spModel.setReceived((spModel.getPrice() / 100) * spModel.getType().getPorcent());
            spModel.setId(spModel.getClient().hashCode());
            spModel.setRegisteredDate(new Date());
            sp.addNewServiceProcedure(spModel);
            
            spModel = new ServiceProcedureModel();
            spModel.setPerformedIn(format.parse("01/09/2021"));
            spModel.setClient("Pedro");
            spModel.setPrice(300.00);
            spModel.setType(typeModel01);
            spModel.setReceived((spModel.getPrice() / 100) * spModel.getType().getPorcent());
            spModel.setId(spModel.getClient().hashCode());
            spModel.setRegisteredDate(new Date());
            sp.addNewServiceProcedure(spModel);
            
            spModel = new ServiceProcedureModel();
            spModel.setPerformedIn(format.parse("18/09/2021"));
            spModel.setClient("Ana");
            spModel.setPrice(3150.20);
            spModel.setType(typeModel04);
            spModel.setReceived((spModel.getPrice() / 100) * spModel.getType().getPorcent());
            spModel.setId(spModel.getClient().hashCode());
            spModel.setRegisteredDate(new Date());
            sp.addNewServiceProcedure(spModel);
            
            spModel = new ServiceProcedureModel();
            spModel.setPerformedIn(format.parse("13/09/2021"));
            spModel.setClient("Paula");
            spModel.setPrice(1000);
            spModel.setType(typeModel04);
            spModel.setReceived((spModel.getPrice() / 100) * spModel.getType().getPorcent());
            spModel.setId(spModel.getClient().hashCode());
            spModel.setRegisteredDate(new Date());
            sp.addNewServiceProcedure(spModel);
            
            spModel = new ServiceProcedureModel();
            spModel.setPerformedIn(format.parse("05/08/2021"));
            spModel.setClient("Mathues");
            spModel.setPrice(1000);
            spModel.setType(typeModel05);
            spModel.setReceived((spModel.getPrice() / 100) * spModel.getType().getPorcent());
            spModel.setId(spModel.getClient().hashCode());
            spModel.setRegisteredDate(format.parse("05/08/2021"));
            sp.addNewServiceProcedure(spModel);
            
            spModel = new ServiceProcedureModel();
            spModel.setPerformedIn(format.parse("30/08/2021"));
            spModel.setClient("Thiago");
            spModel.setPrice(3810.16);
            spModel.setType(typeModel02);
            spModel.setReceived((spModel.getPrice() / 100) * spModel.getType().getPorcent());
            spModel.setId(spModel.getClient().hashCode());
            spModel.setRegisteredDate(format.parse("30/08/2021"));
            sp.addNewServiceProcedure(spModel);
            
        } catch (ParseException ex) {
            Logger.getLogger(InsertDataMockup.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
