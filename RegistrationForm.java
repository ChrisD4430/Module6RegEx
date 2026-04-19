import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * RegistrationForm builds the UI and validation logic for a JavaFX
 * registration form. It validates user input using Regex and only
 * enables the "Add" button when all fields are valid.
 */
public class RegistrationForm {

    // Main layout container
    private VBox layout;

    // Input fields
    private TextField firstNameField, lastNameField, emailField, dobField, zipField;

    // Status labels that show validation messages
    private Label firstNameStatus, lastNameStatus, emailStatus, dobStatus, zipStatus;

    // Button that becomes enabled only when all fields are valid
    private Button addButton;

    // Reference to the main stage so we can switch scenes
    private Stage stage;

    // Regex patterns for validation
    private final String NAME_REGEX = "^[A-Za-z]{2,25}$"; // Only letters, 2–25 chars
    private final String DOB_REGEX = "^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/([0-9]{4})$"; // MM/DD/YYYY
    private final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@farmingdale\\.edu$"; // Farmingdale email only
    private final String ZIP_REGEX = "^[0-9]{5}$"; // 5 digits

    public RegistrationForm(Stage stage) {
        this.stage = stage;
        createUI();        // Build the interface
        setupValidation(); // Attach validation logic
    }

    /** Returns the fully constructed UI layout */
    public VBox getView() {
        return layout;
    }

    /**
     * Creates all UI components and arranges them visually.
     * This method focuses only on layout and styling.
     */
    private void createUI() {

        // Title label
        Label title = new Label("User Registration");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        // Create text fields with placeholder text
        firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        firstNameStatus = new Label(); // Will show validation result

        lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");
        lastNameStatus = new Label();

        emailField = new TextField();
        emailField.setPromptText("Email (Farmingdale only)");
        emailStatus = new Label();

        dobField = new TextField();
        dobField.setPromptText("MM/DD/YYYY");
        dobStatus = new Label();

        zipField = new TextField();
        zipField.setPromptText("Zip Code");
        zipStatus = new Label();

        // Add button starts disabled until all fields are valid
        addButton = new Button("Add");
        addButton.setDisable(true);
        addButton.setOnAction(e -> openSuccessPage()); // Navigate to next UI

        // Main layout container
        layout = new VBox(12, title,
                firstNameField, firstNameStatus,
                lastNameField, lastNameStatus,
                emailField, emailStatus,
                dobField, dobStatus,
                zipField, zipStatus,
                addButton);

        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_CENTER);
    }

    /**
     * Sets up validation listeners for each field.
     * Validation occurs when the user leaves (unfocuses) a field.
     * The Add button updates dynamically based on validity.
     */
    private void setupValidation() {

        // Validate FIRST NAME when focus is lost
        firstNameField.focusedProperty().addListener((obs, oldV, newV) -> {
            if (!newV) validateField(firstNameField, firstNameStatus, NAME_REGEX, "Invalid first name");
        });

        // Validate LAST NAME
        lastNameField.focusedProperty().addListener((obs, oldV, newV) -> {
            if (!newV) validateField(lastNameField, lastNameStatus, NAME_REGEX, "Invalid last name");
        });

        // Validate EMAIL
        emailField.focusedProperty().addListener((obs, oldV, newV) -> {
            if (!newV) validateField(emailField, emailStatus, EMAIL_REGEX, "Must be a Farmingdale email");
        });

        // Validate DATE OF BIRTH
        dobField.focusedProperty().addListener((obs, oldV, newV) -> {
            if (!newV) validateField(dobField, dobStatus, DOB_REGEX, "Use MM/DD/YYYY");
        });

        // Validate ZIP CODE
        zipField.focusedProperty().addListener((obs, oldV, newV) -> {
            if (!newV) validateField(zipField, zipStatus, ZIP_REGEX, "Zip must be 5 digits");
        });

        // Lambda to update Add button whenever any field changes
        Runnable updateButton = () -> addButton.setDisable(!allValid());

        // Listen for text changes in all fields
        firstNameField.textProperty().addListener((o, a, b) -> updateButton.run());
        lastNameField.textProperty().addListener((o, a, b) -> updateButton.run());
        emailField.textProperty().addListener((o, a, b) -> updateButton.run());
        dobField.textProperty().addListener((o, a, b) -> updateButton.run());
        zipField.textProperty().addListener((o, a, b) -> updateButton.run());
    }

    /**
     * Validates a single field using a regex pattern.
     * Updates the status label with success or error message.
     *
     * @param field     The TextField being validated
     * @param status    The Label that displays validation feedback
     * @param regex     The regex pattern to test against
     * @param errorMsg  Message shown when validation fails
     */
    private void validateField(TextField field, Label status, String regex, String errorMsg) {

        // If the text matches the regex, mark as valid
        if (field.getText().matches(regex)) {
            status.setText("✓ Valid");
            status.setStyle("-fx-text-fill: green;");
        } else {
            // Otherwise show error message
            status.setText(errorMsg);
            status.setStyle("-fx-text-fill: red;");
        }
    }

    /**
     * Checks whether ALL fields currently contain valid data.
     * This determines whether the Add button should be enabled.
     */
    private boolean allValid() {
        return firstNameField.getText().matches(NAME_REGEX)
                && lastNameField.getText().matches(NAME_REGEX)
                && emailField.getText().matches(EMAIL_REGEX)
                && dobField.getText().matches(DOB_REGEX)
                && zipField.getText().matches(ZIP_REGEX);
    }

    /**
     * Opens a simple success page after the user clicks Add.
     * This simulates navigating to a new UI screen.
     */
    private void openSuccessPage() {

        // New layout for success screen
        VBox successLayout = new VBox(20);
        successLayout.setAlignment(Pos.CENTER);

        Label msg = new Label("Registration Successful!");
        msg.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Button to return to the form
        Button back = new Button("Back");
        back.setOnAction(e -> stage.setScene(new Scene(getView(), 450, 450)));

        successLayout.getChildren().addAll(msg, back);

        // Replace the scene with the success screen
        stage.setScene(new Scene(successLayout, 450, 300));
    }
}
