package clases;

public class Actividad {
	private int idActividad;
	private String nombre;
	private String descripcion;
	private String nivel;

	public Actividad(int idActividad, String nombre, String descripcion, String nivel) {
		this.idActividad = idActividad;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.nivel = nivel;
	}

	public int getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(int idActividad) {
		this.idActividad = idActividad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
}