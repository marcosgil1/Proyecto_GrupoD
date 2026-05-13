package clases;

public class SocioFlexible extends Socio {
	
	private double precioPorActividad;

	public SocioFlexible(String dni, String nombre, String apellidos, String telefono, String email, String fechaAlta,
			String estado, String contrasena, double precioPorActividad) {
		super(dni, nombre, apellidos, telefono, email, fechaAlta, estado, contrasena);
		this.precioPorActividad = precioPorActividad;
	}

	public double getPrecioPorActividad() {
		return precioPorActividad;
	}

	public void setPrecioPorActividad(double precioPorActividad) {
		this.precioPorActividad = precioPorActividad;
	}
}