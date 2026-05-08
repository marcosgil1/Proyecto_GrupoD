package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clases.Socio;
import clases.Usuario;
import controlador.GestionarUsuario;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EliminarUsuarioGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textDni;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EliminarUsuarioGUI frame = new EliminarUsuarioGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

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
		textDni.setColumns(10);

		JButton btnEliminar = new JButton("ELIMINAR");

		btnEliminar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String dni = textDni.getText();

				Usuario u = new Socio(dni, null, null, null, null, null, null, null);

				GestionarUsuario.eliminarUsuario(u);

			}
		});

		btnEliminar.setBounds(170, 140, 120, 30);
		contentPane.add(btnEliminar);
	}
}