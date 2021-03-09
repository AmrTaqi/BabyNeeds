package com.ammt.babyneeds.model;

public class Item {
    private int id;
    private String itemName;
    private int itemQty;
    private String itemColor;
    private int size;
    private String dateAdded;

    public Item() {}

    public Item(int id, String itemName, int itemQty, String itemColor, int size) {
        this.id = id;
        this.itemName = itemName;
        this.itemQty = itemQty;
        this.itemColor = itemColor;
        this.size = size;
    }

    public Item(String itemName, int itemQty, String itemColor, int size) {
        this.itemName = itemName;
        this.itemQty = itemQty;
        this.itemColor = itemColor;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public String getItemColor() {
        return itemColor;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", itemQty=" + itemQty +
                ", itemColor='" + itemColor + '\'' +
                ", size=" + size +
                ", dateAdded='" + dateAdded + '\'' +
                '}';
    }
}
