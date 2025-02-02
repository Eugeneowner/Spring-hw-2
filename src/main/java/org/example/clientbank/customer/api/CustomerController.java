package org.example.clientbank.customer.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.clientbank.account.Account;
import org.example.clientbank.account.api.dto.AccountMapper;
import org.example.clientbank.account.api.dto.ResponseAccountDto;
import org.example.clientbank.customer.api.dto.RequestCustomerDto;
import org.example.clientbank.customer.Customer;
import org.example.clientbank.account.enums.Currency;
import org.example.clientbank.customer.api.dto.CustomerMapper;
import org.example.clientbank.customer.api.dto.ResponseCustomerAllDataDto;
import org.example.clientbank.customer.api.dto.ResponseCustomerDto;
import org.example.clientbank.customer.status.CustomerStatus;
import org.example.clientbank.customer.model.CreateAccountByIdModel;
import org.example.clientbank.ResponseMessage;
import org.example.clientbank.customer.service.CustomerServiceImpl;
import org.example.clientbank.employer.status.EmployerStatus;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "http://localhost:3001",
        "https://client-bank-front.vercel.app"
}, allowedHeaders = "*")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerServiceImpl customerService;

    @GetMapping("/all_data")
    public ResponseEntity<List<ResponseCustomerAllDataDto>> findAllDataAboutCustomers() {
        log.info("Getting all customer information");
        List<ResponseCustomerAllDataDto> customers = customerService.findAll()
                .stream().map(CustomerMapper.INSTANCE::customerToCustomerAllDataDto).toList();
        if (customers.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(customers);
        }
    }

    @GetMapping
    public ResponseEntity<List<ResponseCustomerDto>> findAll() {
        log.info("Getting the list of all customers");
        List<ResponseCustomerDto> customers = customerService.findAll().stream()
                .map(CustomerMapper.INSTANCE::customerToCustomerDto).toList();
        if (customers.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(customers);
        }
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable long id) {
        log.info("Getting customer details by ID");
        Optional<ResponseCustomerDto> customerOptional = customerService.getCustomerById(id)
                .map(CustomerMapper.INSTANCE::customerToCustomerDto);
        if (customerOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(CustomerStatus.CUSTOMER_NOT_FOUND.getMessage());
        }
        return ResponseEntity.ok(customerOptional.get());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@RequestBody RequestCustomerDto requestCustomerDto) {
        log.info("Creating a new customer");
        Customer customer = CustomerMapper.INSTANCE.customerDtoToCustomer(requestCustomerDto);
        try {
            Customer createdCustomer = customerService.createCustomer(customer);
            return ResponseEntity.ok(CustomerMapper.INSTANCE.customerToCustomerDto(createdCustomer));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer creation failed: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseMessage> updateCustomer(@PathVariable Long id, @Valid @RequestBody RequestCustomerDto requestCustomerDto) {
        log.info("Updating customer details");

        CustomerStatus status = customerService.updateCustomer(id, requestCustomerDto);

        return switch (status) {
            case SUCCESS -> ResponseEntity.ok(new ResponseMessage("Customer information updated successfully."));
            case NOTHING_TO_UPDATE ->
                    ResponseEntity.ok(new ResponseMessage(CustomerStatus.NOTHING_TO_UPDATE.getMessage()));
            case CUSTOMER_NOT_FOUND ->
                    ResponseEntity.badRequest().body(new ResponseMessage(CustomerStatus.CUSTOMER_NOT_FOUND.getMessage()));
            default -> ResponseEntity.badRequest().body(new ResponseMessage(CustomerStatus.UNEXPECTED.getMessage()));
        };
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseMessage> deleteById(@PathVariable long id) {
        log.info("Deleting customer record by ID: {}", id);

        try {
            customerService.deleteById(id);
            return ResponseEntity.ok(new ResponseMessage("Customer record deleted successfully."));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.badRequest().body(new ResponseMessage(CustomerStatus.CUSTOMER_NOT_FOUND.getMessage()));
        }
    }

    @PostMapping("/create_account_by_id")
    public ResponseEntity<?> createAccountByCustomerId(@Valid @RequestBody CreateAccountByIdModel createAccountByIdModel) {
        log.info("Creating new account based on customer ID");
        Currency currency = Currency.valueOf(createAccountByIdModel.currency());
        Account createdAccount = customerService.createAccountByCustomerId(createAccountByIdModel.id(), currency);

        if (createdAccount == null) {
            return ResponseEntity.badRequest().body(new ResponseMessage(CustomerStatus.CUSTOMER_NOT_FOUND.getMessage()));

        } else {
            ResponseAccountDto responseAccountDto = AccountMapper.INSTANCE.accountToAccountDto(createdAccount);
            return ResponseEntity.ok(responseAccountDto);
        }
    }

    @DeleteMapping("/delete_account_by_id")
    public ResponseEntity<ResponseMessage> deleteAccountByCustomerId(@RequestParam long id, @RequestParam String accountNumber) {
        CustomerStatus status = customerService.deleteAccountByCustomerId(id, accountNumber);

        return switch (status) {
            case SUCCESS -> ResponseEntity.ok(new ResponseMessage("Account has been deleted successfully."));
            case CUSTOMER_NOT_FOUND ->
                    ResponseEntity.badRequest().body(new ResponseMessage(CustomerStatus.CUSTOMER_NOT_FOUND.getMessage()));
            case CARD_NOT_FOUND ->
                    ResponseEntity.badRequest().body(new ResponseMessage(CustomerStatus.CARD_NOT_FOUND.getMessage()));
            default -> ResponseEntity.badRequest().body(new ResponseMessage(CustomerStatus.UNEXPECTED.getMessage()));
        };
    }

    @DeleteMapping("/delete_accounts_by_id")
    public ResponseEntity<ResponseMessage> deleteAccountsByCustomerId(@RequestParam long id) {
        boolean deleted = customerService.deleteAccountsByCustomerId(id);
        if (deleted) {
            return ResponseEntity.ok(new ResponseMessage("Customer with ID has had all accounts deleted successfully: " + id));
        } else {
            return ResponseEntity.badRequest().body(new ResponseMessage(CustomerStatus.CUSTOMER_NOT_FOUND.getMessage()));
        }
    }

    @PutMapping("/customer/add_employer")
    public ResponseEntity<?> addEmployerToCustomer(@RequestParam long customerId,
                                                                 @RequestParam long employerId) {
        Enum<?> status = customerService.addEmployerToCustomer(customerId, employerId);


        if (status == CustomerStatus.SUCCESS) {
            return ResponseEntity.ok(new ResponseMessage(CustomerStatus.SUCCESS.getMessage()));
        } else if (status == CustomerStatus.CUSTOMER_NOT_FOUND) {
            return ResponseEntity.badRequest().body(new ResponseMessage(CustomerStatus.CUSTOMER_NOT_FOUND.getMessage()));
        } else if (status == EmployerStatus.EMPLOYER_NOT_FOUND) {
            return ResponseEntity.badRequest().body(new ResponseMessage(EmployerStatus.EMPLOYER_NOT_FOUND.getMessage()));
        } else if (status == CustomerStatus.NOTHING_TO_UPDATE) {
            return ResponseEntity.badRequest().body(new ResponseMessage(CustomerStatus.NOTHING_TO_UPDATE.getMessage()));
        }

        return ResponseEntity.badRequest().body(new ResponseMessage(CustomerStatus.UNEXPECTED.getMessage()));
    }

    @PutMapping("/customer/remove_employer")
    public ResponseEntity<ResponseMessage> removeEmployerFromCustomer(
            @RequestParam long customerId,
            @RequestParam long employerId) {


        Enum<?> status = customerService.removeEmployerFromCustomer(customerId, employerId);

        if (status == CustomerStatus.SUCCESS) {
            return ResponseEntity.ok(new ResponseMessage(CustomerStatus.SUCCESS.getMessage()));
        } else if (status == CustomerStatus.CUSTOMER_NOT_FOUND) {
            return ResponseEntity.badRequest().body(new ResponseMessage(CustomerStatus.CUSTOMER_NOT_FOUND.getMessage()));
        } else if (status == EmployerStatus.EMPLOYER_NOT_FOUND) {
            return ResponseEntity.badRequest().body(new ResponseMessage(EmployerStatus.EMPLOYER_NOT_FOUND.getMessage()));
        } else if (status == CustomerStatus.NOTHING_TO_UPDATE) {
            return ResponseEntity.badRequest().body(new ResponseMessage(CustomerStatus.NOTHING_TO_UPDATE.getMessage()));
        }

        return ResponseEntity.badRequest().body(new ResponseMessage(CustomerStatus.UNEXPECTED.getMessage()));
    }
}
