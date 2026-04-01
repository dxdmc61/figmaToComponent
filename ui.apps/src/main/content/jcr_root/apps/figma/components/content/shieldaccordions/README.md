# shieldAccordions Component

Generate US bank branding component which contains 1 image left alignment with shield-accordions right alignment as per attached screenshot .

## Properties

The component has the following configurable properties in its dialog:

1.  **Image**: Pathfield to select an image from the AEM DAM for the left side of the component.
2.  **Image Alt Text**: A text field for the image's alternative text, important for accessibility.
3.  **Accordions**: A multifield to configure the accordion items on the right side of the component.
    *   **Accordion Title**: The text displayed on the clickable header of each accordion item.
    *   **Accordion Description**: A rich text editor for the content that is revealed when an accordion item is expanded.

## Usage

Drag and drop the `shieldAccordions` component onto a page. Open the component's dialog to configure the image and the accordion items. The component will render the image on the left and the interactive accordion list on the right.

## Clientlibs

This component includes a client library (`figma.shieldaccordions`) that provides the necessary CSS for styling and JavaScript for the accordion functionality.