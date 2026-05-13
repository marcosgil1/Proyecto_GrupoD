package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controlador.GestionarUsuario;
import clases.Socio;
import clases.Usuario;

public class LeerUsuarioGUI extends JFrame {

	private JTable tabla;
	private DefaultTableModel modelo;

	public LeerUsuarioGUI() {

		setTitle("Clientes");
		setSize(1100, 300);
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
		modelo.addColumn("Eliminar");

		tabla = new JTable(modelo);

		tabla.getColumnModel().getColumn(8).setPreferredWidth(100);
		tabla.getColumnModel().getColumn(9).setPreferredWidth(100);

		tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int fila = tabla.rowAtPoint(e.getPoint());
				int columna = tabla.columnAtPoint(e.getPoint());

				if (columna == 8) { // Botón Modificar
					
					String dni = tabla.getValueAt(fila, 0).toString();
					String nombre = tabla.getValueAt(fila, 1).toString();
					String apellidos = tabla.getValueAt(fila, 2).toString();
					String telefono = tabla.getValueAt(fila, 3).toString();
					String email = tabla.getValueAt(fila, 4).toString();
					String fechaAlta = tabla.getValueAt(fila, 5).toString();
					String estado = tabla.getValueAt(fila, 6).toString();
					String contrasena = tabla.getValueAt(fila, 7).toString();

					new ActualizarUsuarioGUI(dni, nombre, apellidos, telefono, email, fechaAlta, estado, contrasena);
					
				} else if (columna == 9) { // Botón Eliminar
					
					String dni = tabla.getValueAt(fila, 0).toString();
					
					int confirm = JOptionPane.showConfirmDialog(
						null,
						"¿Estás seguro de que quieres eliminar al usuario con DNI: " + dni + "?",
						"Confirmar eliminación",
						JOptionPane.YES_NO_OPTION
					);
					
					if (confirm == JOptionPane.YES_OPTION) {
						Usuario u = new Socio(dni, null, null, null, null, null, null, null);
						boolean eliminado = GestionarUsuario.eliminarUsuario(u);
						
						if (eliminado) {
							JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente");
							cargarUsuarios(); // Recargar la tabla
						} else {
							JOptionPane.showMessageDialog(null, "Error al eliminar el usuario");
						}
					}
				}
			}
		});

		JScrollPane scroll = new JScrollPane(tabla);

		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(e -> cargarUsuarios());

		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(e -> dispose());

		JPanel panelSuperior = new JPanel();
		panelSuperior.add(btnActualizar);
		panelSuperior.add(btnVolver);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(panelSuperior, BorderLayout.NORTH);
		getContentPane().add(scroll, BorderLayout.CENTER);
		
		// Personalizar el renderer de la tabla para colorear los botones
		tabla.getColumn("Modificar").setCellRenderer(new ButtonRenderer("Modificar", new Color(0, 120, 215)));
		tabla.getColumn("Eliminar").setCellRenderer(new ButtonRenderer("Eliminar", new Color(200, 50, 50)));
	}

	private void cargarUsuarios() {

		modelo.setRowCount(0);

		Object[][] datos = GestionarUsuario.mostrarUsuarios();

		for (Object[] fila : datos) {

			Object[] nuevaFila = new Object[10];
			System.arraycopy(fila, 0, nuevaFila, 0, 8);
			nuevaFila[8] = "Modificar";
			nuevaFila[9] = "Eliminar";

			modelo.addRow(nuevaFila);
		}
	}
	
	// Clase para renderizar botones en la tabla
	class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
		
		public ButtonRenderer(String text, Color color) {
			setText(text);
			setBackground(color);
			setForeground(Color.WHITE);
			setOpaque(true);
			setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		}
		
		@Override
		public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			return this;
		}
	}
}