Feature: SIM Card Activation
  As a user of the SIM card activation service
  I want to activate a SIM card and verify the activation status

  Scenario: Successful SIM card activation
    Given a SIM card with ICCID "1255789453849037777"
    When the SIM card is activated
    Then the activation should be successful
    And I query the activation status using the ICCID
    Then the activation status should be "true"

  Scenario: Failed SIM card activation
    Given a SIM card with ICCID "8944500102198304826"
    When the SIM card is activated
    Then the activation should fail
    And I query the activation status using the ICCID
    Then the activation status should be "false"