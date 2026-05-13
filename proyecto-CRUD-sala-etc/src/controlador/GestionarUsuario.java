package controlador;

import DAO.*;
import clases.*;

public class GestionarUsuario {
	
	// Socio FULL
	public static boolean insertarSocioFull(SocioFull s) {
		return InsertarUsuarioDAO.insertarSocioFull(s);
	}
	
	// Socio Flexible
	public static boolean insertarSocioFlexible(SocioFlexible s) {
		return InsertarUsuarioDAO.insertarSocioFlexible(s);
	}
	
	// Entrenador
	public static boolean insertarEntrenador(Entrenador e) {
		return InsertarUsuarioDAO.insertarEntrenador(e);
	}
	
	// Administrador
	public static boolean insertarAdministrador(Administrador a) {
		return InsertarUsuarioDAO.insertarUsuarioBasico(a, "Administrador");
	}
	
	// Recepcionista
	public static boolean insertarRecepcionista(Recepcionista r) {
		return InsertarUsuarioDAO.insertarUsuarioBasico(r, "Recepcionista");
	}

	public static boolean eliminarUsuario(Usuario u) {
		return EliminarUsuarioDAO.eliminarUsuarioBD(u);
	}
	
	public static Object[][] mostrarUsuarios() {
		return MostrarUsuariosDAO.obtenerUsuarios();
	}

	public static boolean actualizarUsuario(String dni, String nombre, String apellidos, String telefono, String email,
			String fechaAlta, String estado, String contrasena) {
		return ActualizarUsuarioDAO.actualizarUsuario(dni, nombre, apellidos, telefono, email, fechaAlta, estado, contrasena);
	}
}