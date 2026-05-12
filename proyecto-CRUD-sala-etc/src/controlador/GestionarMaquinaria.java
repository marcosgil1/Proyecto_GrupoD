package controlador;

import java.util.List;
import clases.Maquinaria;
import DAO.MaquinariaDAO;

public class GestionarMaquinaria {
	
	///
	/// MAQUINARIA
	/// 
	
	// Insertar Maquinaria
	public static boolean insertarMaquinaria(Maquinaria m) {
		return MaquinariaDAO.insertar(m);
	}
	
	// Mostrar todas las Maquinarias (para la tabla)
	public static Object[][] mostrarMaquinaria() {
		return MaquinariaDAO.obtenerDatosTabla();
	}
	
	// Obtener todas las Maquinarias (para combos, etc.)
	public static List<Maquinaria> obtenerTodasMaquinaria() {
		return MaquinariaDAO.obtenerTodas();
	}
	
	// Obtener Maquinaria por ID
	public static Maquinaria obtenerMaquinariaPorId(int id) {
		return MaquinariaDAO.obtenerPorId(id);
	}
	
	// Actualizar Maquinaria
	public static boolean actualizarMaquinaria(Maquinaria m) {
		return MaquinariaDAO.actualizar(m);
	}
	
	// Eliminar Maquinaria
	public static boolean eliminarMaquinaria(int id) {
		return MaquinariaDAO.eliminar(id);
	}
	
	// Obtener salas para el comboBox
	public static Object[][] obtenerSalasParaCombo() {
		return MaquinariaDAO.obtenerSalasParaCombo();
	}
}