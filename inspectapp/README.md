# iNSPECTAPP - Plateforme de Gestion 360° pour Inspecteurs Pédagogiques

## Description
iNSPECTAPP est une application Java complète de gestion pour les inspecteurs pédagogiques. Elle permet de piloter l'activité administrative et pédagogique, gérer les dossiers des enseignants, planifier les visites de terrain, et générer automatiquement des documents officiels.

## Fonctionnalités Principales

### 🎯 Pôle PILOTAGE (Gestion Stratégique)
- **Tableau de Bord** : Vue d'ensemble avec statistiques clés et bannière personnalisée
- **Bilans** : Statistiques avancées avec graphiques sur l'activité annuelle
- **Programme Annuel** : Planification à long terme des actions

### 🌍 Pôle TERRAIN (Gestion Opérationnelle)
- **Enseignants** : Fiches complètes avec historique des notes et visites
- **Zones & Établissements** : Découpage géographique de la circonscription
- **Tâches** : Planning des visites, réunions et commissions
- **Annuaire** : Répertoire des contacts institutionnels

### 📄 Pôle DOCUMENTS (Production Administrative)
- **Bibliothèque** : Gestionnaire de fichiers pour circulaires
- **Correspondance AR** : Éditeur de courriers en arabe
- **Modèles Word** : Configuration des templates pour génération automatique
- **Exports** : Extraction vers Excel/PDF

### ⚙️ Pôle APPLICATION (Configuration)
- **Profil** : Informations personnelles, signature, cachet
- **Paramètres** : Thème clair/sombre, configuration IA

## Technologies Utilisées
- **Java 17+**
- **JavaFX 21** : Interface graphique moderne
- **SQLite** : Base de données locale
- **Apache POI** : Génération de documents Word/Excel
- **Gson** : Traitement JSON
- **Maven** : Gestion de dépendances

## Architecture du Projet
```
inspectapp/
├── src/main/java/com/inspectapp/
│   ├── MainApp.java              # Point d'entrée
│   ├── model/                    # Modèles de données
│   │   ├── Inspector.java
│   │   ├── Teacher.java
│   │   ├── Task.java
│   │   ├── Etablissement.java
│   │   └── InspectionHistory.java
│   ├── database/                 # Couche données
│   │   └── DatabaseManager.java
│   ├── service/                  # Services métier
│   │   ├── TeacherService.java
│   │   └── DocumentGenerationService.java
│   └── ui/
│       ├── controller/           # Contrôleurs FXML
│       └── component/            # Composants UI
├── src/main/resources/
│   ├── fxml/                     # Vues FXML
│   ├── css/                      # Styles MacGlass
│   └── templates/                # Modèles de documents
└── pom.xml                       # Configuration Maven
```

## Installation et Exécution

### Prérequis
- JDK 17 ou supérieur
- Maven 3.8+
- JavaFX SDK (inclus dans les dépendances Maven)

### Compilation
```bash
cd inspectapp
mvn clean compile
```

### Exécution via Maven
```bash
mvn javafx:run
```

### Création du JAR exécutable
```bash
mvn package
java -jar target/inspectapp-1.0.0.jar
```

## Modèle de Données

### Entité Centrale : Inspector
Contient toutes les informations de l'inspecteur :
- Identité administrative (DOTI, CIN, RIB, PPR)
- Carrière (grade, échelon, discipline)
- Affectation (académie, direction provinciale)
- Identité visuelle (photo, signature, cachet)

### Entités Périphériques
- **Teacher** : Enseignants avec historique complet
- **Task** : Tâches avec statuts et priorités
- **Etablissement** : Écoles/collèges/lycées
- **InspectionHistory** : Historique des inspections

## Génération de Documents

L'application supporte la génération automatique de :
- Rapports d'inspection
- Attestations de présence
- Bordereaux d'envoi
- Courriers officiels (FR/AR)

Les modèles Word utilisent le format `{{placeholder}}` pour la fusion de données.

## Design MacGlass
Interface moderne inspirée de macOS avec :
- Effets de transparence
- Coins arrondis (Pill design)
- Thèmes clair/sombre
- Transitions fluides

## License
Propriétaire - Tous droits réservés

## Contact
Développé pour les inspecteurs pédagogiques du Maroc
