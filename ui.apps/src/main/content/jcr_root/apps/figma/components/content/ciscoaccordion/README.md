# ciscoAccordion Component

This component displays a two-column layout featuring an interactive accordion in the left column and a corresponding image in the right column. When an accordion item is expanded, its associated image is displayed.

## Features

- A main title for the entire component.
- A configurable list of accordion items.
- Each accordion item consists of a title, a rich text description, and an associated image.
- The image in the right column dynamically changes to match the currently selected accordion item.
- The component is fully responsive and styled according to Cisco branding guidelines.

## Dialog Properties

- **Component Title**: The main heading displayed above the accordion.
- **Accordion Items**: A multifield to add, remove, and reorder accordion items.
  - **Title**: The title of the accordion item, displayed in the header.
  - **Description**: The content of the accordion item, displayed when the item is expanded. This is a rich text field.
  - **Image**: A pathfield to select an image from the DAM that corresponds to this accordion item.