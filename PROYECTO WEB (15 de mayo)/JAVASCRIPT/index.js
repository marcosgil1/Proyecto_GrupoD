fetch("../XML/feed.xml")
    .then(response => {
        if (!response.ok) {
            throw new Error("No se pudo cargar el XML");
        }
        return response.text();
    })
    .then(data => {

        const parser = new DOMParser();
        const xml = parser.parseFromString(data, "text/xml");

        const items = xml.querySelectorAll("item");

        let noticias = "";

        items.forEach(item => {

            const titulo =
                item.querySelector("title").textContent;

            const descripcion =
                item.querySelector("description").textContent;

            const fecha =
                item.querySelector("pubDate").textContent;

            const fechaFormateada =
                new Date(fecha).toLocaleDateString("es-ES");

            noticias += `
                <article class="card">
                    <h4>${titulo}</h4>
                    <p>${descripcion}</p>
                    <p><small>Publicado: ${fechaFormateada}</small></p>
                </article>
            `;
        });

        document.getElementById("novedad").innerHTML = noticias;
    })
    .catch(error => {
        console.error("Error cargando el RSS:", error);
    });