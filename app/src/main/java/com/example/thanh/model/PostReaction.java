package com.example.thanh.model;

public class PostReaction {
    private String attachment;
    private int _id;
    private String attachmentId;
    private String comment;
    private boolean isDisReact;
    private int __v;
    private int postId;
    private int reactedDate;
    private int typeReact;
    private int userId;
    private UserInfo userInfo;

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isDisReact() {
        return isDisReact;
    }

    public void setDisReact(boolean disReact) {
        isDisReact = disReact;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getReactedDate() {
        return reactedDate;
    }

    public void setReactedDate(int reactedDate) {
        this.reactedDate = reactedDate;
    }

    public int getTypeReact() {
        return typeReact;
    }

    public void setTypeReact(int typeReact) {
        this.typeReact = typeReact;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public class UserInfo {
        private int age;
        private int height;
        private int weight;
        private int workingLevel;
        private String avtId;
        private String coverId;
        private int wallet;
        private boolean isBan;
        private int status;
        private int lastActive;
        private String bankAccount;
        private String bankName;
        private int _id;

        public int getAge() {
            return age;
        }

        public int getHeight() {
            return height;
        }

        public int getWeight() {
            return weight;
        }

        public int getWorkingLevel() {
            return workingLevel;
        }

        public String getAvtId() {
            return avtId;
        }

        public String getCoverId() {
            return coverId;
        }

        public int getWallet() {
            return wallet;
        }

        public boolean isBan() {
            return isBan;
        }

        public int getStatus() {
            return status;
        }

        public int getLastActive() {
            return lastActive;
        }

        public String getBankAccount() {
            return bankAccount;
        }

        public String getBankName() {
            return bankName;
        }

        public int get_id() {
            return _id;
        }

        public String getFirstname() {
            return firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public String getGender() {
            return gender;
        }

        public String getEmail() {
            return email;
        }

        public String getPhonenumber() {
            return phonenumber;
        }

        public String getAddress() {
            return address;
        }

        public int get__v() {
            return __v;
        }

        private String firstname;
        private String lastname;
        private String gender;
        private String email;
        private String phonenumber;
        private String address;
        private int __v;

        // Các phương thức getter và setter tương ứng

        // ...
    }

}
