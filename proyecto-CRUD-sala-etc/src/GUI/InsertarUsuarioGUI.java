package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import clases.*;
import controlador.GestionarUsuario;

public class InsertarUsuarioGUI extends JFrame {

	private JPanel contentPane;
	private JPanel panelCamposExtras;
	private CardLayout cardLayout;

	// Campos comunes
	private JTextField textDni;
	private JTextField textNombre;
	private JTextField textApellidos;
	private JTextField textTelefono;
	private JTextField textEmail;
	private JTextField textFechaAlta;
	private JComboBox<String> comboEstado;
	private JTextField textContrasena;

	// Campos específicos
	private JTextField textTipo;           // Para entrenador
	private JTextField textCuotaMensual;   // Para socio full
	private JTextField textPrecioActividad; // Para socio flexible

	private JComboBox<String> comboRol;
	private JComboBox<String> comboTipoSocio; // Socio full o flexible

	public InsertarUsuarioGUI() {

		setTitle("Insertar Usuario");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 580, 600);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// ========== CAMPOS COMUNES ==========
		JLabel lblDni = new JLabel("DNI *");
		lblDni.setBounds(47, 30, 80, 17);
		contentPane.add(lblDni);

		textDni = new JTextField();
		textDni.setBounds(140, 28, 150, 21);
		contentPane.add(textDni);

		JLabel lblNombre = new JLabel("Nombre *");
		lblNombre.setBounds(47, 59, 80, 17);
		contentPane.add(lblNombre);

		textNombre = new JTextField();
		textNombre.setBounds(140, 61, 150, 21);
		contentPane.add(textNombre);

		JLabel lblApellidos = new JLabel("Apellidos");
		lblApellidos.setBounds(47, 96, 80, 17);
		contentPane.add(lblApellidos);

		textApellidos = new JTextField();
		textApellidos.setBounds(140, 94, 150, 21);
		contentPane.add(textApellidos);

		JLabel lblTelefono = new JLabel("Teléfono");
		lblTelefono.setBounds(47, 125, 80, 17);
		contentPane.add(lblTelefono);

		textTelefono = new JTextField();
		textTelefono.setBounds(140, 127, 150, 21);
		contentPane.add(textTelefono);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(47, 155, 80, 17);
		contentPane.add(lblEmail);

		textEmail = new JTextField();
		textEmail.setBounds(140, 153, 150, 21);
		contentPane.add(textEmail);

		JLabel lblFechaAlta = new JLabel("Fecha Alta");
		lblFechaAlta.setBounds(47, 184, 80, 17);
		contentPane.add(lblFechaAlta);

		textFechaAlta = new JTextField();
		textFechaAlta.setBounds(140, 182, 150, 21);
		textFechaAlta.setText("2026-05-11"); 
		contentPane.add(textFechaAlta);

		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setBounds(47, 213, 80, 17);
		contentPane.add(lblEstado);

		comboEstado = new JComboBox<>();
		comboEstado.setBounds(140, 209, 150, 21);
		comboEstado.addItem("activo");
		comboEstado.addItem("inactivo");
		comboEstado.addItem("moroso");
		contentPane.add(comboEstado);

		JLabel lblContrasena = new JLabel("Contraseña *");
		lblContrasena.setBounds(47, 242, 80, 17);
		contentPane.add(lblContrasena);

		textContrasena = new JTextField();
		textContrasena.setBounds(140, 242, 150, 21);
		contentPane.add(textContrasena);

		JLabel lblRol = new JLabel("Rol");
		lblRol.setBounds(47, 270, 80, 17);
		contentPane.add(lblRol);

		comboRol = new JComboBox<>();
		comboRol.setBounds(140, 270, 150, 21);
		comboRol.addItem("Socio");
		comboRol.addItem("Entrenador");
		comboRol.addItem("Administrador");
		comboRol.addItem("Recepcionista");
		contentPane.add(comboRol);

		// ========== PANEL DE CAMPOS EXTRAS ==========
		cardLayout = new CardLayout();
		panelCamposExtras = new JPanel(cardLayout);
		panelCamposExtras.setBounds(47, 310, 480, 120);
		panelCamposExtras.setBorder(BorderFactory.createTitledBorder("Datos específicos del rol"));
		contentPane.add(panelCamposExtras);

		// ----- PANEL PARA SOCIO -----
		JPanel panelSocio = new JPanel();
		panelSocio.setLayout(new BoxLayout(panelSocio, BoxLayout.Y_AXIS));
		
		JPanel panelTipoSocio = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelTipoSocio.add(new JLabel("Tipo de Socio:"));
		comboTipoSocio = new JComboBox<>();
		comboTipoSocio.addItem("Full (cuota mensual fija)");
		comboTipoSocio.addItem("Flexible (pago por actividad)");
		panelTipoSocio.add(comboTipoSocio);
		panelSocio.add(panelTipoSocio);
		
		// Panel para Socio Full (cuota mensual)
		JPanel panelCuota = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelCuota.add(new JLabel("Cuota mensual:"));
		textCuotaMensual = new JTextField(10);
		panelCuota.add(textCuotaMensual);
		panelCuota.add(new JLabel("€"));
		
		// Panel para Socio Flexible (precio por actividad)
		JPanel panelPrecio = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelPrecio.add(new JLabel("Precio por actividad:"));
		textPrecioActividad = new JTextField(10);
		panelPrecio.add(textPrecioActividad);
		panelPrecio.add(new JLabel("€"));
		
		panelSocio.add(panelCuota);
		panelSocio.add(panelPrecio);
		
