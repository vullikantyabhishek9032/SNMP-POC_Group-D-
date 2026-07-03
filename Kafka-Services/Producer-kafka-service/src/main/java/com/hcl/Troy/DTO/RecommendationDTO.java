package com.hcl.Troy.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
public class RecommendationDTO {

    private String customerId;
    private String currentPlan;
    private String recommendedPlan;
    private Double usage;
    private String benefits;
    private Timestamp timestamp;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCurrentPlan() {
        return currentPlan;
    }

    public void setCurrentPlan(String currentPlan) {
        this.currentPlan = currentPlan;
    }

    public String getRecommendedPlan() {
        return recommendedPlan;
    }

    public void setRecommendedPlan(String recommendedPlan) {
        this.recommendedPlan = recommendedPlan;
    }

    public Double getUsage() {
        return usage;
    }

    public void setUsage(Double usage) {
        this.usage = usage;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public RecommendationDTO(String customerId, String currentPlan, String recommendedPlan, Double usage, String benefits, Timestamp timestamp) {
        this.customerId = customerId;
        this.currentPlan = currentPlan;
        this.recommendedPlan = recommendedPlan;
        this.usage = usage;
        this.benefits = benefits;
        this.timestamp = timestamp;
    }

    public RecommendationDTO() {
    }
}