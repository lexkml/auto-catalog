package com.kamelchukov.autocatalog.model;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String model;

    @Column(name = "class")
    private char classCar;

    private int year;
    private String color;
    private int price;

    public Car() {
    }

    public Car(String model, char classCar, int year, String color, int price) {
        this.model = model;
        this.classCar = classCar;
        this.year = year;
        this.color = color;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", classCar=" + classCar +
                ", year=" + year +
                ", color='" + color + '\'' +
                ", price=" + price +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public char getClassCar() {
        return classCar;
    }

    public int getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public int getPrice() {
        return price;
    }
}
