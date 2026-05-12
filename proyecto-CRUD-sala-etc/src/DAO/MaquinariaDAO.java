package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import clases.Maquinaria;

public class MaquinariaDAO {

	// INSERTAR MAQUINARIA
	public static boolean insertar(Maquinaria m) {

		try {
			Connection con = Conexion.obtenerConexion();

			String sql = "INSERT INTO maquinaria (tipo, marca, n_serie, estado, fecha_compra, fecha_ultimo_mantenimiento, id_sala) VALUES (?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, m.getTipo());
			ps.setString(2, m.getMarca());
			ps.setString(3, m.getnSerie());
			ps.setString(4, m.getEstado());
			ps.setString(5, m.getFechaCompra());
			ps.setString(6, m.getFechaUltimoMantenimiento());
			ps.setInt(7, m.getIdSala());

			int filas = ps.executeUpdate();

			if (filas > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					m.setIdMaquinaria(rs.getInt(1));
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

		String sql = "SELECT m.id_maquinaria, m.tipo, m.marca, m.n_serie, m.estado, "
				+ "m.fecha_compra, m.fecha_ultimo_mantenimiento, s.nombre as sala_nombre "
				+ "FROM maquinaria m JOIN sala s ON m.id_sala = s.id_sala ORDER BY m.id_maquinaria";

		try (Connection con = Conexion.obtenerConexion();
				Statement sta = con.createStatement();
				ResultSet rs = sta.executeQuery(sql)) {

			while (rs.next()) {

				Object[] fila = new Object[] { rs.getInt("id_maquinaria"), rs.getString("tipo"), rs.getString("marca"),
						rs.getString("n_serie"), rs.getString("estado"), rs.getString("fecha_compra"),
						rs.getString("fecha_ultimo_mantenimiento"), rs.getString("sala_nombre") };

				lista.add(fila);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista.toArray(new Object[0][]);
	}

	// OBTENER TODA LA MAQUINARIA (como lista de objetos)
	public static List<Maquinaria> obtenerTodas() {

		List<Maquinaria> lista = new ArrayList<>();

		String sql = "SELECT m.*, s.nombre as sala_nombre FROM maquinaria m JOIN sala s ON m.id_sala = s.id_sala ORDER BY m.id_maquinaria";

		try (Connection con = Conexion.obtenerConexion();
				Statement sta = con.createStatement();
				ResultSet rs = sta.executeQuery(sql)) {

			while (rs.next()) {

				Maquinaria m = new Maquinaria(rs.getInt("id_maquinaria"), rs.getString("tipo"), rs.getString("marca"),
						rs.getString("n_serie"), rs.getString("estado"), rs.getString("fecha_compra"),
						rs.getString("fecha_ultimo_mantenimiento"), rs.getInt("id_sala"));
				m.setNombreSala(rs.getString("sala_nombre"));

				lista.add(m);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	// OBTENER MAQUINARIA POR ID
	public static Maquinaria obtenerPorId(int id) {

		String sql = "SELECT m.*, s.nombre as sala_nombre FROM maquinaria m JOIN sala s ON m.id_sala = s.id_sala WHERE m.id_maquinaria = ?";

		try (Connection con = Conexion.obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Maquinaria m = new Maquinaria(rs.getInt("id_maquinaria"), rs.getString("tipo"), rs.getString("marca"),
						rs.getString("n_serie"), rs.getString("estado"), rs.getString("fecha_compra"),
						rs.getString("fecha_ultimo_mantenimiento"), rs.getInt("id_sala"));
				m.setNombreSala(rs.getString("sala_nombre"));
				rs.close();
				return m;
			}

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	// ACTUALIZAR MAQUINARIA
	public static boolean actualizar(Maquinaria m) {

		try {

			Connection con = Conexion.obtenerConexion();

			String sql = "UPDATE maquinaria SET tipo = ?, marca = ?, n_serie = ?, estado = ?, "
					+ "fecha_compra = ?, fecha_ultimo_mantenimiento = ?, id_sala = ? WHERE id_maquinaria = ?";

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, m.getTipo());
			ps.setString(2, m.getMarca());
			ps.setString(3, m.getnSerie());
			ps.setString(4, m.getEstado());
			ps.setString(5, m.getFechaCompra());
			ps.setString(6, m.getFechaUltimoMantenimiento());
			ps.setInt(7, m.getIdSala());
			ps.setInt(8, m.getIdMaquinaria());

			int filasAfectadas = ps.executeUpdate();

			ps.close();
			con.close();

			return filasAfectadas > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// ELIMINAR MAQUINARIA
	public static boolean eliminar(int id) {

		Connection con = null;

		try {

			con = Conexion.obtenerConexion();

			String sql = "DELETE FROM maquinaria WHERE id_maquinaria = ?";

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

	// OBTENER SALAS PARA COMBOBOX
	public static Object[][] obtenerSalasParaCombo() {

		List<Object[]> lista = new ArrayList<>();

		String sql = "SELECT id_sala, nombre FROM sala ORDER BY id_sala";

		try (Connection con = Conexion.obtenerConexion();
				Statement sta = con.createStatement();
				ResultSet rs = sta.executeQuery(sql)) {

			while (rs.next()) {
				Object[] fila = new Object[] { rs.getInt("id_sala"), rs.getString("nombre") };
				lista.add(fila);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista.toArray(new Object[0][]);
	}
}