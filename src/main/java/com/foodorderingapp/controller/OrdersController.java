package com.foodorderingapp.controller;

import com.foodorderingapp.commons.WebUrlConstant;
import com.foodorderingapp.dto.BillDto;
import com.foodorderingapp.dto.OrderDto;
import com.foodorderingapp.dto.OrderListDto;
import com.foodorderingapp.dto.UserListDto;
import com.foodorderingapp.model.Orders;
import com.foodorderingapp.service.OrderDetailService;
import com.foodorderingapp.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(WebUrlConstant.Order.ORDER_API)
public class OrdersController {

    private final OrdersService ordersService;
    private final OrderDetailService orderDetailService;

    @Autowired
    public OrdersController(OrdersService ordersService,OrderDetailService orderDetailService){
        this.ordersService=ordersService;
        this.orderDetailService=orderDetailService;
    }

    @PostMapping
    public BillDto addOrder(@RequestBody OrderDto orderDto) {
        return ordersService.add(orderDto);
    }

    @GetMapping
    public List<OrderListDto> getOrder(){
        return ordersService.getOrder();
    }

    @PutMapping("/{orderId}")
    public Orders update(@PathVariable int orderId){
       return ordersService.update(orderId);
    }

    @GetMapping(value = "/orderList/{userId}")
    public List<UserListDto> getByUserId(@PathVariable("userId") int userId) {
        return ordersService.getByUserId(userId);
    }
}
