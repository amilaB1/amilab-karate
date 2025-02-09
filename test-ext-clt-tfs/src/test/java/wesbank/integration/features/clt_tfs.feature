Feature: test_integration_osb_wesbank_external_clt_tfs

  Scenario Outline: test_integration_osb_wesbank_external_bus_vehicle_services request & response.
    Given That wesbank_external_clt_tfs ( <serviceName> ) service is up and running

    Examples: 
      | serviceName                 |
      | "copyOfNatisDocument"       |
     | "settlementAmountIndicator" |
     | "settlementAmountLetter"    |
      
