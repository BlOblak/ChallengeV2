## Use Cases

This chapter describes use cases supported by the Challenge v2 module.

## Public endpoints

## Energy Resource

### Get All measurements

Service URL: /public/measurement

HTTP method: **GET**

Other modules have the option to retrieve all measurements.

#### Request
!INCLUDE "../build/generated-snippets/energy-resource-test/when-get-all_then-return-all/http-request.md"

#### Response
!INCLUDE "../build/generated-snippets/energy-resource-test/when-get-all_then-return-all/http-response.md"

### Get one measurement

Service URL: /public/measurement/{id_of_the_measurement}

HTTP method: **GET**

Other modules have the option to retrieve one measurement by its Id.

#### Request
!INCLUDE "../build/generated-snippets/energy-resource-test/when-get-by-id_then-return-one/http-request.md"

#### Response
!INCLUDE "../build/generated-snippets/energy-resource-test/when-get-by-id_then-return-one/http-response.md"

### Insert one measurement

Service URL: /public/measurement

HTTP method: **POST**

Energy asset has the option to insert one measurement.

#### Request
!INCLUDE "../build/generated-snippets/energy-resource-test/when-insert-one_then-return-one/http-request.md"

#### Response
!INCLUDE "../build/generated-snippets/energy-resource-test/when-insert-one_then-return-one/http-response.md"

### Get latest measurement

Service URL: /public/measurement/latest

HTTP method: **GET**

Other modules have the option to retrieve latest measurement.

#### Request
!INCLUDE "../build/generated-snippets/energy-resource-test/when-get-latest_then-return-latest-one/http-request.md"

#### Response
!INCLUDE "../build/generated-snippets/energy-resource-test/when-get-latest_then-return-latest-one/http-response.md"

### Get measurements by date and time range

Service URL: /public/measurement/byRange

HTTP method: **POST**

Other modules have the option to retrieve latest measurement.

#### Request
!INCLUDE "../build/generated-snippets/energy-resource-test/when-get-measurements-by-time-range_then-return-list/http-request.md"

#### Response
!INCLUDE "../build/generated-snippets/energy-resource-test/when-get-measurements-by-time-range_then-return-list/http-response.md"

## Insure Listener

## Insurance document created

A message is received when energy asset sends a message with measurement data as content.
After receiving and deserializing the message content into Java Object it inserts the measurement in the database.

Message example:

!INCLUDE "./assets/MQ/snippets/insertMeasurementByMessage/message.md"