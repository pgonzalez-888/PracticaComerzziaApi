package org.openapitools.client.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.model.DiscountDetails;
import org.openapitools.client.model.FixedPriceDetails;
import org.openapitools.client.model.PromotionDTO;
import org.openapitools.client.model.PromotionDTO.TypeEnum;
import org.openapitools.client.model.SaleLineDTO;
import org.openapitools.client.model.TicketDTO;

/**
 * Demo application to exercise PromotionsApi outside of JUnit tests.
 */
public class PromotionsApiDemo {

    public static void main(String[] args) {
        ApiClient client = new ApiClient();
        client.addDefaultHeader("API-KEY", "czzpromociones");
        PromotionsApi api = new PromotionsApi(client);

        String discountId = "DEMO_DISCOUNT_" + UUID.randomUUID().toString().substring(0,8);
        String fixedId    = "DEMO_FIXED_"    + UUID.randomUUID().toString().substring(0,8);

        try {
            // 1. Creaar una promocion de descuento
            PromotionDTO discountPromo = new PromotionDTO();
            discountPromo.setId(discountId);
            discountPromo.setType(TypeEnum.DISCOUNT);
            discountPromo.setItemCode("DEMO_ITEM");
            discountPromo.setStartDate(LocalDate.now());
            discountPromo.setEndDate(LocalDate.now().plusDays(30));
            DiscountDetails dd = new DiscountDetails();
            dd.setPercentage(new BigDecimal("15")); // 15% off
            discountPromo.setDiscountDetails(dd);
            api.createPromotion(discountPromo);
            System.out.println("Created discount promotion: " + discountId);

            // 2. Crear una promoción de precio fijo
            PromotionDTO fixedPromo = new PromotionDTO();
            fixedPromo.setId(fixedId);
            fixedPromo.setType(TypeEnum.FIXED_PRICE);
            fixedPromo.setItemCode("DEMO_ITEM_FIXED");
            fixedPromo.setStartDate(LocalDate.now());
            fixedPromo.setEndDate(LocalDate.now().plusDays(10));
            FixedPriceDetails fp = new FixedPriceDetails();
            fp.setPrice(new BigDecimal("2"));
            fixedPromo.setFixedPriceDetails(fp);
            api.createPromotion(fixedPromo);
            System.out.println("Created fixed-price promotion: " + fixedId);

            // 3. Recibir e imprimir promociones
            PromotionDTO gotDiscount = api.getPromotionById(discountId);
            System.out.println("Retrieved discount promo: " + gotDiscount.getDiscountDetails().getPercentage() + "%");
            PromotionDTO gotFixed = api.getPromotionById(fixedId);
            System.out.println("Retrieved fixed promo for item " + gotFixed.getItemCode() + ": unit price=" + gotFixed.getFixedPriceDetails().getPrice());

            // 4. Aplicar promociones a un ticket
            TicketDTO ticket = new TicketDTO();
            ticket.setId("DEMO_TICKET");
            SaleLineDTO line1 = new SaleLineDTO();
            line1.setItemCode("DEMO_ITEM");
            line1.setQuantity(2);
            line1.setOriginalUnitPrice(new BigDecimal("50"));
            SaleLineDTO line2 = new SaleLineDTO();
            line2.setItemCode("DEMO_ITEM_FIXED");
            line2.setQuantity(3);
            line2.setOriginalUnitPrice(new BigDecimal("5"));
            ticket.setSaleLines(Arrays.asList(line1, line2));

            TicketDTO result = api.applyPromotions(ticket);
            System.out.println("After promotions:");
            result.getSaleLines().forEach(sl -> {
                System.out.println("  Item: " + sl.getItemCode() +
                                   ", Original: " + sl.getOriginalUnitPrice() +
                                   ", Discounted: " + sl.getDiscountedUnitPrice());
            });

            // 5. Limpiar datos
            api.deletePromotion(discountId);
            api.deletePromotion(fixedId);
            System.out.println("Cleaned up promotions.");

        } catch (ApiException e) {
            System.err.println("API error (code " + e.getCode() + "): " + e.getMessage());
            e.printStackTrace();
        }
    }
}