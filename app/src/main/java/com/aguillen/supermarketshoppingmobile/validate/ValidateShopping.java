package com.aguillen.supermarketshoppingmobile.validate;

import com.aguillen.supermarketshoppingmobile.model.Shopping;

import java.util.List;

public class ValidateShopping {

    public static boolean isShoppingEmpty(List<Shopping> shoppingList) {
        for(Shopping shopping : shoppingList) {
            if(shopping.getCantidad() > 0) return false;
        }
        return true;
    }
}
