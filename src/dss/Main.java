package dss;

import java.util.ArrayList;
import java.util.List;

import dss.models.manufacturer.Manufacturer;

public class Main {

    public static void main(String[] args) {

        Manufacturer.manager.createTable();

        /*
         * Simple INSERT
         */
        Manufacturer x = new Manufacturer();
        x.name = "Samsung";
        x.save();

        Manufacturer y = new Manufacturer();
        y.name = "Sony";
        y.save();

        /*
         * Bulk INSERT
         */
        Manufacturer a = new Manufacturer();
        a.name = "HTC";

        Manufacturer b = new Manufacturer();
        b.name = "Motorola";

        List<Manufacturer> manufacturers = new ArrayList<Manufacturer>();
        manufacturers.add(a);
        manufacturers.add(b);
        Manufacturer.manager.insert(manufacturers);
    }
}
