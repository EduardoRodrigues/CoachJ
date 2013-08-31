package coachj;

import coachj.builders.GeneralManagerBuilder;
import coachj.dao.DatabaseDirectConnection;
import coachj.lists.IdDescriptionListItem;
import coachj.models.GeneralManager;
import coachj.structures.GeneralManagerTransactionRecord;
import coachj.utils.FranchiseUtils;
import coachj.utils.GeneralManagerUtils;
import coachj.utils.ListUtils;
import coachj.utils.SceneUtils;
import coachj.utils.SettingsUtils;
import coachj.utils.ValidationUtils;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

/**
 * Controller for the generalManager contract negotiation scene
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 06/08/2013
 */
public class GeneralManagerContractNegotiationController implements Initializable {

    /**
     * FXML components
     */
    @FXML
    private ComboBox generalManagersComboBox;
    @FXML
    private Label franchiseNameLabel;
    @FXML
    private Label generalManagerAgeLabel;
    @FXML
    private Label generalManagerSeasonsLabel;
    @FXML
    private Label generalManagerTitlesLabel;
    @FXML
    private Label generalManagerMarketValueLabel;
    @FXML
    private Label generalManagerSalaryLabel;
    @FXML
    private Label generalManagerTotalEarningsLabel;
    @FXML
    private Button releaseGeneralManagerButton;
    @FXML
    private Button makeOfferButton;
    @FXML
    private Label generalManagerRemainingYearsLabel;
    @FXML
    private ProgressBar failedContractAttemptsProgressBar;
    @FXML
    private Label generalManagerProposalLabel;
    @FXML
    private TextField franchiseProposalTextField;
    @FXML
    private Label franchiseAssetsLabel;
    @FXML
    private ComboBox contractLengthComboBox;
    
    /**
     * provides list for the generalManagers combobox
     */
    private ObservableList<IdDescriptionListItem> generalManagersObservableList;
    
    /**
     * Keeps a reference to the application's thread
     */
    private CoachJ application;
    
    /**
     * Database connection
     */
    private DatabaseDirectConnection connection;
    
    /**
     * Stores user franchise's id
     */
    private short userFranchiseId;
   
    /**
     * Stores franchise's generalManager's id
     */
    private short userFranchiseGeneralManagerId;
    
    /**
     * Stores franchise's financial assets
     */
    private long franchiseAssets;
    
    /**
     * Store franchise's proposal
     */
    private int franchiseOffer;
    
    /**
     * Stores generalManager's proposal
     */
    private int generalManagerProposal;
   
    /**
     * Stores generalManager's reference
     */
    GeneralManager generalManager;
    
    /**
     * Reference to resources file
     */
    private ResourceBundle resources;

    /**
     * Creates a reference to the application's thread
     *
     * @param application Reference to the CoachJ thread
     */
    public void setApp(CoachJ application) {
        this.application = application;
    }

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
         * Creates a reference to the resources file
         */
        this.resources = rb;
        
        /**
         * Variables that store franchise's general manager's id, franchise's
         * complete name and franchise's assets
         */
        userFranchiseId = Short.parseShort(SettingsUtils.getSetting("userFranchise", "0"));
        String franchiseCompleteName = FranchiseUtils.getFranchiseCompleteName(userFranchiseId,
                connection);
        franchiseAssets = Long.parseLong(FranchiseUtils.getFieldValueAsString(userFranchiseId,
                "assets", connection));

        /**
         * In case the franchise doesn't have a general manager and the value
         * returned from the database is null, sets the general manager id to 0,
         * it means none
         */
        try {
            userFranchiseGeneralManagerId = Short.parseShort(FranchiseUtils
                    .getFieldValueAsString(userFranchiseId, "generalManager", connection));
        } catch (NumberFormatException ex) {
            userFranchiseGeneralManagerId = 0;
        }

        int franchiseGeneralManagerIndex; // auxiliary variable

        /**
         * Displaying franchise name
         */
        franchiseNameLabel.setText(franchiseCompleteName);

        /**
         * Filling contract lenght combo
         */
        contractLengthComboBox.getItems().add("1");
        contractLengthComboBox.getItems().add("2");
        contractLengthComboBox.getItems().add("3");
        contractLengthComboBox.getItems().add("4");
        contractLengthComboBox.getItems().add("5");

