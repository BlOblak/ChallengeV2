# MeasurementResourceApi

All URIs are relative to *https://localhost:8200*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllMeasurementUsingGET**](MeasurementResourceApi.md#getAllMeasurementUsingGET) | **GET** /public/measurement | getAllMeasurement
[**getLatestMeasurementUsingGET**](MeasurementResourceApi.md#getLatestMeasurementUsingGET) | **GET** /public/measurement/latest | getLatestMeasurement
[**getMeasurementUsingGET**](MeasurementResourceApi.md#getMeasurementUsingGET) | **GET** /public/measurement/{id} | getMeasurement
[**getMeasurementsByTimeRangeUsingPOST**](MeasurementResourceApi.md#getMeasurementsByTimeRangeUsingPOST) | **POST** /public/measurement/byRange | getMeasurementsByTimeRange
[**insertMeasurementUsingPOST**](MeasurementResourceApi.md#insertMeasurementUsingPOST) | **POST** /public/measurement | insertMeasurement


<a name="getAllMeasurementUsingGET"></a>
# **getAllMeasurementUsingGET**
> WebApiResponseListMeasurement getAllMeasurementUsingGET(page, size)

getAllMeasurement

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.MeasurementResourceApi;


MeasurementResourceApi apiInstance = new MeasurementResourceApi();
Integer page = 56; // Integer | page
Integer size = 56; // Integer | size
try {
    WebApiResponseListMeasurement result = apiInstance.getAllMeasurementUsingGET(page, size);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MeasurementResourceApi#getAllMeasurementUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **page** | **Integer**| page | [optional]
 **size** | **Integer**| size | [optional]

### Return type

[**WebApiResponseListMeasurement**](WebApiResponseListMeasurement.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getLatestMeasurementUsingGET"></a>
# **getLatestMeasurementUsingGET**
> Measurement getLatestMeasurementUsingGET()

getLatestMeasurement

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.MeasurementResourceApi;


MeasurementResourceApi apiInstance = new MeasurementResourceApi();
try {
    Measurement result = apiInstance.getLatestMeasurementUsingGET();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MeasurementResourceApi#getLatestMeasurementUsingGET");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**Measurement**](Measurement.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMeasurementUsingGET"></a>
# **getMeasurementUsingGET**
> Measurement getMeasurementUsingGET(id)

getMeasurement

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.MeasurementResourceApi;


MeasurementResourceApi apiInstance = new MeasurementResourceApi();
Long id = 789L; // Long | id
try {
    Measurement result = apiInstance.getMeasurementUsingGET(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MeasurementResourceApi#getMeasurementUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| id |

### Return type

[**Measurement**](Measurement.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMeasurementsByTimeRangeUsingPOST"></a>
# **getMeasurementsByTimeRangeUsingPOST**
> WebApiResponseListMeasurement getMeasurementsByTimeRangeUsingPOST(measurementByRange)

getMeasurementsByTimeRange

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.MeasurementResourceApi;


MeasurementResourceApi apiInstance = new MeasurementResourceApi();
MeasurementByRange measurementByRange = new MeasurementByRange(); // MeasurementByRange | measurementByRange
try {
    WebApiResponseListMeasurement result = apiInstance.getMeasurementsByTimeRangeUsingPOST(measurementByRange);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MeasurementResourceApi#getMeasurementsByTimeRangeUsingPOST");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **measurementByRange** | [**MeasurementByRange**](MeasurementByRange.md)| measurementByRange |

### Return type

[**WebApiResponseListMeasurement**](WebApiResponseListMeasurement.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertMeasurementUsingPOST"></a>
# **insertMeasurementUsingPOST**
> Measurement insertMeasurementUsingPOST(measurement)

insertMeasurement

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.MeasurementResourceApi;


MeasurementResourceApi apiInstance = new MeasurementResourceApi();
Measurement measurement = new Measurement(); // Measurement | measurement
try {
    Measurement result = apiInstance.insertMeasurementUsingPOST(measurement);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MeasurementResourceApi#insertMeasurementUsingPOST");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **measurement** | [**Measurement**](Measurement.md)| measurement |

### Return type

[**Measurement**](Measurement.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

