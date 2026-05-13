package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import clases.Usuario;

public class EliminarUsuarioDAO {

	public static boolean eliminarUsuarioBD(Usuario s) {

		Connection con = null;

		try {

			con = Conexion.obtenerConexion();
			
			// Primero eliminar de las tablas relacionadas (si existen)
			String[] tablasRelacionadas = {"socio", "entrenador", "administrador", "recepcionista"};
			
			for (String tabla : tablasRelacionadas) {
				String sqlDeleteRelacion = "DELETE FROM " + tabla + " WHERE dni_usuario = ?";
				PreparedStatement psRelacion = con.prepareStatement(sqlDeleteRelacion);
				psRelacion.setString(1, s.getDni());
				psRelacion.executeUpdate();
				psRelacion.close();
			}

			// BORRAR USUARIO
			String sqlUsuario = "DELETE FROM usuario WHERE dni = ?";

			PreparedStatement psUsuario = con.prepareStatement(sqlUsuario);
			psUsuario.setString(1, s.getDni());

			int filas = psUsuario.executeUpdate();

			con.close();

			return filas > 0;

		} catch (SQLException e) {

			e.printStackTrace();

			return false;
		}
	}
}