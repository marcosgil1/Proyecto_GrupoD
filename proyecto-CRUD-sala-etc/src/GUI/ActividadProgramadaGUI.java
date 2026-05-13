package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controlador.GestionarActividadProgramada;
import clases.ActividadProgramada;

public class ActividadProgramadaGUI extends JFrame {

	private JTable tabla;
	private DefaultTableModel modelo;

	public ActividadProgramadaGUI() {

		setTitle("Gestión de Actividades Programadas");
		setSize(1100, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		iniciarTabla();
		cargarActividadesProgramadas();

		setVisible(true);
	}

	private void iniciarTabla() {

		modelo = new DefaultTableModel();

		modelo.addColumn("ID Actividad");
		modelo.addColumn("Actividad");
		modelo.addColumn("Fecha Inicio");
		modelo.addColumn("Fecha Fin");
		modelo.addColumn("Entrenador");
		modelo.addColumn("Sala");
		modelo.addColumn("Modificar");
		modelo.addColumn("Eliminar");

		tabla = new JTable(modelo);

		tabla.getColumnModel().getColumn(6).setPreferredWidth(100);
		tabla.getColumnModel().getColumn(7).setPreferredWidth(100);

		tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int fila = tabla.rowAtPoint(e.getPoint());
				int columna = tabla.columnAtPoint(e.getPoint());

				if (columna == 6) { // Botón Modificar
					
					int idActividad = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
					String fechaInicio = tabla.getValueAt(fila, 2).toString();
					
					ActividadProgramada ap = GestionarActividadProgramada.obtenerActividadProgramada(idActividad, fechaInicio);
					mostrarFormularioEditar(ap);
					
				} else if (columna == 7) { // Botón Eliminar
					
					int idActividad = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
					String fechaInicio = tabla.getValueAt(fila, 2).toString();
					String nombreActividad = tabla.getValueAt(fila, 1).toString();
					
					int confirm = JOptionPane.showConfirmDialog(
						null,
						"¿Estás seguro de que quieres eliminar la programación de: " + nombreActividad + " del " + fechaInicio + "?",
						"Confirmar eliminación",
						JOptionPane.YES_NO_OPTION
					);
					
					if (confirm == JOptionPane.YES_OPTION) {
						boolean eliminado = GestionarActividadProgramada.eliminarActividadProgramada(idActividad, fechaInicio);
						
						if (eliminado) {
							JOptionPane.showMessageDialog(null, "Programación eliminada correctamente");
							cargarActividadesProgramadas();
						} else {
							JOptionPane.showMessageDialog(null, "Error al eliminar la programación");
						}
					}
				}
			}
		});

		JScrollPane scroll = new JScrollPane(tabla);

		JButton btnNuevo = new JButton("+ Nueva Programación");
		btnNuevo.addActionListener(e -> mostrarFormularioNuevo());
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(e -> cargarActividadesProgramadas());

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
		
		JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
		
		// ComboBox de Actividades
		Object[][] actividades = GestionarActividadProgramada.obtenerActividadesParaCombo();
		JComboBox<String> comboActividad = new JComboBox<>();
		for (Object[] act : actividades) {
			comboActividad.addItem(act[0] + " - " + act[1]);
		}
		
		// Fechas
		JTextField txtFechaInicio = new JTextField(20);
		txtFechaInicio.setText(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		JTextField txtFechaFin = new JTextField(20);
		txtFechaFin.setText(java.time.LocalDateTime.now().plusHours(1).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		
		// ComboBox de Entrenadores (solo los que son entrenadores)
		Object[][] entrenadores = GestionarActividadProgramada.obtenerEntrenadoresParaCombo();
		JComboBox<String> comboEntrenador = new JComboBox<>();
		for (Object[] ent : entrenadores) {
			comboEntrenador.addItem(ent[0] + " - " + ent[1]);
		}
		
		// ComboBox de Salas
		Object[][] salas = GestionarActividadProgramada.obtenerSalasParaCombo();
		JComboBox<String> comboSala = new JComboBox<>();
		for (Object[] sala : salas) {
			comboSala.addItem(sala[0] + " - " + sala[1]);
		}
		
		panel.add(new JLabel("Actividad:"));
		panel.add(comboActividad);
		panel.add(new JLabel("Fecha Inicio (YYYY-MM-DD HH:MM:SS):"));
		panel.add(txtFechaInicio);
		panel.add(new JLabel("Fecha Fin (YYYY-MM-DD HH:MM:SS):"));
		panel.add(txtFechaFin);
		panel.add(new JLabel("Entrenador:"));
		panel.add(comboEntrenador);
		panel.add(new JLabel("Sala:"));
		panel.add(comboSala);
		
		int resultado = JOptionPane.showConfirmDialog(this, panel, "Nueva Actividad Programada", 
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		
		if (resultado == JOptionPane.OK_OPTION) {
			
			try {
				// Obtener ID de la actividad seleccionada
				String actSeleccionada = (String) comboActividad.getSelectedItem();
				int idActividad = Integer.parseInt(actSeleccionada.split(" - ")[0]);
				
				String fechaInicio = txtFechaInicio.getText();
				String fechaFin = txtFechaFin.getText();
				
				// Validar que fecha_fin sea mayor que fecha_inicio
				if (fechaFin.compareTo(fechaInicio) <= 0) {
					JOptionPane.showMessageDialog(this, "La fecha fin debe ser mayor que la fecha inicio");
					return;
				}
				
				// Obtener DNI del entrenador
				String entSeleccionado = (String) comboEntrenador.getSelectedItem();
				String dniEntrenador = entSeleccionado.split(" - ")[0];
				
				// Obtener ID de la sala
				String salaSeleccionada = (String) comboSala.getSelectedItem();
				int idSala = Integer.parseInt(salaSeleccionada.split(" - ")[0]);
				
				ActividadProgramada nueva = new ActividadProgramada(idActividad, fechaInicio, fechaFin, dniEntrenador, idSala);
				boolean insertado = GestionarActividadProgramada.insertarActividadProgramada(nueva);
				
				if (insertado) {
					JOptionPane.showMessageDialog(this, "Actividad programada correctamente");
					cargarActividadesProgramadas();
				} else {
					JOptionPane.showMessageDialog(this, "Error al programar la actividad");
				}
				
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error en los datos ingresados: " + ex.getMessage());
			}
		}
	}
	
	private void mostrarFormularioEditar(ActividadProgramada ap) {
		
		JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
		
		// ComboBox de Actividades
		Object[][] actividades = GestionarActividadProgramada.obtenerActividadesParaCombo();
		JComboBox<String> comboActividad = new JComboBox<>();
		int actIndex = 0;
		for (int i = 0; i < actividades.length; i++) {
			comboActividad.addItem(actividades[i][0] + " - " + actividades[i][1]);
			if (Integer.parseInt(actividades[i][0].toString()) == ap.getIdActividad()) {
				actIndex = i;
			}
		}
		comboActividad.setSelectedIndex(actIndex);
		comboActividad.setEnabled(false); // No se puede cambiar la actividad en edición
		
		// Fechas
		JTextField txtFechaInicio = new JTextField(ap.getFechaInicio(), 20);
		txtFechaInicio.setEnabled(false); // No se puede cambiar la fecha inicio (es parte de la PK)
		JTextField txtFechaFin = new JTextField(ap.getFechaFin(), 20);
		
		// ComboBox de Entrenadores
		Object[][] entrenadores = GestionarActividadProgramada.obtenerEntrenadoresParaCombo();
		JComboBox<String> comboEntrenador = new JComboBox<>();
		int entIndex = 0;
		for (int i = 0; i < entrenadores.length; i++) {
			comboEntrenador.addItem(entrenadores[i][0] + " - " + entrenadores[i][1]);
			if (entrenadores[i][0].toString().equals(ap.getDniEntrenador())) {
				entIndex = i;
			}
		}
		comboEntrenador.setSelectedIndex(entIndex);
		
		// ComboBox de Salas
		Object[][] salas = GestionarActividadProgramada.obtenerSalasParaCombo();
		JComboBox<String> comboSala = new JComboBox<>();
		int salaIndex = 0;
		for (int i = 0; i < salas.length; i++) {
			comboSala.addItem(salas[i][0] + " - " + salas[i][1]);
			if (Integer.parseInt(salas[i][0].toString()) == ap.getIdSala()) {
				salaIndex = i;
			}
		}
		comboSala.setSelectedIndex(salaIndex);
		
		panel.add(new JLabel("Actividad:"));
		panel.add(comboActividad);
		panel.add(new JLabel("Fecha Inicio:"));
		panel.add(txtFechaInicio);
		panel.add(new JLabel("Fecha Fin:"));
		panel.add(txtFechaFin);
		panel.add(new JLabel("Entrenador:"));
		panel.add(comboEntrenador);
		panel.add(new JLabel("Sala:"));
		panel.add(comboSala);
		
		int resultado = JOptionPane.showConfirmDialog(this, panel, "Editar Actividad Programada", 
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		
		if (resultado == JOptionPane.OK_OPTION) {
			
			try {
				String fechaFin = txtFechaFin.getText();
				String fechaInicio = ap.getFechaInicio();
				
				// Validar que fecha_fin sea mayor que fecha_inicio
				if (fechaFin.compareTo(fechaInicio) <= 0) {
					JOptionPane.showMessageDialog(this, "La fecha fin debe ser mayor que la fecha inicio");
					return;
				}
				
				// Obtener DNI del entrenador
				String entSeleccionado = (String) comboEntrenador.getSelectedItem();
				String dniEntrenador = entSeleccionado.split(" - ")[0];
				
				// Obtener ID de la sala
				String salaSeleccionada = (String) comboSala.getSelectedItem();
				int idSala = Integer.parseInt(salaSeleccionada.split(" - ")[0]);
				
				ap.setFechaFin(fechaFin);
				ap.setDniEntrenador(dniEntrenador);
				ap.setIdSala(idSala);
				
				boolean actualizado = GestionarActividadProgramada.actualizarActividadProgramada(ap);
				
				if (actualizado) {
					JOptionPane.showMessageDialog(this, "Programación actualizada correctamente");
					cargarActividadesProgramadas();
				} else {
					JOptionPane.showMessageDialog(this, "Error al actualizar la programación");
				}
				
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error en los datos ingresados");
			}
		}
	}

	private void cargarActividadesProgramadas() {

		modelo.setRowCount(0);

		Object[][] datos = GestionarActividadProgramada.mostrarActividadesProgramadas();

		for (Object[] fila : datos) {

			Object[] nuevaFila = new Object[8];
			System.arraycopy(fila, 0, nuevaFila, 0, 6);
			nuevaFila[6] = "Modificar";
			nuevaFila[7] = "Eliminar";

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