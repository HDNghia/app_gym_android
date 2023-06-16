package com.example.thanh.model;

public class FoodItem {
    private int _id;
    private String attachId;
    private String ingredients;
    private int kcal;
    private String name;
    private String nutrition;
    private String recipe;
    private int __v;
    private String attachment;

    // Constructors, getters, and setters
    public FoodItem(){

    }

    public FoodItem(int _id, String attachId, String ingredients, int kcal, String name, String nutrition, String recipe, int __v, String attachment) {
        this._id = _id;
        this.attachId = attachId;
        this.ingredients = ingredients;
        this.kcal = kcal;
        this.name = name;
        this.nutrition = nutrition;
        this.recipe = recipe;
        this.__v = __v;
        this.attachment = attachment;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getAttachId() {
        return attachId;
    }

    public void setAttachId(String attachId) {
        this.attachId = attachId;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNutrition() {
        return nutrition;
    }

    public void setNutrition(String nutrition) {
        this.nutrition = nutrition;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
