# azure_card

COMPONENT TITLE: Featured News Cards
DESCRIPTION: A "Featured News" section component matching the Azure Microsoft design system. Displays a section label, a large heading, and a horizontal grid of 3 news cards. Each card has a top image, bold title, short description, and a "Learn more" CTA button with a right-arrow icon. Cards have a white background, subtle rounded corners, and a light drop shadow. The section background is a very light blue-grey (#f0f4fa). The CTA button is a solid dark-blue rounded square icon button. Overall typography is clean, modern, and corporate — dark navy headings, medium-grey body text.

## Fields

### Section Fields (non-repeatable)
- **sectionLabel** (textfield): Small uppercase label above heading (e.g., "FEATURED NEWS").
- **sectionHeading** (textfield): Large H2 heading text (e.g., "Discover what's happening on Azure").

### Card Fields (composite multifield, min 1, max 6 cards)
- **cardImage** (fileupload / pathfield): Card hero image, 16:9 ratio.
- **cardTitle** (textfield): Bold card title, supports 1–3 lines.
- **cardDescription** (textarea): Short body text, 2–4 lines.
- **cardLinkText** (textfield): CTA label, e.g. "Learn more".
- **cardLinkUrl** (pathfield): CTA destination URL.
- **openInNewTab** (checkbox): Whether CTA opens in new tab.

## Design Notes
- **Layout**: Uses CSS Grid for a 3-column layout on desktop, which collapses to a single column on mobile devices.
- **Styling**: Adheres to the Azure design system with a light blue-grey background, white cards with shadows, and specific typography and color choices.
- **CTA**: The "Learn more" call-to-action is composed of a dark navy icon button and accompanying text.