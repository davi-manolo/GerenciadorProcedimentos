package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class ProcedureManagerDB {
    
    private static Class<?> driver;
    private static Connection conn;
    
    static {
        try {
            driver = Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver padrão do Banco de Dados carregado: " + driver.getName());
        } catch (ClassNotFoundException e) {
            System.out.println("Driver do Banco de Dados não carregado: " + e.getMessage());
        }
    }
    
    public static boolean connect() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/procedures_db", "procedure_user", "procedure");
        } catch (SQLException e) {
            System.out.println("Acesso negado do Banco: " + e.getMessage());
            return false;
        }
        System.out.println("Conectado ao Banco de Dados!");
        return true;
    }
    
    public static boolean connect(String database, String user, String pwd) {
        try {
            conn = DriverManager.getConnection(database, user, pwd);
        } catch (SQLException e) {
            System.out.println("Acesso negado ao Banco, verifique a instância do banco, usuário ou senha: " + e.getMessage());
            return false;
        }
        System.out.println("Conectado ao Banco de Dados!");
        return true;
    }
    
    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("Conexão ao Banco de Dados foi fechada!");
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão ao Banco de Dados: " + e.getMessage());
        }
    }
    
    public static void setDriver(String fullyQualifiedName) {
        try {
            ProcedureManagerDB.driver = Class.forName(fullyQualifiedName);
            System.out.println("Novo driver do Banco de Dados carregado: " + driver.getName());
        } catch (ClassNotFoundException e) {
            System.out.println("Novo driver do Banco de Dados não encontrado: " + e.getMessage());
            closeConnection();
        }
    }

    public static Class<?> getDriver() {
        return driver;
    }
    
    public static Connection getConnection() {
        return conn;
    }
    
    //Usar na tela de conexões da aplicação, ainda não construída.
    //Ainda em desenvolvimento
    @SuppressWarnings("unused")
    @Deprecated
    private static boolean createDatabase() {
        String databaseSQL = "create database procedures_db;";
        String typeTableSQL = "create table procedures_db.procedures_type(" + "\n" +
                           "	id int not null auto_increment," + "\n" +
                           "	name varchar(30) not null," + "\n" +
                           "  	porcent decimal(5,2) not null," + "\n" +
                           "  	primary key(id)" + "\n" +
                           ");"; 
        String spTableSQL = "create table procedures_db.service_procedure(" + "\n" +
                           "	id int not null auto_increment," + "\n" +
                           "  	performedIn date not null," + "\n" +
                           "  	client varchar(45) not null," + "\n" +
                           "  	price decimal(6,2) not null," + "\n" +
                           "  	typeId int not null," + "\n" +
                           "  	received decimal(6,2) not null," + "\n" +
                           "  	registered_date timestamp not null default current_timestamp," + "\n" +
                           "  	primary key(id)," + "\n" +
                           "  	foreign key(typeId) references procedures_type(id)" + "\n" +
                           ");";
        try(Statement stmt = conn.createStatement()) {
            stmt.execute(databaseSQL);
            stmt.execute(typeTableSQL);
            stmt.execute(spTableSQL);
            System.out.println("Banco de Dados criado!");
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao criar o Banco de Dados: " + e.getMessage());
            return false;
        }
    }
    
}
