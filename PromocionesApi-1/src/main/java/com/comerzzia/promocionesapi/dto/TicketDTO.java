package com.comerzzia.promocionesapi.dto;


import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "DTO representing a purchase ticket")
public class TicketDTO {

    @NotEmpty(message = "Ticket ID is required")
    @Schema(description = "Unique ticket ID", example = "TCK123456")
    private String id;

    @NotNull(message = "Sale lines must be provided")
    @Size(min = 1, message = "At least one sale line is required")
    @Valid
    @Schema(description = "List of sale lines included in the ticket")
    private List<SaleLineDTO> saleLines;
}

