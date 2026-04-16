# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is the **AEM WKND Sites Project** — an Adobe Experience Manager (AEM) reference implementation for a fictitious lifestyle brand. It demonstrates best practices for full-stack AEM Sites development.

**Requirements:** Java 21 exactly (enforcer enforces `[21,22)` — not 21+), Maven 3.9.4+, Node.js v16.17.0, npm 8.15.0

## Build Commands

```bash
# Build and deploy to local AEM as a Cloud Service SDK (author @ localhost:4502)
mvn clean install -PautoInstallSinglePackage

# Build and deploy to AEM 6.5.x
mvn clean install -PautoInstallSinglePackage -Pclassic

# Deploy only the OSGi bundle (faster iteration on Java changes)
mvn clean install -PautoInstallBundle -pl core

# Deploy only content packages to author
mvn clean install -PautoInstallPackage -pl all

# Deploy to publish instance (localhost:4503)
mvn clean install -PautoInstallPackagePublish -pl all
```

## Testing

```bash
# Run Java unit tests only
mvn test -pl core

# Run a single test class
mvn test -pl core -Dtest=BylineImplTest

# Run a single test method
mvn test -pl core -Dtest=BylineImplTest#testGetName

# Run integration tests (requires both author @ 4502 AND publish @ 4503 running)
mvn clean verify -Plocal -pl it.tests

# Build UI test Docker image
mvn clean package -Pui-tests-docker-build -pl ui.tests

# Run Cypress UI tests in Docker
mvn verify -Pui-tests-docker-execution -pl ui.tests
```

### Unit Test Patterns (`core`)

Tests use dual JUnit 5 extensions: `@ExtendWith({AemContextExtension.class, MockitoExtension.class})`. Key patterns:

- **AemContext** (`io.wcm.testing.mock.aem`) provides an in-memory JCR + Sling environment — no running AEM needed.
- **JSON fixtures** in `src/test/resources/.../impl/` (e.g., `BylineImplTest.json`) set up the JCR content tree; loaded via `ctx.load().json(...)`.
- **Service mocking**: Register Mockito mocks with `ctx.registerService(Interface.class, mock, SERVICE_RANKING, Integer.MAX_VALUE)` to override default implementations.
- **Model adaptation**: `ctx.request().adaptTo(ModelClass.class)` is the standard way to instantiate models under test.

### Integration Tests (`it.tests`)

Tests cover GraphQL queries (using `AEMHeadlessClient`) and basic page lifecycle (create/get/publish). Persisted query endpoint tested: `/wknd-shared/adventures-all`. Requires `CQAuthorPublishClassRule` which expects both author and publish instances.

## Frontend Development (ui.frontend)

```bash
cd ui.frontend

# Install dependencies
npm ci

# Dev build (source maps, no minification) + generate AEM client libraries
npm run dev

# Production build (minified, tree-shaken) + generate AEM client libraries
npm run prod

# Start webpack dev server with proxy to localhost:4502
npm run start

# Watch mode: dev server + auto-sync changed files to AEM
npm run watch

# Storybook component explorer
npm run storybook
```

**Linting:** ESLint with TypeScript support. Config: `.eslintrc.js`. Rules: max-len 150 chars (warning), semicolons required. TypeScript strict checks are relaxed (`@typescript-eslint/no-explicit-any` and explicit return types are disabled).

## Architecture

This is a **multi-module Maven reactor project**. The root `pom.xml` orchestrates all modules.

### Module Map

| Module | Type | Purpose |
|--------|------|---------|
| `core` | OSGi bundle | Java Sling Models and business logic |
| `ui.frontend` | npm/webpack | TypeScript/SCSS frontend assets |
| `ui.apps` | content-package | AEM components, templates, client libraries |
| `ui.apps.structure` | content-package | Base `/apps` structure (required for AEMaaCS) |
| `ui.config` | content-package | OSGi configurations for AEM as a Cloud Service |
| `ui.content` | content-package | Base site structure and editable templates |
| `ui.content.sample` | content-package | Sample WKND pages and content |
| `all` | content-package | Aggregate package that embeds all others for single-deploy |
| `it.tests` | integration tests | JUnit tests against running AEM (GraphQL endpoints) |
| `ui.tests` | Cypress tests | Docker-based end-to-end UI tests |
| `dispatcher` | dispatcher-config | Apache/dispatcher cache and routing rules |
| `config` | config | CDN traffic filters and WAF rules |

