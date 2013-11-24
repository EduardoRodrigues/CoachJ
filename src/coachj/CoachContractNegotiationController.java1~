package coachj;

import coachj.builders.CoachBuilder;
import coachj.dao.DatabaseDirectConnection;
import coachj.lists.IdDescriptionListItem;
import coachj.models.Coach;
import coachj.structures.CoachTransactionRecord;
import coachj.utils.CoachUtils;
import coachj.utils.FranchiseUtils;
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
 * Controller for the coach contract negotiation scene
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 06/08/2013
 */
public class CoachContractNegotiationController implements Initializable {

    /**
     * FXML components
     */
    @FXML
    private ComboBox coachesComboBox;
    @FXML
    private Label franchiseNameLabel;
    @FXML
    private Label coachAgeLabel;
    @FXML
    private Label coachSeasonsLabel;
    @FXML
    private Label coachTitlesLabel;
    @FXML
    private Label coachMarketValueLabel;
    @FXML
    private Label coachSalaryLabel;
    @FXML
    private Label coachTotalEarningsLabel;
    @FXML
    private Label coachRotationUseLabel;
    @FXML
    private Label coachPatienceLabel;
    @FXML
    private Label coachTechniqueLabel;
    @FXML
    private Label coachDisciplineLabel;
    @FXML
    private Label coachWorkEthicLabel;
    @FXML
    private Label coachShootLabel;
    @FXML
    private Label coachPassLabel;
    @FXML
    private Label coachReboundLabel;
    @FXML
    private Label coachDefenseLabel;
    @FXML
    private Label coachPhysicalityLabel;
    @FXML
    private Label coachTempoLabel;   
    @FXML
    private Button releaseCoachButton;
    @FXML
    private Button makeOfferButton;
    @FXML
    private Label coachRemainingYearsLabel;
    @FXML
    private ProgressBar failedContractAttemptsProgressBar;
    @FXML
    private Label coachProposalLabel;
    @FXML
    private TextField franchiseProposalTextField;
    @FXML
    private Label franchiseAssetsLabel;
    @FXML
    private ComboBox contractLengthComboBox;
    
    /**
     * provides list for the coaches combobox
     */
    private ObservableList<IdDescriptionListItem> coachesObservableList;
    
    /**
     * Keeps a reference to the application's thread
     */
    private CoachJ application;
    
    /**
     * Database connection
     */
    private DatabaseDirectConnection connection;
    
    /**
     * Stores franchise's id
     */
    private short userFranchiseId;
    
    /**
     * Stores franchise's coach's id
     */
    private short userFranchiseCoachId;
    
    /**
     * Stores franchise's financial assets
     */
    private long franchiseAssets;
    
    /**
     * Store franchise's proposal
     */
    private int franchiseOffer;
    
    /**
     * Stores coach's proposal
     */
    private int coachProposal;
    
    /**
     * Stores coach's reference
     */
    Coach coach;
    
    /**
     * Reference to resources file
     */
    private ResourceBundle resources;

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
         * Variables that store franchise's coach's id, franchise's complete
         * name and franchise's assets
         */
        userFranchiseId = Short.parseShort(SettingsUtils.getSetting("userFranchise", "0"));
        String franchiseCompleteName = FranchiseUtils.getFranchiseCompleteName(userFranchiseId,
                connection);
        franchiseAssets = Long.parseLong(FranchiseUtils.getFieldValueAsString(userFranchiseId, 
                "assets", connection));

        /**
         * In case the franchise doesn't have a coach and the value returned
         * from the database is null, sets the coach id to 0, it means none
         */
        try {
            userFranchiseCoachId = Short.parseShort(FranchiseUtils
                    .getFieldValueAsString(userFranchiseId, "coach", connection));
        } catch (NumberFormatException ex) {
            userFranchiseCoachId = 0;
        }

