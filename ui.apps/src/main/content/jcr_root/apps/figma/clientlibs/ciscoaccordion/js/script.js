document.addEventListener('DOMContentLoaded', function () {
    const accordionComponents = document.querySelectorAll('.cisco-accordion-component');

    accordionComponents.forEach(component => {
        const headers = component.querySelectorAll('.accordion-header');
        const panels = component.querySelectorAll('.accordion-panel');
        const images = component.querySelectorAll('.accordion-image');

        // Set initial state: first item active
        if (headers.length > 0) {
            const firstHeader = headers[0];
            const firstPanel = panels[0];
            const firstImage = images[0];

            firstHeader.setAttribute('aria-expanded', 'true');
            firstHeader.classList.add('active');
            firstPanel.style.maxHeight = firstPanel.scrollHeight + 'px';
            if (firstImage) {
                firstImage.classList.add('active');
            }
        }

        headers.forEach(header => {
            header.addEventListener('click', function () {
                const clickedIndex = this.getAttribute('data-index');
                const isExpanded = this.getAttribute('aria-expanded') === 'true';

                // Deactivate all items
                headers.forEach((h, i) => {
                    h.setAttribute('aria-expanded', 'false');
                    h.classList.remove('active');
                    panels[i].style.maxHeight = null;
                    if (images[i]) {
                        images[i].classList.remove('active');
                    }
                });

                // Activate the clicked item only if it was not already expanded
                if (!isExpanded) {
                    this.setAttribute('aria-expanded', 'true');
                    this.classList.add('active');
                    const targetPanel = panels[clickedIndex];
                    targetPanel.style.maxHeight = targetPanel.scrollHeight + 'px';
                    const targetImage = images[clickedIndex];
                    if (targetImage) {
                        targetImage.classList.add('active');
                    }
                }
            });
        });
    });
});
