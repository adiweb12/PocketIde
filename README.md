# ⚡ PocketIDE – Mobile Developer Studio

A professional-grade Android IDE built with Kotlin, inspired by VS Code.

## Features

- **Monaco Editor** – The same editor engine as VS Code
- **Multi-language Support** – Kotlin, Python, JavaScript, HTML/CSS, Java, Dart, C++
- **File Explorer** – Full file tree with create, rename, delete
- **Tab System** – Open multiple files simultaneously
- **Terminal** – Shell command execution
- **Plugin System** – Lightweight extension format
- **Autocomplete** – Keyword suggestions per language
- **Themes** – vs-dark, vs-light, hc-black, hc-light
- **MVVM Architecture** – Clean separation of concerns

## Setup

### 1. Open in Android Studio
Open the `PocketIDE/` folder in Android Studio (Hedgehog or later).

### 2. Add Monaco Editor
Follow instructions in `app/src/main/assets/monaco/MONACO_SETUP.md`

### 3. Build
```bash
./gradlew assembleDebug
```

## Architecture

```
MVVM + Modular Clean Architecture
├── ui/           Activities, Fragments, ThemeManager
├── editor/       MonacoWebView, EditorBridge, EditorViewModel
├── filemanager/  FileExplorer, FileTree, FileRepository
├── terminal/     TerminalFragment, ShellExecutor
├── lsp/          LSPManager, ProcessHandler
├── plugins/      PluginManager, PluginLoader
├── domain/       UseCases, Language suggestion engines
└── data/         Models, Repository
```

## Adding a Plugin

Create a folder in `files/plugins/your-plugin/` with:
- `plugin_manifest.json`
- `snippets/your-lang.json`

## Roadmap

- [ ] LSP full integration (pyls, dart-language-server)
- [ ] Python embedded runtime (Chaquopy)
- [ ] Git integration
- [ ] Cloud sync
- [ ] Extension marketplace

## License
MIT License
