package com.comerzzia.promocionesapi.mappers;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comerzzia.promocionesapi.dto.TicketDTO;
import com.comerzzia.promocionesapi.model.SaleLine;
import com.comerzzia.promocionesapi.model.Ticket;

@Component
public class TicketMapper {

	@Autowired
	private SaleLineMapper saleLineMapper;
	
	public TicketDTO toDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setSaleLines(
            ticket.getSaleLines().stream()
                  .map(saleLineMapper::toDTO)
                  .collect(Collectors.toList())
        );
        return dto;
    }

    public Ticket fromDTO(TicketDTO dto) {
        Ticket ticket = new Ticket(dto.getId());
        List<SaleLine> lines = dto.getSaleLines().stream()
                                  .map(saleLineMapper::fromDTO)
                                  .collect(Collectors.toList());
        ticket.setSaleLines(lines);
        return ticket;
    }
}
