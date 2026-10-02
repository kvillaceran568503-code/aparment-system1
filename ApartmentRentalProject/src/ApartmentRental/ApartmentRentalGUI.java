package ApartmentRental;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class ApartmentRentalGUI extends JFrame {

    // =========================================================
    // APPLICANT CLASS
    // =========================================================

    static class Applicant {

        String name;
        int age;
        String contact;
        String apartmentType;
        int numberOfOccupants;
        int rentalDuration;
        String durationType;
        String priority;

        String unit;
        String status;

        double originalRent;
        double discount;
        double totalRent;
        double amountPaid;
        double balance;
        double change;

        String paymentStatus;

        public Applicant(String name, int age, String contact,
                String apartmentType, int numberOfOccupants,
                int rentalDuration, String durationType,
                String priority) {

            this.name = name;
            this.age = age;
            this.contact = contact;
            this.apartmentType = apartmentType;
            this.numberOfOccupants = numberOfOccupants;
            this.rentalDuration = rentalDuration;
            this.durationType = durationType;
            this.priority = priority;

            unit = "Not Assigned";
            status = "Waiting";

            originalRent = 0;
            discount = 0;
            totalRent = 0;
            amountPaid = 0;
            balance = 0;
            change = 0;

            paymentStatus = "No Payment";
        }

        // =====================================================
        // PRIORITY VALUE
        // =====================================================

        public int getPriority() {

            if (priority.equals("Senior Citizen")) {
                return 1;
            }

            if (priority.equals("Family")) {
                return 2;
            }

            return 3;
        }
    }

    // =========================================================
    // APARTMENT UNITS
    // =========================================================

    private String[] studioUnits = {
        "S101", "S102", "S103"
    };

    private String[] oneBedroomUnits = {
        "B101", "B102", "B103"
    };

    private String[] twoBedroomUnits = {
        "T101", "T102", "T103"
    };

    private String[] threeBedroomUnits = {
        "F101", "F102", "F103"
    };

    private boolean[] studioOccupied = {
        false, false, false
    };

    private boolean[] oneBedroomOccupied = {
        false, false, false
    };

    private boolean[] twoBedroomOccupied = {
        false, false, false
    };

    private boolean[] threeBedroomOccupied = {
        false, false, false
    };

    // =========================================================
    // DATA STRUCTURES
    // =========================================================

    // Regular Queue - FIFO
    private Queue<Applicant> regularQueue = new LinkedList<>();

    // Priority Queue
    private PriorityQueue<Applicant> priorityQueue =
        new PriorityQueue<>(
            Comparator.comparingInt(Applicant::getPriority)
        );

    // Current records
    private ArrayList<Applicant> rentalRecords = new ArrayList<>();

    // =========================================================
    // GUI COMPONENTS
    // =========================================================

    private JTextField nameField;
    private JTextField ageField;
    private JTextField contactField;
    private JTextField occupantsField;
    private JTextField durationField;

    private JComboBox<String> apartmentTypeBox;
    private JComboBox<String> durationTypeBox;
    private JComboBox<String> priorityBox;

    private JTable table;

    private DefaultTableModel tableModel;

    private JLabel regularLabel;
    private JLabel priorityLabel;
    private JLabel availableLabel;
    private JLabel occupiedLabel;
    private JLabel peopleLabel;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public ApartmentRentalGUI() {

        setTitle("Apartment Rental Management System");

        setSize(1350, 800);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        // =====================================================
        // HEADER
        // =====================================================

        JPanel header = new JPanel(new BorderLayout());

        header.setBorder(
            BorderFactory.createEmptyBorder(15, 15, 5, 15)
        );

        JLabel title = new JLabel(
            "APARTMENT RENTAL MANAGEMENT SYSTEM"
        );

        title.setFont(
            new Font("Arial", Font.BOLD, 22)
        );

        JLabel subtitle = new JLabel(
            "Affordable Apartment Tenant Management"
        );

        header.add(title, BorderLayout.NORTH);

        header.add(subtitle, BorderLayout.SOUTH);

        add(header, BorderLayout.NORTH);

        // =====================================================
        // MAIN PANEL
        // =====================================================

        JPanel mainPanel =
            new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
            BorderFactory.createEmptyBorder(0, 15, 15, 15)
        );

        mainPanel.add(
            createFormPanel(),
            BorderLayout.WEST
        );

        // =====================================================
        // RIGHT PANEL
        // =====================================================

        JPanel rightPanel =
            new JPanel(new BorderLayout(10, 10));

        rightPanel.add(
            createStatsPanel(),
            BorderLayout.NORTH
        );

        rightPanel.add(
            createTablePanel(),
            BorderLayout.CENTER
        );

        rightPanel.add(
            createButtonPanel(),
            BorderLayout.SOUTH
        );

        mainPanel.add(
            rightPanel,
            BorderLayout.CENTER
        );

        add(mainPanel, BorderLayout.CENTER);

        updateStatus();

        setVisible(true);
    }

    // =========================================================
    // FORM PANEL
    // =========================================================

    private JPanel createFormPanel() {

        JPanel panel = new JPanel();

        panel.setPreferredSize(
            new Dimension(330, 0)
        );

        panel.setBorder(
            BorderFactory.createTitledBorder(
                "Applicant Information"
            )
        );

        panel.setLayout(
            new BoxLayout(panel, BoxLayout.Y_AXIS)
        );

        // =====================================================
        // TENANT NAME
        // =====================================================

        panel.add(new JLabel("Tenant Name"));

        nameField = new JTextField();

        panel.add(nameField);

        panel.add(
            Box.createVerticalStrut(8)
        );

        // =====================================================
        // AGE
        // =====================================================

        panel.add(new JLabel("Age"));

        ageField = new JTextField();

        panel.add(ageField);

        panel.add(
            Box.createVerticalStrut(8)
        );

        // =====================================================
        // CONTACT NUMBER
        // =====================================================

        panel.add(new JLabel("Contact Number"));

        contactField = new JTextField();

        panel.add(contactField);

        panel.add(
            Box.createVerticalStrut(8)
        );

        // =====================================================
        // APARTMENT TYPE
        // =====================================================

        panel.add(new JLabel("Apartment Type"));

        apartmentTypeBox =
            new JComboBox<>(
                new String[] {
                    "Studio",
                    "1-Bedroom",
                    "2-Bedroom",
                    "3-Bedroom"
                }
            );

        panel.add(apartmentTypeBox);

        panel.add(
            Box.createVerticalStrut(8)
        );

        // =====================================================
        // NUMBER OF OCCUPANTS
        // =====================================================

        panel.add(
            new JLabel("Number of Occupants")
        );

        occupantsField = new JTextField();

        panel.add(occupantsField);

        panel.add(
            Box.createVerticalStrut(8)
        );

        // =====================================================
        // RENTAL DURATION
        // =====================================================

        panel.add(
            new JLabel("Rental Duration")
        );

        durationField = new JTextField();

        panel.add(durationField);

        panel.add(
            Box.createVerticalStrut(8)
        );

        // =====================================================
        // DURATION TYPE
        // =====================================================

        panel.add(
            new JLabel("Duration Type")
        );

        durationTypeBox =
            new JComboBox<>(
                new String[] {
                    "Days",
                    "Months"
                }
            );

        panel.add(durationTypeBox);

        panel.add(
            Box.createVerticalStrut(8)
        );

        // =====================================================
        // PRIORITY
        // =====================================================

        panel.add(
            new JLabel("Priority Level")
        );

        priorityBox =
            new JComboBox<>(
                new String[] {
                    "Regular Applicant",
                    "Family",
                    "Senior Citizen"
                }
            );

        panel.add(priorityBox);

        panel.add(
            Box.createVerticalStrut(12)
        );

        // =====================================================
        // RENTAL RATES
        // =====================================================

        JPanel ratePanel =
            new JPanel(new BorderLayout(5, 5));

        ratePanel.setBorder(
            BorderFactory.createTitledBorder(
                "Rental Rates"
            )
        );

        // =====================================================
        // RATE HEADER
        // =====================================================

        JPanel rateHeader =
            new JPanel(new GridLayout(1, 3));

        rateHeader.add(
            new JLabel(
                "Apartment",
                SwingConstants.CENTER
            )
        );

        rateHeader.add(
            new JLabel(
                "Monthly",
                SwingConstants.CENTER
            )
        );

        rateHeader.add(
            new JLabel(
                "Daily",
                SwingConstants.CENTER
            )
        );

        ratePanel.add(
            rateHeader,
            BorderLayout.NORTH
        );

        // =====================================================
        // RATE ROWS
        // =====================================================

        JPanel rateRows =
            new JPanel(new GridLayout(5, 3));

        // Studio
        rateRows.add(
            new JLabel(
                "Studio",
                SwingConstants.CENTER
            )
        );

        rateRows.add(
            new JLabel(
                "P4,000",
                SwingConstants.CENTER
            )
        );

        rateRows.add(
            new JLabel(
                "P133.33",
                SwingConstants.CENTER
            )
        );

        // 1-Bedroom
        rateRows.add(
            new JLabel(
                "1-Bedroom",
                SwingConstants.CENTER
            )
        );

        rateRows.add(
            new JLabel(
                "P6,000",
                SwingConstants.CENTER
            )
        );

        rateRows.add(
            new JLabel(
                "P200.00",
                SwingConstants.CENTER
            )
        );

        // 2-Bedroom
        rateRows.add(
            new JLabel(
                "2-Bedroom",
                SwingConstants.CENTER
            )
        );

        rateRows.add(
            new JLabel(
                "P8,000",
                SwingConstants.CENTER
            )
        );

        rateRows.add(
            new JLabel(
                "P266.67",
                SwingConstants.CENTER
            )
        );

        // 3-Bedroom
        rateRows.add(
            new JLabel(
                "3-Bedroom",
                SwingConstants.CENTER
            )
        );

        rateRows.add(
            new JLabel(
                "P10,000",
                SwingConstants.CENTER
            )
        );

        rateRows.add(
            new JLabel(
                "P333.33",
                SwingConstants.CENTER
            )
        );

        // 30 Days
        rateRows.add(
            new JLabel(
                "30 Days",
                SwingConstants.CENTER
            )
        );

        rateRows.add(
            new JLabel(
                "= 1 Month",
                SwingConstants.CENTER
            )
        );

        rateRows.add(
            new JLabel(
                "",
                SwingConstants.CENTER
            )
        );

        ratePanel.add(
            rateRows,
            BorderLayout.CENTER
        );

        JLabel discountLabel =
            new JLabel(
                "Senior Citizen Discount: 20%",
                SwingConstants.CENTER
            );

        ratePanel.add(
            discountLabel,
            BorderLayout.SOUTH
        );

        panel.add(ratePanel);

        panel.add(
            Box.createVerticalStrut(12)
        );

        // =====================================================
        // ADD APPLICANT BUTTON
        // =====================================================

        JButton addButton =
            new JButton("ADD APPLICANT");

        addButton.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );

        addButton.addActionListener(
            e -> addApplicant()
        );

        panel.add(addButton);

        return panel;
    }

    // =========================================================
    // STATISTICS PANEL
    // =========================================================

    private JPanel createStatsPanel() {

        JPanel panel =
            new JPanel(
                new GridLayout(1, 5, 10, 0)
            );

        panel.setBorder(
            BorderFactory.createTitledBorder(
                "System Status"
            )
        );

        regularLabel =
            new JLabel(
                "0",
                SwingConstants.CENTER
            );

        priorityLabel =
            new JLabel(
                "0",
                SwingConstants.CENTER
            );

        availableLabel =
            new JLabel(
                "12",
                SwingConstants.CENTER
            );

        occupiedLabel =
            new JLabel(
                "0",
                SwingConstants.CENTER
            );

        peopleLabel =
            new JLabel(
                "0",
                SwingConstants.CENTER
            );

        panel.add(
            createStatPanel(
                "Regular Queue",
                regularLabel
            )
        );

        panel.add(
            createStatPanel(
                "Priority Queue",
                priorityLabel
            )
        );

        panel.add(
            createStatPanel(
                "Available",
                availableLabel
            )
        );

        panel.add(
            createStatPanel(
                "Occupied",
                occupiedLabel
            )
        );

        panel.add(
            createStatPanel(
                "Current Occupants",
                peopleLabel
            )
        );

        return panel;
    }

    // =========================================================
    // STAT PANEL
    // =========================================================

    private JPanel createStatPanel(
        String title,
        JLabel value
    ) {

        JPanel panel =
            new JPanel(new BorderLayout());

        panel.setBorder(
            BorderFactory.createEtchedBorder()
        );

        JLabel titleLabel =
            new JLabel(
                title,
                SwingConstants.CENTER
            );

        value.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                20
            )
        );

        panel.add(
            titleLabel,
            BorderLayout.NORTH
        );

        panel.add(
            value,
            BorderLayout.CENTER
        );

        return panel;
    }

    // =========================================================
    // TABLE PANEL
    // =========================================================

    private JPanel createTablePanel() {

        JPanel panel =
            new JPanel(new BorderLayout());

        panel.setBorder(
            BorderFactory.createTitledBorder(
                "Rental Records"
            )
        );

        String[] columns = {

            "Tenant",
            "Age",
            "Contact",
            "Apartment Type",
            "Number of Occupants",
            "Duration",
            "Priority",
            "Unit",
            "Status",
            "Original Rent",
            "Discount",
            "Total Rent",
            "Paid",
            "Balance",
            "Payment"
        };

        tableModel =
            new DefaultTableModel(
                columns,
                0
            ) {

                @Override
                public boolean isCellEditable(
                    int row,
                    int column
                ) {

                    return false;
                }
            };

        table =
            new JTable(tableModel);

        table.setRowHeight(25);

        table.setAutoResizeMode(
            JTable.AUTO_RESIZE_OFF
        );

        JScrollPane scroll =
            new JScrollPane(table);

        panel.add(
            scroll,
            BorderLayout.CENTER
        );

        return panel;
    }

    // =========================================================
    // BUTTON PANEL
    // =========================================================

    private JPanel createButtonPanel() {

        JPanel panel =
            new JPanel(
                new FlowLayout(
                    FlowLayout.RIGHT
                )
            );

        JButton process =
            new JButton(
                "PROCESS APPLICANT"
            );

        JButton moveOut =
            new JButton("MOVE OUT");

        JButton units =
            new JButton("VIEW UNITS");

        JButton records =
            new JButton("VIEW RECORDS");

        JButton save =
            new JButton("SAVE FILE");

        JButton delete =
            new JButton("DELETE");

        JButton clear =
            new JButton("CLEAR");

        process.addActionListener(
            e -> processNextApplicant()
        );

        moveOut.addActionListener(
            e -> moveOut()
        );

        units.addActionListener(
            e -> viewApartments()
        );

        records.addActionListener(
            e -> showRecords()
        );

        save.addActionListener(
            e -> saveAllRecords()
        );

        delete.addActionListener(
            e -> deleteSelected()
        );

        clear.addActionListener(
            e -> clearTable()
        );

        panel.add(process);
        panel.add(moveOut);
        panel.add(units);
        panel.add(records);
        panel.add(save);
        panel.add(delete);
        panel.add(clear);

        return panel;
    }

    // =========================================================
    // GET ROOM PRICE
    // =========================================================

    private double getRoomPrice(
        String apartmentType
    ) {

        if (apartmentType.equals("Studio")) {
            return 4000;
        }

        if (apartmentType.equals("1-Bedroom")) {
            return 6000;
        }

        if (apartmentType.equals("2-Bedroom")) {
            return 8000;
        }

        if (apartmentType.equals("3-Bedroom")) {
            return 10000;
        }

        return 0;
    }

    // =========================================================
    // CALCULATE TOTAL RENT
    // =========================================================

    private double calculateTotalRent(
        Applicant applicant
    ) {

        double price =
            getRoomPrice(
                applicant.apartmentType
            );

        double originalTotal;

        if (applicant.durationType.equals("Months")) {

            originalTotal =
                price *
                applicant.rentalDuration;

        } else {

            originalTotal =
                (price / 30) *
                applicant.rentalDuration;
        }

        applicant.originalRent =
            Math.round(
                originalTotal * 100.0
            ) / 100.0;

        // =====================================================
        // SENIOR CITIZEN DISCOUNT
        // =====================================================

        if (
            applicant.priority.equals(
                "Senior Citizen"
            )
        ) {

            applicant.discount =
                Math.round(
                    originalTotal * 0.20 * 100.0
                ) / 100.0;

        } else {

            applicant.discount = 0;
        }

        return Math.round(
            (originalTotal -
             applicant.discount) * 100.0
        ) / 100.0;
    }

    // =========================================================
    // ADD APPLICANT
    // =========================================================

    private void addApplicant() {

        try {

            String name =
                nameField.getText().trim();

            int age =
                Integer.parseInt(
                    ageField.getText().trim()
                );

            String contact =
                contactField.getText().trim();

            String apartmentType =
                apartmentTypeBox
                    .getSelectedItem()
                    .toString();

            int numberOfOccupants =
                Integer.parseInt(
                    occupantsField
                        .getText()
                        .trim()
                );

            int duration =
                Integer.parseInt(
                    durationField
                        .getText()
                        .trim()
                );

            String durationType =
                durationTypeBox
                    .getSelectedItem()
                    .toString();

            String priority =
                priorityBox
                    .getSelectedItem()
                    .toString();

            // =================================================
            // VALIDATION
            // =================================================

            if (
                name.isEmpty() ||
                contact.isEmpty()
            ) {

                JOptionPane.showMessageDialog(
                    this,
                    "Please complete all fields."
                );

                return;
            }

            if (age <= 0) {

                JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid age."
                );

                return;
            }

            if (numberOfOccupants <= 0) {

                JOptionPane.showMessageDialog(
                    this,
                    "Number of occupants must be greater than 0."
                );

                return;
            }

            if (duration <= 0) {

                JOptionPane.showMessageDialog(
                    this,
                    "Rental duration must be greater than 0."
                );

                return;
            }

            // =================================================
            // CREATE APPLICANT
            // =================================================

            Applicant applicant =
                new Applicant(
                    name,
                    age,
                    contact,
                    apartmentType,
                    numberOfOccupants,
                    duration,
                    durationType,
                    priority
                );

            // =================================================
            // CALCULATE RENT
            // =================================================

            applicant.totalRent =
                calculateTotalRent(
                    applicant
                );

            applicant.balance =
                applicant.totalRent;

            // =================================================
            // ADD TO QUEUE
            // =================================================

            if (
                priority.equals(
                    "Regular Applicant"
                )
            ) {

                regularQueue.add(
                    applicant
                );

            } else {

                priorityQueue.add(
                    applicant
                );
            }

            // =================================================
            // ADD TO RECORDS
            // =================================================

            rentalRecords.add(
                applicant
            );

            // IMPORTANT:
            // DO NOT SORT HERE.
            // Applicant remains in the order added.

            refreshTable();

            updateStatus();

            clearFields();

            // =================================================
            // SHOW RESULT
            // =================================================

            JOptionPane.showMessageDialog(
                this,

                "Applicant Added Successfully!\n\n" +

                "Tenant: " +
                applicant.name + "\n" +

                "Apartment: " +
                applicant.apartmentType + "\n" +

                "Number of Occupants: " +
                applicant.numberOfOccupants + "\n" +

                "Duration: " +
                applicant.rentalDuration +
                " " +
                applicant.durationType + "\n" +

                "Priority: " +
                applicant.priority + "\n\n" +

                "Original Rent: P" +
                String.format(
                    "%.2f",
                    applicant.originalRent
                ) + "\n" +

                "Senior Citizen Discount: P" +
                String.format(
                    "%.2f",
                    applicant.discount
                ) + "\n" +

                "Total Rent: P" +
                String.format(
                    "%.2f",
                    applicant.totalRent
                )
            );

        } catch (
            NumberFormatException e
        ) {

            JOptionPane.showMessageDialog(
                this,

                "Please enter valid numbers for Age, " +
                "Number of Occupants, and Rental Duration."
            );
        }
    }

    // =========================================================
    // SORT RECORDS BY PRIORITY
    // =========================================================

    private void sortRecordsByPriority() {

        rentalRecords.sort(
            Comparator.comparingInt(
                Applicant::getPriority
            )
        );

        refreshTable();
    }

    // =========================================================
    // PROCESS NEXT APPLICANT
    // =========================================================

    private void processNextApplicant() {

        // =====================================================
        // ARRANGE TABLE BY PRIORITY
        // Senior Citizen -> Family -> Regular Applicant
        // =====================================================

        sortRecordsByPriority();

        Applicant applicant = null;

        // =====================================================
        // PRIORITY QUEUE FIRST
        // =====================================================

        if (!priorityQueue.isEmpty()) {

            applicant =
                priorityQueue.poll();

        } else if (!regularQueue.isEmpty()) {

            applicant =
                regularQueue.poll();
        }

        if (applicant == null) {

            JOptionPane.showMessageDialog(
                this,
                "No applicants waiting."
            );

            return;
        }

        // =====================================================
        // ASSIGN UNIT
        // =====================================================

        String unit =
            assignUnit(
                applicant.apartmentType
            );

        if (unit == null) {

            JOptionPane.showMessageDialog(
                this,

                "No available unit for " +
                applicant.apartmentType
            );

            // Return applicant to queue
            if (
                applicant.priority.equals(
                    "Regular Applicant"
                )
            ) {

                regularQueue.add(
                    applicant
                );

            } else {

                priorityQueue.add(
                    applicant
                );
            }

            refreshTable();

            updateStatus();

            return;
        }

        applicant.unit = unit;

        // =====================================================
        // PAYMENT
        // =====================================================

        boolean paymentSuccess =
            processPayment(applicant);

        if (!paymentSuccess) {

            releaseUnit(
                applicant.apartmentType,
                applicant.unit
            );

            applicant.unit =
                "Not Assigned";

            // Return applicant to queue
            if (
                applicant.priority.equals(
                    "Regular Applicant"
                )
            ) {

                regularQueue.add(
                    applicant
                );

            } else {

                priorityQueue.add(
                    applicant
                );
            }

            refreshTable();

            updateStatus();

            return;
        }

        // =====================================================
        // SET STATUS
        // =====================================================

        applicant.status =
            "Rented";

        // =====================================================
        // SAVE MOVE-IN
        // =====================================================

        saveRecordToFile(
            applicant,
            "MOVE-IN"
        );

        // =====================================================
        // SORT TABLE AGAIN
        // =====================================================

        sortRecordsByPriority();

        updateStatus();
    }

    // =========================================================
    // PROCESS PAYMENT
    // =========================================================

    private boolean processPayment(
        Applicant applicant
    ) {

        String[] choices = {
            "Full Payment",
            "Downpayment",
            "Cancel"
        };

        int choice =
            JOptionPane.showOptionDialog(
                this,

                "Tenant: " +
                applicant.name + "\n" +

                "Unit: " +
                applicant.unit + "\n\n" +

                "Original Rent: P" +
                String.format(
                    "%.2f",
                    applicant.originalRent
                ) + "\n" +

                "Senior Citizen Discount: P" +
                String.format(
                    "%.2f",
                    applicant.discount
                ) + "\n" +

                "Total Rent: P" +
                String.format(
                    "%.2f",
                    applicant.totalRent
                ) + "\n\n" +

                "Select payment type:",

                "Move-In Payment",

                JOptionPane.DEFAULT_OPTION,

                JOptionPane.INFORMATION_MESSAGE,

                null,

                choices,

                choices[0]
            );

        if (
            choice == 2 ||
            choice == -1
        ) {

            return false;
        }

        double required;

        // =====================================================
        // FULL PAYMENT
        // =====================================================

        if (choice == 0) {

            required =
                applicant.totalRent;

        } else {

            // 50% Downpayment
            required =
                applicant.totalRent * 0.50;
        }

        String input =
            JOptionPane.showInputDialog(
                this,

                "Required Payment: P" +
                String.format(
                    "%.2f",
                    required
                ) +

                "\n\nEnter amount:"
            );

        if (input == null) {

            return false;
        }

        try {

            double payment =
                Double.parseDouble(
                    input
                );

            if (payment < required) {

                JOptionPane.showMessageDialog(
                    this,

                    "Payment is not enough.\n\n" +

                    "Required: P" +
                    String.format(
                        "%.2f",
                        required
                    ) + "\n" +

                    "Entered: P" +
                    String.format(
                        "%.2f",
                        payment
                    )
                );

                return false;
            }

            applicant.amountPaid =
                payment;

            applicant.balance =
                applicant.totalRent -
                payment;

            // =================================================
            // CHANGE
            // =================================================

            if (applicant.balance < 0) {

                applicant.change =
                    Math.abs(
                        applicant.balance
                    );

                applicant.balance = 0;

            } else {

                applicant.change = 0;
            }

            // =================================================
            // PAYMENT STATUS
            // =================================================

            if (applicant.balance == 0) {

                applicant.paymentStatus =
                    "Fully Paid";

            } else {

                applicant.paymentStatus =
                    "Downpayment";
            }

            JOptionPane.showMessageDialog(
                this,

                "Payment Successful!\n\n" +

                "Amount Paid: P" +
                String.format(
                    "%.2f",
                    applicant.amountPaid
                ) + "\n" +

                "Balance: P" +
                String.format(
                    "%.2f",
                    applicant.balance
                ) + "\n" +

                "Change: P" +
                String.format(
                    "%.2f",
                    applicant.change
                )
            );

            return true;

        } catch (
            NumberFormatException e
        ) {

            JOptionPane.showMessageDialog(
                this,
                "Invalid payment amount."
            );

            return false;
        }
    }

    // =========================================================
    // MOVE OUT
    // =========================================================

    private void moveOut() {

        int row =
            table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                this,
                "Please select a tenant."
            );

            return;
        }

        Applicant applicant =
            rentalRecords.get(row);

        if (
            !applicant.status.equals(
                "Rented"
            )
        ) {

            JOptionPane.showMessageDialog(
                this,

                "This tenant is not currently rented."
            );

            return;
        }

        // =====================================================
        // CHECK BALANCE
        // =====================================================

        if (applicant.balance > 0) {

            JOptionPane.showMessageDialog(
                this,

                "NOT FULLY PAID\n\n" +

                "Tenant: " +
                applicant.name + "\n" +

                "Remaining Balance: P" +
                String.format(
                    "%.2f",
                    applicant.balance
                )
            );

            String input =
                JOptionPane.showInputDialog(
                    this,

                    "Remaining Balance: P" +
                    String.format(
                        "%.2f",
                        applicant.balance
                    ) +

                    "\n\nEnter payment:"
                );

            if (input == null) {

                return;
            }

            try {

                double payment =
                    Double.parseDouble(
                        input
                    );

                if (payment <= 0) {

                    JOptionPane.showMessageDialog(
                        this,
                        "Invalid payment."
                    );

                    return;
                }

                applicant.amountPaid +=
                    payment;

                applicant.balance =
                    applicant.totalRent -
                    applicant.amountPaid;

                // =================================================
                // CHANGE
                // =================================================

                if (applicant.balance < 0) {

                    applicant.change =
                        Math.abs(
                            applicant.balance
                        );

                    applicant.balance = 0;
                }

                // =================================================
                // STILL HAS BALANCE
                // =================================================

                if (applicant.balance > 0) {

                    applicant.paymentStatus =
                        "Downpayment";

                    refreshTable();

                    saveRecordToFile(
                        applicant,
                        "CHECK-OUT FAILED - NOT FULLY PAID"
                    );

                    JOptionPane.showMessageDialog(
                        this,

                        "Check-out denied.\n\n" +

                        "Remaining Balance: P" +
                        String.format(
                            "%.2f",
                            applicant.balance
                        )
                    );

                    return;
                }

                applicant.paymentStatus =
                    "Fully Paid";

                JOptionPane.showMessageDialog(
                    this,

                    "Balance Fully Paid!\n\n" +

                    "Change: P" +
                    String.format(
                        "%.2f",
                        applicant.change
                    )
                );

            } catch (
                NumberFormatException e
            ) {

                JOptionPane.showMessageDialog(
                    this,
                    "Invalid payment."
                );

                return;
            }
        }

        // =====================================================
        // RELEASE UNIT
        // =====================================================

        releaseUnit(
            applicant.apartmentType,
            applicant.unit
        );

        applicant.status =
            "Moved Out";

        // =====================================================
        // SAVE MOVE-OUT
        // =====================================================

        saveRecordToFile(
            applicant,
            "MOVE-OUT / CHECK-OUT"
        );

        refreshTable();

        updateStatus();

        JOptionPane.showMessageDialog(
            this,

            "Check-out successful!\n\n" +

            "Tenant: " +
            applicant.name + "\n" +

            "Unit: " +
            applicant.unit
        );
    }

    // =========================================================
    // ASSIGN UNIT
    // =========================================================

    private String assignUnit(
        String apartmentType
    ) {

        if (
            apartmentType.equals(
                "Studio"
            )
        ) {

            for (
                int i = 0;
                i < studioUnits.length;
                i++
            ) {

                if (!studioOccupied[i]) {

                    studioOccupied[i] =
                        true;

                    return studioUnits[i];
                }
            }
        }

        if (
            apartmentType.equals(
                "1-Bedroom"
            )
        ) {

            for (
                int i = 0;
                i < oneBedroomUnits.length;
                i++
            ) {

                if (!oneBedroomOccupied[i]) {

                    oneBedroomOccupied[i] =
                        true;

                    return oneBedroomUnits[i];
                }
            }
        }

        if (
            apartmentType.equals(
                "2-Bedroom"
            )
        ) {

            for (
                int i = 0;
                i < twoBedroomUnits.length;
                i++
            ) {

                if (!twoBedroomOccupied[i]) {

                    twoBedroomOccupied[i] =
                        true;

                    return twoBedroomUnits[i];
                }
            }
        }

        if (
            apartmentType.equals(
                "3-Bedroom"
            )
        ) {

            for (
                int i = 0;
                i < threeBedroomUnits.length;
                i++
            ) {

                if (!threeBedroomOccupied[i]) {

                    threeBedroomOccupied[i] =
                        true;

                    return threeBedroomUnits[i];
                }
            }
        }

        return null;
    }

    // =========================================================
    // RELEASE UNIT
    // =========================================================

    private void releaseUnit(
        String apartmentType,
        String unit
    ) {

        if (
            unit.equals(
                "Not Assigned"
            )
        ) {

            return;
        }

        if (
            apartmentType.equals(
                "Studio"
            )
        ) {

            for (
                int i = 0;
                i < studioUnits.length;
                i++
            ) {

                if (
                    studioUnits[i].equals(
                        unit
                    )
                ) {

                    studioOccupied[i] =
                        false;
                }
            }
        }

        if (
            apartmentType.equals(
                "1-Bedroom"
            )
        ) {

            for (
                int i = 0;
                i < oneBedroomUnits.length;
                i++
            ) {

                if (
                    oneBedroomUnits[i].equals(
                        unit
                    )
                ) {

                    oneBedroomOccupied[i] =
                        false;
                }
            }
        }

        if (
            apartmentType.equals(
                "2-Bedroom"
            )
        ) {

            for (
                int i = 0;
                i < twoBedroomUnits.length;
                i++
            ) {

                if (
                    twoBedroomUnits[i].equals(
                        unit
                    )
                ) {

                    twoBedroomOccupied[i] =
                        false;
                }
            }
        }

        if (
            apartmentType.equals(
                "3-Bedroom"
            )
        ) {

            for (
                int i = 0;
                i < threeBedroomUnits.length;
                i++
            ) {

                if (
                    threeBedroomUnits[i].equals(
                        unit
                    )
                ) {

                    threeBedroomOccupied[i] =
                        false;
                }
            }
        }
    }

    // =========================================================
    // DELETE SELECTED
    // =========================================================

    private void deleteSelected() {

        int row =
            table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                this,
                "Please select a record."
            );

            return;
        }

        Applicant applicant =
            rentalRecords.get(row);

        int confirm =
            JOptionPane.showConfirmDialog(
                this,

                "Delete this record from the table?\n\n" +

                "The record will remain saved in the file.",

                "Confirm Delete",

                JOptionPane.YES_NO_OPTION
            );

        if (
            confirm !=
            JOptionPane.YES_OPTION
        ) {

            return;
        }

        // =====================================================
        // SAVE DELETE HISTORY
        // =====================================================

        saveRecordToFile(
            applicant,
            "DELETED FROM CURRENT TABLE"
        );

        // =====================================================
        // RELEASE UNIT
        // =====================================================

        if (
            applicant.status.equals(
                "Rented"
            )
        ) {

            releaseUnit(
                applicant.apartmentType,
                applicant.unit
            );
        }

        // =====================================================
        // REMOVE FROM QUEUES
        // =====================================================

        regularQueue.remove(
            applicant
        );

        priorityQueue.remove(
            applicant
        );

        // =====================================================
        // REMOVE CURRENT RECORD
        // =====================================================

        rentalRecords.remove(
            row
        );

        refreshTable();

        updateStatus();
    }

    // =========================================================
    // CLEAR TABLE
    // =========================================================

    private void clearTable() {

        if (rentalRecords.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "No records to clear."
            );

            return;
        }

        int confirm =
            JOptionPane.showConfirmDialog(
                this,

                "Clear all records from the table?\n\n" +

                "The records will remain saved in the file.",

                "Confirm Clear",

                JOptionPane.YES_NO_OPTION
            );

        if (
            confirm !=
            JOptionPane.YES_OPTION
        ) {

            return;
        }

        // =====================================================
        // SAVE HISTORY BEFORE CLEARING
        // =====================================================

        for (
            Applicant applicant :
            rentalRecords
        ) {

            saveRecordToFile(
                applicant,
                "CLEARED FROM CURRENT TABLE"
            );

            if (
                applicant.status.equals(
                    "Rented"
                )
            ) {

                releaseUnit(
                    applicant.apartmentType,
                    applicant.unit
                );
            }
        }

        rentalRecords.clear();

        regularQueue.clear();

        priorityQueue.clear();

        refreshTable();

        updateStatus();
    }

    // =========================================================
    // REFRESH TABLE
    // =========================================================

    private void refreshTable() {

        tableModel.setRowCount(0);

        for (
            Applicant applicant :
            rentalRecords
        ) {

            tableModel.addRow(
                new Object[] {

                    applicant.name,

                    applicant.age,

                    applicant.contact,

                    applicant.apartmentType,

                    applicant.numberOfOccupants,

                    applicant.rentalDuration +
                    " " +
                    applicant.durationType,

                    applicant.priority,

                    applicant.unit,

                    applicant.status,

                    "P" +
                    String.format(
                        "%.2f",
                        applicant.originalRent
                    ),

                    "P" +
                    String.format(
                        "%.2f",
                        applicant.discount
                    ),

                    "P" +
                    String.format(
                        "%.2f",
                        applicant.totalRent
                    ),

                    "P" +
                    String.format(
                        "%.2f",
                        applicant.amountPaid
                    ),

                    "P" +
                    String.format(
                        "%.2f",
                        applicant.balance
                    ),

                    applicant.paymentStatus
                }
            );
        }
    }

    // =========================================================
    // UPDATE STATUS
    // =========================================================

    private void updateStatus() {

        int occupied = 0;

        // =====================================================
        // STUDIO
        // =====================================================

        for (
            boolean value :
            studioOccupied
        ) {

            if (value) {
                occupied++;
            }
        }

        // =====================================================
        // 1-BEDROOM
        // =====================================================

        for (
            boolean value :
            oneBedroomOccupied
        ) {

            if (value) {
                occupied++;
            }
        }

        // =====================================================
        // 2-BEDROOM
        // =====================================================

        for (
            boolean value :
            twoBedroomOccupied
        ) {

            if (value) {
                occupied++;
            }
        }

        // =====================================================
        // 3-BEDROOM
        // =====================================================

        for (
            boolean value :
            threeBedroomOccupied
        ) {

            if (value) {
                occupied++;
            }
        }

        int totalUnits =
            studioUnits.length +
            oneBedroomUnits.length +
            twoBedroomUnits.length +
            threeBedroomUnits.length;

        int available =
            totalUnits - occupied;

        // =====================================================
        // CURRENT NUMBER OF OCCUPANTS
        // =====================================================

        int currentOccupants = 0;

        for (
            Applicant applicant :
            rentalRecords
        ) {

            if (
                applicant.status.equals(
                    "Rented"
                )
            ) {

                currentOccupants +=
                    applicant.numberOfOccupants;
            }
        }

        regularLabel.setText(
            String.valueOf(
                regularQueue.size()
            )
        );

        priorityLabel.setText(
            String.valueOf(
                priorityQueue.size()
            )
        );

        availableLabel.setText(
            String.valueOf(
                available
            )
        );

        occupiedLabel.setText(
            String.valueOf(
                occupied
            )
        );

        peopleLabel.setText(
            String.valueOf(
                currentOccupants
            )
        );
    }

    // =========================================================
    // VIEW APARTMENTS
    // =========================================================

    private void viewApartments() {

        StringBuilder text =
            new StringBuilder();

        text.append(
            "APARTMENT UNITS\n\n"
        );

        text.append(
            "STUDIO\n" +
            "Rate: P4,000/month | P133.33/day\n"
        );

        for (
            int i = 0;
            i < studioUnits.length;
            i++
        ) {

            text.append(
                studioUnits[i] +
                " - " +
                (
                    studioOccupied[i]
                    ? "OCCUPIED"
                    : "AVAILABLE"
                ) +
                "\n"
            );
        }

        text.append(
            "\n1-BEDROOM\n" +
            "Rate: P6,000/month | P200.00/day\n"
        );

        for (
            int i = 0;
            i < oneBedroomUnits.length;
            i++
        ) {

            text.append(
                oneBedroomUnits[i] +
                " - " +
                (
                    oneBedroomOccupied[i]
                    ? "OCCUPIED"
                    : "AVAILABLE"
                ) +
                "\n"
            );
        }

        text.append(
            "\n2-BEDROOM\n" +
            "Rate: P8,000/month | P266.67/day\n"
        );

        for (
            int i = 0;
            i < twoBedroomUnits.length;
            i++
        ) {

            text.append(
                twoBedroomUnits[i] +
                " - " +
                (
                    twoBedroomOccupied[i]
                    ? "OCCUPIED"
                    : "AVAILABLE"
                ) +
                "\n"
            );
        }

        text.append(
            "\n3-BEDROOM\n" +
            "Rate: P10,000/month | P333.33/day\n"
        );

        for (
            int i = 0;
            i < threeBedroomUnits.length;
            i++
        ) {

            text.append(
                threeBedroomUnits[i] +
                " - " +
                (
                    threeBedroomOccupied[i]
                    ? "OCCUPIED"
                    : "AVAILABLE"
                ) +
                "\n"
            );
        }

        text.append(
            "\n--------------------------------\n"
        );

        text.append(
            "30 Days = 1 Month\n"
        );

        text.append(
            "Senior Citizen Discount = 20%"
        );

        JTextArea area =
            new JTextArea(
                text.toString()
            );

        area.setEditable(false);

        area.setFont(
            new Font(
                "Arial",
                Font.PLAIN,
                14
            )
        );

        JScrollPane scroll =
            new JScrollPane(area);

        scroll.setPreferredSize(
            new Dimension(500, 450)
        );

        JOptionPane.showMessageDialog(
            this,

            scroll,

            "Apartment Units",

            JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================================================
    // SHOW RECORDS
    // =========================================================

    private void showRecords() {

        if (rentalRecords.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "No current records."
            );

            return;
        }

        StringBuilder text =
            new StringBuilder();

        text.append(
            "CURRENT RENTAL RECORDS\n\n"
        );

        for (
            Applicant applicant :
            rentalRecords
        ) {

            text.append(
                "Tenant: " +
                applicant.name +
                "\n"
            );

            text.append(
                "Age: " +
                applicant.age +
                "\n"
            );

            text.append(
                "Contact: " +
                applicant.contact +
                "\n"
            );

            text.append(
                "Apartment Type: " +
                applicant.apartmentType +
                "\n"
            );

            text.append(
                "Number of Occupants: " +
                applicant.numberOfOccupants +
                "\n"
            );

            text.append(
                "Rental Duration: " +
                applicant.rentalDuration +
                " " +
                applicant.durationType +
                "\n"
            );

            text.append(
                "Priority: " +
                applicant.priority +
                "\n"
            );

            text.append(
                "Unit: " +
                applicant.unit +
                "\n"
            );

            text.append(
                "Status: " +
                applicant.status +
                "\n"
            );

            text.append(
                "Original Rent: P" +
                String.format(
                    "%.2f",
                    applicant.originalRent
                ) +
                "\n"
            );

            text.append(
                "Senior Citizen Discount: P" +
                String.format(
                    "%.2f",
                    applicant.discount
                ) +
                "\n"
            );

            text.append(
                "Total Rent: P" +
                String.format(
                    "%.2f",
                    applicant.totalRent
                ) +
                "\n"
            );

            text.append(
                "Amount Paid: P" +
                String.format(
                    "%.2f",
                    applicant.amountPaid
                ) +
                "\n"
            );

            text.append(
                "Balance: P" +
                String.format(
                    "%.2f",
                    applicant.balance
                ) +
                "\n"
            );

            text.append(
                "Change: P" +
                String.format(
                    "%.2f",
                    applicant.change
                ) +
                "\n"
            );

            text.append(
                "Payment Status: " +
                applicant.paymentStatus +
                "\n"
            );

            text.append(
                "----------------------------------------\n"
            );
        }

        JTextArea area =
            new JTextArea(
                text.toString()
            );

        area.setEditable(false);

        area.setFont(
            new Font(
                "Arial",
                Font.PLAIN,
                13
            )
        );

        JScrollPane scroll =
            new JScrollPane(area);

        scroll.setPreferredSize(
            new Dimension(600, 500)
        );

        JOptionPane.showMessageDialog(
            this,

            scroll,

            "Rental Records",

            JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================================================
    // SAVE RECORD TO FILE
    // =========================================================

    private void saveRecordToFile(
        Applicant applicant,
        String action
    ) {

        try {

            FileWriter writer =
                new FileWriter(
                    "rental_records.txt",
                    true
                );

            writer.write(
                "========================================\n"
            );

            writer.write(
                "ACTION: " +
                action +
                "\n"
            );

            writer.write(
                "Tenant Name: " +
                applicant.name +
                "\n"
            );

            writer.write(
                "Age: " +
                applicant.age +
                "\n"
            );

            writer.write(
                "Contact: " +
                applicant.contact +
                "\n"
            );

            writer.write(
                "Apartment Type: " +
                applicant.apartmentType +
                "\n"
            );

            writer.write(
                "Number of Occupants: " +
                applicant.numberOfOccupants +
                "\n"
            );

            writer.write(
                "Rental Duration: " +
                applicant.rentalDuration +
                " " +
                applicant.durationType +
                "\n"
            );

            writer.write(
                "Priority: " +
                applicant.priority +
                "\n"
            );

            writer.write(
                "Unit: " +
                applicant.unit +
                "\n"
            );

            writer.write(
                "Status: " +
                applicant.status +
                "\n"
            );

            writer.write(
                "Original Rent: P" +
                String.format(
                    "%.2f",
                    applicant.originalRent
                ) +
                "\n"
            );

            writer.write(
                "Senior Citizen Discount: P" +
                String.format(
                    "%.2f",
                    applicant.discount
                ) +
                "\n"
            );

            writer.write(
                "Total Rent: P" +
                String.format(
                    "%.2f",
                    applicant.totalRent
                ) +
                "\n"
            );

            writer.write(
                "Amount Paid: P" +
                String.format(
                    "%.2f",
                    applicant.amountPaid
                ) +
                "\n"
            );

            writer.write(
                "Balance: P" +
                String.format(
                    "%.2f",
                    applicant.balance
                ) +
                "\n"
            );

            writer.write(
                "Change: P" +
                String.format(
                    "%.2f",
                    applicant.change
                ) +
                "\n"
            );

            writer.write(
                "Payment Status: " +
                applicant.paymentStatus +
                "\n"
            );

            writer.write(
                "========================================\n\n"
            );

            writer.close();

        } catch (
            IOException e
        ) {

            JOptionPane.showMessageDialog(
                this,

                "Error saving file:\n" +
                e.getMessage()
            );
        }
    }

    // =========================================================
    // SAVE ALL RECORDS
    // =========================================================

    private void saveAllRecords() {

        if (rentalRecords.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "No records to save."
            );

            return;
        }

        for (
            Applicant applicant :
            rentalRecords
        ) {

            saveRecordToFile(
                applicant,
                "MANUAL SAVE"
            );
        }

        JOptionPane.showMessageDialog(
            this,

            "Records saved successfully!\n\n" +
            "File: rental_records.txt"
        );
    }

    // =========================================================
    // CLEAR INPUT FIELDS
    // =========================================================

    private void clearFields() {

        nameField.setText("");

        ageField.setText("");

        contactField.setText("");

        occupantsField.setText("");

        durationField.setText("");

        apartmentTypeBox.setSelectedIndex(0);

        durationTypeBox.setSelectedIndex(0);

        priorityBox.setSelectedIndex(0);
    }
}

