# Figma → AEM Component Generation via MCP — HOW TO

**Goal:** Validate AI-assisted AEM component generation from Figma designs using MCP servers and the Adobe AEM Skill.

**Stack:**
- AEM 6.5.1 LTS on-premise — `http://localhost:4502/` (WKND site)
- Claude Code with MCP protocol
- Figma MCP: official remote server `https://mcp.figma.com/mcp` (HTTP transport, OAuth)  
  Supported clients: https://www.figma.com/mcp-catalog/  
  Docs: https://developers.figma.com/docs/figma-mcp-server/remote-server-installation/#claude-code
- AEM MCP: `aem-mcp-server` (npm, supports on-prem)
- Adobe AEM Skill: `github.com/adobe/skills` branch `beta`

---

## Step 1 — Figma MCP ✅

Uses the official Figma remote MCP server over HTTP with OAuth — no API key stored in config.  
Config lives in `.mcp.json` at the project root (committed to git, safe — no secrets).

### Option A — Plugin (recommended, includes Agent Skills)

```bash
claude plugin install figma@claude-plugins-official
```

### Option B — Manual setup (what was done here)

```bash
# Add to current project scope
claude mcp add --transport http figma https://mcp.figma.com/mcp

# Or globally across all projects
claude mcp add --transport http --scope user figma https://mcp.figma.com/mcp
```

Then restart Claude Code, run `/mcp` → select `figma` → click **Authenticate** → confirm "Authentication successful. Connected to figma".

**Current state:** config stored in `.mcp.json`, server name **`figma`** (connected ✔).

### Generate design system rules ✅

Once connected, run this Figma MCP tool to generate a context file for AI-assisted code generation:

```
create_design_system_rules
```

Generates `FIGMA_DESIGN_SYSTEM.md` at the project root — the authoritative reference for translating Figma designs into frontend code (design tokens, component patterns, naming conventions). Run once per project, re-run when the design system changes significantly.

> **Note for Adobe AEM Skill:** The skill may expect the Figma server to be named `plugin-figma-figma`. To be resolved in Step 3.

---

## Step 2 — AEM MCP

Add the AEM MCP server for on-prem access (57 tools, supports AEM 6.5):

```bash
claude mcp add --transport http aem <URL>
```

> URL and auth details TBD — see `aem-mcp-server` docs.

Once added, verify via `/mcp`:
- `figma` — connected ✔
- `aem` — connected

**AEM MCP test:** ask Claude to list WKND components from `/apps/wknd/components/`

---

## Step 3 — Review Adobe AEM Skill

URL: `https://github.com/adobe/skills/blob/beta/skills/aem/cloud-service/skills/create-component/SKILL.md`

The skill lives under `/cloud-service/` — need to verify:
- Is there an on-premise variant in the same repository?
- Does the cloud-service skill work with on-prem via `aem-mcp-server` (which supports both modes)?

---

## Step 4 — Configure Skill and `source/`

Based on Step 3 findings:
1. Set up `source/.aem-skills-config.yaml` for on-prem AEM 6.5
2. Create `source/.claude/settings.json` if needed

---

## Step 5 — Test component generation

- Provide a Figma frame URL with a component design
- Prompt: `"Create an AEM component <name> based on Figma design <URL>"`
- Expected output: HTL template, Sling Model, authoring dialog, SCSS clientlib
- Deploy: `mvn clean install -PautoInstallSinglePackage -Pclassic`
- Verify in AEM Author: `http://localhost:4502/`
