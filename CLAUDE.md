# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is the **AEM WKND Sites Project** — an Adobe Experience Manager (AEM) reference implementation for a fictitious lifestyle brand. It demonstrates best practices for full-stack AEM Sites development.

**Requirements:** Java 21, Maven 3.9.4+, Node.js v16.17.0, npm 8.15.0

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

# Run integration tests (requires running AEM instance)
mvn clean verify -Plocal -pl it.tests

# Build UI test Docker image
mvn clean package -Pui-tests-docker-build -pl ui.tests

# Run Cypress UI tests in Docker
mvn verify -Pui-tests-docker-execution -pl ui.tests
```

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

**Linting:** ESLint with TypeScript support. Rules: max-len 150 chars, semicolons required, no `var`. Config: `.eslintrc.js`.

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

Unit tests use AEM Mocks (`io.wcm.testing.mock.aem`) — no running AEM required.

### Frontend: Webpack + TypeScript (`ui.frontend`)

Source lives under `ui.frontend/src/main/webpack/`. Webpack globs all `.ts`/`.scss` files under `components/` automatically — no manual imports needed.

**Build output flow:**
1. Webpack compiles TypeScript → ES5 JS and SCSS → CSS into `dist/`
2. `aem-clientlib-generator` copies `dist/` into `ui.apps` as AEM client libraries:
   - `clientlib-site` — site-specific JS/CSS
   - `clientlib-dependencies` — third-party dependencies (jQuery, etc.)

AEM components reference these client libraries via page policies on the editable template.

### AEM Components (`ui.apps`)

Components live under `ui.apps/src/main/content/jcr_root/apps/wknd/components/`. Each component folder contains:
- `.content.xml` — component definition (sling:resourceType, group, title)
- `*.html` — HTL (Sightly) template
- Optional `_cq_dialog/.content.xml` — Touch UI authoring dialog
- Optional `clientlib/js/` and `clientlib/(s)css/` — component-level JS/SCSS (globbed by webpack)

WKND extends **AEM Core Components** (v2.30.2) — always check if a Core Component already provides the needed behavior before creating a custom component.

### Deployment Dependency Order

When deploying individually, order matters: `core` → `ui.apps` → `ui.config` → `ui.content`. The `all` package handles this automatically.
