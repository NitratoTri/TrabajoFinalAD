document.addEventListener('DOMContentLoaded', function() {
    function addPaginationListeners() {
        document.querySelectorAll('#pagination .page-link').forEach(function(link) {
            link.addEventListener('click', function(e) {
                e.preventDefault();
                const page = this.getAttribute('data-page');
                if (page !== null && !this.parentElement.classList.contains('disabled') && !this.parentElement.classList.contains('active')) {
                    fetch(`/index?page=${page}`, { headers: { 'X-Requested-With': 'XMLHttpRequest' } })
                        .then(response => response.text())
                        .then(html => {
                            // Reemplaza solo el contenido interno del contenedor
                            document.querySelector('.container.my-5 > div[th\\:replace], .container.my-5 > div').innerHTML = html;
                            addPaginationListeners();
                        });
                }
            });
        });
    }
    addPaginationListeners();
});