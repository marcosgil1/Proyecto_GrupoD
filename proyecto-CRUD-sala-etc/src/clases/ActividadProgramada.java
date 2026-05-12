package clases;

public class ActividadProgramada {
	private int idActividad;
	private String fechaInicio;
	private String fechaFin;
	private String dniEntrenador;
	private int idSala;

	// Campos adicionales para mostrar
	private String nombreActividad;
	private String nombreEntrenador;
	private String nombreSala;

	public ActividadProgramada(int idActividad, String fechaInicio, String fechaFin, String dniEntrenador, int idSala) {
		this.idActividad = idActividad;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.dniEntrenador = dniEntrenador;
		this.idSala = idSala;
	}

	// Getters y Setters (generarlos)
	public int getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(int idActividad) {
		this.idActividad = idActividad;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getDniEntrenador() {
		return dniEntrenador;
	}

	public void setDniEntrenador(String dniEntrenador) {
		this.dniEntrenador = dniEntrenador;
	}

	public int getIdSala() {
		return idSala;
	}

	public void setIdSala(int idSala) {
		this.idSala = idSala;
	}

	public String getNombreActividad() {
		return nombreActividad;
	}

	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}

	public String getNombreEntrenador() {
		return nombreEntrenador;
	}

	public void setNombreEntrenador(String nombreEntrenador) {
		this.nombreEntrenador = nombreEntrenador;
	}

	public String getNombreSala() {
		return nombreSala;
	}

	public void setNombreSala(String nombreSala) {
		this.nombreSala = nombreSala;
	}
}