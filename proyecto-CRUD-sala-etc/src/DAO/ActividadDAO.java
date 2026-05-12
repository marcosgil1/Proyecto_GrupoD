package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import clases.Actividad;

public class ActividadDAO {

	// INSERTAR ACTIVIDAD
	public static boolean insertar(Actividad actividad) {

		try {
			Connection con = Conexion.obtenerConexion();

			String sql = "INSERT INTO actividad (nombre, descripcion, nivel) VALUES (?, ?, ?)";

			PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, actividad.getNombre());
			ps.setString(2, actividad.getDescripcion());
			ps.setString(3, actividad.getNivel());

			int filas = ps.executeUpdate();

			if (filas > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					actividad.setIdActividad(rs.getInt(1));
				}
				rs.close();
			}

			ps.close();
			con.close();

			return filas > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// OBTENER TODOS LOS DATOS PARA LA TABLA
	public static Object[][] obtenerDatosTabla() {

		List<Object[]> lista = new ArrayList<>();

		String sql = "SELECT id_actividad, nombre, descripcion, nivel FROM actividad ORDER BY id_actividad";

		try (Connection con = Conexion.obtenerConexion();
				Statement sta = con.createStatement();
				ResultSet rs = sta.executeQuery(sql)) {

			while (rs.next()) {

				Object[] fila = new Object[] { 
					rs.getInt("id_actividad"),
					rs.getString("nombre"), 
					rs.getString("descripcion"), 
					rs.getString("nivel") 
				};

				lista.add(fila);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista.toArray(new Object[0][]);
	}

	// OBTENER TODAS LAS ACTIVIDADES (como lista de objetos)
	public static List<Actividad> obtenerTodas() {

		List<Actividad> lista = new ArrayList<>();

		String sql = "SELECT * FROM actividad ORDER BY id_actividad";

		try (Connection con = Conexion.obtenerConexion();
				Statement sta = con.createStatement();
				ResultSet rs = sta.executeQuery(sql)) {

			while (rs.next()) {

				Actividad actividad = new Actividad(
					rs.getInt("id_actividad"),
					rs.getString("nombre"),
					rs.getString("descripcion"),
					rs.getString("nivel")
				);

				lista.add(actividad);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	// OBTENER ACTIVIDAD POR ID
	public static Actividad obtenerPorId(int id) {

		String sql = "SELECT * FROM actividad WHERE id_actividad = ?";

		try (Connection con = Conexion.obtenerConexion();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Actividad actividad = new Actividad(
					rs.getInt("id_actividad"),
					rs.getString("nombre"),
					rs.getString("descripcion"),
					rs.getString("nivel")
				);
				rs.close();
				return actividad;
			}

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	// ACTUALIZAR ACTIVIDAD
	public static boolean actualizar(Actividad actividad) {

		try {

			Connection con = Conexion.obtenerConexion();

			String sql = "UPDATE actividad SET nombre = ?, descripcion = ?, nivel = ? WHERE id_actividad = ?";

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, actividad.getNombre());
			ps.setString(2, actividad.getDescripcion());
			ps.setString(3, actividad.getNivel());
			ps.setInt(4, actividad.getIdActividad());

			int filasAfectadas = ps.executeUpdate();

			ps.close();
			con.close();

			return filasAfectadas > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// ELIMINAR ACTIVIDAD
	public static boolean eliminar(int id) {

		Connection con = null;

		try {

			con = Conexion.obtenerConexion();

			String sql = "DELETE FROM actividad WHERE id_actividad = ?";

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, id);

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
}