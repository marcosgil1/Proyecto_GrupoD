package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import clases.Socio;
import clases.Usuario;
import controlador.GestionarUsuario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EliminarUsuarioGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textDni;

	public EliminarUsuarioGUI() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUsuarioAEliminar = new JLabel("USUARIO A ELIMINAR");
		lblUsuarioAEliminar.setBounds(140, 40, 200, 20);
		contentPane.add(lblUsuarioAEliminar);

		JLabel lblDni = new JLabel("DNI");
		lblDni.setBounds(100, 90, 60, 20);
		contentPane.add(lblDni);

		textDni = new JTextField();
		textDni.setBounds(170, 90, 150, 25);
		contentPane.add(textDni);

		JButton btnEliminar = new JButton("ELIMINAR");
		btnEliminar.setBounds(170, 140, 120, 30);

		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String dni = textDni.getText();
				Usuario u = new Socio(dni, null, null, null, null, null, null, null);

				GestionarUsuario.eliminarUsuario(u);
			}
		});

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(170, 190, 120, 30);
		btnVolver.addActionListener(e -> dispose());

		contentPane.add(btnEliminar);
		contentPane.add(btnVolver);
	}
}