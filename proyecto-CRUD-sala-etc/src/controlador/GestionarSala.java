package controlador;

import java.util.List;
import clases.Sala;
import DAO.SalaDAO;

public class GestionarSala {
	
	///
	/// SALAS
	/// 
	
	// Insertar Sala
	public static boolean insertarSala(Sala sala) {
		return SalaDAO.insertar(sala);
	}
	
	// Mostrar todas las Salas (para la tabla)
	public static Object[][] mostrarSalas() {
		return SalaDAO.obtenerDatosTabla();
	}
	
	// Obtener todas las Salas (para combos, etc.)
	public static List<Sala> obtenerTodasSalas() {
		return SalaDAO.obtenerTodas();
	}
	
	// Obtener Sala por ID
	public static Sala obtenerSalaPorId(int id) {
		return SalaDAO.obtenerPorId(id);
	}
	
	// Actualizar Sala
	public static boolean actualizarSala(Sala sala) {
		return SalaDAO.actualizar(sala);
	}
	
	// Eliminar Sala
	public static boolean eliminarSala(int id) {
		return SalaDAO.eliminar(id);
	}
}