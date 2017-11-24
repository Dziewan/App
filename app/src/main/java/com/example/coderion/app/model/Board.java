package com.example.coderion.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by coderion on 20.11.17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Board {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("material")
    private String material;

    @JsonProperty("place")
    private String place;

    @JsonProperty("size")
    private String size;

    @JsonProperty("thickness")
    private Double thickness;

    @JsonProperty("image")
    private Boolean image;

    @JsonProperty("description")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getThickness() {
        return thickness;
    }

    public void setThickness(Double thickness) {
        this.thickness = thickness;
    }

    public Boolean getImage() {
        return image;
    }

    public void setImage(Boolean image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
