package ru.natapro.eatnfitbot.telegram.nonCommand;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Пользовательские настройки
 */
@Getter
@EqualsAndHashCode
public class Settings {

    /**
     * Начальный вес
     */
    private Double startWeight;

    /**
     * Текущий вес
     */
    private Double currentWeight;

    /**
     * Возраст
     */
    private int age;

    /**
     * Телефон
     */
    private String phone;

    public Settings(Double startWeight, Double currentWeight, int age, String phone) {
        this.startWeight = validateAndSetStartWeight(startWeight);
        this.currentWeight = validateAndSetCurrentWeight(currentWeight);
        this.age = age;
        this.phone = phone;
    }

    public Settings(Double startWeight, int age, String phone) {
        this.startWeight = validateAndSetStartWeight(startWeight);
        this.currentWeight = startWeight;
        this.age = age;
        this.phone = phone;
    }

    public Double validateAndSetStartWeight(Double startWeight) {
        return startWeight == null ? 0 : startWeight;
    }

    public Double validateAndSetCurrentWeight(Double currentWeight) {
        return currentWeight == null ? 0 : currentWeight;
    }
}