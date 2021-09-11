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

public class ProcedureTypeImp implements ProcedureTypeDAO {

    private static final Connection conn = ProcedureManagerDB.getConnection();

    @Override
    public boolean addNewType(ProcedureTypeModel typeModel) {
        String sql = "insert into procedures_db.procedures_type(name, porcent) values(?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, typeModel.getName());
            stmt.setDouble(2, typeModel.getPorcent());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar um novo tipo de Procedimento: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean removeType(List<ProcedureTypeModel> typesModel) {
        String sql = "delete from procedures_db.procedures_type where id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (ProcedureTypeModel typeModel : typesModel) {
                stmt.setInt(1, typeModel.getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            System.out.println("Erro ao remover o/os Tipo(s) de Procedimento(s): " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean updateType(ProcedureTypeModel typeModel) {
        String sql = "update procedures_db.procedures_type set name = ?, porcent = ? where id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, typeModel.getName());
            stmt.setDouble(2, typeModel.getPorcent());
            stmt.setInt(3, typeModel.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o Procedimento: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public ProcedureTypeModel getTypeById(int id) {
        String sql = "select * from procedures_db.procedures_type where id = ?";
        ProcedureTypeModel requestType = null;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                requestType = new ProcedureTypeModel();
                requestType.setId(rs.getInt("id"));
                requestType.setName(rs.getString("name"));
                requestType.setPorcent(rs.getDouble("porcent"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar o tipo de Procedimento: " + e.getMessage());
        } finally {
            if(requestType == null) {
                System.out.println("Nenhum tipo de Procedimento encontrado para o ID " + id);
            }
        }
        return requestType;
    }

    @Override
    public List<ProcedureTypeModel> getTypes() {
        List<ProcedureTypeModel> list = new ArrayList<>();
        String sql = "select * from procedures_db.procedures_type";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new ProcedureTypeModel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("porcent")));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar tipos de Procedimentos: " + e.getMessage());
        } finally {
            if(list.isEmpty()) {
                System.out.println("Nenhum tipo de Procedimento retornado do Banco de Dados.");
            }
        }
        return list;
    }

}
