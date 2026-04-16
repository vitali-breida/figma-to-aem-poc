# WKND AEM — Figma Design System Integration Rules

This document is the authoritative reference for AI-assisted Figma → AEM component generation in this repository. Follow every rule here when converting a Figma design into code. It complements `HOWTO.md` (setup) and `CLAUDE.md` (build commands).

---

## 1. Design Tokens

### 1.1 Source of truth

All design tokens live in `ui.frontend/src/main/webpack/base/sass/`:

| File | Purpose |
|------|---------|
| `_variables.scss` | Colors, typography, spacing, breakpoints |
| `_mixins.scss` | Reusable patterns (e.g., `respond-to`, `wkndiconstyle`) |
| `_wkndicons.scss` | Icon font constants and mixin |
| `_grid.scss` | 12-column responsive grid |
| `_shared.scss` | Aggregates the above for component imports |

**Always import tokens via `@import '../../base/sass/shared';`** at the top of each component SCSS file.

### 1.2 Color palette

```scss
// Base palette — never use raw hex in component code
$black:         #202020;
$gray:          #696969;
$gray-light:    #EBEBEB;
$gray-lighter:  #F7F7F7;
$white:         #ffffff;
$yellow:        #FFEA00;  // brand primary
$blue:          #0045FF;
$pink:          #FF0058;

// Semantic aliases backed by CSS custom properties
$brand-primary:   var(--brandPrimary,   $yellow);
$brand-secondary: var(--brandSecondary, $black);
$brand-third:     var(--brandThird,     $gray-light);
```

**Mapping rule:** Match a Figma color to the closest SCSS variable. If no variable matches within ~5% perceptual distance, use the CSS custom-property form (`var(--brandPrimary, …)`) rather than a raw hex.

### 1.3 Typography

```scss
$font-family-sans-serif: var(--fontFamilySansSerif, "Source Sans Pro", "Helvetica Neue", Helvetica, Arial, sans-serif);
$font-family-serif:      var(--fontFamilySerif, "Asar", Georgia, "Times New Roman", Times, serif);

$font-size-base:   18px;
$font-size-large:  var(--fontSizeLarge,  24px);
$font-size-xlarge: var(--fontSizeXLarge, 48px);
$font-size-h1:     var(--fontSizeH1, 40px);
$font-size-h2:     var(--fontSizeH2, 36px);
$font-size-h3:     var(--fontSizeH3, 24px);

$font-weight-light:     var(--fontWeightLight,    300);
$font-weight-normal:    var(--fontWeightNormal,   normal);
$font-weight-semi-bold: var(--fontWeightSemiBold, 400);
$font-weight-bold:      var(--fontWeightBold,     600);

$line-height-base: 1.5;
```

### 1.4 Spacing & layout

```scss
$gutter-padding:       14px;
$max-width:            1164px;   // content column max
$max-body-width:       1680px;   // full-bleed container max
$header-height:        var(--headerHeight,       200px);
$header-mobile-height: var(--headerMobileHeight, 130px);
$button-size:          var(--buttonSize,         48px);
$button-border-radius: var(--buttonBorderRadius, 0px);
```

Use `$gutter-padding` for consistent horizontal padding on containers. Use `$max-width` for centered content.

### 1.5 Responsive breakpoints

```scss
$screen-xsmall: 475px;   // extra-small phones
$screen-small:  767px;   // phones  (< 767px)
$screen-medium: 1024px;  // tablets (768px – 1024px)
$screen-large:  1200px;  // desktop (> 1200px)
```

**Media query pattern:**

```scss
// Phone
@media (max-width: $screen-small) { … }

// Tablet
@media (min-width: ($screen-small + 1)) and (max-width: $screen-medium) { … }

// Desktop (default — mobile-first styles go here or outside breakpoints)
@media (min-width: ($screen-medium + 1)) { … }
```

---

## 2. Naming Conventions — BEM with `cmp-` prefix

All component CSS follows BEM with the mandatory `cmp-` prefix:

```
.cmp-{component}                        Block
.cmp-{component}__{element}             Element
.cmp-{component}--{modifier}            Modifier on Block
.cmp-{component}__{element}--{modifier} Modifier on Element
```

**Examples:**

