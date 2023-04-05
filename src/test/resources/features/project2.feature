@wip
Feature: Retrieving Animal APIs from Public API Registry

  Scenario: Get APIs from Public API Registry
    Given the API endpoint "https://api.publicapis.org/entries"
    When I retrieve all APIs with category "Animals" and link containing "github"
    And the list should contain distinct "Cors" values
    And I should see the count of APIs belonging to the "Animals" category