let actividades = [];
let actividadesFiltradas = [];

const itemsPorPagina = 6;
let paginaActual = 1;

// cargar JSON
fetch("../JSON/actividades.json")
    .then(response => response.json())
    .then(data => {
        actividades = data.actividades;
        actividadesFiltradas = actividades;
        mostrarTarjetas();
        actualizarPaginacion();
    })
    .catch(error => console.error("Error cargando JSON:", error));


// mostrar tarjetas (CON PAGINACIÓN)
function mostrarTarjetas() {

    const contenedor = document.getElementById("tarjetasContainer");
    contenedor.innerHTML = "";

    const inicio = (paginaActual - 1) * itemsPorPagina;
    const fin = inicio + itemsPorPagina;

    const pagina = actividadesFiltradas.slice(inicio, fin);

    pagina.forEach(act => {

        const tarjeta = document.createElement("div");
        tarjeta.classList.add("tarjeta");

        tarjeta.innerHTML = `
            <h3>${act.sala}</h3>
            <p><strong>Actividad:</strong> ${act.actividad}</p>
            <p><strong>Entrenador:</strong> ${act.entrenador}</p>
            <p><strong>Fecha y hora:</strong> ${formatearFecha(act.fecha)} - ${act.hora}</p>
            <p><strong>Capacidad máxima:</strong> ${act.capacidad}</p>
            <p><strong>Plazas libres:</strong> ${act.plazas}</p>
        `;

        contenedor.appendChild(tarjeta);
    });

    actualizarPaginacion();
}


// paginación UI
function actualizarPaginacion() {

    const totalPaginas = Math.ceil(actividadesFiltradas.length / itemsPorPagina);

    document.getElementById("paginaInfo").textContent =
        `Página ${paginaActual} de ${totalPaginas || 1}`;

    document.getElementById("prev").disabled = paginaActual === 1;
    document.getElementById("next").disabled = paginaActual === totalPaginas || totalPaginas === 0;
}


// cambiar fecha
function formatearFecha(fecha) {
    const [anio, mes, dia] = fecha.split("-");
    return `${dia}/${mes}/${anio}`;
}


// botón filtrar
document.querySelector(".btn-filtrar").addEventListener("click", function () {

    const sala = document.getElementById("filtroSala").value.toLowerCase();
    const fecha = document.getElementById("filtroFecha").value;

    actividadesFiltradas = actividades.filter(act => {

        const coincideSala =
            sala === "" || act.sala.toLowerCase().includes(sala);

        const coincideFecha =
            fecha === "" || act.fecha === fecha;

        return coincideSala && coincideFecha;
    });

    paginaActual = 1; // importante reiniciar página
    mostrarTarjetas();
});


// botones paginación
document.getElementById("prev").addEventListener("click", () => {
    if (paginaActual > 1) {
        paginaActual--;
        mostrarTarjetas();
    }
});

document.getElementById("next").addEventListener("click", () => {
    const totalPaginas = Math.ceil(actividadesFiltradas.length / itemsPorPagina);

    if (paginaActual < totalPaginas) {
        paginaActual++;
        mostrarTarjetas();
    }
});