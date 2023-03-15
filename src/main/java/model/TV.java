package model;

import collections.HashTable;

import java.util.ArrayList;
import java.util.Objects;

public class TV {
    private String brand;
    private String model;
    private String color;
    private double price;

    public TV(String brand, String model, String color, double price) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TV tv = (TV) o;
        return Double.compare(tv.price, price) == 0 && Objects.equals(brand, tv.brand) && Objects.equals(model, tv.model) && Objects.equals(color, tv.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, model, color, price);
    }


    @Override
    public String toString() {
        return "TV{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", price=" + price +
                '}';
    }

    public HashTable<String, ArrayList<TV>> groupBy(ArrayList<TV> tvs) {
        HashTable<String, ArrayList<TV>> hashTable = new HashTable<>();
        for (int i = 0; i < tvs.size(); i++) {
            ArrayList<TV> getOrDefault = hashTable.getOrDefault(tvs.get(i).brand, new ArrayList<>());
            getOrDefault.add(tvs.get(i));
            hashTable.put(tvs.get(i).brand,getOrDefault);
        }

        return hashTable;

    }
}
