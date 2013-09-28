package coachj;

import coachj.enums.SeasonStatus;
import coachj.lists.IdDescriptionListItem;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import coachj.utils.SettingsUtils;
import coachj.builders.AutomaticEntitiesGenerator;
import coachj.dao.DatabaseDirectConnection;
import coachj.structures.CoachTransactionRecord;
import coachj.structures.GeneralManagerTransactionRecord;
import coachj.utils.CoachUtils;
import coachj.utils.CountingUtils;
import coachj.utils.FranchiseUtils;
import coachj.utils.GeneralManagerUtils;
import coachj.utils.ListUtils;
import coachj.utils.SceneUtils;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Locale;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;

/**
 * Controller for the offseason scene
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 07/07/2013
 */
public class OffSeasonController implements Initializable {

    /**
     * FXML components
     */
    @FXML
    private AnchorPane mainContent; /* main content holder */

    @FXML
    private Label availableCountriesCountLabel;
    @FXML
    private Label availableCitiesCountLabel;
    @FXML
    private Label availableFranchisesCountLabel;
    @FXML
    private Label availableFirstNamesCountLabel;
    @FXML
    private Label availableLastNamesCountLabel;
    @FXML
    private Label availableGeneralManagersCountLabel;
    @FXML
    private Label availableCoachesCountLabel;
    @FXML
    private Label availablePlayersCountLabel;
    @FXML
    private Label availableDrafteesCountLabel;
    @FXML
    private Label availableArenasCountLabel;
    @FXML
    private Label availableRefereesCountLabel;
    @FXML
    private Button proceedButton;
    @FXML
    private ImageView availableCountriesCountImageView;
    @FXML
    private ImageView availableCitiesCountImageView;
    @FXML
    private ImageView availableFranchisesCountImageView;
    @FXML
    private ImageView availableFirstNamesCountImageView;
    @FXML
    private ImageView availableLastNamesCountImageView;
    @FXML
    private ImageView availableGeneralManagersCountImageView;
    @FXML
    private ImageView availableCoachesCountImageView;
    @FXML
    private ImageView availablePlayersCountImageView;
    @FXML
    private ImageView availableDrafteesCountImageView;
    @FXML
    private ImageView availableArenasCountImageView;
    @FXML
    private ImageView availableRefereesCountImageView;
    @FXML
    private Label entityGenerationLabel;
    @FXML
    private ProgressIndicator entityGenerationProgressIndicator;
    @FXML
    private ProgressIndicator franchisesHiringStaffProgressIndicator;
    @FXML
    private Button generateCoachButton;
    @FXML
    private Button generateRefereeButton;
    @FXML
    private Button generateGeneralManagerButton;
    @FXML
    private Button generatePlayerButton;
    @FXML
    private ComboBox franchiseComboBox;
    @FXML
    private Label financialAssetsLabel;
    @FXML
    private Label payrollLabel;
    @FXML
    private Label rosterPlayersCountLabel;
    @FXML
    private Label activePlayersCountLabel;
    @FXML
    private Button coachContractNegotationButton;
    @FXML
    private Button generalManagerContractNegotationButton;
    @FXML
    private ImageView franchiseHasCoachImageView;
    @FXML
    private ImageView franchiseHasGeneralManagerImageView;
    @FXML
    private Label additionalMessagesLabel;
    @FXML
    private Button fireCoachButton;
    @FXML
    private Button fireGeneralManagerButton;
    /**
     * Keeps a reference to the application's thread
     */
    private CoachJ application;
    /**
     * Database connection
     */
    private DatabaseDirectConnection connection;
    /**
     * Reference to resources file
     */
    private ResourceBundle resources;
    /**
     * task runner
     */
    Task task;
    /**
     * provides list for the franchise's combobox
     */
    private ObservableList<IdDescriptionListItem> franchisesObservableList;
    /**
     * Images to be loaded into the UI
     */
    final private Image okImage = new Image(getClass().getResourceAsStream("img/ok16.png"));
    final private Image errorImage = new Image(getClass().getResourceAsStream("img/error16.png"));
    /**
     * Store franchise's id
     */
    private int franchiseId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Creating and opening database connection
         */
        connection = new DatabaseDirectConnection();
        connection.open();

