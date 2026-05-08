package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controlador.GestionarUsuario;

public class LeerUsuariosGUI extends JFrame {

	private JTable tabla;
	private DefaultTableModel modelo;

	public LeerUsuariosGUI() {

		setTitle("Clientes");
		setSize(1000, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		iniciarTabla();
		cargarUsuarios();

		setVisible(true);
	}

	private void iniciarTabla() {

		modelo = new DefaultTableModel();

		modelo.addColumn("DNI");
		modelo.addColumn("Nombre");
		modelo.addColumn("Apellidos");
		modelo.addColumn("Teléfono");
		modelo.addColumn("Email");
		modelo.addColumn("Fecha Alta");
		modelo.addColumn("Estado");
		modelo.addColumn("Contraseña");
		modelo.addColumn("Modificar");

		tabla = new JTable(modelo);

		tabla.getColumnModel().getColumn(8).setPreferredWidth(100);

		tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int fila = tabla.rowAtPoint(e.getPoint());
				int columna = tabla.columnAtPoint(e.getPoint());

				if (columna == 8) {

					String dni = tabla.getValueAt(fila, 0).toString();
					String nombre = tabla.getValueAt(fila, 1).toString();
					String apellidos = tabla.getValueAt(fila, 2).toString();
					String telefono = tabla.getValueAt(fila, 3).toString();
					String email = tabla.getValueAt(fila, 4).toString();
					String fechaAlta = tabla.getValueAt(fila, 5).toString();
					String estado = tabla.getValueAt(fila, 6).toString();
					String contrasena = tabla.getValueAt(fila, 7).toString();

					new ActualizarUsuarioGUI(dni, nombre, apellidos, telefono, email, fechaAlta, estado, contrasena);
				}
			}
		});

		JScrollPane scroll = new JScrollPane(tabla);

		JButton btnActualizar = new JButton("Actualizar");

		btnActualizar.addActionListener(e -> cargarUsuarios());

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(btnActualizar, BorderLayout.NORTH);
		getContentPane().add(scroll, BorderLayout.CENTER);
	}

	private void cargarUsuarios() {

		modelo.setRowCount(0);

		Object[][] datos = GestionarUsuario.mostrarUsuarios();

		for (Object[] fila : datos) {

			Object[] nuevaFila = new Object[9];

			System.arraycopy(fila, 0, nuevaFila, 0, 8);

			nuevaFila[8] = "Modificar";

			modelo.addRow(nuevaFila);
		}

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(LeerUsuariosGUI::new);
	}
}