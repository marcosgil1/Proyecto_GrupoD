package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clases.Socio;
import clases.Usuario;
import controlador.GestionarUsuario;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InsertarUsuarioGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTextField textDni;
	private JTextField textNombre;
	private JTextField textApellidos;
	private JTextField textTelefono;
	private JTextField textEmail;
	private JTextField textFechaAlta;
	private JTextField textEstado;
	private JTextField textContrasena;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InsertarUsuarioGUI frame = new InsertarUsuarioGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public InsertarUsuarioGUI() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 350);

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

		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.setBounds(318, 150, 105, 27);

		btnEnviar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				Usuario u = new Socio(textDni.getText(), textNombre.getText(), textApellidos.getText(),textTelefono.getText(), textEmail.getText(), textFechaAlta.getText(), textEstado.getText(),textContrasena.getText());

				GestionarUsuario.insertarUsuario(u);

				System.out.println("Socio insertado correctamente");
			}
		});

		contentPane.add(btnEnviar);
	}
}
