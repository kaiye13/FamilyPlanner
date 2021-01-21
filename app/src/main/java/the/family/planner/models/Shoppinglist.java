package the.family.planner.models;

import java.util.List;

public class Shoppinglist {
    private String shopping_id;
    private String shop;
    private String products;
    private String family_id;

    public Shoppinglist(String shopping_id, String shop, String products, String family_id) {
        this.shopping_id = shopping_id;
        this.shop = shop;
        this.products = products;
        this.family_id = family_id;
    }

    public Shoppinglist() {
    }

    public String getShopping_id() {
        return shopping_id;
    }

    public void setShopping_id(String shopping_id) {
        this.shopping_id = shopping_id;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getFamily_id() {
        return family_id;
    }

    public void setFamily_id(String family_id) {
        this.family_id = family_id;
    }

    @Override
    public String toString() {
        return "Shoppinglist{" +
                "shopping_id='" + shopping_id + '\'' +
                ", shop='" + shop + '\'' +
                ", products='" + products + '\'' +
                ", family_id='" + family_id + '\'' +
                '}';
    }
}
