package com.inspection.ui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * Éditeur WYSIWYG pour la rédaction des rapports
 * Utilise un WebView avec un éditeur HTML simple
 */
public class HTMLEditor extends VBox {
    private WebView webView;
    private WebEngine webEngine;
    private TextArea sourceEditor;
    private boolean showingSource = false;

    public HTMLEditor() {
        super(5);
        setPadding(new Insets(5));
        initialize();
    }

    private void initialize() {
        // Barre d'outils
        ToolBar toolBar = createToolBar();

        // WebView pour l'édition WYSIWYG
        webView = new WebView();
        webEngine = webView.getEngine();
        
        // Charger l'éditeur HTML
        String editorHtml = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { 
                        font-family: Arial, sans-serif; 
                        padding: 10px;
                        min-height: 300px;
                    }
                    #editor {
                        border: 1px solid #ccc;
                        padding: 10px;
                        min-height: 300px;
                        background: white;
                    }
                    #editor:focus {
                        outline: none;
                        border-color: #4CAF50;
                    }
                </style>
            </head>
            <body>
                <div id="editor" contenteditable="true">
                    <p>Rédigez votre rapport ici...</p>
                </div>
                <script>
                    function getContent() {
                        return document.getElementById('editor').innerHTML;
                    }
                    function setContent(html) {
                        document.getElementById('editor').innerHTML = html;
                    }
                    function format(command, value) {
                        document.execCommand(command, false, value);
                        document.getElementById('editor').focus();
                    }
                </script>
            </body>
            </html>
            """;
        
        webEngine.loadContent(editorHtml);

        // Zone de texte pour voir le code source HTML
        sourceEditor = new TextArea();
        sourceEditor.setPrefHeight(300);
        sourceEditor.setVisible(false);
        sourceEditor.setWrapText(true);

        getChildren().addAll(toolBar, webView, sourceEditor);
    }

    private ToolBar createToolBar() {
        ToolBar toolBar = new ToolBar();

        Button btnBold = new Button("Gras");
        btnBold.setOnAction(e -> executeCommand("bold"));

        Button btnItalic = new Button("Italique");
        btnItalic.setOnAction(e -> executeCommand("italic"));

        Button btnUnderline = new Button("Souligné");
        btnUnderline.setOnAction(e -> executeCommand("underline"));

        ComboBox<String> fontSizeCombo = new ComboBox<>();
        fontSizeCombo.getItems().addAll("Normal", "Grand", "Très grand");
        fontSizeCombo.setValue("Normal");
        fontSizeCombo.setOnAction(e -> {
            String size = fontSizeCombo.getValue();
            if ("Grand".equals(size)) {
                executeCommand("fontSize", "5");
            } else if ("Très grand".equals(size)) {
                executeCommand("fontSize", "7");
            } else {
                executeCommand("fontSize", "3");
            }
        });

        Button btnBullet = new Button("Liste à puces");
        btnBullet.setOnAction(e -> executeCommand("insertUnorderedList"));

        Button btnNumber = new Button("Liste numérotée");
        btnNumber.setOnAction(e -> executeCommand("insertOrderedList"));

        Button btnSource = new Button("Voir source");
        btnSource.setOnAction(e -> toggleSource());

        toolBar.getItems().addAll(btnBold, btnItalic, btnUnderline, 
                                  new Separator(), fontSizeCombo,
                                  new Separator(), btnBullet, btnNumber,
                                  new Separator(), btnSource);

        return toolBar;
    }

    private void executeCommand(String command) {
        executeCommand(command, null);
    }

    private void executeCommand(String command, String value) {
        if (webEngine != null && !showingSource) {
            if (value == null) {
                webEngine.executeScript("format('" + command + "')");
            } else {
                webEngine.executeScript("format('" + command + "', '" + value + "')");
            }
        }
    }

    private void toggleSource() {
        if (showingSource) {
            // Passer de la source au WYSIWYG
            String html = sourceEditor.getText();
            webView.setVisible(true);
            sourceEditor.setVisible(false);
            webEngine.executeScript("setContent('" + escapeJs(html) + "')");
            showingSource = false;
        } else {
            // Passer du WYSIWYG à la source
            Object result = webEngine.executeScript("getContent()");
            if (result != null) {
                sourceEditor.setText(result.toString());
            }
            webView.setVisible(false);
            sourceEditor.setVisible(true);
            showingSource = true;
        }
    }

    public String getHtml() {
        if (showingSource) {
            return sourceEditor.getText();
        }
        Object result = webEngine.executeScript("getContent()");
        return result != null ? result.toString() : "";
    }

    public void setHtml(String html) {
        if (showingSource) {
            sourceEditor.setText(html);
        } else {
            webEngine.executeScript("setContent('" + escapeJs(html) + "')");
        }
    }

    private String escapeJs(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("'", "\\'")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r");
    }
}