        /**
         * Filling combobox with general managers data that currently do not
         * have a team, enabling/disabling it according to its general manager
         * status and, if it does have a general manager, selecting the general
         * manager
         */
        generalManagersObservableList = ListUtils.fillIdDescriptionListFromSQL("SELECT id, CONCAT(firstName, "
                + "CONCAT(' ', lastName)) AS description "
                + "FROM general_manager "
                + "WHERE retired = false AND remainingYears = 0 "
                + "ORDER BY firstName", connection);
        generalManagersComboBox.setItems(generalManagersObservableList);
        generalManagersComboBox.setDisable(FranchiseUtils.franchiseHasGeneralManager(userFranchiseId,
                connection));
        franchiseGeneralManagerIndex = ListUtils.selectComboBoxItem(generalManagersObservableList,
                userFranchiseGeneralManagerId);
        generalManagersComboBox.getSelectionModel().select(franchiseGeneralManagerIndex);
        updateNegotiationPanel();        
    }

    /**
     * Loads the offseason scene
     */
    @FXML
    private void gotoOffSeason() {
        SceneUtils.loadScene(this.application, OffSeasonController.class.getClass(),
                "OffSeason.fxml");
        
        /**
         * Closing connection
         */
        connection.close();
    }

    /**
     * Updates information for the negotiation panel
     */
    @FXML
    private void updateNegotiationPanel() {        

        /**
         * Updating whenever a general manager is selected from the combobox
         */
        if (generalManagersComboBox.getSelectionModel().getSelectedIndex() != -1) {
            IdDescriptionListItem selectedGeneralManager = (IdDescriptionListItem) 
                    generalManagersComboBox.getSelectionModel().getSelectedItem();
            short generalManagerId = (short) selectedGeneralManager.getId();
            GeneralManagerBuilder generalManagerBuilder = new GeneralManagerBuilder();
            generalManagerBuilder.fillAttributesFromDatabase(generalManagerId,
                    connection);
            generalManager = generalManagerBuilder.generateGeneralManagerEntity();

            /**
             * Displaying general manager's data in controls
             */
            generalManagerAgeLabel.setText(String.valueOf(generalManager.getAge()));
            generalManagerSeasonsLabel.setText(String.valueOf(generalManager.getSeasons()));
            generalManagerTitlesLabel.setText(String.valueOf(generalManager.getTitles()));
            generalManagerMarketValueLabel.setText(String.valueOf(generalManager.getMarketValue()));
            generalManagerSalaryLabel.setText(DecimalFormat.getCurrencyInstance()
                    .format(generalManager.getSalary()));
            generalManagerTotalEarningsLabel.setText(DecimalFormat.getCurrencyInstance()
                    .format(generalManager.getTotalEarnings()));

            /**
             * Calculating general manager's proposal and updating contract
             * terms
             */
            generalManagerProposal = (int) (generalManager.getSalary() * (1 + (double) generalManager.getGreed() / 100));
            generalManagerRemainingYearsLabel.setText(String.valueOf(generalManager.getRemainingYears()));
            failedContractAttemptsProgressBar.setProgress((double) generalManager.getFailedContractAttempts() / 3);
            System.out.println("failedContract Attempts: " + generalManager.getFailedContractAttempts()); // delete
            System.out.println("failedContract Attempts Progress: " + failedContractAttemptsProgressBar.getProgress()); // delete
            generalManagerProposalLabel.setText(DecimalFormat.getCurrencyInstance()
                    .format(generalManagerProposal));
            franchiseAssetsLabel.setText(DecimalFormat.getCurrencyInstance()
                    .format(franchiseAssets));

            /**
             * Enabling/disabling buttons and combobox
             */
            generalManagersComboBox.setDisable((generalManager.getRemainingYears() == 0
                    && userFranchiseGeneralManagerId == generalManager.getId()));
            releaseGeneralManagerButton.setDisable(!(generalManager.getRemainingYears() == 0
                    && userFranchiseGeneralManagerId == generalManager.getId()));
            makeOfferButton.setDisable(FranchiseUtils.franchiseHasGeneralManager(userFranchiseId,
                    connection));
        }
    }

    /**
     * Submits a contract offer to a general manager
     */
    @FXML
    private void makeOffer() {
        /**
         * Checking whether the franchise had already made the maximum of three
         * offer to the general manager
         */
        if (failedContractAttemptsProgressBar.getProgress() == 1) {
            SceneUtils.warning(resources.getString("ch_maximo_ofertas_gerente"),
                    resources.getString("ch_atencao"));
            return;
        }

        /**
         * Checking whether the user had selected the length of the contract
         */
        if (contractLengthComboBox.getSelectionModel().getSelectedIndex() == -1) {
            SceneUtils.warning(resources.getString("ch_tempo_contrato"),
                    resources.getString("ch_atencao"));
            contractLengthComboBox.requestFocus();
            return;
        }

        /**
         * Validating offer value
         */
        if (generalManagersComboBox.getSelectionModel().getSelectedIndex() == -1) {
            SceneUtils.warning(resources.getString("ch_selecione_gerente"),
                    resources.getString("ch_atencao"));
            generalManagersComboBox.requestFocus();
            return;
        }

        if (!ValidationUtils.isLong(franchiseProposalTextField.getText())) {
            SceneUtils.warning(resources.getString("ch_valor_proposta_invalido"),
                    resources.getString("ch_atencao"));
            franchiseProposalTextField.requestFocus();
            return;
        }

        franchiseOffer = Integer.parseInt(franchiseProposalTextField.getText());
        int minimumSalary = Integer.parseInt(SettingsUtils.getSetting("minimumSalary", "1500000"));
        short contractLength = Short.parseShort((String) contractLengthComboBox.getSelectionModel().getSelectedItem());

        if (franchiseOffer < minimumSalary) {
            SceneUtils.warning(resources.getString("ch_valor_proposta_inferior_minimo"),
                    resources.getString("ch_atencao"));
            franchiseProposalTextField.requestFocus();
            return;
        }

        if (franchiseOffer > franchiseAssets) {
            SceneUtils.warning(resources.getString("ch_proposta_maior_recursos"),
                    resources.getString("ch_atencao"));
            franchiseProposalTextField.requestFocus();
            return;
        }

        /**
         * Creating contract object and checking general manager's response to
         * offer
         */
        GeneralManagerTransactionRecord offer = new GeneralManagerTransactionRecord();
        offer.setGeneralManager(generalManager.getId());
        offer.setDate(SettingsUtils.getSetting("currentDate",
                Calendar.getInstance().get(Calendar.YEAR) + "-01-01"));
        offer.setFranchise(userFranchiseId);
        offer.setLength(contractLength);
        offer.setSalary(franchiseOffer);
        offer.setType("C");
        offer.setSeason(Short.parseShort(SettingsUtils.getSetting("currentSeason",
                String.valueOf(Calendar.getInstance().get(Calendar.YEAR)))));

        if (GeneralManagerUtils.agreeWithTerms(generalManager, generalManagerProposal, offer)) {
            GeneralManagerUtils.hireGeneralManager(offer, connection);
            SceneUtils.warning(resources.getString("ch_aceitou_oferta"),
                    resources.getString("ch_atencao"));
            gotoOffSeason();
        } else {
            GeneralManagerUtils.recordFailedContractAttempt(generalManager.getId(),
                    connection);
            SceneUtils.warning(resources.getString("ch_rejeitou_oferta"),
                    resources.getString("ch_atencao"));
            updateNegotiationPanel();
        }
    }

    /**
     * Releases the general manager, asking confirmation before
     */
    @FXML
    private void confirmReleaseGeneralManager() {
        if (SceneUtils.confirm(resources.getString("ch_liberar_gerente"),
                resources.getString("ch_confirmar")) == 0) {

            /**
             * Creating contract object, releasing general manager and updating
             * controls
             */
            GeneralManagerTransactionRecord currentContract = new GeneralManagerTransactionRecord();
            currentContract.setGeneralManager(generalManager.getId());
            currentContract.setDate(SettingsUtils.getSetting("currentDate",
                    Calendar.getInstance().get(Calendar.YEAR) + "-01-01"));
            currentContract.setFranchise(userFranchiseId);
            currentContract.setLength((short) 0);
            currentContract.setSalary(franchiseOffer);
            currentContract.setType("L");
            currentContract.setSeason(Short.parseShort(SettingsUtils.getSetting("currentSeason",
                    String.valueOf(Calendar.getInstance().get(Calendar.YEAR)))));

            GeneralManagerUtils.releaseGeneralManager(currentContract, connection);
            generalManagersComboBox.setDisable(false);

            /* updating negotiation panel */
            updateNegotiationPanel();
        }
    }
}
