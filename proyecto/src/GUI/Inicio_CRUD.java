package GUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;

public class Inicio_CRUD extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inicio_CRUD frame = new Inicio_CRUD();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Inicio_CRUD() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitulo = new JLabel("CRUD PRINCIPAL");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(125, 30, 250, 40);
		contentPane.add(lblTitulo);

		JButton btnInsertar = new JButton("INSERTAR");
		btnInsertar.setFont(new Font("Arial", Font.PLAIN, 14));
		btnInsertar.setBounds(175, 100, 150, 40);
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertarUsuarioGUI insertarGUI = new InsertarUsuarioGUI();
				insertarGUI.setVisible(true);
			}
		});
		contentPane.add(btnInsertar);

		JButton btnLeer = new JButton("LEER");
		btnLeer.setFont(new Font("Arial", Font.PLAIN, 14));
		btnLeer.setBounds(175, 155, 150, 40);
		btnLeer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LeerUsuarioGUI leerGUI = new LeerUsuarioGUI();
				leerGUI.setVisible(true);
			}
		});
		contentPane.add(btnLeer);

		JButton btnEliminar = new JButton("ELIMINAR");
		btnEliminar.setFont(new Font("Arial", Font.PLAIN, 14));
		btnEliminar.setBounds(175, 207, 150, 40);
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EliminarUsuarioGUI eliminarGUI = new EliminarUsuarioGUI();
				eliminarGUI.setVisible(true);
			}
		});
		contentPane.add(btnEliminar);
	}

}