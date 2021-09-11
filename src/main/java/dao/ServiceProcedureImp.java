package dao;

import database.ProcedureManagerDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.ProcedureTypeModel;
import model.ServiceProcedureModel;

public class ServiceProcedureImp implements ServiceProcedureDAO {

    private static final Connection conn = ProcedureManagerDB.getConnection();

    @Override
    public boolean addNewServiceProcedure(ServiceProcedureModel procedureModel) {
        String sql = "insert into procedures_db.service_procedure(performedIn, client, price, typeId, received) values(?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(procedureModel.getPerformedIn().getTime()));
            stmt.setString(2, procedureModel.getClient());
            stmt.setDouble(3, procedureModel.getPrice());
            stmt.setInt(4, procedureModel.getType().getId());
            stmt.setDouble(5, procedureModel.getReceived());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar um novo Serviço de Procedimento: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean removeServiceProcedure(List<ServiceProcedureModel> proceduresModel) {
        String sql = "delete from procedures_db.service_procedure where id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (ServiceProcedureModel procedureModel : proceduresModel) {
                stmt.setInt(1, procedureModel.getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            System.out.println("Erro ao remover o/os Serviço(s) de Procedimento(s): " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean updateServiceProcedure(ServiceProcedureModel procedureModel) {
        String sql = "update procedures_db.service_procedure set performedIn = ?, client = ?, price = ?, typeId = ?, received = ? where id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(procedureModel.getPerformedIn().getTime()));
            stmt.setString(2, procedureModel.getClient());
            stmt.setDouble(3, procedureModel.getPrice());
            stmt.setInt(4, procedureModel.getType().getId());
            stmt.setDouble(5, procedureModel.getReceived());
            stmt.setInt(6, procedureModel.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o Serviço de Procedimento: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public ServiceProcedureModel getServiceProcedureById(int id) {
        String sql = "select * from procedures_db.service_procedure where id = ?";
        ServiceProcedureModel serviceProcedure = null;
        ProcedureTypeModel type = null;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ProcedureTypeDAO t = new ProcedureTypeImp();
                type = t.getTypeById(rs.getInt("typeId"));
                serviceProcedure = new ServiceProcedureModel();
                serviceProcedure.setId(rs.getInt("id"));
                serviceProcedure.setPerformedIn(rs.getDate("performedIn"));
                serviceProcedure.setClient(rs.getString("client"));
                serviceProcedure.setPrice(rs.getDouble("price"));
                serviceProcedure.setType(type);
                serviceProcedure.setReceived(rs.getDouble("received"));
                serviceProcedure.setRegisteredDate(rs.getDate("registered_date"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar o Serviço de Procedimento: " + e.getMessage());
        } finally {
            if (serviceProcedure == null) {
                System.out.println("Nenhum Serviço de Procedimento encontrado para o ID " + id);
            }
        }
        return serviceProcedure;
    }

    @Override
    public List<ServiceProcedureModel> getServiceProcedures() {
        List<ServiceProcedureModel> list = new ArrayList<>();
        String sql = "select * from procedures_db.service_procedure";
        ServiceProcedureModel serviceProcedure = null;
        ProcedureTypeModel type = null;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ProcedureTypeDAO t = new ProcedureTypeImp();
                type = t.getTypeById(rs.getInt("typeId"));
                serviceProcedure = new ServiceProcedureModel();
                serviceProcedure.setId(rs.getInt("id"));
                serviceProcedure.setPerformedIn(rs.getDate("performedIn"));
                serviceProcedure.setClient(rs.getString("client"));
                serviceProcedure.setPrice(rs.getDouble("price"));
                serviceProcedure.setType(type);
                serviceProcedure.setReceived(rs.getDouble("received"));
                serviceProcedure.setRegisteredDate(rs.getDate("registered_date"));
                list.add(serviceProcedure);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar Serviços de Procedimentos realizados: " + e.getMessage());
        } finally {
            if (list.isEmpty()) {
                System.out.println("Nenhum Serviço de Procedimento retornado do Banco de Dados.");
            }
        }
        return list;
    }
    
    @Override
    public List<ServiceProcedureModel> getServiceProceduresByPeriod(String yearMonth) {
        List<ServiceProcedureModel> list = new ArrayList<>();
        String sql = "select * from procedures_db.service_procedure where registered_date like ?"; //2021-08%
        ServiceProcedureModel serviceProcedure = null;
        ProcedureTypeModel type = null;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, yearMonth + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ProcedureTypeDAO t = new ProcedureTypeImp();
                type = t.getTypeById(rs.getInt("typeId"));
                serviceProcedure = new ServiceProcedureModel();
                serviceProcedure.setId(rs.getInt("id"));
                serviceProcedure.setPerformedIn(rs.getDate("performedIn"));
                serviceProcedure.setClient(rs.getString("client"));
                serviceProcedure.setPrice(rs.getDouble("price"));
                serviceProcedure.setType(type);
                serviceProcedure.setReceived(rs.getDouble("received"));
                serviceProcedure.setRegisteredDate(rs.getDate("registered_date"));
                list.add(serviceProcedure);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar Serviços de Procedimentos realizados neste período: " + e.getMessage());
        } finally {
            if (list.isEmpty()) {
                System.out.println("Nenhum Serviço de Procedimento retornado do Banco de Dados neste período.");
            }
        }
        return list;
    }

}
