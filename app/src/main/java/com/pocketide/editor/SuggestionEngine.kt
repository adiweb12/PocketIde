package com.pocketide.editor

object SuggestionEngine {

    private val pythonKeywords = listOf(
        "def", "class", "import", "from", "return", "if", "elif", "else",
        "for", "while", "try", "except", "finally", "with", "as", "pass",
        "break", "continue", "lambda", "yield", "raise", "assert", "global",
        "nonlocal", "del", "in", "not", "and", "or", "is", "True", "False", "None",
        "print()", "len()", "range()", "enumerate()", "zip()", "map()", "filter()",
        "list()", "dict()", "set()", "tuple()", "str()", "int()", "float()",
        "open()", "isinstance()", "type()", "super()", "__init__", "__str__",
        "self", "cls", "args", "kwargs"
    )

    private val jsKeywords = listOf(
        "const", "let", "var", "function", "class", "return", "if", "else",
        "for", "while", "do", "switch", "case", "break", "continue", "try",
        "catch", "finally", "throw", "new", "this", "typeof", "instanceof",
        "import", "export", "default", "async", "await", "Promise", "then",
        "catch", "resolve", "reject", "console.log()", "console.error()",
        "document.getElementById()", "addEventListener()", "querySelector()",
        "fetch()", "JSON.stringify()", "JSON.parse()", "Array.from()",
        "Object.keys()", "Object.values()", "Object.entries()", "map()",
        "filter()", "reduce()", "forEach()", "find()", "includes()"
    )

    private val kotlinKeywords = listOf(
        "fun", "val", "var", "class", "object", "interface", "enum", "data",
        "sealed", "abstract", "override", "open", "private", "public", "protected",
        "internal", "companion", "return", "if", "else", "when", "for", "while",
        "do", "try", "catch", "finally", "throw", "null", "true", "false",
        "is", "as", "in", "by", "init", "constructor", "get", "set",
        "suspend", "coroutineScope", "launch", "async", "await", "Flow",
        "LiveData", "ViewModel", "MutableLiveData", "viewModelScope",
        "println()", "listOf()", "mutableListOf()", "mapOf()", "setOf()"
    )

    private val htmlKeywords = listOf(
        "<!DOCTYPE html>", "<html>", "<head>", "<body>", "<div>", "<span>",
        "<p>", "<h1>", "<h2>", "<h3>", "<a href=\"\">", "<img src=\"\">",
        "<ul>", "<ol>", "<li>", "<table>", "<tr>", "<td>", "<th>",
        "<form>", "<input>", "<button>", "<select>", "<option>",
        "<script>", "<style>", "<link>", "<meta>", "<title>",
        "class=\"\"", "id=\"\"", "style=\"\"", "onclick=\"\"", "href=\"\""
    )

    private val dartKeywords = listOf(
        "void", "var", "final", "const", "dynamic", "String", "int", "double",
        "bool", "List", "Map", "Set", "Future", "Stream", "class", "extends",
        "implements", "mixin", "abstract", "static", "factory", "return",
        "if", "else", "for", "while", "switch", "case", "try", "catch",
        "throw", "async", "await", "yield", "import", "export", "part",
        "Widget", "StatelessWidget", "StatefulWidget", "BuildContext", "State",
        "build()", "setState()", "initState()", "dispose()", "Column()", "Row()",
        "Container()", "Text()", "Padding()", "Scaffold()", "AppBar()"
    )

    fun getSuggestions(prefix: String, language: String): List<String> {
        if (prefix.isBlank() || prefix.length < 2) return emptyList()

        val keywords = when (language.lowercase()) {
            "python" -> pythonKeywords
            "javascript", "typescript", "js", "ts" -> jsKeywords
            "kotlin" -> kotlinKeywords
            "html" -> htmlKeywords
            "dart" -> dartKeywords
            "java" -> kotlinKeywords // basic overlap
            else -> jsKeywords
        }

        return keywords
            .filter { it.startsWith(prefix, ignoreCase = true) }
            .sortedBy { it.length }
            .take(10)
    }
}
