Feature: Accounts Management System

    # ========== Get All Accounts ==========
  Scenario Outline: Get All Accounts present in db, but isn't there
    Given the account table is empty
    When  error flow, the client calls to retrieve all accounts, GET /api/accounts
    Then the client recieves status code of <status_code>
    And the client recieves the error response
      |                    message           | statusCode    |   path   |
      | There is no accounts present in db.  | <status_code> |   <path> |
    Examples:
      | status_code  |  path           |
      |  404         |  /api/accounts  |

  # ========== Create Account ==========
  Scenario: Create Account
    When the client calls POST /api/accounts with request body
      | accountHolderName | accountName | ifsc | currency | country | balance |
      |  name1              | saving       | 11111 | rupees  | IND    | 10000.00 |
    Then the client recieves status code of 201
    And the client recieves the newly created account
      | accountId | accountHolderName | accountName | ifsc | currency | country | balance |
      | 1         |  name1              | saving       | 11111 | rupees  | IND    | 10000.00 |

# ========== Get Account By Id ==========
  Scenario: Get Account By Id
    Given the collection of accounts
      | accountId | accountHolderName | accountName | ifsc | currency | country | balance  |
      | 1         |  name1            | saving       | 11111 | rupees  | IND    | 10000.00 |
      | 2         |  name2            | saving       | 2222 | rupees  | IND    | 20000.00 |
    When  normal flow, the client calls GET /api/accounts/1
    Then the client recieves status code of 200
    And the client recieves the account details
      | accountId | accountHolderName | accountName | ifsc | currency | country | balance  |
      | 1         |  name1            | saving       | 11111 | rupees  | IND    | 10000.00 |

  Scenario Outline: Get Account By Id, but isn't there
    Given the collection of accounts
      | accountId | accountHolderName | accountName | ifsc | currency | country | balance  |
      | 1         |  name1            | saving       | 11111 | rupees  | IND    | 10000.00 |
      | 2         |  name2            | saving       | 2222 | rupees  | IND    | 20000.00 |
    When  error flow, the client calls GET <path>
    Then the client recieves status code of <status_code>
    And the client recieves the error response
      |                    message                           | statusCode       |    path  |
      | Account not found in db with given account id = <id> |   <status_code>  | <path>  |
    Examples:
      | path              |  id  | status_code  |
      | /api/accounts/100 | 100  |  404         |

    # ========== Get All Accounts ==========
  Scenario: Get All Accounts present in db
    Given the collection of accounts
      | accountId | accountHolderName | accountName | ifsc | currency | country | balance  |
      | 1         |  name1            | saving       | 11111 | rupees  | IND    | 10000.00 |
      | 2         |  name2            | saving       | 2222 | rupees  | IND    | 20000.00 |
    When  normal flow, the client calls to retrieve all accounts, GET /api/accounts
    Then the client recieves status code of 200
    And the client recieves the all accounts present in db
      | accountId | accountHolderName | accountName | ifsc | currency | country | balance  |
      | 1         |  name1            | saving       | 11111 | rupees  | IND    | 10000.00 |
      | 2         |  name2            | saving       | 2222 | rupees  | IND    | 20000.00 |


    # ========== Update Account By Id ==========
  Scenario: Update Account By Id
    Given the collection of accounts
      | accountId | accountHolderName | accountName | ifsc | currency | country | balance  |
      | 1         |  name1            | saving       | 11111 | rupees  | IND    | 10000.00 |
      | 2         |  name2            | saving       | 2222 | rupees  | IND    | 20000.00 |
    When  normal flow, the client calls PUT /api/accounts/1 with request body
      | accountHolderName | accountName | ifsc | currency | country | balance  |
      |  madhu            | current       | 12345 | rupees  | IND    | 8000.00 |
    Then the client recieves status code of 200
    And the client recieves the updated account details
      | accountId | accountHolderName | accountName | ifsc  | currency | country | balance  |
      |    1      |  madhu            | current     | 12345 | rupees   | IND     | 8000.00  |

  Scenario Outline: Update Account By Id, but isn't there
    Given the collection of accounts
      | accountId | accountHolderName | accountName | ifsc | currency | country | balance  |
      | 1         |  name1            | saving       | 11111 | rupees  | IND    | 10000.00 |
      | 2         |  name2            | saving       | 2222 | rupees  | IND    | 20000.00 |
    When  error flow, the client calls PUT <path>
      | accountHolderName | accountName | ifsc | currency | country | balance  |
      |  madhu            | current       | 12345 | rupees  | IND    | 8000.00 |
    Then the client recieves status code of <status_code>
    And the client recieves the error response
      |                    message                           | statusCode       |    path  |
      | Account not found in db with given account id = <id> |   <status_code>  | <path>  |
    Examples:
      | path              |  id  | status_code  |
      | /api/accounts/100 | 100  |  404         |

    # ========== Delete Account By Id ==========
  Scenario: Delete Account By Id
    Given the collection of accounts
      | accountId | accountHolderName | accountName | ifsc | currency | country | balance  |
      | 1         |  name1            | saving       | 11111 | rupees  | IND    | 10000.00 |
      | 2         |  name2            | saving       | 2222 | rupees  | IND    | 20000.00 |
    When  normal flow, the client calls DELETE /api/accounts/1
    Then the client recieves status code of 204

  Scenario Outline: Delete Account By Id, but isn't there
    Given the collection of accounts
      | accountId | accountHolderName | accountName | ifsc | currency | country | balance  |
      | 2         |  name2            | saving       | 2222 | rupees  | IND    | 20000.00 |
    When  error flow, the client calls DELETE <path>
    Then the client recieves status code of <status_code>
    And the client recieves the error response
      |                    message                           | statusCode       |    path  |
      | Account not found in db with given account id = <id> |   <status_code>  | <path>  |
    Examples:
      | path              |  id  | status_code  |
      | /api/accounts/100 | 100  |  404         |

