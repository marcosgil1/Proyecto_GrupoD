	package clases;

public class Maquinaria {
	private int idMaquinaria;
	private String tipo;
	private String marca;
	private String nSerie;
	private String estado;
	private String fechaCompra;
	private String fechaUltimoMantenimiento;
	private int idSala;
	private String nombreSala;

	public Maquinaria(int idMaquinaria, String tipo, String marca, String nSerie, String estado, String fechaCompra,
			String fechaUltimoMantenimiento, int idSala) {
		this.idMaquinaria = idMaquinaria;
		this.tipo = tipo;
		this.marca = marca;
		this.nSerie = nSerie;
		this.estado = estado;
		this.fechaCompra = fechaCompra;
		this.fechaUltimoMantenimiento = fechaUltimoMantenimiento;
		this.idSala = idSala;
	}

	public int getIdMaquinaria() {
		return idMaquinaria;
	}

	public void setIdMaquinaria(int idMaquinaria) {
		this.idMaquinaria = idMaquinaria;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getnSerie() {
		return nSerie;
	}

	public void setnSerie(String nSerie) {
		this.nSerie = nSerie;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(String fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public String getFechaUltimoMantenimiento() {
		return fechaUltimoMantenimiento;
	}

	public void setFechaUltimoMantenimiento(String fechaUltimoMantenimiento) {
		this.fechaUltimoMantenimiento = fechaUltimoMantenimiento;
	}

	public int getIdSala() {
		return idSala;
	}

	public void setIdSala(int idSala) {
		this.idSala = idSala;
	}

	public String getNombreSala() {
		return nombreSala;
	}

	public void setNombreSala(String nombreSala) {
		this.nombreSala = nombreSala;
	}
}