### Backend: Sling Models (`core`)

Java models live under `com.adobe.aem.guides.wknd.core.models`. Interfaces define the API; implementations are in the `impl/` subpackage. The `@Model` annotation registers models for component adaptation.

- `Byline` / `BylineImpl` — author byline component with image and occupations
- `ImageList` / `ImageListImpl` — list of images from a page tree
- `HelloWorldModel` — minimal example model

There are no custom servlets or filters — all backend logic is via Sling Models.

The bundle uses `bnd-maven-plugin` with `org.apache.sling.bnd.models` (Sling Models scanning) and `org.apache.sling.caconfig.bnd-plugin` (Context-Aware Configuration scanning) — both run automatically during build.

### Frontend: Webpack + TypeScript (`ui.frontend`)

Source lives under `ui.frontend/src/main/webpack/`. Webpack uses **glob-import-loader** to automatically discover all `.ts`/`.scss` files under `components/` — no manual entry point imports needed when adding a new component.

**Build output flow:**
1. Webpack compiles TypeScript → ES5 JS and SCSS → CSS (with PostCSS/Autoprefixer) into `dist/`
2. `aem-clientlib-generator` (configured in `clientlib.config.js`) copies `dist/` into `ui.apps` as AEM client libraries:
   - `clientlib-site` — site-specific JS/CSS (depends on clientlib-dependencies)
   - `clientlib-dependencies` — third-party dependencies (jQuery, etc.)

The webpack dev server (`npm run start`) proxies `/content`, `/etc.clientlibs`, and `/libs` to `localhost:4502`. Hot module replacement is disabled; file watching triggers a full rebuild.

### AEM Components (`ui.apps`)

Components live under `ui.apps/src/main/content/jcr_root/apps/wknd/components/`. Each component folder contains:
- `.content.xml` — component definition (sling:resourceType, group, title)
- `*.html` — HTL (Sightly) template
- Optional `_cq_dialog/.content.xml` — Touch UI authoring dialog
- Optional `clientlib/js/` and `clientlib/(s)css/` — component-level JS/SCSS (globbed automatically by webpack)

WKND extends **AEM Core Components** (v2.30.2) — always check if a Core Component already provides the needed behavior before creating a custom component.

### OSGi Configuration (`ui.config`)

Environment-specific OSGi configs use `.cfg.json` format under `osgiconfig/`:

| Path | When active |
|------|-------------|
| `config/` | All environments |
| `config.author/` | Author run-mode (CORS, CSRF, Mobile Emulator) |
| `config.publish/` | Publish run-mode (Referrer Filter, Root Mapping, Login Handler) |
| `config.dev/`, `config.stage/`, `config.prod/` | Per-environment logging and tuning |
| `config.publish.prod/` | Publish + prod combined |

### Deployment Dependency Order

When deploying individually, order matters: `core` → `ui.apps` → `ui.config` → `ui.content`. The `all` package handles this automatically.

## MCP / AI-Assisted Development

See `HOWTO.md` for a workflow using Figma MCP (`figma-developer-mcp`) and AEM MCP (`aem-mcp-server`) to generate AEM components directly from Figma designs. The AEM skill supports both AEMaaCS and on-premise instances.

## Figma → Code Rules

**When you receive a Figma URL or are asked to generate any code from a Figma design, you MUST follow every rule in `FIGMA_DESIGN_SYSTEM.md` without exception.** That file is the authoritative design system contract for this repository.

Key obligations:
- Use `$variable` tokens from `_variables.scss` — never raw hex, px literals, or inline font names.
- Apply BEM with the mandatory `cmp-` prefix to every CSS class you write.
- Output SCSS (not plain CSS) and HTL (not plain HTML).
- Create files in both `ui.frontend` and `ui.apps` — a component is incomplete if either side is missing.
- Match Figma icons to existing `$wkndicon-*` constants; never export new SVG assets.
- Check for an existing WKND or Core Component before generating a new one.
- Include a Storybook story for every new component.

@.claude/rules/FIGMA_DESIGN_SYSTEM.md
