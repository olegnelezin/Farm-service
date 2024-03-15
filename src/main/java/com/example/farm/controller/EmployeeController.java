package com.example.farm.controller;

import com.example.farm.mapper.EmployeeMapper;
import com.example.farm.mapper.PlanedProductMapper;
import com.example.farm.model.dto.EmployeeDTO;
import com.example.farm.model.dto.MarkDTO;
import com.example.farm.model.dto.MessageDTO;
import com.example.farm.model.dto.PlanDTO;
import com.example.farm.model.request.admin.*;
import com.example.farm.model.request.employee.CollectProductRequest;
import com.example.farm.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class EmployeeController {
    private final CollectedProductService collectedProductService;
    private final RemainedPlanedProductsService remainedPlanedProductsService;
    private final EmployeeMarkService employeeMarkService;
    private final PlanedProductService planedProductService;
    private final PlanedProductMapper planedProductMapper;

    @PostMapping("/collect-product")
    public MessageDTO collectProduct(Authentication authentication,
                                     @RequestBody CollectProductRequest request) {
        String email = (String) authentication.getPrincipal();
        return new MessageDTO(collectedProductService.collectProducts(request, email)
                + " " + remainedPlanedProductsService.getRemainedCountMessage(request, email));
    }

    @GetMapping("/mark")
    public MarkDTO getMark(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        return new MarkDTO(employeeMarkService.getEmployeeMark(email));
    }

    @GetMapping("/plan")
    public List<PlanDTO> getPlan(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        return planedProductMapper.toDTOFromEntity(
                planedProductService.getEmployeePlan(email));
    }
}
