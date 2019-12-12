/*The GUI that will be displayed to the user everytime main hits
the end of the requested time steps*/
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
public class HonorsGUI extends JFrame
{   
   public static Random rand = new Random();
   private final int WIDTH = 850;
   private final int HEIGHT = 700;
   private int gridLength;
   private int gridWidth;
   private int numOfTimeSteps;
   private static int newNumOfTimeSteps = 1;
   private boolean end = false;
   
   private JPanel gridPanel;//  = new JPanel();
   private JPanel buttonPanel; //= new JPanel();
   private JButton doTimeStepsButton;// = new JButton("Advance Time Step");
   private JButton numTimeSteps;//  = new JButton("N Time Steps");
   private JLabel numOfOrgs;
   
   public HonorsGUI(int length, int width)// int timeSteps, Organism[][] world, int ants, int doodleBugs)
   {
//       setTitle("State of the World: Step " + timeSteps);
      setSize(WIDTH, HEIGHT);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLayout(new BorderLayout());
      
      buttonPanel = new JPanel();
      gridPanel = new JPanel();
      doTimeStepsButton = new JButton("Advance Time Step");
      numOfOrgs = new JLabel();
      numTimeSteps = new JButton("N Time Steps");
      numTimeSteps.addActionListener(new NumTimeStepsListener());
      doTimeStepsButton.addActionListener(new DoTimeStepsButtonListener());
      
      buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 7));
      buttonPanel.add(numOfOrgs);
      buttonPanel.add(doTimeStepsButton);
      buttonPanel.add(numTimeSteps);
      gridPanel.setLayout(new GridLayout(width, length));
      
      //adding the labels/panels to the layout(like putting a puzzle piece in)
      add(gridPanel, BorderLayout.CENTER);
      add(buttonPanel, BorderLayout.SOUTH);
      setVisible(true);
   }
   
   public String getOrgs(Organism org)
   {
      String o = "";
      if (org instanceof Ant)
         o = "a";
      else if (org instanceof DoodleBug)
         o = "D";
      return o;
   }
   
   public int getTimeSteps()
   {
      //setVisible(false);
      return newNumOfTimeSteps;
   }
   
   public boolean getOver()
   {
      return end;
   }
   
   public void display(Organism[][] world, int timeSteps)
   {
      setTitle("State of the World: Step " + timeSteps);
      setVisible(true);
   }
   
   public void update(int timeSteps, Organism[][] world, int ants, int doodleBugs)
   {
      end = false;
      remove(gridPanel);
//       setVisible(false);
      
      setTitle("State of the World: Step " + timeSteps);
      numOfOrgs.setText("Ants: " + ants + " Dbg: " + doodleBugs);
      JPanel newGridPanel = new JPanel();
      newGridPanel.setLayout(new GridLayout(20, 20));
      String lab;
      for (int i = 0; i < MasterOrg.width; i++)
      {
         for (int j = 0; j < MasterOrg.length; j++)
         {
            if(world[i][j] != null)
               lab = String.valueOf(world[i][j].getId());
            else
               lab = "";
            JLabel label = new JLabel(lab, JLabel.CENTER);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            
            if (world[i][j] != null)
               label.setBackground(world[i][j].getColor());
                           
            label.setForeground(Color.WHITE);
            newGridPanel.add(label);
            label.setOpaque(true);
         } 
      }
      gridPanel = newGridPanel;
      add(gridPanel, BorderLayout.CENTER);
      
      setVisible(true);
   }
   private class DoTimeStepsButtonListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         //newNumOfTimeSteps = 1;
         end = true;
      }
   }
   
   private class NumTimeStepsListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         try
         {
            newNumOfTimeSteps = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of \"time-steps\" you'd like."));
            while (newNumOfTimeSteps <= 0)
               newNumOfTimeSteps = Integer.parseInt(JOptionPane.showInputDialog("Enter A number greater than 0."));;
         }
         catch(java.lang.ClassCastException ex)
         {
            return;
         }
         catch(java.lang.NumberFormatException ex)
         {
            return;
         }
         end = true;
      }
   }
}