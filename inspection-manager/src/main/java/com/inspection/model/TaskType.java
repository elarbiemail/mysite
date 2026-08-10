package com.inspection.model;

import java.time.LocalDate;

/**
 * Enumération des types de tâches pour l'inspecteur pédagogique
 */
public enum TaskType {
    VISITE("Visite"),
    INSPECTION("Inspection"),
    LECON_EXPERIMENTALE("Leçon expérimentale"),
    REUNION_PEDAGOGIQUE("Réunion pédagogique");

    private final String label;

    TaskType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
