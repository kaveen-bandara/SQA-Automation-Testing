Feature: feature to test google functionality

  Scenario: Validate google search is working
    Given browser is open
    And user is on google search page
    When user enters a text in search box
    And press enter
    Then user is navigate to search results