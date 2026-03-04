# Monaco Editor Setup

## Download Monaco Editor

1. Go to: https://www.npmjs.com/package/monaco-editor
2. Download the package (or use npm/CDN)
3. Copy the `min/` folder into `assets/monaco/monaco-editor/`

## Directory Structure Required

```
assets/
  monaco/
    index.html          <- Already provided
    monaco-editor/
      min/
        vs/
          loader.js
          editor/
            editor.main.js
            editor.main.css
            ...
```

## CDN Alternative (for debug builds)

Replace the script src in index.html:
```html
<script src="https://cdnjs.cloudflare.com/ajax/libs/monaco-editor/0.44.0/min/vs/loader.min.js"></script>
```
And update require.config:
```js
require.config({ paths: { vs: 'https://cdnjs.cloudflare.com/ajax/libs/monaco-editor/0.44.0/min/vs' } });
```
