package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MostrarUsuariosDAO {

	public static Object[][] obtenerUsuarios() {

		ArrayList<Object[]> lista = new ArrayList<>();

		String sql = "SELECT * FROM usuario";

		try (Connection con = Conexion.obtenerConexion();
				Statement sta = con.createStatement();
				ResultSet rs = sta.executeQuery(sql)) {

			while (rs.next()) {

				Object[] fila = new Object[] { rs.getString("dni"), rs.getString("nombre"), rs.getString("apellidos"),
						rs.getString("telefono"), rs.getString("email"), rs.getString("fecha_Alta"),
						rs.getString("estado"), rs.getString("contrasena") };

				lista.add(fila);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista.toArray(new Object[0][]);
	}
}