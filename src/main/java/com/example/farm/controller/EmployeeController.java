package com.example.farm.controller;

import com.example.farm.mapper.PlanedProductMapper;
import com.example.farm.model.dto.MarkDTO;
import com.example.farm.model.dto.MessageDTO;
import com.example.farm.model.dto.PlanDTO;
import com.example.farm.model.request.employee.CollectProductRequest;
import com.example.farm.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final CollectedProductService collectedProductService;
    private final RemainedPlanedProductsService remainedPlanedProductsService;
    private final EmployeeMarkService employeeMarkService;
    private final PlanedProductService planedProductService;
    private final PlanedProductMapper planedProductMapper;

    /**
     * Собрать товар.
     @param request Данные о товаре.
     @return Ответ HTTP-запроса со статусом 200, с сообщением, что товар собран
     и информацией об оставшихся товарах,
     или со статусом 403, с сообщением "Unauthorized",
     или со статусом 404, с сообщением, что товара не существует.
     */
    @PostMapping("/collect-product")
    public MessageDTO collectProduct(Authentication authentication,
                                     @RequestBody CollectProductRequest request) {
        String email = (String) authentication.getPrincipal();
        return new MessageDTO(collectedProductService.collectProducts(request, email)
                + " " + remainedPlanedProductsService.getRemainedCountMessage(request, email));
    }

    /**
     * Узнать оценку.
     @param authentication Данные об аутентификации.
     @return Ответ HTTP-запроса со статусом 200, с сообщением о выставленной оценки,
     или со статусом 403, с сообщением "Unauthorized",
     или со статусом 404, с сообщением, что оценки не существует.
     */
    @GetMapping("/mark")
    public MarkDTO getMark(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        return new MarkDTO(employeeMarkService.getEmployeeMark(email));
    }

    /**
     * Узнать план сбора товаров.
     @param authentication Данные об аутентификации.
     @return Ответ HTTP-запроса со статусом 200, с сообщением о плане сбора товаров на день,
     или со статусом 403, с сообщением "Unauthorized",
     или со статусом 404, с сообщением, что плана не существует.
     */
    @GetMapping("/plan")
    public List<PlanDTO> getPlan(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        return planedProductMapper.toDTOFromEntity(
                planedProductService.getEmployeePlan(email));
    }
}
