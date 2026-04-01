document.addEventListener('DOMContentLoaded', function () {
    const accordionGroups = document.querySelectorAll('.shield-accordions__list');

    accordionGroups.forEach(group => {
        const accordionItems = group.querySelectorAll('.shield-accordion-item');

        accordionItems.forEach(item => {
            const button = item.querySelector('.shield-accordion-item__button');
            const panel = item.querySelector('.shield-accordion-item__panel');

            if (button && panel) {
                button.addEventListener('click', () => {
                    const isExpanded = button.getAttribute('aria-expanded') === 'true';

                    // Optional: Close all other accordions in the same group
                    accordionItems.forEach(otherItem => {
                        const otherButton = otherItem.querySelector('.shield-accordion-item__button');
                        const otherPanel = otherItem.querySelector('.shield-accordion-item__panel');
                        if (otherButton !== button) {
                            otherButton.setAttribute('aria-expanded', 'false');
                            otherPanel.setAttribute('hidden', '');
                        }
                    });

                    // Toggle the clicked accordion
                    if (isExpanded) {
                        button.setAttribute('aria-expanded', 'false');
                        panel.setAttribute('hidden', '');
                    } else {
                        button.setAttribute('aria-expanded', 'true');
                        panel.removeAttribute('hidden');
                    }
                });
            }
        });
    });
});
