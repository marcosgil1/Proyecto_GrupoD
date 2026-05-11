package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import clases.Socio;
import clases.Usuario;
import controlador.GestionarUsuario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertarUsuarioGUI extends JFrame {

	private JPanel contentPane;

	private JTextField textDni;
	private JTextField textNombre;
	private JTextField textApellidos;
	private JTextField textTelefono;
	private JTextField textEmail;
	private JTextField textFechaAlta;
	private JTextField textEstado;
	private JTextField textContrasena;

	private JComboBox<String> comboRol; 
	public InsertarUsuarioGUI() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblDni = new JLabel("DNI");
		lblDni.setBounds(47, 30, 60, 17);
		contentPane.add(lblDni);

		textDni = new JTextField();
		textDni.setBounds(126, 28, 114, 21);
		contentPane.add(textDni);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(47, 59, 60, 17);
		contentPane.add(lblNombre);

		textNombre = new JTextField();
		textNombre.setBounds(126, 61, 114, 21);
		contentPane.add(textNombre);

		JLabel lblApellidos = new JLabel("Apellidos");
		lblApellidos.setBounds(47, 96, 70, 17);
		contentPane.add(lblApellidos);

		textApellidos = new JTextField();
		textApellidos.setBounds(126, 94, 114, 21);
		contentPane.add(textApellidos);

		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setBounds(47, 125, 70, 17);
		contentPane.add(lblTelefono);

		textTelefono = new JTextField();
		textTelefono.setBounds(126, 127, 114, 21);
		contentPane.add(textTelefono);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(47, 155, 60, 17);
		contentPane.add(lblEmail);

		textEmail = new JTextField();
		textEmail.setBounds(126, 153, 114, 21);
		contentPane.add(textEmail);

		JLabel lblFechaAlta = new JLabel("Fecha Alta");
		lblFechaAlta.setBounds(34, 184, 96, 17);
		contentPane.add(lblFechaAlta);

		textFechaAlta = new JTextField();
		textFechaAlta.setBounds(126, 182, 114, 21);
		contentPane.add(textFechaAlta);

		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setBounds(34, 213, 96, 17);
		contentPane.add(lblEstado);

		textEstado = new JTextField();
		textEstado.setBounds(126, 209, 114, 21);
		contentPane.add(textEstado);

		JLabel lblContrasena = new JLabel("Contraseña");
		lblContrasena.setBounds(34, 242, 96, 17);
		contentPane.add(lblContrasena);

		textContrasena = new JTextField();
		textContrasena.setBounds(126, 242, 114, 21);
		contentPane.add(textContrasena);

		JLabel lblRol = new JLabel("Rol");
		lblRol.setBounds(47, 270, 60, 17);
		contentPane.add(lblRol);

		comboRol = new JComboBox<>();
		comboRol.setBounds(126, 270, 150, 21);
		comboRol.addItem("Socio");
		comboRol.addItem("Entrenador");
		comboRol.addItem("Administrador");
		comboRol.addItem("Recepcionista");
		contentPane.add(comboRol);

		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.setBounds(135, 310, 105, 27);

		btnEnviar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				Usuario u = new Socio(textDni.getText(), textNombre.getText(), textApellidos.getText(),
						textTelefono.getText(), textEmail.getText(), textFechaAlta.getText(), textEstado.getText(),
						textContrasena.getText());

				String rol = (String) comboRol.getSelectedItem();

				GestionarUsuario.insertarUsuario(u, rol);

			}
		});

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(274, 310, 105, 27);
		btnVolver.addActionListener(e -> dispose());

		contentPane.add(btnEnviar);
		contentPane.add(btnVolver);
	}
}