package org.example.clientbank.account.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.clientbank.account.status.AccountStatus;
import org.example.clientbank.account.model.AddWithdrawFundsModel;
import org.example.clientbank.ResponseMessage;
import org.example.clientbank.account.model.SendFundsModel;
import org.example.clientbank.account.service.AccountServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "http://localhost:3001",
        "https://client-bank-front.vercel.app"
}, allowedHeaders = "*")
@RequiredArgsConstructor
public class AccountController {
    private final AccountServiceImpl accountService;

    @PostMapping("/add_funds")
    public ResponseEntity<ResponseMessage> addFunds(@Valid @RequestBody AddWithdrawFundsModel addWithdrawFundsModel) {


        AccountStatus status = accountService.addFunds(addWithdrawFundsModel.cardNumber(), addWithdrawFundsModel.sum());
        return switch (status) {
            case SUCCESS -> ResponseEntity.ok(new ResponseMessage("Funds added successfully."));
            case ACCOUNT_NOT_FOUND ->
                    ResponseEntity.badRequest().body(new ResponseMessage(AccountStatus.ACCOUNT_NOT_FOUND.getMessage()));
            default -> ResponseEntity.badRequest().body(new ResponseMessage(AccountStatus.UNEXPECTED.getMessage()));
        };
    }

    @PostMapping("/withdraw_funds")
    public ResponseEntity<ResponseMessage> withdrawFunds(@Valid @RequestBody AddWithdrawFundsModel addWithdrawFundsModel) {

        AccountStatus status = accountService.withdrawFunds(addWithdrawFundsModel.cardNumber(), addWithdrawFundsModel.sum());
        return switch (status) {
            case SUCCESS -> ResponseEntity.ok(new ResponseMessage("Funds have been successfully withdrawn."));
            case INSUFFICIENT_FUNDS ->
                    ResponseEntity.badRequest().body(new ResponseMessage(AccountStatus.INSUFFICIENT_FUNDS.getMessage()));
            case ACCOUNT_NOT_FOUND ->
                    ResponseEntity.badRequest().body(new ResponseMessage(AccountStatus.ACCOUNT_NOT_FOUND.getMessage()));
            default -> ResponseEntity.badRequest().body(new ResponseMessage(AccountStatus.UNEXPECTED.getMessage()));
        };
    }

    @PostMapping("/send_funds")
    public ResponseEntity<ResponseMessage> sendFunds(@Valid @RequestBody SendFundsModel sendFundsModel) {
        AccountStatus status = accountService.sendFunds(sendFundsModel.numberFrom(), sendFundsModel.numberTo(), sendFundsModel.sum());
        return switch (status) {
            case SUCCESS -> ResponseEntity.ok(new ResponseMessage("Funds transfer successful."));
            case ACCOUNT_FROM_NOT_FOUND ->
                    ResponseEntity.badRequest().body(new ResponseMessage(AccountStatus.ACCOUNT_FROM_NOT_FOUND.getMessage()));
            case ACCOUNT_TO_NOT_FOUND ->
                    ResponseEntity.badRequest().body(new ResponseMessage(AccountStatus.ACCOUNT_TO_NOT_FOUND.getMessage()));
            case INSUFFICIENT_FUNDS ->
                    ResponseEntity.badRequest().body(new ResponseMessage(AccountStatus.INSUFFICIENT_FUNDS.getMessage()));
            default -> ResponseEntity.badRequest().body(new ResponseMessage(AccountStatus.UNEXPECTED.getMessage()));
        };
    }
}
