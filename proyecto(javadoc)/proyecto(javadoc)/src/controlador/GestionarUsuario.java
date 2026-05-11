package controlador;

import java.awt.event.ActionEvent;

import DAO.ActualizarUsuarioDAO;
import DAO.EliminarUsuarioDAO;
import DAO.InsertarUsuarioDAO;
import DAO.MostrarUsuariosDAO;
import clases.Socio;
import clases.Usuario;

public class GestionarUsuario {
	
	///
	/// USUARIOS
	/// 

	//Insertar Segun su ROL
	
	public static void insertarUsuario(Usuario u,String rol) {
		
		InsertarUsuarioDAO.insertarUsuarioBD(u,rol);
	}

	//Eliminar
	
	public static boolean eliminarUsuario(Usuario u) {

		return EliminarUsuarioDAO.eliminarUsuarioBD(u);
	}
	
	//Mostrar
	
	public static Object[][] mostrarUsuarios() {

		return MostrarUsuariosDAO.obtenerUsuarios();
	}

	//Actualizar
	
	public static boolean actualizarUsuario(String dni, String nombre, String apellidos, String telefono, String email,
			String fechaAlta, String estado, String contrasena) {

		return ActualizarUsuarioDAO.actualizarUsuario(dni, nombre, apellidos, telefono, email, fechaAlta, estado,
				contrasena);
	}

	
	
	
	
	
}