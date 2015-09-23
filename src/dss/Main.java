package dss;

import java.util.ArrayList;
import java.util.List;

import dss.models.manufacturer.Manufacturer;

public class Main {

    public static void main(String[] args) {

        Manufacturer.manager.createTable();

        // Simple INSERT
        Manufacturer x = new Manufacturer();
        x.name = "Samsung";
        x.save();

        Manufacturer y = new Manufacturer();
        y.name = "Sony";
        y.save();

        // Bulk INSERT
        Manufacturer a = new Manufacturer();
        a.name = "HTC";

        Manufacturer b = new Manufacturer();
        b.name = "Motorola";

        List<Manufacturer> manufacturers = new ArrayList<Manufacturer>();
        manufacturers.add(a);
        manufacturers.add(b);
        Manufacturer.manager.insert(manufacturers);

        showManufacturer(x);
        showManufacturer(y);
        showManufacturer(a);
        showManufacturer(b);

        // Query
        System.out.println("all");
        showManufacturers(Manufacturer.manager.select("all"));

        System.out.println("byName, Samsung");
        showManufacturers(Manufacturer.manager.select("byName", "Samsung"));

        System.out.println("byIdGte, 3");
        showManufacturers(Manufacturer.manager.select("byIdGte", 3));

        System.out.println("byIdLte 2");
        showManufacturers(Manufacturer.manager.select("byIdLte", 2));
    }

    private static void showManufacturer(Manufacturer manufacturer) {
        System.out.println(manufacturer.id + ": " + manufacturer.name);
    }

    private static void showManufacturers(List<Manufacturer> manufacturers) {
        for (Manufacturer manufacturer : manufacturers) {
            showManufacturer(manufacturer);
        }
    }
}
