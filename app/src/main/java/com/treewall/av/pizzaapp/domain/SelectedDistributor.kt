package com.treewall.av.pizzaapp.domain

import com.treewall.av.pizzaapp.domain.pizza.entity.PinLocation
import com.treewall.av.pizzaapp.domain.pizza.entity.PizzaMachineEntity

data class SelectedDistributor(
    val distributor: PizzaMachineEntity,
    val personLocation: PinLocation?
)