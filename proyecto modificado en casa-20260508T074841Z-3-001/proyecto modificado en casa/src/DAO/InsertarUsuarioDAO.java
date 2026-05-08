package DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextField;

import clases.Socio;
import clases.Usuario;

public class InsertarUsuarioDAO {

	public static void insertarUsuarioBD(Usuario s) {

		try {
			Connection con = Conexion.obtenerConexion();

			String sql = "INSERT INTO usuario (dni,nombre,apellidos,telefono,email,fecha_alta,estado,contrasena) VALUES "
					+ "(\"" + s.getDni() + "\",\"" + s.getNombre() + "\",\"" + s.getApellidos() + "\","
					+ s.getTelefono() + ",\"" + s.getEmail() + "\",\"" + s.getFechaAlta() + "\",\"" + s.getEstado()
					+ "\",\"" + s.getContrasena() + "\");";

			Statement sta = con.createStatement();

			sta.execute(sql);

			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
