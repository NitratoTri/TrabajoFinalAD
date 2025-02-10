document.addEventListener("DOMContentLoaded", function () {
    // Selecciona todos los botones de "Añadir al carrito"
    const addToCartButtons = document.querySelectorAll(".add-to-cart-btn");

    addToCartButtons.forEach((button) => {
        button.addEventListener("click", function (event) {
            event.preventDefault(); // Evita que el formulario se envíe y recargue la página

            // Obtén el formulario asociado al botón
            const form = this.closest(".add-to-cart-form");

            // Serializa los datos del formulario
            const formData = new FormData(form);

            // Envía los datos al servidor mediante fetch
            fetch(form.action, {
                method: "POST",
                body: formData,
            })
                .then((response) => {
                    if (!response.ok) {
                        throw new Error("Error al añadir el producto al carrito");
                    }
                    return response.text(); // O JSON si tu respuesta es en formato JSON
                })
                .then((data) => {
                    // Muestra un mensaje de éxito (puedes personalizarlo)
                    alert("Producto añadido al carrito");

                    // Opcional: Actualiza dinámicamente algún elemento del DOM, como un contador del carrito
                    const cartCount = document.getElementById("cart-count");
                    if (cartCount) {
                        cartCount.textContent = parseInt(cartCount.textContent) + 1;
                    }
                })
                .catch((error) => {
                    console.error(error);
                    alert("Hubo un error al añadir el producto");
                });
        });
    });
});
