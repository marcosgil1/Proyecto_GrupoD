package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import clases.*;

public class InsertarUsuarioDAO {

	// Método genérico para insertar usuario base
	private static boolean insertarUsuarioBase(Usuario u) throws SQLException {
		Connection con = Conexion.obtenerConexion();
		String sql = "INSERT INTO usuario (dni, nombre, apellidos, telefono, email, fecha_alta, estado, contrasena) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, u.getDni());
		ps.setString(2, u.getNombre());
		ps.setString(3, u.getApellidos());
		ps.setString(4, u.getTelefono());
		ps.setString(5, u.getEmail());
		ps.setString(6, u.getFechaAlta());
		ps.setString(7, u.getEstado());
		ps.setString(8, u.getContrasena());
		
		int resultado = ps.executeUpdate();
		ps.close();
		con.close();
		return resultado > 0;
	}
	
	// Socio Full
	public static boolean insertarSocioFull(SocioFull s) {
		try {
			if (!insertarUsuarioBase(s)) return false;
			
			Connection con = Conexion.obtenerConexion();
			
			// Insertar en socio
			String sqlSocio = "INSERT INTO socio (dni_usuario) VALUES (?)";
			PreparedStatement psSocio = con.prepareStatement(sqlSocio);
			psSocio.setString(1, s.getDni());
			psSocio.executeUpdate();
			psSocio.close();
			
			// Insertar en full
			String sqlFull = "INSERT INTO full (dni_socio, cuota_mensual) VALUES (?, ?)";
			PreparedStatement psFull = con.prepareStatement(sqlFull);
			psFull.setString(1, s.getDni());
			psFull.setDouble(2, s.getCuotaMensual());
			psFull.executeUpdate();
			psFull.close();
			
			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// Socio Flexible
	public static boolean insertarSocioFlexible(SocioFlexible s) {
		try {
			if (!insertarUsuarioBase(s)) return false;
			
			Connection con = Conexion.obtenerConexion();
			
			String sqlSocio = "INSERT INTO socio (dni_usuario) VALUES (?)";
			PreparedStatement psSocio = con.prepareStatement(sqlSocio);
			psSocio.setString(1, s.getDni());
			psSocio.executeUpdate();
			psSocio.close();
			
			String sqlFlexible = "INSERT INTO flexible (dni_socio, precio_por_actividad) VALUES (?, ?)";
			PreparedStatement psFlex = con.prepareStatement(sqlFlexible);
			psFlex.setString(1, s.getDni());
			psFlex.setDouble(2, s.getPrecioPorActividad());
			psFlex.executeUpdate();
			psFlex.close();
			
			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// Entrenador
	public static boolean insertarEntrenador(Entrenador e) {
		try {
			if (!insertarUsuarioBase(e)) return false;
			
			Connection con = Conexion.obtenerConexion();
			
			String sql = "INSERT INTO entrenador (dni_usuario, tipo) VALUES (?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, e.getDni());
			ps.setString(2, e.getTipo());
			ps.executeUpdate();
			ps.close();
			
			con.close();
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	// Usuario básico (Admin, Recepcionista)
	public static boolean insertarUsuarioBasico(Usuario u, String tabla) {
		try {
			if (!insertarUsuarioBase(u)) return false;
			
			Connection con = Conexion.obtenerConexion();
			
			String sql = "INSERT INTO " + tabla + " (dni_usuario) VALUES (?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, u.getDni());
			ps.executeUpdate();
			ps.close();
			
			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}