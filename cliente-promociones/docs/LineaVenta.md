

# LineaVenta

Representa una línea de venta dentro de un ticket

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**codigoArticulo** | **String** | Código del artículo vendido |  |
|**cantidad** | **Integer** | Cantidad de artículos vendidos |  [optional] |
|**precioUnitarioOriginal** | **BigDecimal** | Precio original por unidad |  [optional] |
|**precioUnitarioPromocionado** | **BigDecimal** | Precio por unidad después de aplicar promociones |  [optional] |
|**importeTotal** | **BigDecimal** | Importe total de la línea (precio promocionado x cantidad) |  [optional] |
|**idPromocionAplicada** | **String** | ID de la promoción aplicada (si existe) |  [optional] |



