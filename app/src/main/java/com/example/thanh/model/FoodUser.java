package com.example.thanh.model;

public class FoodUser {
    private int id;
    private int foodId;
    private int userId;
    private long useDatetime;
    private String session;
    private FoodInfo foodinfo ;

    // Constructors, getters, and setters

    public FoodUser() {

    }


    public FoodUser(int id, int foodId, int userId, long useDatetime, String session, FoodInfo foodinfo ) {
        this.id = id;
        this.foodId = foodId;
        this.userId = userId;
        this.useDatetime = useDatetime;
        this.session = session;
        this.foodinfo  = foodinfo ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getUseDatetime() {
        return useDatetime;
    }

    public void setUseDatetime(long useDatetime) {
        this.useDatetime = useDatetime;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public FoodInfo getFoodInfo() {
        return foodinfo ;
    }

    public void setFoodInfo(FoodInfo foodInfo) {
        this.foodinfo  = foodInfo;
    }


    public class FoodInfo {
        private String attachment;
        private int id;
        private String attachId;
        private String ingredients;
        private float kcal;
        private String name;
        private String nutrition;
        private String recipe;

        // Constructors, getters, and setters

        public FoodInfo() {
        }

        public FoodInfo(String attachment, int id, String attachId, String ingredients, int kcal, String name, String nutrition, String recipe) {
            this.attachment = attachment;
            this.id = id;
            this.attachId = attachId;
            this.ingredients = ingredients;
            this.kcal = kcal;
            this.name = name;
            this.nutrition = nutrition;
            this.recipe = recipe;
        }

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public float getKcal() {
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
    }

}
