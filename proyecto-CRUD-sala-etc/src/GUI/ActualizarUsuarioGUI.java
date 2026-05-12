package GUI;

import javax.swing.*;

import controlador.GestionarUsuario;

import java.awt.*;

public class ActualizarUsuarioGUI extends JFrame {

	private JTextField txtDni;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JTextField txtTelefono;
	private JTextField txtEmail;
	private JTextField txtFechaAlta;
	private JTextField txtEstado;
	private JTextField txtContrasena;

	public ActualizarUsuarioGUI(String dni, String nombre, String apellidos, String telefono, String email,
			String fechaAlta, String estado, String contrasena) {

		setTitle("Actualizar Usuario");
		setSize(400, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(10, 2));

		txtDni = new JTextField(dni);
		txtDni.setEditable(false); // El DNI no se puede modificar
		txtNombre = new JTextField(nombre);
		txtApellidos = new JTextField(apellidos);
		txtTelefono = new JTextField(telefono);
		txtEmail = new JTextField(email);
		txtFechaAlta = new JTextField(fechaAlta);
		txtEstado = new JTextField(estado);
		txtContrasena = new JTextField(contrasena);

		add(new JLabel("DNI"));
		add(txtDni);

		add(new JLabel("Nombre"));
		add(txtNombre);

		add(new JLabel("Apellidos"));
		add(txtApellidos);

		add(new JLabel("Telefono"));
		add(txtTelefono);

		add(new JLabel("Email"));
		add(txtEmail);

		add(new JLabel("Fecha Alta"));
		add(txtFechaAlta);

		add(new JLabel("Estado"));
		add(txtEstado);

		add(new JLabel("Contraseña"));
		add(txtContrasena);

		JButton btnGuardar = new JButton("Guardar cambios");
		btnGuardar.addActionListener(e -> actualizarUsuario());

		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(e -> dispose());

		add(btnGuardar);
		add(btnVolver);

		setVisible(true);
	}

	private void actualizarUsuario() {

		String dni = txtDni.getText();
		String nombre = txtNombre.getText();
		String apellidos = txtApellidos.getText();
		String telefono = txtTelefono.getText();
		String email = txtEmail.getText();
		String fechaAlta = txtFechaAlta.getText();
		String estado = txtEstado.getText();
		String contrasena = txtContrasena.getText();

		boolean actualizado = GestionarUsuario.actualizarUsuario(dni, nombre, apellidos, telefono, email, fechaAlta, estado, contrasena);
		
		if(actualizado) {
			JOptionPane.showMessageDialog(null, "Usuario actualizado correctamente");
			dispose(); // Cerrar la ventana después de actualizar
		} else {
			JOptionPane.showMessageDialog(null, "Error: No se pudo actualizar el usuario");
		}
	}
}