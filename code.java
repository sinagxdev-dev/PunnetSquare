import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// OOP: The System Class inherits from JFrame (The Window)
public class PunnettSystem extends JFrame implements ActionListener {

    // GUI Components (The "Parts" of our system)
    JLabel lblTrait, lblDom, lblRec, lblDad, lblMom;
    JTextField txtTrait, txtDom, txtRec, txtDad, txtMom;
    JButton btnCalculate, btnClear;
    JTextArea txtResult;
    JScrollPane scrollPane;

    public PunnettSystem() {
        // 1. SETUP THE WINDOW
        setTitle("Genetic Predictor System");
        setSize(350, 650); // Adjusted height slightly to fit everything
        setLayout(new FlowLayout()); // Simple layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen

        // 2. CREATE COMPONENTS (Inputs)
        // Trait Input
        lblTrait = new JLabel("Trait Name (e.g., Eye Color):");
        txtTrait = new JTextField(20); // 20 columns wide
        
        // Labels for Dominant/Recessive
        lblDom = new JLabel("Dominant Label (e.g., Brown):");
        txtDom = new JTextField(20);
        
        lblRec = new JLabel("Recessive Label (e.g., Blue):");
        txtRec = new JTextField(20);

        // Gene Inputs
        lblDad = new JLabel("Father's Genes (e.g., Bb):");
        txtDad = new JTextField(10);
        
        lblMom = new JLabel("Mother's Genes (e.g., bb):");
        txtMom = new JTextField(10);

        // Buttons
        btnCalculate = new JButton("PREDICT RESULTS");
        btnCalculate.addActionListener(this); // Listen for clicks
        
        btnClear = new JButton("Clear");
        btnClear.addActionListener(this);

        // Output Area
        txtResult = new JTextArea(15, 30); // 15 rows, 30 chars wide
        txtResult.setEditable(false); // User cannot type here
        txtResult.setFont(new Font("Monospaced", Font.BOLD, 12));
        scrollPane = new JScrollPane(txtResult);

        // 3. ADD COMPONENTS TO WINDOW (Sequence Matters!)
        add(lblTrait); add(txtTrait);
        add(lblDom);   add(txtDom);
        add(lblRec);   add(txtRec);
        // Note: Separator might be small in FlowLayout, but it won't crash
        add(new JSeparator(SwingConstants.HORIZONTAL)); 
        add(lblDad);   add(txtDad);
        add(lblMom);   add(txtMom);
        add(btnCalculate); add(btnClear);
        add(scrollPane);

        setVisible(true); // Show the window
    }

    // 4. LOGIC: This runs when you click the button
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnClear) {
            txtResult.setText("");
            // Clear text fields too for a full reset
            txtTrait.setText("");
            txtDom.setText("");
            txtRec.setText("");
            txtDad.setText("");
            txtMom.setText("");
            return;
        }

        try {
            // GET INPUTS (Added .trim() to remove accidental spaces)
            String domLabel = txtDom.getText().trim();
            String recLabel = txtRec.getText().trim();
            String dadGenes = txtDad.getText().trim();
            String momGenes = txtMom.getText().trim();

            // Validate Input (Basic Logic)
            if(dadGenes.length() != 2 || momGenes.length() != 2) {
                txtResult.setText("Error: Genes must be exactly 2 letters.\nExample: Bb, BB, or bb");
                return;
            }

            // HEADER OUTPUT
            StringBuilder sb = new StringBuilder();
            sb.append("--- PREDICTION REPORT ---\n");
            sb.append("Trait: ").append(txtTrait.getText()).append("\n");
            sb.append("Dominant: ").append(domLabel).append("\n");
            sb.append("Recessive: ").append(recLabel).append("\n\n");
            sb.append("Mixing: ").append(dadGenes).append(" x ").append(momGenes).append("\n");
            sb.append("-------------------------\n");

            int domCount = 0;
            int recCount = 0;

            // 5. REPETITION & LOGIC (The Core Algorithm)
            for (int i = 0; i < dadGenes.length(); i++) {
                for (int j = 0; j < momGenes.length(); j++) {
                    
                    char letter1 = dadGenes.charAt(i);
                    char letter2 = momGenes.charAt(j);
                    String babyGene = "" + letter1 + letter2;
                    
                    String prediction = "";

                    // SMART LOGIC: Check for Uppercase (Dominant)
                    boolean hasDominant = Character.isUpperCase(letter1) || Character.isUpperCase(letter2);
                    
                    if (hasDominant) {
                        prediction = domLabel;
                        domCount++;
                    } else {
                        prediction = recLabel;
                        recCount++;
                    }
                    
                    sb.append("Combo: ").append(babyGene).append("  ->  ").append(prediction).append("\n");
                }
            }

            // FINAL STATS
            sb.append("-------------------------\n");
            sb.append(domLabel).append(": ").append(domCount * 25).append("%\n");
            sb.append(recLabel).append(": ").append(recCount * 25).append("%\n");

            // Display in Text Area
            txtResult.setText(sb.toString());

        } catch (Exception ex) {
            txtResult.setText("Error: Please check your inputs.");
        }
    }

    public static void main(String[] args) {
        new PunnettSystem(); // Start the App
    }
}
