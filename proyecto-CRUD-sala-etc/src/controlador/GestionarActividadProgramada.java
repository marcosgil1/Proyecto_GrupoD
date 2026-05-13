package controlador;

import java.util.List;
import clases.ActividadProgramada;
import DAO.ActividadProgramadaDAO;

public class GestionarActividadProgramada {
	
	///
	/// ACTIVIDADES PROGRAMADAS
	/// 
	
	// Insertar Actividad Programada
	public static boolean insertarActividadProgramada(ActividadProgramada ap) {
		return ActividadProgramadaDAO.insertar(ap);
	}
	
	// Mostrar todas las Actividades Programadas (para la tabla)
	public static Object[][] mostrarActividadesProgramadas() {
		return ActividadProgramadaDAO.obtenerDatosTabla();
	}
	
	// Obtener todas las Actividades Programadas (como lista)
	public static List<ActividadProgramada> obtenerTodasActividadesProgramadas() {
		return ActividadProgramadaDAO.obtenerTodas();
	}
	
	// Obtener Actividad Programada por ID y fecha
	public static ActividadProgramada obtenerActividadProgramada(int idActividad, String fechaInicio) {
		return ActividadProgramadaDAO.obtenerPorId(idActividad, fechaInicio);
	}
	
	// Actualizar Actividad Programada
	public static boolean actualizarActividadProgramada(ActividadProgramada ap) {
		return ActividadProgramadaDAO.actualizar(ap);
	}
	
	// Eliminar Actividad Programada
	public static boolean eliminarActividadProgramada(int idActividad, String fechaInicio) {
		return ActividadProgramadaDAO.eliminar(idActividad, fechaInicio);
	}
	
	// Obtener actividades para comboBox
	public static Object[][] obtenerActividadesParaCombo() {
		return ActividadProgramadaDAO.obtenerActividadesParaCombo();
	}
	
	// Obtener entrenadores para comboBox
	public static Object[][] obtenerEntrenadoresParaCombo() {
		return ActividadProgramadaDAO.obtenerEntrenadoresParaCombo();
	}
	
	// Obtener salas para comboBox
	public static Object[][] obtenerSalasParaCombo() {
		return ActividadProgramadaDAO.obtenerSalasParaCombo();
	}
}