		// Controlar visibilidad según tipo de socio
		comboTipoSocio.addActionListener(e -> {
			boolean isFull = comboTipoSocio.getSelectedIndex() == 0;
			panelCuota.setVisible(isFull);
			panelPrecio.setVisible(!isFull);
		});
		panelCuota.setVisible(true);
		panelPrecio.setVisible(false);
		
		panelCamposExtras.add(panelSocio, "Socio");

		// ----- PANEL PARA ENTRENADOR -----
		JPanel panelEntrenador = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelEntrenador.add(new JLabel("Tipo de entrenador (ej: Musculación, Cardio, Yoga):"));
		textTipo = new JTextField(20);
		panelEntrenador.add(textTipo);
		panelCamposExtras.add(panelEntrenador, "Entrenador");

		// ----- PANEL PARA ADMINISTRADOR (vacío) -----
		JPanel panelAdmin = new JPanel();
		panelAdmin.add(new JLabel("No se requieren datos adicionales para Administrador"));
		panelCamposExtras.add(panelAdmin, "Administrador");

		// ----- PANEL PARA RECEPCIONISTA (vacío) -----
		JPanel panelRecepcionista = new JPanel();
		panelRecepcionista.add(new JLabel("No se requieren datos adicionales para Recepcionista"));
		panelCamposExtras.add(panelRecepcionista, "Recepcionista");

		// Cambiar panel según rol seleccionado
		comboRol.addActionListener(e -> {
			String rol = (String) comboRol.getSelectedItem();
			cardLayout.show(panelCamposExtras, rol);
		});

		// ========== BOTONES ==========
		JButton btnEnviar = new JButton("Guardar");
		btnEnviar.setBounds(140, 460, 120, 30);
		btnEnviar.addActionListener(e -> guardarUsuario());

		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBounds(280, 460, 120, 30);
		btnLimpiar.addActionListener(e -> limpiarCampos());

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(210, 500, 120, 30);
		btnVolver.addActionListener(e -> dispose());

		contentPane.add(btnEnviar);
		contentPane.add(btnLimpiar);
		contentPane.add(btnVolver);
		
		// Mostrar panel inicial
		cardLayout.show(panelCamposExtras, "Socio");
	}

	private void guardarUsuario() {
		// Validar campos obligatorios
		if (textDni.getText().isEmpty() || textNombre.getText().isEmpty() || textContrasena.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "DNI, Nombre y Contraseña son obligatorios");
			return;
		}

		String dni = textDni.getText();
		String nombre = textNombre.getText();
		String apellidos = textApellidos.getText();
		String telefono = textTelefono.getText();
		String email = textEmail.getText();
		String fechaAlta = textFechaAlta.getText().isEmpty() ? "2025-03-01" : textFechaAlta.getText();
		String estado = (String) comboEstado.getSelectedItem();
		String contrasena = textContrasena.getText();
		String rol = (String) comboRol.getSelectedItem();

		boolean exito = false;

		switch (rol) {
			case "Socio":
				boolean isFull = comboTipoSocio.getSelectedIndex() == 0;
				if (isFull) {
					// Socio FULL
					String cuotaStr = textCuotaMensual.getText();
					if (cuotaStr.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Debe ingresar la cuota mensual para Socio Full");
						return;
					}
					try {
						double cuota = Double.parseDouble(cuotaStr);
						SocioFull socioFull = new SocioFull(dni, nombre, apellidos, telefono, email, fechaAlta, estado, contrasena, cuota);
						exito = GestionarUsuario.insertarSocioFull(socioFull);
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "La cuota debe ser un número válido");
						return;
					}
				} else {
					// Socio FLEXIBLE
					String precioStr = textPrecioActividad.getText();
					if (precioStr.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Debe ingresar el precio por actividad para Socio Flexible");
						return;
					}
					try {
						double precio = Double.parseDouble(precioStr);
						SocioFlexible socioFlex = new SocioFlexible(dni, nombre, apellidos, telefono, email, fechaAlta, estado, contrasena, precio);
						exito = GestionarUsuario.insertarSocioFlexible(socioFlex);
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "El precio debe ser un número válido");
						return;
					}
				}
				break;

			case "Entrenador":
				String tipo = textTipo.getText();
				if (tipo.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Debe ingresar el tipo de entrenador");
					return;
				}
				Entrenador entrenador = new Entrenador(dni, nombre, apellidos, telefono, email, fechaAlta, estado, contrasena, tipo);
				exito = GestionarUsuario.insertarEntrenador(entrenador);
				break;

			case "Administrador":
				Administrador admin = new Administrador(dni, nombre, apellidos, telefono, email, fechaAlta, estado, contrasena);
				exito = GestionarUsuario.insertarAdministrador(admin);
				break;

			case "Recepcionista":
				Recepcionista recep = new Recepcionista(dni, nombre, apellidos, telefono, email, fechaAlta, estado, contrasena);
				exito = GestionarUsuario.insertarRecepcionista(recep);
				break;
		}

		if (exito) {
			JOptionPane.showMessageDialog(null, "Usuario " + rol + " insertado correctamente");
			limpiarCampos();
		} else {
			JOptionPane.showMessageDialog(null, "Error al insertar el usuario.\nVerifique que el DNI no exista o que los datos sean correctos.");
		}
	}

	private void limpiarCampos() {
		textDni.setText("");
		textNombre.setText("");
		textApellidos.setText("");
		textTelefono.setText("");
		textEmail.setText("");
		textFechaAlta.setText("2025-03-01");
		comboEstado.setSelectedIndex(0);
		textContrasena.setText("");
		comboRol.setSelectedIndex(0);
		textTipo.setText("");
		textCuotaMensual.setText("");
		textPrecioActividad.setText("");
		comboTipoSocio.setSelectedIndex(0);
		cardLayout.show(panelCamposExtras, "Socio");
	}
}