package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String USUARIO = "bd";
    private static final String CLAVE = "bd";
    private static final String SERVIDOR = "192.168.4.204";
    private static final String PUERTO = "3306";
    private static final String BASE_DATOS = "gimnasioGrupoD";

    private static final String URL =
            "jdbc:mysql://" + SERVIDOR + ":" + PUERTO + "/" + BASE_DATOS;

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }

    
}