package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import clases.ActividadProgramada;

public class ActividadProgramadaDAO {

	// INSERTAR ACTIVIDAD PROGRAMADA
	public static boolean insertar(ActividadProgramada ap) {

		try {
			Connection con = Conexion.obtenerConexion();

			String sql = "INSERT INTO actividad_programada (id_actividad, fecha_inicio, fecha_fin, dni_entrenador, id_sala) VALUES (?, ?, ?, ?, ?)";

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, ap.getIdActividad());
			ps.setString(2, ap.getFechaInicio());
			ps.setString(3, ap.getFechaFin());
			ps.setString(4, ap.getDniEntrenador());
			ps.setInt(5, ap.getIdSala());

			int filas = ps.executeUpdate();

			ps.close();
			con.close();

			return filas > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// OBTENER TODOS LOS DATOS PARA LA TABLA (con JOIN para mostrar nombres)
	public static Object[][] obtenerDatosTabla() {

		List<Object[]> lista = new ArrayList<>();

		String sql = "SELECT ap.id_actividad, a.nombre as nombre_actividad, "
				+ "ap.fecha_inicio, ap.fecha_fin, "
				+ "u.nombre as nombre_entrenador, s.nombre as nombre_sala "
				+ "FROM actividad_programada ap "
				+ "INNER JOIN actividad a ON ap.id_actividad = a.id_actividad "
				+ "INNER JOIN entrenador e ON ap.dni_entrenador = e.dni_usuario "
				+ "INNER JOIN usuario u ON e.dni_usuario = u.dni "
				+ "INNER JOIN sala s ON ap.id_sala = s.id_sala "
				+ "ORDER BY ap.fecha_inicio";

		try (Connection con = Conexion.obtenerConexion();
				Statement sta = con.createStatement();
				ResultSet rs = sta.executeQuery(sql)) {

			while (rs.next()) {

				Object[] fila = new Object[] { 
					rs.getInt("id_actividad"),
					rs.getString("nombre_actividad"),
					rs.getString("fecha_inicio"),
					rs.getString("fecha_fin"),
					rs.getString("nombre_entrenador"),
					rs.getString("nombre_sala")
				};

				lista.add(fila);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista.toArray(new Object[0][]);
	}

	// OBTENER TODAS LAS ACTIVIDADES PROGRAMADAS (como lista de objetos)
	public static List<ActividadProgramada> obtenerTodas() {

		List<ActividadProgramada> lista = new ArrayList<>();

		String sql = "SELECT ap.*, a.nombre as nombre_actividad, u.nombre as nombre_entrenador, s.nombre as nombre_sala "
				+ "FROM actividad_programada ap "
				+ "INNER JOIN actividad a ON ap.id_actividad = a.id_actividad "
				+ "INNER JOIN entrenador e ON ap.dni_entrenador = e.dni_usuario "
				+ "INNER JOIN usuario u ON e.dni_usuario = u.dni "
				+ "INNER JOIN sala s ON ap.id_sala = s.id_sala "
				+ "ORDER BY ap.fecha_inicio";

		try (Connection con = Conexion.obtenerConexion();
				Statement sta = con.createStatement();
				ResultSet rs = sta.executeQuery(sql)) {

			while (rs.next()) {

				ActividadProgramada ap = new ActividadProgramada(
					rs.getInt("id_actividad"),
					rs.getString("fecha_inicio"),
					rs.getString("fecha_fin"),
					rs.getString("dni_entrenador"),
					rs.getInt("id_sala")
				);
				ap.setNombreActividad(rs.getString("nombre_actividad"));
				ap.setNombreEntrenador(rs.getString("nombre_entrenador"));
				ap.setNombreSala(rs.getString("nombre_sala"));

				lista.add(ap);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	// OBTENER ACTIVIDAD PROGRAMADA POR ID Y FECHA
	public static ActividadProgramada obtenerPorId(int idActividad, String fechaInicio) {

		String sql = "SELECT ap.*, a.nombre as nombre_actividad, u.nombre as nombre_entrenador, s.nombre as nombre_sala "
				+ "FROM actividad_programada ap "
				+ "INNER JOIN actividad a ON ap.id_actividad = a.id_actividad "
				+ "INNER JOIN entrenador e ON ap.dni_entrenador = e.dni_usuario "
				+ "INNER JOIN usuario u ON e.dni_usuario = u.dni "
				+ "INNER JOIN sala s ON ap.id_sala = s.id_sala "
				+ "WHERE ap.id_actividad = ? AND ap.fecha_inicio = ?";

		try (Connection con = Conexion.obtenerConexion();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, idActividad);
			ps.setString(2, fechaInicio);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				ActividadProgramada ap = new ActividadProgramada(
					rs.getInt("id_actividad"),
					rs.getString("fecha_inicio"),
					rs.getString("fecha_fin"),
					rs.getString("dni_entrenador"),
					rs.getInt("id_sala")
				);
				ap.setNombreActividad(rs.getString("nombre_actividad"));
				ap.setNombreEntrenador(rs.getString("nombre_entrenador"));
				ap.setNombreSala(rs.getString("nombre_sala"));
				rs.close();
				return ap;
			}

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	// ACTUALIZAR ACTIVIDAD PROGRAMADA (solo se puede actualizar fecha_fin, entrenador y sala)
	public static boolean actualizar(ActividadProgramada ap) {

		try {

			Connection con = Conexion.obtenerConexion();

			String sql = "UPDATE actividad_programada SET fecha_fin = ?, dni_entrenador = ?, id_sala = ? "
					+ "WHERE id_actividad = ? AND fecha_inicio = ?";

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, ap.getFechaFin());
			ps.setString(2, ap.getDniEntrenador());
			ps.setInt(3, ap.getIdSala());
			ps.setInt(4, ap.getIdActividad());
			ps.setString(5, ap.getFechaInicio());

			int filasAfectadas = ps.executeUpdate();

			ps.close();
			con.close();

			return filasAfectadas > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// ELIMINAR ACTIVIDAD PROGRAMADA
	public static boolean eliminar(int idActividad, String fechaInicio) {

		Connection con = null;

		try {

			con = Conexion.obtenerConexion();

			String sql = "DELETE FROM actividad_programada WHERE id_actividad = ? AND fecha_inicio = ?";

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, idActividad);
			ps.setString(2, fechaInicio);

			int filas = ps.executeUpdate();

			ps.close();
			con.close();

			return filas > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// OBTENER ACTIVIDADES PARA COMBOBOX
	public static Object[][] obtenerActividadesParaCombo() {

		List<Object[]> lista = new ArrayList<>();

		String sql = "SELECT id_actividad, nombre FROM actividad ORDER BY nombre";

		try (Connection con = Conexion.obtenerConexion();
				Statement sta = con.createStatement();
				ResultSet rs = sta.executeQuery(sql)) {

			while (rs.next()) {
				Object[] fila = new Object[] { 
					rs.getInt("id_actividad"), 
					rs.getString("nombre") 
				};
				lista.add(fila);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista.toArray(new Object[0][]);
	}

	// OBTENER ENTRENADORES PARA COMBOBOX
	public static Object[][] obtenerEntrenadoresParaCombo() {

		List<Object[]> lista = new ArrayList<>();

		String sql = "SELECT e.dni_usuario, u.nombre, u.apellidos "
				+ "FROM entrenador e "
				+ "INNER JOIN usuario u ON e.dni_usuario = u.dni "
				+ "ORDER BY u.nombre";

		try (Connection con = Conexion.obtenerConexion();
				Statement sta = con.createStatement();
				ResultSet rs = sta.executeQuery(sql)) {

			while (rs.next()) {
				Object[] fila = new Object[] { 
					rs.getString("dni_usuario"), 
					rs.getString("nombre") + " " + rs.getString("apellidos")
				};
				lista.add(fila);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista.toArray(new Object[0][]);
	}

	// OBTENER SALAS PARA COMBOBOX
	public static Object[][] obtenerSalasParaCombo() {

		List<Object[]> lista = new ArrayList<>();

		String sql = "SELECT id_sala, nombre FROM sala ORDER BY nombre";

		try (Connection con = Conexion.obtenerConexion();
				Statement sta = con.createStatement();
				ResultSet rs = sta.executeQuery(sql)) {

			while (rs.next()) {
				Object[] fila = new Object[] { 
					rs.getInt("id_sala"), 
					rs.getString("nombre") 
				};
				lista.add(fila);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista.toArray(new Object[0][]);
	}
}