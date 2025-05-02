package com.comerzzia.promocionesapi.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Represents a purchase ticket containing multiple sale lines")
public class Ticket {

    @NotEmpty(message = "Ticket ID is required")
    @Schema(description = "Unique ticket ID", example = "TCK123456")
    private String id;

    @Size(min = 1, message = "At least one sale line is required")
    @Valid
    @Schema(description = "List of sale lines included in the ticket")
    private List<SaleLine> saleLines = new ArrayList<>();

    public Ticket(String id) {
        this.id = id;
    }

    public void addLine(SaleLine line) {
        this.saleLines.add(line);
    }

    public BigDecimal calculateTotal() {
        return saleLines.stream()
                .map(SaleLine::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
