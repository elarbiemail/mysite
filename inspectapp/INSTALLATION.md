# 🚀 Installation de iNSPECTAPP

## Fichier d'installation généré

Le fichier JAR exécutable a été créé avec succès:
- **Fichier:** `target/inspectapp-1.0.0.jar` (101 MB)
- **Type:** Uber-JAR contenant toutes les dépendances

---

## 📋 Méthode 1: Script d'Installation Automatique (Recommandé)

Un script d'installation automatique est fourni pour faciliter le déploiement.

### Sur Linux/macOS:
```bash
cd /workspace/inspectapp
./install.sh
```

Le script va:
1. ✅ Vérifier que Java 17+ est installé
2. ✅ Copier l'application dans `~/iNSPECTAPP`
3. ✅ Créer un lanceur executable
4. ✅ Ajouter un raccourci sur le Bureau
5. ✅ Créer une entrée dans le menu des applications (Linux)

### Sur Windows:
Ouvrez PowerShell en tant qu'administrateur et exécutez:
```powershell
cd \workspace\inspectapp
.\install.bat
```

---

## 📋 Méthode 2: Installation Manuelle

### Prérequis
- **Java JDK 17 ou supérieur** obligatoire
  - Télécharger depuis: https://adoptium.net/
  - Vérifier: `java -version`

### Étapes:

1. **Copier le fichier JAR**
   ```bash
   cp target/inspectapp-1.0.0.jar ~/iNSPECTAPP/
   ```

2. **Créer un script de lancement** (Linux/macOS)
   ```bash
   #!/bin/bash
   java -Xmx512m --add-modules javafx.controls,javafx.fxml,javafx.web,javafx.swing,javafx.media \
        --add-opens java.base/java.lang=ALL-UNNAMED \
        --add-opens java.base/java.util=ALL-UNNAMED \
        --add-opens java.base/java.lang.reflect=ALL-UNNAMED \
        --add-opens java.base/java.text=ALL-UNNAMED \
        --add-opens java.desktop/java.awt.font=ALL-UNNAMED \
        -jar ~/iNSPECTAPP/inspectapp-1.0.0.jar
   ```

3. **Lancer l'application**
   ```bash
   java -jar inspectapp-1.0.0.jar
   ```

---

## 📋 Méthode 3: Exécution Directe depuis Maven

Pour le développement, vous pouvez lancer l'application directement:

```bash
cd /workspace/inspectapp
mvn clean javafx:run
```

---

## 🔧 Résolution des Problèmes

### Erreur: "Module javafx.controls not found"
Ajoutez les options JVM suivantes:
```bash
java --module-path /chemin/vers/javafx/lib --add-modules javafx.controls,javafx.fxml -jar inspectapp-1.0.0.jar
```

### Erreur: "Could not find or load main class"
Vérifiez que Java 17+ est installé:
```bash
java -version
```

### L'application ne démarre pas
Consultez les logs dans:
- Linux/macOS: `~/.inspectapp/logs/`
- Windows: `%USERPROFILE%\.inspectapp\logs\`

---

## 📊 Structure après Installation

```
~/iNSPECTAPP/
├── inspectapp-1.0.0.jar    # Application principale
├── inspectapp.sh           # Script de lancement (Linux/macOS)
└── inspectapp.bat          # Script de lancement (Windows)
```

La base de données SQLite sera créée automatiquement au premier lancement:
- `~/.inspectapp/inspectapp.db`

---

## ✨ Fonctionnalités Disponibles

Dès le premier lancement, vous avez accès à:

### 🎯 Pôle PILOTAGE
- Tableau de bord avec statistiques
- Bilans et graphiques
- Programme annuel

### 🌍 Pôle TERRAIN
- Gestion des enseignants (fiches complètes)
- Zones & établissements
- Planification des tâches
- Annuaire des contacts

### 📄 Pôle DOCUMENTS
- Bibliothèque de documents
- Correspondance en arabe
- Génération automatique de rapports Word
- Exports Excel/PDF

### ⚙️ Pôle APPLICATION
- Profil inspecteur (signature, cachet)
- Paramètres d'apparence
- Configuration IA (Cloud/Local)

---

## 🆘 Besoin d'Aide?

Consultez la documentation complète dans le fichier `README.md` ou contactez le support technique.

**Version:** 1.0.0  
**Date:** Août 2026  
**Licence:** Propriétaire
