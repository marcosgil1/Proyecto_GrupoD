package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import clases.Sala;

public class SalaDAO {

	// INSERTAR SALA
	public static boolean insertar(Sala sala) {

		try {
			Connection con = Conexion.obtenerConexion();

			String sql = "INSERT INTO sala (nombre, m_cuadrados, aforo_max) VALUES (?, ?, ?)";

			PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, sala.getNombre());
			ps.setDouble(2, sala.getMetrosCuadrados());
			ps.setInt(3, sala.getAforoMax());

			int filas = ps.executeUpdate();

			if (filas > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					sala.setIdSala(rs.getInt(1));
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

	// OBTENER TODAS LAS SALAS (para la tabla)
	public static Object[][] obtenerDatosTabla() {

		List<Object[]> lista = new ArrayList<>();

		String sql = "SELECT id_sala, nombre, m_cuadrados, aforo_max FROM sala ORDER BY id_sala";

		try (Connection con = Conexion.obtenerConexion();
				Statement sta = con.createStatement();
				ResultSet rs = sta.executeQuery(sql)) {

			while (rs.next()) {

				Object[] fila = new Object[] { 
					rs.getInt("id_sala"), 
					rs.getString("nombre"), 
					rs.getDouble("m_cuadrados"),
					rs.getInt("aforo_max") 
				};

				lista.add(fila);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista.toArray(new Object[0][]);
	}

	// OBTENER TODAS LAS SALAS (como lista de objetos)
	public static List<Sala> obtenerTodas() {

		List<Sala> lista = new ArrayList<>();

		String sql = "SELECT * FROM sala ORDER BY id_sala";

		try (Connection con = Conexion.obtenerConexion();
				Statement sta = con.createStatement();
				ResultSet rs = sta.executeQuery(sql)) {

			while (rs.next()) {

				Sala sala = new Sala(
					rs.getInt("id_sala"),
					rs.getString("nombre"),
					rs.getDouble("m_cuadrados"),
					rs.getInt("aforo_max")
				);

				lista.add(sala);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	// OBTENER SALA POR ID
	public static Sala obtenerPorId(int id) {

		String sql = "SELECT * FROM sala WHERE id_sala = ?";

		try (Connection con = Conexion.obtenerConexion();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Sala sala = new Sala(
					rs.getInt("id_sala"),
					rs.getString("nombre"),
					rs.getDouble("m_cuadrados"),
					rs.getInt("aforo_max")
				);
				rs.close();
				return sala;
			}

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	// ACTUALIZAR SALA
	public static boolean actualizar(Sala sala) {

		try {

			Connection con = Conexion.obtenerConexion();

			String sql = "UPDATE sala SET nombre = ?, m_cuadrados = ?, aforo_max = ? WHERE id_sala = ?";

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, sala.getNombre());
			ps.setDouble(2, sala.getMetrosCuadrados());
			ps.setInt(3, sala.getAforoMax());
			ps.setInt(4, sala.getIdSala());

			int filasAfectadas = ps.executeUpdate();

			ps.close();
			con.close();

			return filasAfectadas > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// ELIMINAR SALA
	public static boolean eliminar(int id) {

		Connection con = null;

		try {

			con = Conexion.obtenerConexion();

			String sql = "DELETE FROM sala WHERE id_sala = ?";

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
}