let index = 0;
        const carousel = document.querySelector(".carousel-wrapper");
        const totalItems = document.querySelectorAll(".carousel-item").length;

        function moveSlide(step) {
            index += step;
            if (index >= totalItems) index = 0;
            if (index < 0) index = totalItems - 1;
            updateCarousel();
        }

        function updateCarousel() {
            carousel.style.transform = `translateX(-${index * 100}%)`;
        }

        // Função para passar automaticamente a cada 3 segundos
        function autoSlide() {
            moveSlide(1);
        }

        // Inicia o carrossel automático
        let autoSlideInterval = setInterval(autoSlide, 3000);

        // Para o carrossel automático ao interagir com os botões
        document.querySelectorAll(".carousel-btn").forEach(button => {
            button.addEventListener("click", () => {
                clearInterval(autoSlideInterval);  // Para o intervalo
                autoSlideInterval = setInterval(autoSlide, 3000); // Reinicia
            });
        });