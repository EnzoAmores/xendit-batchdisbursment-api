@Regression
Feature: Batch Disbursement

  Scenario Outline: Batch Disbursement Validation
    Given I will test batch disbursement api
    When I create a batch disbursement with the following details
      | reference_id        | <reference_id>        |
      | amount              | <amount>              |
      | bank_code           | <bank_code>           |
      | bank_account_number | <bank_account_number> |
      | bank_account_name   | <bank_account_name>   |
      | description         | <description>         |
      | external_id         | <external_id>         |
    Then I will expect a response that the api <expectedOutput>

    Examples: 
      | reference_id   | amount            | bank_code | bank_account_number | bank_account_name | description        | external_id | expectedOutput |
      | Sample-Disb123 |            123.45 | BCA       |          1234567890 | Fadlan            | Batch Disbursement | SampDisb1   | Passed         |
      |                |            123.45 | BCA       |          1234567890 | Fadlan            | Batch Disbursement | SampDisb1   | Failed         |
      | Sample-Disb123 |                   | BCA       |          1234567890 | Fadlan            | Batch Disbursement | SampDisb1   | Failed         |
      | Sample-Disb123 |            123.45 |           |          1234567890 | Fadlan            | Batch Disbursement | SampDisb1   | Failed         |
      | Sample-Disb123 |            123.45 | BCA       |                     | Fadlan            | Batch Disbursement | SampDisb1   | Failed         |
      | Sample-Disb123 |            123.45 | BCA       |          1234567890 |                   | Batch Disbursement | SampDisb1   | Failed         |
      | Sample-Disb123 |            123.45 | BCA       |          1234567890 | Fadlan            |                    | SampDisb1   | Failed         |
      | Sample-Disb123 | 90000000000000000 | BCA       |          1234567890 | Fadlan            | Batch Disbursement | SampDisb1   | Failed         |
      | Sample-Disb123 |            123.45 | ABC       |          1234567890 | Fadlan            | Batch Disbursement | SampDisb1   | Failed         |
      | Sample-Disb123 | 123.45.67         | BCA       |          1234567890 | Fadlan            | Batch Disbursement | SampDisb1   | Failed         |
      | Sample-Disb123 |            123.45 | BCA       | 1001.@343242        | Fadlan            | Batch Disbursement | SampDisb1   | Failed         |

  Scenario: Batch Disbursement with Above Max Rows
    Given I will test batch disbursement api
    When I send a request to the batch disbursement api with more than 10000 transactions
    Then I will expect a response that the api "Failed due to Max Rows Limit Exceeded"

  Scenario: Batch Disbursement with an invalid API Key
    Given I will test batch disbursement api
    When I send a request to the batch disbursement api with an invalid API key
    Then I will expect a response that the api "Cannot be called due to Unauthorized API key"
