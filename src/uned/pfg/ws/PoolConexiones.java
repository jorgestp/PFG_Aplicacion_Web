package uned.pfg.ws;



import org.apache.commons.dbcp.BasicDataSource;



public class PoolConexiones {

	
    
    private static BasicDataSource basicDataSource;

    private static  PoolConexiones instance;
    

    private PoolConexiones(){
        
        Conection();
    }
    
    /*
    Parte que implementa el patron Singleton
    */
    public static PoolConexiones getInstance(){
        
        if(instance == null){
            
             instance = new PoolConexiones();
            
        }
        
        return instance;
    }
    
    /*
    Almacena en el campo de clase Conection la conexion con la base de datos
    */
    private void Conection(){
        
    		
    		basicDataSource = new BasicDataSource();
    		basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
    		basicDataSource.setUsername("root");
    		basicDataSource.setPassword("");
    		basicDataSource.setUrl("jdbc:mysql://localhost:3306/prueba_pfg");
    		basicDataSource.setMaxActive(10);//1000
    		basicDataSource.setValidationQuery("select 1");
    }
    
    public BasicDataSource getConnection(){
        
        return basicDataSource;
    }
}
