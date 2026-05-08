package clases;

public class SocioFlexible extends Socio {
	
	private SocioFlexible(String dni, String nombre, String apellidos, String telefono, String email, String fechaAlta,
			String estado, String contrasena, double cuotaMensual) {
		super(dni, nombre, apellidos, telefono, email, fechaAlta, estado, contrasena);
		this.cuotaMensual = cuotaMensual;
	}

	private double cuotaMensual;

	public double getCuotaMensual() {
		return cuotaMensual;
	}

	public void setCuotaMensual(double cuotaMensual) {
		this.cuotaMensual = cuotaMensual;
	}

}
