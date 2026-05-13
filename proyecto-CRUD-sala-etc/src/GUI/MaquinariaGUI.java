package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controlador.GestionarMaquinaria;
import clases.Maquinaria;

public class MaquinariaGUI extends JFrame {

	private JTable tabla;
	private DefaultTableModel modelo;

	public MaquinariaGUI() {

		setTitle("Gestión de Maquinaria");
		setSize(1200, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		iniciarTabla();
		cargarMaquinaria();

		setVisible(true);
	}

	private void iniciarTabla() {

		modelo = new DefaultTableModel();

		modelo.addColumn("ID");
		modelo.addColumn("Tipo");
		modelo.addColumn("Marca");
		modelo.addColumn("Nº Serie");
		modelo.addColumn("Estado");
		modelo.addColumn("Fecha Compra");
		modelo.addColumn("Últ. Mantenimiento");
		modelo.addColumn("Sala");
		modelo.addColumn("Modificar");
		modelo.addColumn("Eliminar");

		tabla = new JTable(modelo);
		tabla.setFont(new Font("Arial", Font.PLAIN, 11));

		tabla.getColumnModel().getColumn(8).setPreferredWidth(100);
		tabla.getColumnModel().getColumn(9).setPreferredWidth(100);

		tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int fila = tabla.rowAtPoint(e.getPoint());
				int columna = tabla.columnAtPoint(e.getPoint());

				if (columna == 8) { // Botón Modificar

					int id = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
					Maquinaria maquinaria = GestionarMaquinaria.obtenerMaquinariaPorId(id);
					mostrarFormularioEditar(maquinaria);

				} else if (columna == 9) { // Botón Eliminar

					int id = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
					String tipo = tabla.getValueAt(fila, 1).toString();

					int confirm = JOptionPane.showConfirmDialog(null,
							"¿Estás seguro de que quieres eliminar la maquinaria: " + tipo + "?",
							"Confirmar eliminación", JOptionPane.YES_NO_OPTION);

					if (confirm == JOptionPane.YES_OPTION) {
						boolean eliminado = GestionarMaquinaria.eliminarMaquinaria(id);

						if (eliminado) {
							JOptionPane.showMessageDialog(null, "Maquinaria eliminada correctamente");
							cargarMaquinaria();
						} else {
							JOptionPane.showMessageDialog(null, "Error al eliminar la maquinaria");
						}
					}
				}
			}
		});

		JScrollPane scroll = new JScrollPane(tabla);

		JButton btnNuevo = new JButton("+ Nueva Maquinaria");
		btnNuevo.addActionListener(e -> mostrarFormularioNuevo());

		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(e -> cargarMaquinaria());

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

		JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));

		JTextField txtTipo = new JTextField(15);
		JTextField txtMarca = new JTextField(15);
		JTextField txtNSerie = new JTextField(15);

		JComboBox<String> comboEstado = new JComboBox<>(new String[] { "operativa", "averiada", "mantenimiento" });

		JTextField txtFechaCompra = new JTextField(15);
		txtFechaCompra.setText(java.time.LocalDate.now().toString());

		JTextField txtFechaMantenimiento = new JTextField(15);
		txtFechaMantenimiento.setText(java.time.LocalDate.now().toString());

		// ComboBox para seleccionar sala (cargar desde BD)
		Object[][] salas = GestionarMaquinaria.obtenerSalasParaCombo();
		JComboBox<String> comboSala = new JComboBox<>();
		for (Object[] sala : salas) {
			comboSala.addItem(sala[0] + " - " + sala[1]);
		}

		panel.add(new JLabel("Tipo:"));
		panel.add(txtTipo);
		panel.add(new JLabel("Marca:"));
		panel.add(txtMarca);
		panel.add(new JLabel("Nº Serie:"));
		panel.add(txtNSerie);
		panel.add(new JLabel("Estado:"));
		panel.add(comboEstado);
		panel.add(new JLabel("Fecha Compra (YYYY-MM-DD):"));
		panel.add(txtFechaCompra);
		panel.add(new JLabel("Fecha Últ. Mantenimiento:"));
		panel.add(txtFechaMantenimiento);
		panel.add(new JLabel("Sala:"));
		panel.add(comboSala);

		int resultado = JOptionPane.showConfirmDialog(this, panel, "Nueva Maquinaria", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (resultado == JOptionPane.OK_OPTION) {

			if (txtTipo.getText().isEmpty() || txtMarca.getText().isEmpty() || txtNSerie.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Tipo, Marca y Nº Serie son obligatorios");
				return;
			}

			try {
				String fechaCompra = txtFechaCompra.getText();
				String fechaMantenimiento = txtFechaMantenimiento.getText();

				String salaSeleccionada = (String) comboSala.getSelectedItem();
				int idSala = Integer.parseInt(salaSeleccionada.split(" - ")[0]);

				Maquinaria nueva = new Maquinaria(0, txtTipo.getText(), txtMarca.getText(), txtNSerie.getText(),
						(String) comboEstado.getSelectedItem(), fechaCompra, fechaMantenimiento, idSala);

				boolean insertado = GestionarMaquinaria.insertarMaquinaria(nueva);

				if (insertado) {
					JOptionPane.showMessageDialog(this, "Maquinaria creada correctamente");
					cargarMaquinaria();
				} else {
					JOptionPane.showMessageDialog(this, "Error al crear la maquinaria");
				}

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Error en los datos ingresados");
			}
		}
	}

	private void mostrarFormularioEditar(Maquinaria maquinaria) {

		JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));

		JTextField txtTipo = new JTextField(maquinaria.getTipo(), 15);
		JTextField txtMarca = new JTextField(maquinaria.getMarca(), 15);
		JTextField txtNSerie = new JTextField(maquinaria.getnSerie(), 15);

		JComboBox<String> comboEstado = new JComboBox<>(new String[] { "operativa", "averiada", "mantenimiento" });
		comboEstado.setSelectedItem(maquinaria.getEstado());

		JTextField txtFechaCompra = new JTextField(maquinaria.getFechaCompra(), 15);
		JTextField txtFechaMantenimiento = new JTextField(maquinaria.getFechaUltimoMantenimiento(), 15);

		// ComboBox para seleccionar sala
		Object[][] salas = GestionarMaquinaria.obtenerSalasParaCombo();
		JComboBox<String> comboSala = new JComboBox<>();
		int salaIndex = 0;
		for (int i = 0; i < salas.length; i++) {
			comboSala.addItem(salas[i][0] + " - " + salas[i][1]);
			if (Integer.parseInt(salas[i][0].toString()) == maquinaria.getIdSala()) {
				salaIndex = i;
			}
		}
		comboSala.setSelectedIndex(salaIndex);

		panel.add(new JLabel("Tipo:"));
		panel.add(txtTipo);
		panel.add(new JLabel("Marca:"));
		panel.add(txtMarca);
		panel.add(new JLabel("Nº Serie:"));
		panel.add(txtNSerie);
		panel.add(new JLabel("Estado:"));
		panel.add(comboEstado);
		panel.add(new JLabel("Fecha Compra:"));
		panel.add(txtFechaCompra);
		panel.add(new JLabel("Fecha Últ. Mantenimiento:"));
		panel.add(txtFechaMantenimiento);
		panel.add(new JLabel("Sala:"));
		panel.add(comboSala);

		int resultado = JOptionPane.showConfirmDialog(this, panel, "Editar Maquinaria", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (resultado == JOptionPane.OK_OPTION) {

			if (txtTipo.getText().isEmpty() || txtMarca.getText().isEmpty() || txtNSerie.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Tipo, Marca y Nº Serie son obligatorios");
				return;
			}

			try {
				String salaSeleccionada = (String) comboSala.getSelectedItem();
				int idSala = Integer.parseInt(salaSeleccionada.split(" - ")[0]);

				maquinaria.setTipo(txtTipo.getText());
				maquinaria.setMarca(txtMarca.getText());
				maquinaria.setnSerie(txtNSerie.getText());
				maquinaria.setEstado((String) comboEstado.getSelectedItem());
				maquinaria.setFechaCompra(txtFechaCompra.getText());
				maquinaria.setFechaUltimoMantenimiento(txtFechaMantenimiento.getText());
				maquinaria.setIdSala(idSala);

				boolean actualizado = GestionarMaquinaria.actualizarMaquinaria(maquinaria);

				if (actualizado) {
					JOptionPane.showMessageDialog(this, "Maquinaria actualizada correctamente");
					cargarMaquinaria();
				} else {
					JOptionPane.showMessageDialog(this, "Error al actualizar la maquinaria");
				}

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Error en los datos ingresados");
			}
		}
	}

	private void cargarMaquinaria() {

		modelo.setRowCount(0);

		Object[][] datos = GestionarMaquinaria.mostrarMaquinaria();

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
		public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
				boolean hasFocus, int row, int column) {
			return this;
		}
	}
}