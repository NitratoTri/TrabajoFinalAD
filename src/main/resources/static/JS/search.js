const searchInput = document.getElementById('search-input');
const suggestionsList = document.getElementById('suggestions');

// Handle input event for suggestions
searchInput.addEventListener('input', function () {
    const query = this.value.trim();

    if (query.length > 2) {
        fetch(`/productos/autocomplete?query=${encodeURIComponent(query)}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch suggestions');
                }
                return response.json();
            })
            .then(data => {
                suggestionsList.innerHTML = ''; // Clear previous suggestions
                if (data.length === 0) {
                    const noResults = document.createElement('li');
                    noResults.textContent = 'No results found';
                    noResults.className = 'list-group-item text-muted';
                    suggestionsList.appendChild(noResults);
                } else {
                    data.forEach(nombre => {
                        const li = document.createElement('li');
                        li.textContent = nombre;
                        li.className = 'list-group-item list-group-item-action';
                        li.addEventListener('click', () => {
                            searchInput.value = nombre; // Autocomplete the input
                            suggestionsList.innerHTML = ''; // Clear suggestions
                        });
                        suggestionsList.appendChild(li);
                    });
                }
            })
            .catch(error => {
                console.error('Error fetching suggestions:', error);
                suggestionsList.innerHTML = ''; // Clear suggestions on error
                const errorItem = document.createElement('li');
                errorItem.textContent = 'Error loading suggestions';
                errorItem.className = 'list-group-item text-danger';
                suggestionsList.appendChild(errorItem);
            });
    } else {
        suggestionsList.innerHTML = ''; // Clear suggestions if query is too short
    }
});

// Handle "Enter" key event
searchInput.addEventListener('keydown', function (event) {
    if (event.key === 'Enter') {
        event.preventDefault(); // Prevent default form submission
        const query = this.value.trim();
        if (query) {
            // Redirect to the search results page
            window.location.href = `/productos/searchalike?query=${encodeURIComponent(query)}`;
        } else {
            alert('Please enter a search query'); // Prevent empty searches
        }
    }
})
// Handle button click event
const searchButton = document.getElementById('search-button');
searchButton.addEventListener('click', function () {
    const query = searchInput.value.trim();
    if (query) {
        // Redirect to the search results page
        window.location.href = `/productos/searchalike?query=${encodeURIComponent(query)}`;
    } else {
        alert('Please enter a search query'); // Prevent empty searches
    }
});