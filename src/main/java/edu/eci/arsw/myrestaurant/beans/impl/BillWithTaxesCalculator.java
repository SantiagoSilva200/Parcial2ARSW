package edu.eci.arsw.myrestaurant.beans.impl;

import edu.eci.arsw.myrestaurant.model.Order;
import edu.eci.arsw.myrestaurant.model.RestaurantProduct;
import edu.eci.arsw.myrestaurant.beans.BillCalculator;
import edu.eci.arsw.myrestaurant.beans.TaxesCalculator;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.eci.arsw.myrestaurant.beans.impl.colombia.StandardTaxesCalculator;


public class BillWithTaxesCalculator implements BillCalculator {


    TaxesCalculator taxescalc;
    StandardTaxesCalculator s;

    @Override
    public int calculateBill(Order o, Map<String, RestaurantProduct> productsMap) {
        int total = 0;
        
        for (String p : o.getOrderedDishes()) {
            RestaurantProduct rp=productsMap.get(p);
            float taxesP = s.getProductTaxes(rp); //aplicar impuestos a todos los productos de la orden
            total += (int) (o.getDishOrderedAmount(p) * taxesP);
        }
        return total;

    }

}
