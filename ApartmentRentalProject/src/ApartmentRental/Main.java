package ApartmentRental;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            ApartmentRentalGUI app = new ApartmentRentalGUI();
            app.setVisible(true);
        });

    }
}

