package com.example.farm.service;

import com.example.farm.exception.EntityDoesNotExistException;
import com.example.farm.exception.InvalidDataException;
import com.example.farm.mapper.EmployeeMapper;
import com.example.farm.model.CollectedProduct;
import com.example.farm.model.Employee;
import com.example.farm.model.Product;
import com.example.farm.model.dto.MessageDTO;
import com.example.farm.model.request.CollectedProductRequest;
import com.example.farm.model.request.GetCollectedProductsEmployeeRequest;
import com.example.farm.model.request.GetCollectedProductsFarmRequest;
import com.example.farm.repository.CollectedProductRepository;
import com.example.farm.util.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


@AllArgsConstructor
@Service
public class CollectedProductService {
    private final EmployeeService employeeService;
    private final ProductService productService;
    private final CollectedProductRepository collectedProductRepository;
    public String collectProducts(CollectedProductRequest request, String email) {
        Employee employee = employeeService.getEmployeeByEmail(email);
        Product product = productService.getProductByName(request.getProductName());
        CollectedProduct collectedProduct = new CollectedProduct(
                product,
                request.getCount(),
                new GregorianCalendar().getTime(),
                employee
        );
        collectedProductRepository.save(collectedProduct);
        return "Products have been collected.";
    }

    public List<CollectedProduct> getCollectedProductsByPeriod(GetCollectedProductsEmployeeRequest request) {
        if (request.getPeriod().equalsIgnoreCase("day")) {
            Date date = DateUtils.getDayDate(request.getPeriodNumber());
            Employee employee = employeeService.getEmployeeByEmail(request.getEmail());

            return collectedProductRepository.findAllByDateAndEmployee(date, employee);
        } else if (request.getPeriod().equalsIgnoreCase("month")) {
            Date[] date = DateUtils.getMonthDate(request.getPeriodNumber());
            Employee employee = employeeService.getEmployeeByEmail(request.getEmail());

            return collectedProductRepository.findAllByDateBetweenAndEmployee(date[0], date[1], employee);
        }
        throw new InvalidDataException("Invalid type.");
    }

    public List<CollectedProduct> getCollectedProductsByPeriod(GetCollectedProductsFarmRequest request) {
        if (request.getPeriod().equalsIgnoreCase("day")) {
            Date date = DateUtils.getDayDate(request.getPeriodNumber());

            return collectedProductRepository.findAllByDate(date);
        } else if (request.getPeriod().equalsIgnoreCase("month")) {
            Date[] date = DateUtils.getMonthDate(request.getPeriodNumber());

            return collectedProductRepository.findAllByDateBetween(date[0], date[1]);
        }
        throw new InvalidDataException("Invalid type.");
    }
}
