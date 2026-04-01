# Aviva Two-Column Card Group (avivaTwoColumn)

This component displays a vertically stacked group of two-column cards, designed with the Aviva visual style.

## Features

- **Card Group**: Authors can create one or more cards that stack vertically.
- **Two-Column Layout**: Each card has a two-column layout on desktop (image on the left, content on the right) and a single-column layout on mobile (image on top, content below).
- **Rich Content**: Each card can contain a title, an optional description, an image with alt text, and up to three Call-to-Action (CTA) buttons.
- **Styled CTAs**: The first CTA is styled as a primary button, while subsequent CTAs have a secondary style.

## Authoring

1.  **Cards Multifield**: Drag the component onto the page. In the dialog, use the "Cards" multifield to manage the cards.
2.  **Add a Card**: Click "Add" to create a new card item.
3.  **Fill Card Details**:
    *   **Title**: (Required) The main heading for the card.
    *   **Description**: (Optional) A short paragraph of text below the title.
    *   **Image**: Upload an image from your local machine or drag-and-drop from the Asset Finder.
    *   **Alt Text**: Provide descriptive alternative text for the image to ensure accessibility.
4.  **Add CTAs**:
    *   Inside each card item, there is a nested "CTAs" multifield.
    *   Click "Add" to create a new CTA.
    *   **Label**: The visible text for the button.
    *   **Link**: A path to an internal AEM page or an external URL.
    *   **Open in new tab**: Check this box if the link should open in a new browser tab.
    *   **Note**: A maximum of 3 CTAs will be rendered per card.

## Responsive Behavior

-   **Desktop**: Cards are displayed in a two-column format.
-   **Mobile**: The card layout stacks, with the image appearing above the content, and CTAs stack vertically.