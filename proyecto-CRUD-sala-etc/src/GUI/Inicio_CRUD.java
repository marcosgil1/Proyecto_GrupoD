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

	public Inicio_CRUD() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitulo = new JLabel("CRUD GIMNASIO");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(125, 20, 250, 40);
		contentPane.add(lblTitulo);

		// Usuarios
		JLabel lblUsuarios = new JLabel("=== USUARIOS ===");
		lblUsuarios.setBounds(50, 80, 200, 25);
		contentPane.add(lblUsuarios);

		JButton btnInsertarUsuario = new JButton("Insertar Usuario");
		btnInsertarUsuario.setBounds(220, 80, 200, 25);
		btnInsertarUsuario.addActionListener(e -> new InsertarUsuarioGUI().setVisible(true));
		contentPane.add(btnInsertarUsuario);

		JButton btnLeerUsuario = new JButton("Ver Usuarios");
		btnLeerUsuario.setBounds(220, 110, 200, 25);
		btnLeerUsuario.addActionListener(e -> new LeerUsuarioGUI());
		contentPane.add(btnLeerUsuario);

		JButton btnEliminarUsuario = new JButton("Eliminar Usuario");
		btnEliminarUsuario.setBounds(220, 140, 200, 25);
		btnEliminarUsuario.addActionListener(e -> new EliminarUsuarioGUI().setVisible(true));
		contentPane.add(btnEliminarUsuario);

		// Salas
		JLabel lblSalas = new JLabel("=== SALAS ===");
		lblSalas.setBounds(50, 190, 200, 25);
		contentPane.add(lblSalas);

		JButton btnSalas = new JButton("Gestionar Salas");
		btnSalas.setBounds(220, 190, 200, 25);
		btnSalas.addActionListener(e -> new SalaGUI());
		contentPane.add(btnSalas);

		// Actividades
		JLabel lblActividades = new JLabel("=== ACTIVIDADES ===");
		lblActividades.setBounds(50, 240, 200, 25);
		contentPane.add(lblActividades);

		JButton btnActividades = new JButton("Gestionar Actividades");
		btnActividades.setBounds(220, 240, 200, 25);
		btnActividades.addActionListener(e -> new ActividadGUI());
		contentPane.add(btnActividades);

		// Actividades Programadas
		JLabel lblActProgramada = new JLabel("=== ACT. PROGRAMADAS ===");
		lblActProgramada.setBounds(50, 290, 250, 25);
		contentPane.add(lblActProgramada);

		JButton btnActProgramada = new JButton("Gestionar Programaciones");
		btnActProgramada.setBounds(220, 290, 200, 25);
		btnActProgramada.addActionListener(e -> new ActividadProgramadaGUI());
		contentPane.add(btnActProgramada);

		// Maquinaria
		JLabel lblMaquinaria = new JLabel("=== MAQUINARIA ===");
		lblMaquinaria.setBounds(50, 340, 200, 25);
		contentPane.add(lblMaquinaria);

		JButton btnMaquinaria = new JButton("Gestionar Maquinaria");
		btnMaquinaria.setBounds(220, 340, 200, 25);
		btnMaquinaria.addActionListener(e -> new MaquinariaGUI());
		contentPane.add(btnMaquinaria);
	}
}