```scss
.cmp-button { … }
.cmp-button__text { … }
.cmp-button__icon { … }
.cmp-button--primary { … }
.cmp-button--secondary { … }
.cmp-button--icononly { … }

.cmp-teaser { … }
.cmp-teaser__image { … }
.cmp-teaser__content { … }
.cmp-teaser__title { … }
.cmp-teaser__description { … }
.cmp-teaser__action-link { … }
```

**Rule:** When mapping Figma layer names to CSS classes, convert layer names to lowercase-hyphen and prepend `cmp-{component}__`. Modifiers become `--{variant}` suffixes.

---

## 3. Component Architecture

### 3.1 Directory layout

Every component requires entries in **both** `ui.frontend` and `ui.apps`:

```
ui.frontend/src/main/webpack/components/{name}/
├── scss/
│   ├── {name}.scss          ← entry: @import 'styles/…'
│   └── styles/
│       ├── _default.scss    ← base styles
│       └── _{variant}.scss  ← per-style-system variant (optional)
└── js/ or ts/               ← behavioral script (optional)
    └── {name}.ts

ui.apps/src/main/content/jcr_root/apps/wknd/components/{name}/
├── .content.xml             ← component definition (required)
├── {name}.html              ← HTL template (required)
└── _cq_dialog/
    └── .content.xml         ← authoring dialog (required for editable fields)
```

### 3.2 SCSS entry file pattern

```scss
// ui.frontend/src/main/webpack/components/mycomponent/scss/mycomponent.scss
@import '../../base/sass/shared';
@import 'styles/default';
// @import 'styles/variant';  // add per variant
```

### 3.3 HTL template pattern

```html
<!-- ui.apps/.../components/mycomponent/mycomponent.html -->
<sly data-sly-use.model="com.adobe.aem.guides.wknd.core.models.MyComponent" />
<div class="cmp-mycomponent
            ${model.styleClasses ? model.styleClasses : ''}
            ${!wcmmode.disabled ? 'cmp-mycomponent--editing' : ''}"
     data-cmp-is="mycomponent">
    <div class="cmp-mycomponent__content">
        ${model.text @ context='html'}
    </div>
</div>
```

### 3.4 AEM component definition (.content.xml)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<jcr:root xmlns:sling="http://sling.apache.org/jcr/sling/1.0"
          xmlns:cq="http://www.day.com/jcr/cq/1.0"
          xmlns:jcr="http://www.jcp.org/jcr/1.0"
    jcr:primaryType="cq:Component"
    jcr:title="My Component"
    sling:resourceSuperType="core/wcm/components/commons/v1/commons"
    componentGroup="WKND Sites Project - Content"/>
