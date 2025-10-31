import javax.swing.*; 
import java.awt.*;    
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RadioButtonDemo extends JFrame implements ActionListener { 

    private JRadioButton birdButton, catButton, dogButton, rabbitButton, pigButton; 
    private JButton submitButton; 
    private ButtonGroup petGroup;  

    public RadioButtonDemo() { 
        setTitle("Pet Selection"); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLayout(new FlowLayout()); 

        // Initialize radio buttons
        birdButton = new JRadioButton("Bird"); 
        catButton = new JRadioButton("Cat");   
        dogButton = new JRadioButton("Dog");   
        rabbitButton = new JRadioButton("Rabbit");
        pigButton = new JRadioButton("Pig");  

        // Group the radio buttons
        petGroup = new ButtonGroup(); 
        petGroup.add(birdButton);  
        petGroup.add(catButton);
        petGroup.add(dogButton);   
        petGroup.add(rabbitButton);
        petGroup.add(pigButton);  

        // Add action listeners to the radio buttons
        birdButton.addActionListener(this); 
        catButton.addActionListener(this);
        dogButton.addActionListener(this);
        rabbitButton.addActionListener(this);
        pigButton.addActionListener(this);  

        // Initialize submit button
        submitButton = new JButton("Submit"); 
        submitButton.addActionListener(this); 

        // Add components to the frame
        add(birdButton);
        add(catButton);   
        add(dogButton);   
        add(rabbitButton);
        add(pigButton);  
        add(submitButton); 

        // Set frame properties
        setSize(300, 200); 
        setVisible(true); 
    }
    
    public void actionPerformed(ActionEvent e) {  
        if (e.getSource() == submitButton) {
            String selectedPet = null; 
            if (birdButton.isSelected()) {
                selectedPet = "Bird";  
            } else if (catButton.isSelected()) {
                selectedPet = "Cat";   
            } else if (dogButton.isSelected()) { 
                selectedPet = "Dog";   
            } else if (rabbitButton.isSelected()) { 
                selectedPet = "Rabbit";
            } else if (pigButton.isSelected()) { 
                selectedPet = "Pig";  
            }

            if (selectedPet != null) { // Checks if a pet was actually selected.
                JOptionPane.showMessageDialog(this, "You selected: " + selectedPet, "Selection", JOptionPane.INFORMATION_MESSAGE); 
            } else {
                JOptionPane.showMessageDialog(this, "Please select a pet.", "Selection", JOptionPane.WARNING_MESSAGE); 
            }
        }
    }

    public static void main(String[] args) { 
        SwingUtilities.invokeLater(() -> new RadioButtonDemo()); 
    }
}
