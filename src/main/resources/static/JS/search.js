const searchInput = document.getElementById('search-input');
const suggestionsList = document.getElementById('suggestions');

// Manejar el evento de entrada en el campo de búsqueda
searchInput.addEventListener('input', function () {
    const query = this.value.trim();

    if (query.length > 2) {
        fetch(`/productos/autocomplete?query=${encodeURIComponent(query)}`)
            .then(response => response.json())
            .then(data => {
                suggestionsList.innerHTML = ''; // Limpia las sugerencias anteriores
                if (data.length === 0) {
                    const noResults = document.createElement('li');
                    noResults.textContent = 'No se encontraron resultados';
                    noResults.className = 'list-group-item text-muted';
                    suggestionsList.appendChild(noResults);
                } else {
                    data.forEach(nombre => {
                        const li = document.createElement('li');
                        li.textContent = nombre;
                        li.className = 'list-group-item list-group-item-action';
                        li.addEventListener('click', () => {
                            searchInput.value = nombre; // Autocompletar el campo
                            suggestionsList.innerHTML = ''; // Limpiar sugerencias
                        });
                        suggestionsList.appendChild(li);
                    });
                }
            })
            .catch(error => {
                console.error('Error:', error);
                suggestionsList.innerHTML = ''; // Limpia las sugerencias en caso de error
                const errorItem = document.createElement('li');
                errorItem.textContent = 'Error al cargar las sugerencias';
                errorItem.className = 'list-group-item text-danger';
                suggestionsList.appendChild(errorItem);
            });
    } else {
        suggestionsList.innerHTML = ''; // Limpia las sugerencias si el query es muy corto
    }
});

// Manejar el evento de presionar "Enter"
searchInput.addEventListener('keydown', function (event) {
    if (event.key === 'Enter') {
        event.preventDefault(); // Evitar el comportamiento por defecto
        const query = this.value.trim();
        if (query) {
            window.location.href = `/verproducto?nombre=${encodeURIComponent(query)}`;
        }
    }
});