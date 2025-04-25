

# PromocionEntity

Entidad que representa una promoción aplicable a un artículo

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | ID único de la promoción |  |
|**descripcion** | **String** | Descripción de la promoción |  [optional] |
|**fechaAlta** | **LocalDate** | Fecha de alta de la promoción |  [optional] |
|**fechaInicio** | **LocalDate** | Fecha de inicio de la promoción |  |
|**fechaFin** | **LocalDate** | Fecha de finalización de la promoción |  |
|**codigoArticulo** | **String** | Código del artículo al que se aplica la promoción |  |
|**tipo** | [**TipoEnum**](#TipoEnum) | Tipo de promoción (PRECIO o DESCUENTO) |  |
|**precioPromocion** | **BigDecimal** | Nuevo precio promocional (solo si el tipo es PRECIO) |  [optional] |
|**porcentajeDescuento** | **BigDecimal** | Porcentaje de descuento (solo si el tipo es DESCUENTO) |  [optional] |



## Enum: TipoEnum

| Name | Value |
|---- | -----|
| PRECIO | &quot;PRECIO&quot; |
| DESCUENTO | &quot;DESCUENTO&quot; |



