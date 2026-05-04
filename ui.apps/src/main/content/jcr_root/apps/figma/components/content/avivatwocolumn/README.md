# Aviva Two-Column Card Group Component

## Description

The Aviva Two-Column Card Group component is designed to display a collection of cards in a visually appealing, responsive layout. Each card consists of an image on the left and content (title, description, and calls-to-action) on the right. On mobile devices, the layout stacks vertically with the image on top.

## Features

- **Card Group**: Authors can create one or more cards within a single component instance.
- **Two-Column Layout**: On desktop, each card presents a side-by-side view of an image and its associated content.
- **Responsive Stacking**: On mobile viewports, the card layout adapts to a single column, placing the image above the content for optimal readability.
- **Customizable Content**: Each card can have a unique title, optional description, image, and alt text.
- **Multiple CTAs**: Up to three Calls-to-Action (CTAs) can be added per card. The first CTA is styled as a primary button, while subsequent ones are styled as secondary buttons.

## Authoring Dialog

The component dialog is organized under a single "Properties" tab.

### Cards Multifield

This is the main configuration area for the component. Click "Add" to create a new card.

- **Title**: (Required) The main heading for the card.
- **Description**: (Optional) A short paragraph of text that appears below the title.
- **Image**: (Required) An asset path to the image for the card's left column. Use the path browser to select an image from the AEM DAM.
- **Image Alt Text**: (Required for accessibility) A descriptive text for the image, used by screen readers.

### CTAs (Nested Multifield)

Within each card's configuration, you can add up to three CTAs.

- **Label**: The visible text for the button/link.
- **Link**: The destination URL or AEM page path.
- **Open in new tab**: If checked, the link will open in a new browser tab.