```

**Always** set `sling:resourceSuperType` to the closest AEM Core Component (`v2.30.2`) unless there is no applicable parent.

### 3.5 Sling Model pattern (Java)

```java
// core/src/main/java/com/adobe/aem/guides/wknd/core/models/impl/MyComponentImpl.java
@Model(adaptables = {SlingHttpServletRequest.class},
       adapters  = {MyComponent.class},
       resourceType = {MyComponentImpl.RESOURCE_TYPE},
       defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MyComponentImpl implements MyComponent {
    static final String RESOURCE_TYPE = "wknd/components/mycomponent";

    @ValueMapValue
    private String text;

    @Override
    public String getText() { return text; }
}
```

---

## 4. Icon System

### 4.1 Font files

```
ui.frontend/src/main/webpack/resources/fonts/
├── wknd-icon-font.ttf
├── wknd-icon-font.woff
└── wknd-icon-font.svg
```

### 4.2 Available icons

Defined as SCSS constants in `_wkndicons.scss`:

```scss
$wkndicon-menu:        "\e916";
$wkndicon-facebook:    "\e902";
$wkndicon-twitter:     "\e901";
$wkndicon-instagram:   "\e903";
$wkndicon-google:      "\e900";
$wkndicon-social-share:"\e904";
$wkndicon-search:      "\e913";
$wkndicon-download:    "\e907";
$wkndicon-share:       "\e90b";
$wkndicon-email:       "\e909";
$wkndicon-map:         "\e905";
$wkndicon-plus:        "\e911";
$wkndicon-minus:       "\e910";
$wkndicon-cart:        "\e915";
$wkndicon-lock:        "\e98f";
$wkndicon-play3:       "\ea1c";
```

### 4.3 Icon usage

```scss
// Apply the icon font to any element
@include wkndiconstyle();

// Render a specific icon via ::before
.cmp-button__icon--search::before {
    content: $wkndicon-search;
}
```

```html
<!-- HTL markup -->
<span class="cmp-button__icon cmp-button__icon--search"></span>
```

**Rule:** When a Figma design contains an icon, match it to the closest existing `$wkndicon-*` constant. Do **not** export SVGs from Figma and add new font glyphs — reuse the existing icon font.

---

## 5. Asset Management

### 5.1 Static assets

```
ui.frontend/src/main/webpack/resources/
├── fonts/          ← icon font (TTF, WOFF, SVG)
├── images/
│   ├── country-flags/   ← SVG country flags
│   ├── favicons/        ← PNG favicons at multiple sizes
│   └── loading-icon.svg
└── manifest.json   ← PWA manifest
```

### 5.2 Referencing assets in SCSS

Assets are copied verbatim by `file-loader` with the path structure preserved into `clientlib-site/resources/`. Reference them with relative paths from the compiled CSS:

```scss
@font-face {
    font-family: '#{$icomoon-font-family}';
    src: url('#{$icomoon-font-path}/wknd-icon-font.ttf') format('truetype'),
         url('#{$icomoon-font-path}/wknd-icon-font.woff') format('woff');
}
```

### 5.3 Images from Figma

- **Do not** commit raw Figma exports into the repo.
- For component images that must be editable by authors, use an AEM `Image` Core Component as a child or use `sling:resourceType="core/wcm/components/image/v3/image"`.
- For decorative/static images needed by a component, place optimized assets in `ui.frontend/src/main/webpack/resources/images/` and reference via SCSS.

---

## 6. Storybook Integration

### 6.1 Story location

```
ui.frontend/stories/{component}.stories.js
```

### 6.2 Story structure

Stories use vanilla HTML (not JSX). The Storybook builder is HTML:

```javascript
import '../src/main/webpack/site/main.scss';
// Import component SCSS implicitly via main.scss (glob-import)

export default {
    title: 'ComponentName',
};

export const Default = () => `
<div class="cmp-mycomponent">
    <div class="cmp-mycomponent__content">Hello</div>
</div>
`;

export const Variant = () => `
<div class="cmp-mycomponent cmp-mycomponent--variant">…</div>
`;
```

**Rule:** Every new component generated from a Figma design should include a Storybook story covering the default state and each major Figma variant.

---

## 7. Build System

### 7.1 Webpack entry & output

```
Entry:  ui.frontend/src/main/webpack/site/main.js
Output: ui.frontend/dist/clientlib-site/
```

`glob-import-loader` auto-discovers all `.ts`/`.scss` files under `components/` — **no manual import needed** when adding a new component. Just create the file in the right directory.

### 7.2 ClientLib categories

| ClientLib | Category | Purpose |
|-----------|----------|---------|
| `clientlib-site` | `wknd.site` | Site JS + CSS |
| `clientlib-dependencies` | `wknd.dependencies` | Third-party (jQuery) |

The clientlib generator copies `dist/` into `ui.apps/src/main/content/jcr_root/apps/wknd/clientlibs/` after every build.

### 7.3 Build commands relevant to Figma workflow

```bash
# After changing SCSS/TS only — fastest iteration
cd ui.frontend && npm run dev

# After changing SCSS/TS — rebuild and sync to running AEM
cd ui.frontend && npm run watch

# Full deploy (Java + content + frontend)
mvn clean install -PautoInstallSinglePackage -Pclassic
```

### 7.4 PostCSS pipeline

1. `sass-loader` compiles SCSS → CSS
2. `postcss-loader` applies Autoprefixer (targets: `> 0.2%, not dead`)
3. `MiniCssExtractPlugin` writes CSS files
4. Production: `CssMinimizerPlugin` minifies

No manual vendor prefixes needed — write standard CSS.

---

## 8. AEM Responsive Grid

The AEM layout container generates grid classes automatically. Do not recreate grid logic in component SCSS — let AEM handle column layout:

```html
<!-- AEM generates: -->
<div class="aem-GridColumn aem-GridColumn--default--8 aem-GridColumn--tablet--12">
    <!-- component content -->
</div>
```

Component SCSS should only style the component's internal layout, not its grid column width.

---

## 9. Figma → AEM Workflow

### 9.1 Getting design context

Use the Figma MCP `get_design_context` tool with the `nodeId` and `fileKey` from the Figma URL:

```
figma.com/design/:fileKey/:name?node-id=:nodeId
→ nodeId: convert "-" to ":" (e.g., "12-34" → "12:34")
```

### 9.2 Adaptation rules

The `get_design_context` response is a reference — always adapt it:

1. **Colors:** Replace raw hex with the closest `$variable` from `_variables.scss`.
2. **Typography:** Map font sizes to `$font-size-*` tokens; map font families to `$font-family-*`.
3. **Spacing:** Map margins/padding to multiples of `$gutter-padding` (14px) or explicit token values.
4. **BEM class names:** Derive from Figma layer names; prefix with `cmp-{component}`.
5. **Icons:** Match Figma icon layers to existing `$wkndicon-*` constants.
6. **Layout:** Use AEM responsive grid for column layout; use flexbox/grid only for internal component layout.
7. **Code format:** Output SCSS (not plain CSS); output HTL (not HTML).

### 9.3 Checking existing components before creating new ones

Before generating a new component, check:
1. `ui.apps/src/main/content/jcr_root/apps/wknd/components/` — existing WKND components.
2. AEM Core Components v2.30.2 — `sling:resourceSuperType="core/wcm/components/..."`. Always extend rather than duplicate.
3. `ui.frontend/stories/` — an existing story may already represent the design pattern needed.

### 9.4 Output checklist for a new component

- [ ] `ui.frontend/src/main/webpack/components/{name}/scss/{name}.scss`
- [ ] `ui.frontend/src/main/webpack/components/{name}/scss/styles/_default.scss`
- [ ] `ui.apps/.../components/{name}/.content.xml`
- [ ] `ui.apps/.../components/{name}/{name}.html`
- [ ] `ui.apps/.../components/{name}/_cq_dialog/.content.xml` (if any editable fields)
- [ ] `core/.../models/{Name}.java` (interface)
- [ ] `core/.../models/impl/{Name}Impl.java` (implementation)
- [ ] `ui.frontend/stories/{name}.stories.js`

---

## 10. Style System Variants

AEM Style System lets authors choose component variants. Each Figma variant (e.g., "Primary", "Dark", "Wide") should map to an AEM style:

**In the component policy dialog** (registered in ui.config), define available styles.

**In SCSS**, each style is an additional modifier:

```scss
// styles/_default.scss
.cmp-mycomponent { … }

// styles/_dark.scss
.cmp-mycomponent--dark {
    background: $brand-secondary;
    color: $white;
}
```

**In `.content.xml`**, register the style:

```xml
<styles jcr:primaryType="nt:unstructured">
    <dark  jcr:primaryType="nt:unstructured"
           cq:styleId="dark"
           cq:styleLabel="Dark"
           cq:styleClasses="cmp-mycomponent--dark"/>
</styles>
```

---

## 11. Fonts

Two web fonts are loaded via Google Fonts (see `.storybook/preview-head.html` and `site/main.scss`):

- **Source Sans Pro** — sans-serif body text (weights: 400, 600)
- **Asar** — serif display/heading font

These are the only approved typefaces. If a Figma design uses a different font, flag it as a design system deviation rather than adding a new font.

---

## 12. Quick Reference — File Map

| What to change | File |
|----------------|------|
| Add color token | `ui.frontend/src/main/webpack/base/sass/_variables.scss` |
| Add SCSS mixin | `ui.frontend/src/main/webpack/base/sass/_mixins.scss` |
| Add icon | `_wkndicons.scss` (requires regenerating icon font) |
| New component SCSS | `ui.frontend/src/main/webpack/components/{name}/scss/` |
| New component TS | `ui.frontend/src/main/webpack/components/{name}/ts/` |
| New AEM component | `ui.apps/src/main/content/jcr_root/apps/wknd/components/{name}/` |
| New Sling Model | `core/src/main/java/com/adobe/aem/guides/wknd/core/models/` |
| New Storybook story | `ui.frontend/stories/{name}.stories.js` |
| Global element styles | `ui.frontend/src/main/webpack/site/elements.scss` |
| Site entry point | `ui.frontend/src/main/webpack/site/main.js` |
