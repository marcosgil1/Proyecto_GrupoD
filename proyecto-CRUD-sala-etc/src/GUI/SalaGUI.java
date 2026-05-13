package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controlador.GestionarSala;
import clases.Sala;

public class SalaGUI extends JFrame {

	private JTable tabla;
	private DefaultTableModel modelo;

	public SalaGUI() {

		setTitle("Gestión de Salas");
		setSize(900, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		iniciarTabla();
		cargarSalas();

		setVisible(true);
	}

	private void iniciarTabla() {

		modelo = new DefaultTableModel();

		modelo.addColumn("ID");
		modelo.addColumn("Nombre");
		modelo.addColumn("Metros²");
		modelo.addColumn("Aforo Máx");
		modelo.addColumn("Modificar");
		modelo.addColumn("Eliminar");

		tabla = new JTable(modelo);

		tabla.getColumnModel().getColumn(4).setPreferredWidth(100);
		tabla.getColumnModel().getColumn(5).setPreferredWidth(100);

		tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int fila = tabla.rowAtPoint(e.getPoint());
				int columna = tabla.columnAtPoint(e.getPoint());

				if (columna == 4) { // Botón Modificar

					int id = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
					String nombre = tabla.getValueAt(fila, 1).toString();
					double metros = Double.parseDouble(tabla.getValueAt(fila, 2).toString());
					int aforo = Integer.parseInt(tabla.getValueAt(fila, 3).toString());

					Sala sala = GestionarSala.obtenerSalaPorId(id);
					mostrarFormularioEditar(sala);

				} else if (columna == 5) { // Botón Eliminar

					int id = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
					String nombre = tabla.getValueAt(fila, 1).toString();

					int confirm = JOptionPane.showConfirmDialog(null,
							"¿Estás seguro de que quieres eliminar la sala: " + nombre + "?", "Confirmar eliminación",
							JOptionPane.YES_NO_OPTION);

					if (confirm == JOptionPane.YES_OPTION) {
						boolean eliminado = GestionarSala.eliminarSala(id);

						if (eliminado) {
							JOptionPane.showMessageDialog(null, "Sala eliminada correctamente");
							cargarSalas(); // Recargar la tabla
						} else {
							JOptionPane.showMessageDialog(null,
									"Error: No se puede eliminar la sala porque tiene maquinaria o actividades asociadas");
						}
					}
				}
			}
		});

		JScrollPane scroll = new JScrollPane(tabla);

		JButton btnNuevo = new JButton("+ Nueva Sala");
		btnNuevo.addActionListener(e -> mostrarFormularioNuevo());

		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(e -> cargarSalas());

		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(e -> dispose());

		JPanel panelSuperior = new JPanel();
		panelSuperior.add(btnNuevo);
		panelSuperior.add(btnActualizar);
		panelSuperior.add(btnVolver);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(panelSuperior, BorderLayout.NORTH);
		getContentPane().add(scroll, BorderLayout.CENTER);

		// Personalizar el renderer de la tabla para colorear los botones
		tabla.getColumn("Modificar").setCellRenderer(new ButtonRenderer("Modificar", new Color(0, 120, 215)));
		tabla.getColumn("Eliminar").setCellRenderer(new ButtonRenderer("Eliminar", new Color(200, 50, 50)));
	}

	private void mostrarFormularioNuevo() {

		JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
		JTextField txtNombre = new JTextField(15);
		JTextField txtMetros = new JTextField(10);
		JTextField txtAforo = new JTextField(10);

		panel.add(new JLabel("Nombre:"));
		panel.add(txtNombre);
		panel.add(new JLabel("Metros cuadrados:"));
		panel.add(txtMetros);
		panel.add(new JLabel("Aforo máximo:"));
		panel.add(txtAforo);

		int resultado = JOptionPane.showConfirmDialog(this, panel, "Nueva Sala", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (resultado == JOptionPane.OK_OPTION) {

			if (txtNombre.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "El nombre es obligatorio");
				return;
			}

			try {
				double metros = Double.parseDouble(txtMetros.getText());
				int aforo = Integer.parseInt(txtAforo.getText());

				if (metros <= 0 || aforo <= 0) {
					JOptionPane.showMessageDialog(this, "Metros y aforo deben ser mayores que 0");
					return;
				}

				Sala nueva = new Sala(0, txtNombre.getText(), metros, aforo);
				boolean insertado = GestionarSala.insertarSala(nueva);

				if (insertado) {
					JOptionPane.showMessageDialog(this, "Sala creada correctamente");
					cargarSalas();
				} else {
					JOptionPane.showMessageDialog(this, "Error al crear la sala");
				}

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Metros y aforo deben ser números válidos");
			}
		}
	}

	private void mostrarFormularioEditar(Sala sala) {

		JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
		JTextField txtNombre = new JTextField(sala.getNombre(), 15);
		JTextField txtMetros = new JTextField(String.valueOf(sala.getMetrosCuadrados()), 10);
		JTextField txtAforo = new JTextField(String.valueOf(sala.getAforoMax()), 10);

		panel.add(new JLabel("Nombre:"));
		panel.add(txtNombre);
		panel.add(new JLabel("Metros cuadrados:"));
		panel.add(txtMetros);
		panel.add(new JLabel("Aforo máximo:"));
		panel.add(txtAforo);

		int resultado = JOptionPane.showConfirmDialog(this, panel, "Editar Sala", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (resultado == JOptionPane.OK_OPTION) {

			if (txtNombre.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "El nombre es obligatorio");
				return;
			}

			try {
				double metros = Double.parseDouble(txtMetros.getText());
				int aforo = Integer.parseInt(txtAforo.getText());

				if (metros <= 0 || aforo <= 0) {
					JOptionPane.showMessageDialog(this, "Metros y aforo deben ser mayores que 0");
					return;
				}

				sala.setNombre(txtNombre.getText());
				sala.setMetrosCuadrados(metros);
				sala.setAforoMax(aforo);

				boolean actualizado = GestionarSala.actualizarSala(sala);

				if (actualizado) {
					JOptionPane.showMessageDialog(this, "Sala actualizada correctamente");
					cargarSalas();
				} else {
					JOptionPane.showMessageDialog(this, "Error al actualizar la sala");
				}

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Metros y aforo deben ser números válidos");
			}
		}
	}

	private void cargarSalas() {

		modelo.setRowCount(0);

		Object[][] datos = GestionarSala.mostrarSalas();

		for (Object[] fila : datos) {

			Object[] nuevaFila = new Object[6];
			System.arraycopy(fila, 0, nuevaFila, 0, 4);
			nuevaFila[4] = "Modificar";
			nuevaFila[5] = "Eliminar";

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
		public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
				boolean hasFocus, int row, int column) {
			return this;
		}
	}
}