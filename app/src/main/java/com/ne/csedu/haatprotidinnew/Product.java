package com.ne.csedu.haatprotidinnew;

public class Product {

    String id, adminId, dokanId, name, price, description, issueDate, product_type;

    public Product(String id, String adminId, String dokanId, String name, String price, String description, String issueDate, String product_type) {
        this.id = id;
        this.adminId = adminId;
        this.dokanId = dokanId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.issueDate = issueDate;
        this.product_type = product_type;
    }

    public String getId() {
        return id;
    }

    public String getAdminId() {
        return adminId;
    }

    public String getDokanId() {
        return dokanId;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public void setDokanId(String dokanId) {
        this.dokanId = dokanId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }
}
