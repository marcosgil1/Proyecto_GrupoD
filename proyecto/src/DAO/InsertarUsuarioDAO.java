package DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextField;

import clases.Socio;
import clases.Usuario;

public class InsertarUsuarioDAO {

	public static void insertarUsuarioBD(Usuario u, String rol) {

		try {
			Connection con = Conexion.obtenerConexion();

			String sqlUsuario = "INSERT INTO usuario (dni,nombre,apellidos,telefono,email,fecha_alta,estado,contrasena) VALUES "
					+ "(\"" + u.getDni() + "\",\"" + u.getNombre() + "\",\"" + u.getApellidos() + "\","
					+ u.getTelefono() + ",\"" + u.getEmail() + "\",\"" + u.getFechaAlta() + "\",\"" + u.getEstado()
					+ "\",\"" + u.getContrasena() + "\");";

			Statement sta = con.createStatement();

			sta.execute(sqlUsuario);

			switch (rol) {

			case "Socio":
				
				String sqlSocio = "insert into socio	 (dni_usuario) values (\"" + u.getDni() + "\");";
				sta.execute(sqlSocio);

				break;

			case "Entrenador":
				String sqlEntrenador = "insert into entrenador (dni_usuario,tipo) values (\"" + u.getDni()
						+ "\",\"null\");";
				sta.execute(sqlEntrenador);

				break;

			case "Administrador":
				String sqlAdministrador = "insert into administrador (dni_usuario) values (\"" + u.getDni() + "\");";
				sta.execute(sqlAdministrador);

				break;

			case "Recepcionista":
				u.getDni();
				String sqlRecepcionista = "insert into recepcionista	 (dni_usuario) values (\"" + u.getDni()
						+ "\");";
				sta.execute(sqlRecepcionista);

				break;
			}

			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
