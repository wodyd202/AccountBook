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
        HistoryRecord historyRecord = historyService.register(registerHistoryRequest.toRegisterHistory(), createWriter(principal));
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
        HistoryRecord historyRecord = historyService.update(historyId,updateHistoryRequest.toUpdateHistory(), createWriter(principal));
        return ResponseEntity.ok(historyRecord);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{historyId}")
    public void remove(@PathVariable long historyId,
                       Principal principal){
        historyService.remove(historyId, createWriter(principal));
    }

    private Writer createWriter(Principal principal){
        return Writer.of(principal.getName());
    }
}