        int franchiseCoachIndex; // auxiliary variable

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
         * Filling combobox with coaches data that currently do not have a team,
         * enabling/disabling it according to its coach status and, if it does
         * have a coach, selecting the coach
         */
        coachesObservableList = ListUtils.fillIdDescriptionListFromSQL("SELECT id, CONCAT(firstName, "
                + "CONCAT(' ', lastName)) AS description "
                + "FROM coach "
                + "WHERE retired = false AND remainingYears = 0 "
                + "ORDER BY firstName", connection);
        coachesComboBox.setItems(coachesObservableList);
        coachesComboBox.setDisable(FranchiseUtils.franchiseHasCoach(userFranchiseId,
                connection));
        franchiseCoachIndex = ListUtils.selectComboBoxItem(coachesObservableList,
                userFranchiseCoachId);
        coachesComboBox.getSelectionModel().select(franchiseCoachIndex);
        updateNegotiationPanel();       
        
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
         * Updating whenever a coach is selected from the combobox
         */
        if (coachesComboBox.getSelectionModel().getSelectedIndex() != -1) {
            IdDescriptionListItem selectedCoach = (IdDescriptionListItem) coachesComboBox
                    .getSelectionModel().getSelectedItem();
            short coachId = (short) selectedCoach.getId();
            CoachBuilder coachBuilder = new CoachBuilder();
            coachBuilder.fillAttributesFromDatabase(coachId, connection);
            coach = coachBuilder.generateCoachEntity();

            /**
             * Displaying coach's data in controls
             */
            coachAgeLabel.setText(String.valueOf(coach.getAge()));
            coachSeasonsLabel.setText(String.valueOf(coach.getSeasons()));
            coachTitlesLabel.setText(String.valueOf(coach.getTitles()));
            coachMarketValueLabel.setText(String.valueOf(coach.getMarketValue()));
            coachSalaryLabel.setText(DecimalFormat.getCurrencyInstance()
                    .format(coach.getSalary()));
            coachTotalEarningsLabel.setText(DecimalFormat.getCurrencyInstance()
                    .format(coach.getTotalEarnings()));
            coachRotationUseLabel.setText(String.valueOf(coach.getRotationUse()));
            coachPatienceLabel.setText(String.valueOf(coach.getPatience()));
            coachTechniqueLabel.setText(String.valueOf(coach.getTechnique()));
            coachDisciplineLabel.setText(String.valueOf(coach.getDiscipline()));
            coachWorkEthicLabel.setText(String.valueOf(coach.getWorkEthic()));
            coachShootLabel.setText(String.valueOf(coach.getShoot()));
            coachPassLabel.setText(String.valueOf(coach.getPass()));
            coachReboundLabel.setText(String.valueOf(coach.getRebound()));
            coachDefenseLabel.setText(String.valueOf(coach.getDefense()));
            coachPhysicalityLabel.setText(String.valueOf(coach.getPhysicality()));
            coachTempoLabel.setText(String.valueOf(coach.getTempo()));

            /**
             * Calculating coach's proposal and updating contract terms
             */
            coachProposal = (int) (coach.getSalary() * (1 + (double) coach.getGreed() / 100));
            coachRemainingYearsLabel.setText(String.valueOf(coach.getRemainingYears()));
            failedContractAttemptsProgressBar.setProgress((double) coach.getFailedContractAttempts() / 3);
            System.out.println("failedContract Attempts: " + coach.getFailedContractAttempts()); // delete
            System.out.println("failedContract Attempts Progress: " + failedContractAttemptsProgressBar.getProgress()); // delete
            coachProposalLabel.setText(DecimalFormat.getCurrencyInstance()
                    .format(coachProposal));
            franchiseAssetsLabel.setText(DecimalFormat.getCurrencyInstance()
                    .format(franchiseAssets));

            /**
             * Enabling/disabling buttons and combobox
             */
            coachesComboBox.setDisable((coach.getRemainingYears() == 0
                    && userFranchiseCoachId == coach.getId()));           
            releaseCoachButton.setDisable(!(coach.getRemainingYears() == 0
                    && userFranchiseCoachId == coach.getId()));
            makeOfferButton.setDisable(FranchiseUtils.franchiseHasCoach(userFranchiseId,
                    connection));
        }
    }

    /**
     * Submits a contract offer to a coach
     */
    @FXML
    private void makeOffer() {
        /**
         * Checking whether the franchise had already made the maximum of three
         * offer to the head coach
         */
        if (failedContractAttemptsProgressBar.getProgress() == 1) {
            SceneUtils.warning(resources.getString("ch_maximo_ofertas_tecnico"),
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
        if (coachesComboBox.getSelectionModel().getSelectedIndex() == -1) {
            SceneUtils.warning(resources.getString("ch_selecione_tecnico"),
                    resources.getString("ch_atencao"));
            coachesComboBox.requestFocus();
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
         * Creating contract object and checking coach's response to offer
         */
        CoachTransactionRecord offer = new CoachTransactionRecord();
        offer.setCoach(coach.getId());
        offer.setDate(SettingsUtils.getSetting("currentDate",
                Calendar.getInstance().get(Calendar.YEAR) + "-01-01"));
        offer.setFranchise(userFranchiseId);
        offer.setLength(contractLength);
        offer.setSalary(franchiseOffer);
        offer.setType("C");
        offer.setSeason(Short.parseShort(SettingsUtils.getSetting("currentSeason",
                String.valueOf(Calendar.getInstance().get(Calendar.YEAR)))));

        if (CoachUtils.agreeWithTerms(coach, coachProposal, offer)) {
            CoachUtils.hireCoach(offer, connection);
            SceneUtils.warning(resources.getString("ch_aceitou_oferta"),
                    resources.getString("ch_atencao"));
            gotoOffSeason();
        } else {
            CoachUtils.recordFailedContractAttempt(coach.getId(), connection);
            SceneUtils.warning(resources.getString("ch_rejeitou_oferta"),
                    resources.getString("ch_atencao"));
            updateNegotiationPanel();
        }
    }

    /**
     * Releases the coach, asking confirmation before
     */
    @FXML
    private void confirmReleaseCoach() {
        if (SceneUtils.confirm(resources.getString("ch_liberar_tecnico"),
                resources.getString("ch_confirmar")) == 0) {
            
            /**
             * Creating contract object, releasing coach and updating controls
             */
            CoachTransactionRecord currentContract = new CoachTransactionRecord();
            currentContract.setCoach(coach.getId());
            currentContract.setDate(SettingsUtils.getSetting("currentDate",
                    Calendar.getInstance().get(Calendar.YEAR) + "-01-01"));
            currentContract.setFranchise(userFranchiseId);
            currentContract.setLength((short) 0);
            currentContract.setSalary(franchiseOffer);
            currentContract.setType("L");
            currentContract.setSeason(Short.parseShort(SettingsUtils.getSetting("currentSeason",
                    String.valueOf(Calendar.getInstance().get(Calendar.YEAR)))));
            
            CoachUtils.releaseCoach(currentContract, connection);
            coachesComboBox.setDisable(false);
            
            /* updating negotiation panel */
            updateNegotiationPanel();
        }
    }
}
