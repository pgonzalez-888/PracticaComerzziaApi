# PromocionesApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**actualizarPromocion**](PromocionesApi.md#actualizarPromocion) | **PUT** /api/promociones/{id} | Actualizar una promoción existente |
| [**aplicarPromociones**](PromocionesApi.md#aplicarPromociones) | **POST** /api/promociones/aplicar | Aplicar promociones a un ticket |
| [**crearPromocion**](PromocionesApi.md#crearPromocion) | **POST** /api/promociones | Crear una nueva promoción |
| [**eliminarPromocion**](PromocionesApi.md#eliminarPromocion) | **DELETE** /api/promociones/{id} | Eliminar una promoción |
| [**obtenerPromocionPorId**](PromocionesApi.md#obtenerPromocionPorId) | **GET** /api/promociones/{id} | Obtener una promoción por ID |
| [**obtenerPromociones**](PromocionesApi.md#obtenerPromociones) | **GET** /api/promociones | Obtener todas las promociones |


<a name="actualizarPromocion"></a>
# **actualizarPromocion**
> PromocionEntity actualizarPromocion(id, promocionEntity)

Actualizar una promoción existente

Actualiza los datos de una promoción ya existente utilizando su ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.PromocionesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    PromocionesApi apiInstance = new PromocionesApi(defaultClient);
    String id = "id_example"; // String | 
    PromocionEntity promocionEntity = new PromocionEntity(); // PromocionEntity | 
    try {
      PromocionEntity result = apiInstance.actualizarPromocion(id, promocionEntity);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PromocionesApi#actualizarPromocion");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **String**|  | |
| **promocionEntity** | [**PromocionEntity**](PromocionEntity.md)|  | |

### Return type

[**PromocionEntity**](PromocionEntity.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

<a name="aplicarPromociones"></a>
# **aplicarPromociones**
> Ticket aplicarPromociones(ticket)

Aplicar promociones a un ticket

Aplica las promociones disponibles a un ticket dado y devuelve el ticket actualizado

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.PromocionesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    PromocionesApi apiInstance = new PromocionesApi(defaultClient);
    Ticket ticket = new Ticket(); // Ticket | 
    try {
      Ticket result = apiInstance.aplicarPromociones(ticket);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PromocionesApi#aplicarPromociones");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **ticket** | [**Ticket**](Ticket.md)|  | |

### Return type

[**Ticket**](Ticket.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

<a name="crearPromocion"></a>
# **crearPromocion**
> PromocionEntity crearPromocion(promocionEntity)

Crear una nueva promoción

Crea una nueva promoción si no existe una con el mismo ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.PromocionesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    PromocionesApi apiInstance = new PromocionesApi(defaultClient);
    PromocionEntity promocionEntity = new PromocionEntity(); // PromocionEntity | 
    try {
      PromocionEntity result = apiInstance.crearPromocion(promocionEntity);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PromocionesApi#crearPromocion");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **promocionEntity** | [**PromocionEntity**](PromocionEntity.md)|  | |

### Return type

[**PromocionEntity**](PromocionEntity.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

<a name="eliminarPromocion"></a>
# **eliminarPromocion**
> eliminarPromocion(id)

Eliminar una promoción

Elimina una promoción específica dado su ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.PromocionesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    PromocionesApi apiInstance = new PromocionesApi(defaultClient);
    String id = "id_example"; // String | 
    try {
      apiInstance.eliminarPromocion(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling PromocionesApi#eliminarPromocion");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **String**|  | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

<a name="obtenerPromocionPorId"></a>
# **obtenerPromocionPorId**
> PromocionEntity obtenerPromocionPorId(id)

Obtener una promoción por ID

Devuelve los detalles de una promoción específica dado su ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.PromocionesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    PromocionesApi apiInstance = new PromocionesApi(defaultClient);
    String id = "id_example"; // String | 
    try {
      PromocionEntity result = apiInstance.obtenerPromocionPorId(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PromocionesApi#obtenerPromocionPorId");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **String**|  | |

### Return type

[**PromocionEntity**](PromocionEntity.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

<a name="obtenerPromociones"></a>
# **obtenerPromociones**
> List&lt;PromocionEntity&gt; obtenerPromociones()

Obtener todas las promociones

Devuelve una lista con todas las promociones disponibles

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.PromocionesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    PromocionesApi apiInstance = new PromocionesApi(defaultClient);
    try {
      List<PromocionEntity> result = apiInstance.obtenerPromociones();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PromocionesApi#obtenerPromociones");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;PromocionEntity&gt;**](PromocionEntity.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

