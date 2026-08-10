# iNSPECTAPP - Application de Gestion des Inspections Pédagogiques

## Vue d'ensemble

iNSPECTAPP est une plateforme de gestion 360° pour les inspecteurs pédagogiques. Elle permet de piloter l'activité administrative et pédagogique, de gérer les dossiers des enseignants, de planifier les visites de terrain, et de générer automatiquement des documents officiels (rapports, bordereaux, attestations) en français et en arabe.

## Description

Application Java desktop permettant aux inspecteurs pédagogiques de gérer leurs tâches:
- Visites
- Inspections
- Leçons expérimentales
- Réunions pédagogiques

## Fonctionnalités Principales

### A. Pôle PILOTAGE (Gestion Stratégique)
- **Tableau de Bord (Dashboard)** : Vue d'ensemble avec statistiques clés
- **Bilans (Reports)** : Statistiques avancées avec graphiques
- **Programme Annuel** : Planification à long terme des actions

### B. Pôle TERRAIN (Gestion Opérationnelle)
- **Enseignants (Teachers)** : Fiches complètes des enseignants
- **Zones & Établissements** : Découpage géographique
- **Tâches (Tasks)** : Gestionnaire de planning pour visites et réunions
- **Annuaire (Phonebook)** : Répertoire des contacts

### C. Pôle DOCUMENTS (Production Administrative)
- **Bibliothèque de Documents** : Gestionnaire de fichiers
- **Correspondance Arabe** : Éditeur pour courriers en langue arabe
- **Gestionnaire de Modèles** : Configuration des templates Word
- **Exports** : Extraction vers Excel ou PDF

### D. Pôle APPLICATION (Configuration)
- **Profil (Profile)** : Gestion des informations personnelles
- **Paramètres (Settings)** : Personnalisation de l'interface

### Fonctionnalités Détaillées

#### 1. Gestion des enseignants
- Ajout, modification, suppression d'enseignants
- Informations: nom, prénom, email, téléphone, matière, établissement, grade
- Historique des notes et des visites

#### 2. Planification des tâches
- Création de tâches par type (visite, inspection, leçon expérimentale, réunion)
- Association avec un enseignant
- Définition de la date, heure, lieu et objet
- Gestion des statuts (À faire, En cours, Terminé, Annulé)
- Gestion des priorités (Basse, Moyenne, Haute, Urgente)

#### 3. Rédaction de rapports (WYSIWYG)
- Éditeur HTML intégré pour rédiger les rapports
- Mise en forme du texte (gras, italique, souligné)
- Listes à puces et numérotées
- Visualisation du code source HTML

#### 4. Génération de documents Word
- Création automatique de rapports Word (.docx)
- Modèle personnalisable avec placeholders
- Insertion automatique des informations de l'enseignant et de la tâche
- Export pour chaque enseignant
- Support multilingue (Français/Arabe)

## Technologies utilisées

- **Java 17** - Langage de programmation
- **JavaFX 17.0.2** - Interface graphique moderne
- **SQLite** - Base de données locale
- **Apache POI 5.2.3** - Génération de documents Word
- **Maven** - Gestion des dépendances et build

## Architecture

```
com.inspection
├── MainApp.java              # Point d'entrée de l'application
├── model/
│   ├── Inspector.java        # Profil de l'inspecteur (entité centrale)
│   ├── Enseignant.java       # Modèle de données Enseignant
│   ├── Etablissement.java    # Établissement scolaire
│   ├── Tache.java            # Modèle de données Tâche avec statuts/priorités
│   ├── TaskType.java         # Énumération des types de tâches
│   └── Document.java         # Document généré (rapport, courrier, attestation)
├── service/
│   ├── DatabaseService.java  # Gestion de la base de données SQLite
│   ├── WordDocumentService.java # Génération de documents Word
│   ├── TemplateService.java  # Gestion des modèles de documents
│   └── InspectorService.java # Gestion du profil inspecteur
├── controller/
│   └── MainController.java   # Contrôleur principal de l'interface
└── ui/
    └── HTMLEditor.java       # Composant éditeur WYSIWYG
```

## Installation et compilation

### Prérequis
- Java 17 ou supérieur
- Maven 3.6+

### Compilation
```bash
cd inspection-manager
mvn clean package
```

### Exécution
```bash
mvn javafx:run
```

Ou exécuter le JAR généré:
```bash
java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml,javafx.web -jar target/inspection-manager-1.0-SNAPSHOT.jar
```

## Utilisation

### Démarrage
1. Lancez l'application
2. La base de données SQLite est créée automatiquement (`inspection_manager.db`)

### Configurer le profil de l'inspecteur
1. Accédez au menu Profil
2. Remplissez les informations administratives (DOTI, CIN, RIB, Matricule PPR)
3. Configurez l'affectation (Académie, Direction Provinciale, Région)
4. Importez votre signature et cachet scannés
5. Personnalisez le thème (clair/sombre) et la couleur d'accentuation

