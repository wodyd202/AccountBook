package com.payhere.accountbook.services.history.presentation;

import com.payhere.accountbook.services.history.application.HistoryRecord;
import com.payhere.accountbook.services.history.application.HistoryService;
import com.payhere.accountbook.services.history.domain.Writer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static com.payhere.accountbook.web.InvalidRequestException.verifyNotHasErrors;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    @PostMapping
    public ResponseEntity<HistoryRecord> register(@Valid @RequestBody RegisterHistoryRequest registerHistoryRequest,
                                                  Errors errors,
                                                  Principal principal){
        verifyNotHasErrors(errors);
        HistoryRecord historyRecord = historyService.register(registerHistoryRequest.toRegisterHistory(), Writer.of(principal.getName()));
        return ResponseEntity.status(HttpStatus.CREATED).body(historyRecord);
    }

    @PatchMapping("{historyId}")
    public ResponseEntity<HistoryRecord> update(@PathVariable long historyId,
                                                @Valid @RequestBody UpdateHistoryRequest updateHistoryRequest,
                                                Errors errors,
                                                Principal principal){
        if (updateHistoryRequest.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        verifyNotHasErrors(errors);
        HistoryRecord historyRecord = historyService.update(historyId,updateHistoryRequest.toUpdateHistory(), Writer.of(principal.getName()));
        return ResponseEntity.ok(historyRecord);
    }
}
