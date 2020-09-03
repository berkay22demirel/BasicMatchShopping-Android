package com.example.basicmatchshopping.api.response;

import java.io.Serializable;
import java.util.List;

public class ProductResponse implements Serializable {

    private int id;
    private String name;
    private CategoryResponse categoryDTO;
    private List<SubProductResponse> subProductDTOs;
    private String imagePath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryResponse getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(CategoryResponse categoryDTO) {
        this.categoryDTO = categoryDTO;
    }

    public List<SubProductResponse> getSubProductDTOs() {
        return subProductDTOs;
    }

    public void setSubProductDTOs(List<SubProductResponse> subProductDTOs) {
        this.subProductDTOs = subProductDTOs;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "ProductResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryDTO=" + categoryDTO +
                ", subProductDTOs=" + subProductDTOs +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
