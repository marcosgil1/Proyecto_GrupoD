package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controlador.GestionarActividad;
import clases.Actividad;

public class ActividadGUI extends JFrame {

	private JTable tabla;
	private DefaultTableModel modelo;

	public ActividadGUI() {

		setTitle("Gestión de Actividades");
		setSize(900, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		iniciarTabla();
		cargarActividades();

		setVisible(true);
	}

	private void iniciarTabla() {

		modelo = new DefaultTableModel();

		modelo.addColumn("ID");
		modelo.addColumn("Nombre");
		modelo.addColumn("Descripción");
		modelo.addColumn("Nivel");
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
					String descripcion = tabla.getValueAt(fila, 2).toString();
					String nivel = tabla.getValueAt(fila, 3).toString();
					
					Actividad actividad = new Actividad(id, nombre, descripcion, nivel);
					mostrarFormularioEditar(actividad);
					
				} else if (columna == 5) { // Botón Eliminar
					
					int id = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
					String nombre = tabla.getValueAt(fila, 1).toString();
					
					int confirm = JOptionPane.showConfirmDialog(
						null,
						"¿Estás seguro de que quieres eliminar la actividad: " + nombre + "?",
						"Confirmar eliminación",
						JOptionPane.YES_NO_OPTION
					);
					
					if (confirm == JOptionPane.YES_OPTION) {
						boolean eliminado = GestionarActividad.eliminarActividad(id);
						
						if (eliminado) {
							JOptionPane.showMessageDialog(null, "Actividad eliminada correctamente");
							cargarActividades();
						} else {
							JOptionPane.showMessageDialog(null, "Error: No se puede eliminar la actividad porque tiene programaciones asociadas");
						}
					}
				}
			}
		});

		JScrollPane scroll = new JScrollPane(tabla);

		JButton btnNuevo = new JButton("+ Nueva Actividad");
		btnNuevo.addActionListener(e -> mostrarFormularioNuevo());
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(e -> cargarActividades());

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
		JTextArea txtDescripcion = new JTextArea(3, 20);
		txtDescripcion.setLineWrap(true);
		JComboBox<String> comboNivel = new JComboBox<>(new String[]{"iniciacion", "medio", "avanzado"});
		
		panel.add(new JLabel("Nombre:"));
		panel.add(txtNombre);
		panel.add(new JLabel("Descripción:"));
		panel.add(new JScrollPane(txtDescripcion));
		panel.add(new JLabel("Nivel:"));
		panel.add(comboNivel);
		
		int resultado = JOptionPane.showConfirmDialog(this, panel, "Nueva Actividad", 
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		
		if (resultado == JOptionPane.OK_OPTION) {
			
			if (txtNombre.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "El nombre es obligatorio");
				return;
			}
			
			Actividad nueva = new Actividad(0, txtNombre.getText(), txtDescripcion.getText(), (String) comboNivel.getSelectedItem());
			boolean insertado = GestionarActividad.insertarActividad(nueva);
			
			if (insertado) {
				JOptionPane.showMessageDialog(this, "Actividad creada correctamente");
				cargarActividades();
			} else {
				JOptionPane.showMessageDialog(this, "Error al crear la actividad");
			}
		}
	}
	
	private void mostrarFormularioEditar(Actividad actividad) {
		
		JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
		JTextField txtNombre = new JTextField(actividad.getNombre(), 15);
		JTextArea txtDescripcion = new JTextArea(actividad.getDescripcion(), 3, 20);
		txtDescripcion.setLineWrap(true);
		JComboBox<String> comboNivel = new JComboBox<>(new String[]{"iniciacion", "medio", "avanzado"});
		comboNivel.setSelectedItem(actividad.getNivel());
		
		panel.add(new JLabel("Nombre:"));
		panel.add(txtNombre);
		panel.add(new JLabel("Descripción:"));
		panel.add(new JScrollPane(txtDescripcion));
		panel.add(new JLabel("Nivel:"));
		panel.add(comboNivel);
		
		int resultado = JOptionPane.showConfirmDialog(this, panel, "Editar Actividad", 
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		
		if (resultado == JOptionPane.OK_OPTION) {
			
			if (txtNombre.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "El nombre es obligatorio");
				return;
			}
			
			actividad.setNombre(txtNombre.getText());
			actividad.setDescripcion(txtDescripcion.getText());
			actividad.setNivel((String) comboNivel.getSelectedItem());
			
			boolean actualizado = GestionarActividad.actualizarActividad(actividad);
			
			if (actualizado) {
				JOptionPane.showMessageDialog(this, "Actividad actualizada correctamente");
				cargarActividades();
			} else {
				JOptionPane.showMessageDialog(this, "Error al actualizar la actividad");
			}
		}
	}

	private void cargarActividades() {

		modelo.setRowCount(0);

		Object[][] datos = GestionarActividad.mostrarActividades();

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
		public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			return this;
		}
	}
}