### Ajouter un enseignant
1. Cliquez sur "👨‍🏫 Enseignants" dans la sidebar
2. Cliquez sur "+ Nouvel enseignant"
3. Remplissez le formulaire et enregistrez

### Planifier une tâche
1. Cliquez sur "+ Nouvelle tâche"
2. Sélectionnez le type (Visite, Inspection, etc.)
3. Choisissez un enseignant
4. Définissez la date, l'heure, le lieu et l'objet
5. Définissez la priorité et le statut
6. Enregistrez

### Rédiger un rapport
1. Dans le tableau des tâches, cliquez sur "📄" pour une tâche
2. Rédigez votre rapport dans l'éditeur WYSIWYG
3. Cliquez sur "Enregistrer et générer Word"
4. Choisissez l'emplacement du fichier .docx

### Créer un modèle Word personnalisé
1. Menu Fichier → Créer modèle Word
2. Enregistrez le modèle
3. Modifiez-le avec Microsoft Word en utilisant les placeholders:
   - `${enseignant.nomComplet}` - Nom complet de l'enseignant
   - `${enseignant.matiere}` - Matière enseignée
   - `${tache.type}` - Type de tâche
   - `${tache.date}` - Date de la tâche
   - `${dateGeneration}` - Date de génération du document
   - etc.

## Structure de la base de données

### Table `enseignants`
- id (INTEGER PRIMARY KEY)
- nom, prenom, email, telephone
- matiere, etablissement, grade

### Table `taches`
- id (INTEGER PRIMARY KEY)
- type (VISITE, INSPECTION, LECON_EXPERIMENTALE, REUNION_PEDAGOGIQUE)
- enseignant_id (FOREIGN KEY)
- date, heure, lieu, objet
- planifiee, rapport_redige
- contenu_rapport (HTML), date_rapport
- statut, priorite, notes, date_echeance, archivee

### Table `inspector`
- id (INTEGER PRIMARY KEY)
- doti, cin, rib, matricule_ppr
- nom, prenom, date_naissance, lieu_naissance
- adresse, email, telephone
- grade, titre, discipline
- date_recrutement, date_nomination
- academie, direction_provinciale, region
- cycles, zones
- photo_profil_path, signature_path, cachet_path
- password_hash, question_secrete, reponse_secrete
- theme, couleur_accentuation
- ia_cloud_enabled, ia_local_url

### Table `templates`
- id (INTEGER PRIMARY KEY)
- type, titre, destinataire
- langue (FR/AR), statut, tags
- chemin_fichier

## API de Génération de Documents

### Placeholders Disponibles

#### Données de l'enseignant
- `${enseignant.nom}` - Nom
- `${enseignant.prenom}` - Prénom
- `${enseignant.nomComplet}` - Nom complet
- `${enseignant.matiere}` - Matière enseignée
- `${enseignant.etablissement}` - Établissement
- `${enseignant.grade}` - Grade
- `${enseignant.email}` - Email
- `${enseignant.telephone}` - Téléphone

#### Données de la tâche
- `${tache.type}` - Type de mission
- `${tache.date}` - Date (format dd/MM/yyyy)
- `${tache.heure}` - Heure
- `${tache.lieu}` - Lieu
- `${tache.objet}` - Objet
- `${tache.contenuRapport}` - Contenu du rapport
- `${dateGeneration}` - Date de génération

#### Données de l'inspecteur
- `${inspecteur.nomComplet}` - Nom complet
- `${inspecteur.titre}` - Titre
- `${inspecteur.academie}` - Académie
- `${inspecteur.discipline}` - Discipline

## Fonctionnalités Avancées

### 1. Génération Automatique de Documents
L'application injecte les données dans des balises de modèles Word pour produire instantanément:
- Rapports de visite/inspection
- Courriers officiels (FR/AR)
- Attestations
- Bordereaux d'envoi

### 2. Assistant IA Contextuel (Optionnel)
- Mode Cloud activable/désactivable
- Mode Local via Ollama/LM Studio
- Analyse des statistiques
- Recherche dans les documents (RAG)

### 3. OCR Hybride
- Intégration de Tesseract pour extraire le texte des documents scannés
- Facilitation de la recherche dans les archives papier

### 4. Design "MacGlass"
- Interface moderne inspirée de macOS
- Effets de transparence
- Coins arrondis (Pill design)
- Transitions fluides

## Types de Tâches Gérées

1. **Visites** : Visites de classe ordinaires
2. **Inspections** : Inspections formelles avec évaluation
3. **Leçons Expérimentales** : Séances pédagogiques spéciales
4. **Réunions Pédagogiques** : Rencontres de coordination

## Statuts et Priorités

### Statuts de tâche
- À faire
- En cours
- Terminé
- Annulé

### Priorités
- Basse
- Moyenne
- Haute
- Urgente

## Licence

Ce projet est fourni à titre éducatif et professionnel.

## Contact

Pour toute question ou suggestion, veuillez contacter le support technique.
