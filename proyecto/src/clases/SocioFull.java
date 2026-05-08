package clases;

public class SocioFull extends Socio {
	
	private double precioActividad;

	private SocioFull(String dni, String nombre, String apellidos, String telefono, String email, String fechaAlta,
			String estado, String contrasena, double precioActividad) {
		super(dni, nombre, apellidos, telefono, email, fechaAlta, estado, contrasena);
		this.precioActividad = precioActividad;
	}

	public double getPrecioActividad() {
		return precioActividad;
	}

	public void setPrecioActividad(double precioActividad) {
		this.precioActividad = precioActividad;
	}
	
	
	
	
}
