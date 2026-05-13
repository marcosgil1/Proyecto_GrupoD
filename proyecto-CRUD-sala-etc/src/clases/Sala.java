package clases;

public class Sala {
	private int idSala;
	private String nombre;
	private double metrosCuadrados;
	private int aforoMax;

	public Sala(int idSala, String nombre, double metrosCuadrados, int aforoMax) {
		this.idSala = idSala;
		this.nombre = nombre;
		this.metrosCuadrados = metrosCuadrados;
		this.aforoMax = aforoMax;
	}

	public int getIdSala() {
		return idSala;
	}

	public void setIdSala(int idSala) {
		this.idSala = idSala;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getMetrosCuadrados() {
		return metrosCuadrados;
	}

	public void setMetrosCuadrados(double metrosCuadrados) {
		this.metrosCuadrados = metrosCuadrados;
	}

	public int getAforoMax() {
		return aforoMax;
	}

	public void setAforoMax(int aforoMax) {
		this.aforoMax = aforoMax;
	}
}