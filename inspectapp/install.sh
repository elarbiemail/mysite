#!/bin/bash

# Script d'installation automatique de iNSPECTAPP
# Ce script télécharge les dépendances nécessaires et crée un lanceur pour l'application

echo "============================================"
echo "  Installation de iNSPECTAPP"
echo "  Plateforme de gestion pour inspecteurs pédagogiques"
echo "============================================"
echo ""

# Vérifier si Java est installé
if ! command -v java &> /dev/null; then
    echo "ERREUR: Java n'est pas installé sur votre système."
    echo "Veuillez installer JDK 17 ou supérieur depuis:"
    echo "  - Windows: https://adoptium.net/"
    echo "  - macOS: brew install openjdk@17"
    echo "  - Linux: sudo apt install openjdk-17-jdk"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
echo "✓ Java détecté: version $JAVA_VERSION"

if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "ERREUR: Java 17 ou supérieur est requis. Version actuelle: $JAVA_VERSION"
    exit 1
fi

# Définir le répertoire d'installation
INSTALL_DIR="$HOME/iNSPECTAPP"
mkdir -p "$INSTALL_DIR"
echo "✓ Répertoire d'installation: $INSTALL_DIR"

# Copier le fichier JAR
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cp "$SCRIPT_DIR/target/inspectapp-1.0.0.jar" "$INSTALL_DIR/"
echo "✓ Application copiée dans $INSTALL_DIR"

# Créer le script de lancement
cat > "$INSTALL_DIR/inspectapp.sh" << 'EOF'
#!/bin/bash
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
java -Xmx512m --add-modules javafx.controls,javafx.fxml,javafx.web,javafx.swing,javafx.media \
     --add-opens java.base/java.lang=ALL-UNNAMED \
     --add-opens java.base/java.util=ALL-UNNAMED \
     --add-opens java.base/java.lang.reflect=ALL-UNNAMED \
     --add-opens java.base/java.text=ALL-UNNAMED \
     --add-opens java.desktop/java.awt.font=ALL-UNNAMED \
     -jar "$SCRIPT_DIR/inspectapp-1.0.0.jar" "$@"
EOF
chmod +x "$INSTALL_DIR/inspectapp.sh"
echo "✓ Script de lancement créé"

# Créer un raccourci bureau (si possible)
if [ -d "$HOME/Bureau" ]; then
    ln -sf "$INSTALL_DIR/inspectapp.sh" "$HOME/Bureau/iNSPECTAPP"
    echo "✓ Raccourci créé sur le Bureau"
elif [ -d "$HOME/Desktop" ]; then
    ln -sf "$INSTALL_DIR/inspectapp.sh" "$HOME/Desktop/iNSPECTAPP"
    echo "✓ Raccourci créé sur le Desktop"
fi

# Créer une entrée de menu (Linux)
if [ "$(uname)" == "Linux" ]; then
    mkdir -p "$HOME/.local/share/applications"
    cat > "$HOME/.local/share/applications/inspectapp.desktop" << EOF
[Desktop Entry]
Name=iNSPECTAPP
Comment=Plateforme de gestion pour inspecteurs pédagogiques
Exec=$INSTALL_DIR/inspectapp.sh
Icon=office-document
Terminal=false
Type=Application
Categories=Office;Education;
EOF
    echo "✓ Entrée de menu créée"
fi

echo ""
echo "============================================"
echo "  Installation terminée avec succès!"
echo "============================================"
echo ""
echo "Pour lancer l'application:"
echo "  1. Double-cliquez sur le raccourci créé sur votre Bureau"
echo "  2. Ou exécutez: $INSTALL_DIR/inspectapp.sh"
echo ""
echo "La base de données sera créée automatiquement dans:"
echo "  ~/.inspectapp/inspectapp.db"
echo ""
