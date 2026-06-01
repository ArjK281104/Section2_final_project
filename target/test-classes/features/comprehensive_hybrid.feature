Feature: Comprehensive Hybrid End-to-End Note Lifecycle
  As an automation engineer
  I want to interact with the application using both API and UI
  So that I can verify backend data syncs perfectly with frontend interactions

  @hybrid
  Scenario: Create note via API, verify and delete via UI, confirm deletion via API
    # --- API PHASE: Setup Data ---
    Given I authenticate via API with email "arjun.korde3@gmail.com" and password "Arjun@123"
    And I create a note via API with title "E2E Hybrid Test Note", description "Verifying UI and API sync", category "Work"
    
    # --- UI PHASE: Verify and Interact ---
    When I navigate to the application and log in with email "arjun.korde3@gmail.com" and password "Arjun@123"
    Then I should see the note titled "E2E Hybrid Test Note" on the UI dashboard
    When I delete the note titled "E2E Hybrid Test Note" via the UI
    
    # --- API PHASE: Backend Teardown Validation ---
    Then I verify via API that the created note no longer exists