        /**
         * Creates a reference to the resources file and calls the method that
         * retrieves off-season stats
         */
        this.resources = rb;
        this.updateOffSeasonStatus();

        /**
         * Adding a listener to the franchises comboBox
         */
        franchisesObservableList.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                updateFranchiseInfo();
            }
        });
    }

    /**
     * Creates a reference to the application's thread
     *
     * @param application Reference to the CoachJ thread
     */
    public void setApp(CoachJ application) {
        this.application = application;
    }

    /**
     * Updates off-season stats that are necessary to be fulfilled in order to
     * advance to preseason
     */
    private void updateOffSeasonStatus() {

        short userFranchise = Short.parseShort(SettingsUtils.getSetting("userFranchise", "0"));
        boolean offStatusRequirements = true; // control variable       

        /**
         * Retrieving geographical data
         */
        int requiredCountriesCount = 1;
        int requiredCitiesCount = 1;
        long availableCountriesCount = CountingUtils.createdCountriesCount(connection);
        long availableCitiesCount = CountingUtils.createdCitiesCount(connection);

        /**
         * Testing whether the number of required countries and cities has been
         * created
         */
        if (availableCountriesCount >= requiredCountriesCount) {
            availableCountriesCountImageView.setImage(okImage);
        } else {
            availableCountriesCountImageView.setImage(errorImage);
            offStatusRequirements = false;
        }

        if (availableCitiesCount >= requiredCitiesCount) {
            availableCitiesCountImageView.setImage(okImage);
        } else {
            availableCitiesCountImageView.setImage(errorImage);
            offStatusRequirements = false;
        }

        /**
         * Retrieving data about franchises
         */
        int requiredFranchisesCount = Integer.parseInt(SettingsUtils
                .getSetting("requiredFranchises", "32"));
        long availableFranchisesCount = CountingUtils.createdFranchisesCount(connection);

        /**
         * Testing whether the number of required franchises has been created
         */
        if (availableFranchisesCount >= requiredFranchisesCount) {
            availableFranchisesCountImageView.setImage(okImage);
        } else {
            availableFranchisesCountImageView.setImage(errorImage);
            offStatusRequirements = false;
        }

        /**
         * Retrieving data about players
         */
        int minimumPlayersPerTeam = Integer.parseInt(SettingsUtils
                .getSetting("minimumPlayersPerTeam", "15"));
        int draftRounds = Integer.parseInt(SettingsUtils
                .getSetting("draftRounds", "2"));
        int requiredDrafteesCount = requiredFranchisesCount * draftRounds;
        int requiredPlayersCount = requiredFranchisesCount * minimumPlayersPerTeam * 2;
        long availablePlayersCount = CountingUtils.availablePlayersCount(connection);
        long availableDrafteesCount = CountingUtils.drafteesCount(connection);

        /**
         * Testing whether the number of required players has been created
         */
        if (availablePlayersCount >= requiredPlayersCount) {
            availablePlayersCountImageView.setImage(okImage);
        } else {
            availablePlayersCountImageView.setImage(errorImage);
            offStatusRequirements = false;
        }

        if (availableDrafteesCount >= requiredDrafteesCount) {
            availableDrafteesCountImageView.setImage(okImage);
        } else {
            availableDrafteesCountImageView.setImage(errorImage);
            offStatusRequirements = false;
        }

        /**
         * Retrieving naming data
         */
        long requiredFirstNamesCount = requiredPlayersCount;
        long requiredLastNamesCount = requiredPlayersCount;
        long createdFirstNamesCount = CountingUtils.createdFirstNamesCount(connection);
        long createdLastNamesCount = CountingUtils.createdLastNamesCount(connection);

        /**
         * Testing whether the number of required names has been created
         */
        if (createdFirstNamesCount >= requiredFirstNamesCount) {
            availableFirstNamesCountImageView.setImage(okImage);
        } else {
            availableFirstNamesCountImageView.setImage(errorImage);
            offStatusRequirements = false;
        }

        if (createdLastNamesCount >= requiredLastNamesCount) {
            availableLastNamesCountImageView.setImage(okImage);
        } else {
            availableLastNamesCountImageView.setImage(errorImage);
            offStatusRequirements = false;
        }

        /**
         * Retrieving data about general managers
         */
        int requiredGeneralManagersCount = requiredFranchisesCount;
        long availableGeneralManagersCount = CountingUtils
                .availableGeneralManagersCount(connection);

        /**
         * Testing whether the number of required general managers has been
         * created
         */
        if (availableGeneralManagersCount >= requiredGeneralManagersCount) {
            availableGeneralManagersCountImageView.setImage(okImage);
        } else {
            availableGeneralManagersCountImageView.setImage(errorImage);
            offStatusRequirements = false;
        }

        /**
         * Retrieving data about head coaches
         */
        int requiredCoachesCount = requiredFranchisesCount;
        long availableCoachesCount = CountingUtils
                .availableCoachesCount(connection);

        /**
         * Testing whether the number of required coaches has been created
         */
        if (availableCoachesCount >= requiredCoachesCount) {
            availableCoachesCountImageView.setImage(okImage);
        } else {
            availableCoachesCountImageView.setImage(errorImage);
            offStatusRequirements = false;
        }

        /**
         * Retrieving data about arenas
         */
        int requiredArenasCount = requiredFranchisesCount;
        long availableArenasCount = CountingUtils.createdArenasCount(connection);

        /**
         * Testing whether the number of required arenas has been created
         */
        if (availableArenasCount >= requiredArenasCount) {
            availableArenasCountImageView.setImage(okImage);
        } else {
            availableArenasCountImageView.setImage(errorImage);
            offStatusRequirements = false;
        }

        /**
         * Retrieving data about referees
         */
        int requiredRefereesCount = requiredFranchisesCount * 3;
        long availableRefereesCount = CountingUtils.availableRefereesCount(connection);

        /**
         * Testing whether the number of required arenas has been created
         */
        if (availableRefereesCount >= requiredRefereesCount) {
            availableRefereesCountImageView.setImage(okImage);
        } else {
            availableRefereesCountImageView.setImage(errorImage);
            offStatusRequirements = false;
        }

        /**
         * Displaying data on appropiate UI controls
         */
        // geographical data
        availableCountriesCountLabel.setText(String.valueOf(availableCountriesCount) + "/"
                + String.valueOf(requiredCountriesCount));
        availableCitiesCountLabel.setText(String.valueOf(availableCitiesCount) + "/"
                + String.valueOf(requiredCitiesCount));

        // franchises data
        availableFranchisesCountLabel.setText(availableFranchisesCount + "/" + requiredFranchisesCount);

        // naming data
        availableFirstNamesCountLabel.setText(String.valueOf(createdFirstNamesCount) + "/"
                + String.valueOf(requiredFirstNamesCount));
        availableLastNamesCountLabel.setText(String.valueOf(createdLastNamesCount) + "/"
                + String.valueOf(requiredLastNamesCount));

        // general managers data
        availableGeneralManagersCountLabel.setText(String.valueOf(availableGeneralManagersCount) + "/"
                + requiredGeneralManagersCount);

        // coaches data        
        availableCoachesCountLabel.setText(availableCoachesCount + "/"
                + requiredCoachesCount);

        // players data
        availablePlayersCountLabel.setText(String.valueOf(availablePlayersCount)
                + "/" + requiredPlayersCount);
        availableDrafteesCountLabel.setText(String.valueOf(availableDrafteesCount)
                + "/" + requiredDrafteesCount);

        // arenas data
        availableArenasCountLabel.setText(availableArenasCount + "/"
                + requiredArenasCount);

        // referees data
        availableRefereesCountLabel.setText(availableRefereesCount + "/"
                + requiredRefereesCount);

        // setting pre season button 
        proceedButton.setDisable(!offStatusRequirements);

        // adding existing franchises to the list
        franchisesObservableList = ListUtils.fillIdDescriptionListFromSQL("SELECT f.id, CONCAT(c.name, "
                + "CONCAT(' ', f.team)) AS description "
                + "FROM franchise f "
                + "INNER JOIN city c ON f.city = c.id "
                + "ORDER BY c.name", connection);
        franchiseComboBox.setItems(franchisesObservableList);

        /**
         * Selecting user's franchise in the combobox
         */
        int franchiseIndex = ListUtils.selectComboBoxItem(franchisesObservableList, userFranchise);
        System.out.println("Franchise index: " + franchiseIndex); // delete
        franchiseComboBox.getSelectionModel().select(franchiseIndex);
        updateFranchiseInfo();

    }

    /**
     * Invokes that method that creates a task that generates new coaches
     */
    @FXML
    private void generateCoaches() {
        invokeEntityGenerationTask("coach");
    }

    /**
     * Invokes that method that creates a task that generates new referees
     */
    @FXML
    private void generateReferees() {
        invokeEntityGenerationTask("referee");
    }

    /**
     * Invokes that method that creates a task that generates new general
     * managers
     */
    @FXML
    private void generateGeneralManagers() {
        invokeEntityGenerationTask("generalManager");
    }

    /**
     * Invokes that method that creates a task that generates new players
     */
    @FXML
    private void generatePlayers() {
        invokeEntityGenerationTask("player");
    }

    /**
     * Sets the visibility for the label and the progress indicator that monitor
     * the progress of the generation tasks
     *
     * @param state State for the visibility property
     */
    private void setEntityGenerationControls(boolean state) {
        entityGenerationLabel.setVisible(state);
        entityGenerationProgressIndicator.setVisible(state);
    }

    @FXML
    private void proceed() {
        /**
         * Checking whether the user picked a franchise or not
         */
        if (franchiseComboBox.getSelectionModel().getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null,
                    resources.getString("ch_deve_escolher_franquia"), resources.getString("ch_atencao"),
                    JOptionPane.WARNING_MESSAGE);
            franchiseComboBox.requestFocus();
            return;
        }

        /**
         * Asking for confirmation, then registering the user's franchise and
         * invoking the task that hires staff for the other franchises
         */
        if (JOptionPane.showConfirmDialog(null,
                resources.getString("ch_prosseguir_draft"),
                resources.getString("ch_confirmar"), JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == 0) {
            IdDescriptionListItem selectedFranchise = (IdDescriptionListItem) franchiseComboBox.getSelectionModel().getSelectedItem();
            SettingsUtils.setSetting("userFranchise",
                    String.valueOf(selectedFranchise.getId()));
            invokeHiringStaffTask();
        }
    }

    /**
     * Sets the season status to DRAFT and loads the draft scene
     */
    private void gotoDraft() {
        SettingsUtils.setSetting("seasonStatus",
                String.valueOf(SeasonStatus.DRAFT.getStatus()));
        SceneUtils.loadScene(this.application, DraftController.class.getClass(),
                "Draft.fxml");

        /**
         * Closing connection
         */
        connection.close();
    }

    /**
     * Invokes a task the creates new entities in the database
     *
     * @param entityName Name of the table where the new records will be
     * inserted
     */
    private void invokeEntityGenerationTask(String entityName) {
        /**
         * Creates a new task, binds its progress to the progress indicator and
         * starts it
         */
        task = null;
        task = generateEntity(entityName);
        entityGenerationProgressIndicator.progressProperty().unbind();
        entityGenerationProgressIndicator.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    private void invokeHiringStaffTask() {
        /**
         * Creates a new task, binds its progress to the progress indicator and
         * starts it
         */
        task = null;
        task = hireStaff();
        franchisesHiringStaffProgressIndicator.progressProperty().unbind();
        franchisesHiringStaffProgressIndicator.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    /**
     * Asks for confirmation before closing the application
     */
    @FXML
    private void exit() {
        SceneUtils.exit(resources.getString("ch_deseja_sair"),
                resources.getString("ch_sair"));
    }

    /**
     * Creates a task objects that creates new entities
     *
     * @param entityName Name of the entity to be created
     * @return
     */
    private Task<Integer> generateEntity(final String entityName) {

        setEntityGenerationControls(true);
        mainContent.setCursor(Cursor.WAIT);
        
        /**
         * Creating task and iterating through the loop that creates new
         * entities
         */
        Task<Integer> entityGenerationTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                int iterations;
                int entitiesToCreate = 200;
                /**
                 * Database connection
                 */
                DatabaseDirectConnection taskConnection = new DatabaseDirectConnection();
                taskConnection.open();                

                for (iterations = 0; iterations < entitiesToCreate; iterations++) {

                    /**
                     * Identifying what kind entity to create
                     */
                    if (entityName.equalsIgnoreCase("coach")) {
                        AutomaticEntitiesGenerator.generateCoaches(1, taskConnection);
                    } else if (entityName.equalsIgnoreCase("player")) {
                        AutomaticEntitiesGenerator.generatePlayers(1, taskConnection);
                    } else if (entityName.equalsIgnoreCase("generalManager")) {
                        AutomaticEntitiesGenerator.generateGeneralManagers(1,
                                taskConnection);
                    } else if (entityName.equalsIgnoreCase("referee")) {
                        AutomaticEntitiesGenerator.generateReferees(1,
                                taskConnection);
                    }

                    /**
                     * Updating task's progress, which is bound to the progress
                     * indicator
                     */
                    updateProgress(iterations, entitiesToCreate);

                }
                taskConnection.close();
                return iterations;
            }

            /**
             * Method to be executed when the task completes successfully
             */
            @Override
            protected void succeeded() {
                setEntityGenerationControls(false);
                updateOffSeasonStatus();
                System.out.println("Completed!");
                mainContent.setCursor(Cursor.DEFAULT);
            }

            @Override
            protected void cancelled() {
                setEntityGenerationControls(false);
                cancelled();
                System.out.println("Cancelled!");
                mainContent.setCursor(Cursor.DEFAULT);
            }

            @Override
            protected void failed() {
                setEntityGenerationControls(false);
                failed();
                System.out.println("Failed!");
                mainContent.setCursor(Cursor.DEFAULT);
            }
        };

        return entityGenerationTask;
    }

    /**
     * Updates information about the selected franchise
     */
    @FXML
    private void updateFranchiseInfo() {
        /**
         * Updating whenever a franchise is selected from the combobox
         */
        if (franchiseComboBox.getSelectionModel().getSelectedIndex() != -1) {
            /**
             * Variables to store locale settings, the selected franchise and
             * the information to be displayed
             */
            Locale local = new Locale(SettingsUtils.getSetting("language", "pt"));
            IdDescriptionListItem selectedFranchise = (IdDescriptionListItem) franchiseComboBox
                    .getSelectionModel().getSelectedItem();
            this.franchiseId = selectedFranchise.getId();
            String financialAssets = FranchiseUtils.getFieldValueAsString(franchiseId,
                    "assets", connection);
            int payroll = FranchiseUtils.getPayroll(franchiseId, connection);
            short activePlayersCount = FranchiseUtils.getActivePlayers(franchiseId,
                    connection);
            short rosterPlayersCount = FranchiseUtils.getSignedPlayers(franchiseId,
                    connection);

            /**
             * updating controls
             */
            financialAssetsLabel.setText(DecimalFormat.getCurrencyInstance(local)
                    .format(Integer.parseInt(financialAssets)));
            payrollLabel.setText(DecimalFormat.getCurrencyInstance(local)
                    .format(payroll));
            rosterPlayersCountLabel.setText(String.valueOf(rosterPlayersCount));
            activePlayersCountLabel.setText(String.valueOf(activePlayersCount));

            /*
             * checking whether the franchise has coach and general manager
             */
            boolean franchiseHasCoach = FranchiseUtils.franchiseHasCoach(franchiseId,
                    connection);
            boolean franchiseHasGeneralManager = FranchiseUtils
                    .franchiseHasGeneralManager(franchiseId, connection);
            System.out.println("franchise id: " + franchiseId); // delete
            System.out.println("Has coach: " + franchiseHasCoach); // delete

            /**
             * Updating image views
             */
            if (franchiseHasCoach) {
                franchiseHasCoachImageView.setImage(okImage);
            } else {
                franchiseHasCoachImageView.setImage(errorImage);
            }

            if (franchiseHasGeneralManager) {
                franchiseHasGeneralManagerImageView.setImage(okImage);
            } else {
                franchiseHasGeneralManagerImageView.setImage(errorImage);
            }

            /**
             * Setting up buttons
             */
            coachContractNegotationButton.setDisable(franchiseHasCoach);
            fireCoachButton.setDisable(!franchiseHasCoach);
            generalManagerContractNegotationButton.setDisable(franchiseHasGeneralManager);
            fireGeneralManagerButton.setDisable(!franchiseHasGeneralManager);
            proceedButton.setDisable(!(franchiseHasCoach && franchiseHasGeneralManager));

        }
    }

    /**
     * Loads the scene for negotiating coach's contracts
     */
    @FXML
    private void gotoCoachNegotiationContract() {
        /**
         * Checking whether the user selected a franchise
         */
        if (franchiseComboBox.getSelectionModel().getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null,
                    resources.getString("ch_deve_escolher_franquia"), resources.getString("ch_atencao"),
                    JOptionPane.WARNING_MESSAGE);
            franchiseComboBox.requestFocus();
            return;
        }

        IdDescriptionListItem selectedFranchise = (IdDescriptionListItem) franchiseComboBox.getSelectionModel().getSelectedItem();
        SettingsUtils.setSetting("userFranchise",
                String.valueOf(selectedFranchise.getId()));
        SceneUtils.loadScene(this.application, CoachContractNegotiationController.class.getClass(),
                "CoachContractNegotiation.fxml");

        /**
         * Closing connection
         */
        connection.close();
    }

    /**
     * Loads the scene for negotiating general manager's contracts
     */
    @FXML
    private void gotoGeneralManagerNegotiationContract() {
        /**
         * Checking whether the user selected a franchise
         */
        if (franchiseComboBox.getSelectionModel().getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null,
                    resources.getString("ch_deve_escolher_franquia"), resources.getString("ch_atencao"),
                    JOptionPane.WARNING_MESSAGE);
            franchiseComboBox.requestFocus();
            return;
        }

        IdDescriptionListItem selectedFranchise = (IdDescriptionListItem) franchiseComboBox.getSelectionModel().getSelectedItem();
        SettingsUtils.setSetting("userFranchise",
                String.valueOf(selectedFranchise.getId()));
        SceneUtils.loadScene(this.application, GeneralManagerContractNegotiationController.class.getClass(),
                "GeneralManagerContractNegotiation.fxml");
    }

    /**
     * Fires the coach, asking confirmation before
     */
    @FXML
    private void confirmFireCoach() {
        if (SceneUtils.confirm(resources.getString("ch_demitir_tecnico"),
                resources.getString("ch_confirmar")) == 0) {

            /**
             * Creating contract object, firing coach and updating controls
             */
            short coachId = FranchiseUtils.getFranchiseCoachId(this.franchiseId,
                    connection);
            CoachTransactionRecord currentContract = new CoachTransactionRecord();
            currentContract.setCoach(coachId);
            currentContract.setDate(SettingsUtils.getSetting("currentDate",
                    Calendar.getInstance().get(Calendar.YEAR) + "-01-01"));
            currentContract.setFranchise((short) this.franchiseId);
            currentContract.setLength((short) 0);
            currentContract.setSalary(CoachUtils.getCoachSalary(coachId,
                    connection));
            currentContract.setType("W");
            currentContract.setSeason(Short.parseShort(SettingsUtils.getSetting("currentSeason",
                    String.valueOf(Calendar.getInstance().get(Calendar.YEAR)))));

            CoachUtils.fireCoach(currentContract, connection);
            coachContractNegotationButton.setDisable(false);

            /* updating negotiation panel */
            updateFranchiseInfo();
        }
    }

    /**
     * Fires the general, asking confirmation before
     */
    @FXML
    private void confirmFireGeneralManager() {
        if (SceneUtils.confirm(resources.getString("ch_demitir_gerente"),
                resources.getString("ch_confirmar")) == 0) {

            /**
             * Creating contract object, firing coach and updating controls
             */
            short generalManagerId = FranchiseUtils
                    .getFranchiseGeneralManagerId(this.franchiseId, connection);
            GeneralManagerTransactionRecord currentContract = new GeneralManagerTransactionRecord();
            currentContract.setGeneralManager(generalManagerId);
            currentContract.setDate(SettingsUtils.getSetting("currentDate",
                    Calendar.getInstance().get(Calendar.YEAR) + "-01-01"));
            currentContract.setFranchise((short) this.franchiseId);
            currentContract.setLength((short) 0);
            currentContract.setSalary(CoachUtils.getCoachSalary(generalManagerId,
                    connection));
            currentContract.setType("W");
            currentContract.setSeason(Short.parseShort(SettingsUtils.getSetting("currentSeason",
                    String.valueOf(Calendar.getInstance().get(Calendar.YEAR)))));

            GeneralManagerUtils.fireGeneralManager(currentContract, connection);
            generalManagerContractNegotationButton.setDisable(false);

            /* updating negotiation panel */
            updateFranchiseInfo();
        }
    }

    private Task<Integer> hireStaff() {
        /**
         * Displaying controls that show the progress of the task
         */
        additionalMessagesLabel.setVisible(true);
        additionalMessagesLabel.setText(resources.getString("ch_franquias_contratando_staff"));
        franchisesHiringStaffProgressIndicator.setVisible(true);
        mainContent.setCursor(Cursor.WAIT);

        /**
         * Creating task and iterating through the loop that hires general
         * managers and coaches for the other registered franchises
         */
        Task<Integer> hiringStaffTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                /**
                 * Database connection
                 */
                DatabaseDirectConnection taskConnection = new DatabaseDirectConnection();
                taskConnection.open(); 
                
                short userFranchiseId = Short.parseShort(SettingsUtils
                        .getSetting("userFranchise", "0"));
                
                String sqlStatement = "SELECT id, coach, generalManager, record FROM franchise "
                        + "WHERE registered = 1 AND id != " + userFranchiseId
                        + " ORDER BY record, RAND()";
                ResultSet resultSet;
                int iterations = 0;
                long computerRegisteredFranchisesCount = CountingUtils
                        .computerControlledFranchisesCount(taskConnection);
                short currentFranchiseId = 0;

                /**
                 * Opening connection, populating resultset and positioning
                 * before its beginning
                 */
                resultSet = taskConnection.getResultSet(sqlStatement);
                resultSet.beforeFirst();

                /**
                 * Loop to hire staff
                 */
                while (resultSet.next()) {

                    /**
                     * Calling method that analyses general manager's and
                     * coach's performance to decide what to do
                     */
                    currentFranchiseId = resultSet.getShort("id");
                    FranchiseUtils.analyseGeneralManagerPerformance(currentFranchiseId,
                            taskConnection);
                    FranchiseUtils.analyseCoachPerformance(currentFranchiseId,
                            taskConnection);

                    /**
                     * Updating task's progress, which is bound to the progress
                     * indicator
                     */
                    iterations++;
                    updateProgress(iterations, computerRegisteredFranchisesCount);

                }
                taskConnection.close();
                return iterations;
            }

            /**
             * Method to be executed when the task completes successfully
             */
            @Override
            protected void succeeded() {
                additionalMessagesLabel.setVisible(false);
                franchisesHiringStaffProgressIndicator.setVisible(false);
                gotoDraft();
                System.out.println("Completed!");
                mainContent.setCursor(Cursor.DEFAULT);
            }

            @Override
            protected void cancelled() {
                additionalMessagesLabel.setVisible(false);
                franchisesHiringStaffProgressIndicator.setVisible(false);
                cancelled();
                System.out.println("Cancelled!");
                mainContent.setCursor(Cursor.DEFAULT);
            }

            @Override
            protected void failed() {
                additionalMessagesLabel.setVisible(false);
                franchisesHiringStaffProgressIndicator.setVisible(false);
                failed();
                System.out.println("Failed!");
                mainContent.setCursor(Cursor.DEFAULT);
            }
        };

        return hiringStaffTask;
    }
}
