Feature: test_integration_osb_wesbank_external_bus_vehicle_services

  Scenario Outline: test_integration_osb_wesbank_external_bus_vehicle_services request & response.
    Given That osb_wesbank_external_bus_vehicle ( <serviceName> ) service is up and running

    Examples: 
      | serviceName               |
      
      #| "eNatisMotorVehicleQuery" |
      
      | "getFinanceProduct"       |
      | "getLeasingFinanceQuote"  |
      | "getVehicleLabels"        |
      
      #| "FetchMakes"              |
      #| "FetchMMCodeByVIN"        |
      #| "FetchModels"             |
      
      #| "FetchModelValue"         |
      #| "FetchModelYears"         |
