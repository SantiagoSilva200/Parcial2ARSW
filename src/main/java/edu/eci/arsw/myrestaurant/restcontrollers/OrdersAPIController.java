/*
 * Copyright (C) 2016 Pivotal Software, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.arsw.myrestaurant.restcontrollers;

import edu.eci.arsw.myrestaurant.services.OrderServicesException;
import edu.eci.arsw.myrestaurant.services.RestaurantOrderServices;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author hcadavid
 */
@RestController

public class OrdersAPIController {

    @Autowired
    RestaurantOrderServices r;

    @GetMapping({"/orders"})

    public ResponseEntity<?> getOrder() throws OrderServicesException {
        try {
            JSONArray jsonArray = new JSONArray();
            for (Integer orderId : r.getTablesWithOrders()) {
                JSONObject json = new JSONObject(r.getTableOrder(orderId));
                jsonArray.put("total = " + r.calculateTableBill(orderId));
                jsonArray.put(json);

                return new ResponseEntity<>(jsonArray.toString(), HttpStatus.ACCEPTED);
            }
        } catch (OrderServicesException e) {
            throw new OrderServicesException("ERROR");
        }
        return null;
    }
}
