package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ActualizarUsuarioDAO {

	public static boolean actualizarUsuario(String dni, String nombre, String apellidos, String telefono, String email,
			String fechaAlta, String estado, String contrasena) {

		try {

			Connection con = Conexion.obtenerConexion();

			String sql = "UPDATE usuario SET nombre = ?, apellidos = ?, telefono = ?, email = ?, fecha_alta = ?, estado = ?, contrasena = ? WHERE dni = ?";
			
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, nombre);
			ps.setString(2, apellidos);
			ps.setString(3, telefono);
			ps.setString(4, email);
			ps.setString(5, fechaAlta);
			ps.setString(6, estado);
			ps.setString(7, contrasena);
			ps.setString(8, dni);

			int filasAfectadas = ps.executeUpdate();

			ps.close();
			con.close();

			return filasAfectadas > 0;

		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
	}
}