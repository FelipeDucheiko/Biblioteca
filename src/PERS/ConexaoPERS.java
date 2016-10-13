package PERS;
import java.sql.*; 

public class ConexaoPERS {
    
    public Connection con = null;
    
    public Connection conectar(){
        try
        {
            
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection( "jdbc:postgresql://localhost:5432/Biblioteca", "postgres", "123321");
            //con = DriverManager.getConnection( "jdbc:postgresql://localhost:5432/", "felipe", "");
            
        }catch (ClassNotFoundException e) { 
        	System.out.println("Classe nao encontrada!");
        
        }catch (SQLException e) { 
        	System.out.println("Erro ao conectar o banco: " + e.getMessage());
        }
        return con;
    }
    
    public Connection desconectar() {
	 
    	try { 
    		con.close(); 
    	} 
    
    	catch(SQLException e) { 
    		System.out.println("Erro ao desconectar o banco: " + e.getMessage()); 
    	} 
    	return con; 
	}	
}
