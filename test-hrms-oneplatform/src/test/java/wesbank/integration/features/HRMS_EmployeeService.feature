Feature: HRMS-HRonePlatform-WesbankEmployeesData

  Scenario Outline: HRonePlatform-WesbankEmployeesData:|<serviceName>
    Given HRMS_Services ( <serviceName> ) service is up and running

    Examples: 
       | serviceName                                |
      
      
     # | "getBusinessPartnerPerEmployee"            |
     # | "getCostcentreDataPerEmployee"             |
     # | "getDailyVacationRules"                    |
     # | "getEmployeeCount"                         |
     # | "getEmployeeCountPerCompanyCode"           |
     # | "getEmployeeCountPerCostCentre"            |
     # | "getEmployeeInformation"                   |
     # | "getEmployeeInformationByDate"             |
     # | "getEmployeeInformationByIDNumber"         | 
     | "getEmployeeList"                          |
     # | "getEmployeeListPerClassification"         |
     # | "getEmployeeListPerCompany"                |
     # | "getEmployeeListPerCostCentre"             |
     # | "getEmployeeOrgInformation"                |
     # | "getEmployeeQualifications"                |
     # | "getExpatEmployeeList"                     |
     # | "getExpatOutEmployeeInformation"           |
     # | "getExpatOutEmployeeInformationByDate"     |
     # | "getExpatOutEmployeeInformationByIDNumber" |
     # | "getPensionerList"                         |
     # | "getPotentialExpatEmployeeList"            |
     # | "getReportingEmployees"                    |
    #  | "getReportingEmployeesExt"                 |
      #| "getTerminatedEmployeesList"               |
      
      
      
      
      
      
