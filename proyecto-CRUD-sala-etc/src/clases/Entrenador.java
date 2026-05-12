package clases;

public class Entrenador extends Usuario {
	
	private String tipo;

	public Entrenador(String dni, String nombre, String apellidos, String telefono, String email, String fechaAlta,
			String estado, String contrasena, String tipo) {
		super(dni, nombre, apellidos, telefono, email, fechaAlta, estado, contrasena);
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}