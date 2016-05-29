package com.rana.juyelrana.jsonimageandtextparsing;

/**
 * Created by JuyelRana on 5/26/2016.
 */
public class Products {

    String image,title,subtitle,price,description;

    public Products() {
    }

    public Products(String image, String title, String subtitle, String price, String description) {
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;
        this.price = price;
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
