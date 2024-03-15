package com.example.farm.controller;

import com.example.farm.mapper.CollectedProductMapper;
import com.example.farm.mapper.EmployeeMapper;
import com.example.farm.model.dto.*;
import com.example.farm.model.request.admin.*;
import com.example.farm.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final EmployeeService employeeService;
    private final ProductService productService;
    private final EmployeeMapper employeeMapper;
    private final EmployeeMarkService employeeMarkService;
    private final CollectedProductService collectedProductService;
    private final CollectedProductMapper collectedProductMapper;
    private final PlanedProductService planedProductService;

    /**
     Регистрирация нового работника.
     @param request Данные о работнике для регистрации.
     @return Ответ HTTP-запроса со статусом 200,
     или со статусом 403, с сообщением "Unauthorized",
     или со статусом 409, с сообщением, что работник уже зарегистрирован.
     */
    @PostMapping("/register/employee")
    public EmployeeDTO registerEmployee(@RequestBody RegisterEmployeeRequest request) {
        return employeeMapper.toDTOFromEntity(employeeService.saveEmployee(request));
    }

    /**
     Удаление рабоника
     @param request Данные о работнике для удаления(почта).
     @return Ответ HTTP-запроса со статусом 200, с сообщением, что работник удален,
     или со статусом 403, с сообщением "Unauthorized",
     или со статусом 404, с сообщением, что работника не существует.
     */
    @DeleteMapping("/employee")
    public MessageDTO deleteEmployee(@RequestBody DeleteEmployeeRequest request) {
        return new MessageDTO(employeeService.deleteEmployeeByEmail(request));
    }

    /**
     Регистрирация нового товара.
     @param request Данные о товаре.
     @return Ответ HTTP-запроса со статусом 200, с сообщением, что товар добавлен,
     или со статусом 403, с сообщением "Unauthorized",
     или со статусом 409, с сообщением, что товар уже существует.
     */
    @PostMapping("/register/product")
    public MessageDTO registerProduct(@RequestBody RegisterProductRequest request) {
        return new MessageDTO(productService.saveProduct(request));
    }

    /**
     Удаление товара
     @param name Название товара.
     @return Ответ HTTP-запроса со статусом 200, с сообщением, что товар удален,
     или со статусом 403, с сообщением "Unauthorized",
     или со статусом 404, с сообщением, что товара не существует.
     */
    @DeleteMapping("/product/{name}")
    public MessageDTO deleteProduct(@PathVariable("name") String name) {
        return new MessageDTO(productService.deleteProductByName(name));
    }

    @PostMapping("/collected-products/employee")
    public List<EmployeeCollectedProductDTO> getProductsByEmployee(@RequestBody GetCollectedProductsEmployeeRequest request) {
        List<EmployeeCollectedProductDTO> collectedProducts =
                collectedProductMapper.fromEntityToEmployeeCollectedProductDTO(
                        collectedProductService.getCollectedProductsByPeriod(request)
        );
        return collectedProducts;
    }

    @PostMapping("/collected-products/farm")
    public List<FarmCollectedProductDTO> getProductsByFarm(@RequestBody GetCollectedProductsFarmRequest request) {
        List<FarmCollectedProductDTO> collectedProducts =
                collectedProductMapper.fromEntityToFarmCollectedProductDTO(
                        collectedProductService.getCollectedProductsByPeriod(request)
                );
        return collectedProducts;
    }

    @PostMapping("/mark")
    public MessageDTO setMarkForEmployee(@RequestBody SetMarkRequest request) {
        return new MessageDTO(employeeMarkService.saveEmployeeMark(request));
    }

    @DeleteMapping("/mark")
    public MessageDTO deleteMarkForEmployee(@RequestBody DeleteMarkRequest request) {
        return new MessageDTO(employeeMarkService.deleteEmployeeMark(request));
    }

    @PostMapping("/plan")
    public MessageDTO setPlanForEmployee(@RequestBody SetPlanRequest request) {
        return new MessageDTO(planedProductService.setEmployeePlan(request));
    }

    @DeleteMapping("/plan")
    public MessageDTO deletePlanForEmployee(@RequestBody DeletePlanRequest request) {
        return new MessageDTO(planedProductService.deleteEmployeePlan(request));
    }

    @PutMapping("/edit/{type}")
    public MessageDTO editCredentials(@PathVariable("type") String type,
                                      Authentication authentication,
                                      @RequestBody EditCredentialRequest request) {
        String email = (String) authentication.getPrincipal();
        return new MessageDTO(employeeService.editCredential(
                email, type, request.getCredential()));
    }
}
