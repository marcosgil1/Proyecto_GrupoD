package controlador;

import java.util.List;
import clases.Actividad;
import DAO.ActividadDAO;

public class GestionarActividad {
	
	///
	/// ACTIVIDADES
	/// 
	
	// Insertar Actividad
	public static boolean insertarActividad(Actividad actividad) {
		return ActividadDAO.insertar(actividad);
	}
	
	// Mostrar todas las Actividades (para la tabla)
	public static Object[][] mostrarActividades() {
		return ActividadDAO.obtenerDatosTabla();
	}
	
	// Obtener todas las Actividades (para combos, etc.)
	public static List<Actividad> obtenerTodasActividades() {
		return ActividadDAO.obtenerTodas();
	}
	
	// Obtener Actividad por ID
	public static Actividad obtenerActividadPorId(int id) {
		return ActividadDAO.obtenerPorId(id);
	}
	
	// Actualizar Actividad
	public static boolean actualizarActividad(Actividad actividad) {
		return ActividadDAO.actualizar(actividad);
	}
	
	// Eliminar Actividad
	public static boolean eliminarActividad(int id) {
		return ActividadDAO.eliminar(id);
	}
	
	// Obtener actividades para comboBox (muestra "ID - Nombre")
	public static Object[][] obtenerActividadesParaCombo() {
		return ActividadDAO.obtenerActividadesParaCombo();
	}
}