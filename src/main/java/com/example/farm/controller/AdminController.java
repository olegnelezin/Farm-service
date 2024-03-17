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
     Регистрация нового работника.
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
     Удаление работника
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
     Получение списка всех работников.
     @return Ответ HTTP-запроса со статусом 200, со списком работников,
     или со статусом 403, с сообщением "Unauthorized".
     */
    @GetMapping("/employee/all")
    public List<EmployeeDTO> getAllEmployees() {
        return employeeMapper.toDTOFromEntity(employeeService.getAllEmployees());
    }

    /**
     Регистрация нового товара.
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

    /**
     Получение списка собранных товаров по конкретному работнику
     @param request Данные о временном промежутке и работнике.
     @return Ответ HTTP-запроса со статусом 200 и списком собранных товаров,
     или со статусом 403, с сообщением "Unauthorized",
     или со статусом 400, с сообщением, "Invalid data type".
     */
    @PostMapping("/collected-products/employee")
    public List<EmployeeCollectedProductDTO> getProductsByEmployee(@RequestBody GetCollectedProductsEmployeeRequest request) {
        List<EmployeeCollectedProductDTO> collectedProducts =
                collectedProductMapper.fromEntityToEmployeeCollectedProductDTO(
                        collectedProductService.getCollectedProductsByPeriod(request)
        );
        return collectedProducts;
    }

    /**
     Получение списка собранных товаров по ферме
     @param request Данные о временном промежутке.
     @return Ответ HTTP-запроса со статусом 200 и списком собранных товаров,
     или со статусом 403, с сообщением "Unauthorized",
     или со статусом 400, с сообщением, "Invalid data type".
     */
    @PostMapping("/collected-products/farm")
    public List<FarmCollectedProductDTO> getProductsByFarm(@RequestBody GetCollectedProductsFarmRequest request) {
        List<FarmCollectedProductDTO> collectedProducts =
                collectedProductMapper.fromEntityToFarmCollectedProductDTO(
                        collectedProductService.getCollectedProductsByPeriod(request)
                );
        return collectedProducts;
    }

    /**
     Установление оценки
     @param request Данные о работнике и оценке.
     @return Ответ HTTP-запроса со статусом 200, с сообщением, что оценка поставлена,
     или со статусом 403, с сообщением "Unauthorized",
     или со статусом 404, с сообщением, что работника не существует,
     или со статусом 409, с сообщением, что оценка уже поставлена.
     */
    @PostMapping("/mark")
    public MessageDTO setMarkForEmployee(@RequestBody SetMarkRequest request) {
        return new MessageDTO(employeeMarkService.saveEmployeeMark(request));
    }

    /**
     Удаление оценки
     @param request Данные о работнике.
     @return Ответ HTTP-запроса со статусом 200, с сообщением, что оценка удалена,
     или со статусом 403, с сообщением "Unauthorized",
     или со статусом 404, с сообщением, что работника не существует,
     или со статусом 404, с сообщением, что оценка не существует.
     */
    @DeleteMapping("/mark")
    public MessageDTO deleteMarkForEmployee(@RequestBody DeleteMarkRequest request) {
        return new MessageDTO(employeeMarkService.deleteEmployeeMark(request));
    }

    /**
     Установление плана
     @param request Данные о работнике и плане.
     @return Ответ HTTP-запроса со статусом 200, с сообщением, что план установлен,
     или со статусом 403, с сообщением "Unauthorized",
     или со статусом 404, с сообщением, что работника не существует,
     или со статусом 409, с сообщением, что план на продукт уже установлен.
     */
    @PostMapping("/plan")
    public MessageDTO setPlanForEmployee(@RequestBody SetPlanRequest request) {
        return new MessageDTO(planedProductService.setEmployeePlan(request));
    }

    /**
     Удаление плана
     @param request Данные о работнике.
     @return Ответ HTTP-запроса со статусом 200, с сообщением, что план удален,
     или со статусом 403, с сообщением "Unauthorized",
     или со статусом 404, с сообщением, что работника не существует,
     или со статусом 404, с сообщением, что плана не существует.
     */
    @DeleteMapping("/plan")
    public MessageDTO deletePlanForEmployee(@RequestBody DeletePlanRequest request) {
        return new MessageDTO(planedProductService.deleteEmployeePlan(request));
    }

    /**
     Изменение данных администратора
     @param type Тип данных.(email/password).
     @param authentication Данные об аутентификации.
     @param request Новые данные.
     @return Ответ HTTP-запроса со статусом 200, с сообщением, данные изменены,
     или со статусом 403, с сообщением "Unauthorized",
     или со статусом 404, с сообщением, что работника не существует,
     или со статусом 400, с сообщением "Invalid data type".
     */
    @PutMapping("/edit/{type}")
    public MessageDTO editCredentials(@PathVariable("type") String type,
                                      Authentication authentication,
                                      @RequestBody EditCredentialRequest request) {
        String email = (String) authentication.getPrincipal();
        return new MessageDTO(employeeService.editCredential(
                email, type, request.getCredential()));
    }
}
