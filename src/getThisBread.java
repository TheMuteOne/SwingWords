import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
//import javax.swing.plaf.windows.*;
import java.io.*;
import java.util.Scanner;
import java.awt.event.*;
import javax.swing.text.*;

//TODO: change the class name

public class getThisBread extends JFrame implements ActionListener{
    //need constructor to instantiate GUI, need bare bones menu w/ maybe save, open file, new/clear
    //should override actionPerformed from action listener

    private JFrame frame;
    private JTextArea textarea;

    public getThisBread() {

        frame = new JFrame("editStuff");
        frame.setSize(400,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ends program with exit button
        //see if there's anything to set the graphics of the frame (now below)
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {  //all four auto-generated, maybe just use exception
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        textarea = new JTextArea("Edit This!");

        //make a menu with basic options as a start
        JMenuBar menubar = new JMenuBar();
        JMenu menu1 = new JMenu("File Options");

        //make and add menu items like new file, open file, save file, give them action listeners
        JMenuItem f1 = new JMenuItem("New File");
        f1.addActionListener(this);  //listening to the current object, use this
        menu1.add(f1);
        JMenuItem f2 = new JMenuItem("Open File");
        f2.addActionListener(this);
        menu1.add(f2);
        JMenuItem f3 = new JMenuItem("Save File");
        f3.addActionListener(this);
        menu1.add(f3);
        //TODO: maybe add print to this if I can figure out how to connect that
        //TODO: add other menus?

        frame.setJMenuBar(menubar);
        menubar.add(menu1);
        menubar.setVisible(true);
        frame.add(textarea);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent event) {  //have to override java's awt actionPerformed
        String action_name = event.getActionCommand();
        //to differentiate between actions

        switch (action_name) {
            case "Open File":  openAFile();
                break;
            case "New File":  newFile();
                break;
            case "Save File":  saveYourFile();
                break;
        }
    }

    private void openAFile() {
        //i think the fact that there's a JFileChooser is incredibly neat
        JFileChooser choose = new JFileChooser();

        //only let them choose .txt files
        choose.setAcceptAllFileFilterUsed(false);
        choose.setDialogTitle("Select a .txt file");
        FileNameExtensionFilter restrictThis = new FileNameExtensionFilter("Only .txt files", "txt");
        choose.addChoosableFileFilter(restrictThis);
        //if the user clicks on a file:
        if (choose.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { //looks odd but was on the swing documentation
            String path = choose.getSelectedFile().getAbsolutePath(); //get path
            File fselected = new File(path);                              //use path
            try {
                //
                FileInputStream f = new FileInputStream(fselected);
                Scanner scanner = new Scanner(f);
                StringBuilder sb = new StringBuilder();
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    (sb.append(line)).append("\n");

                }
                textarea.setText(sb.toString());


            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, e.getMessage());  //display message in frame instead of in our IDEs
            }
        } else {  //the user exits the chooser (lol rhymes)
            JOptionPane.showMessageDialog(frame, "No file chosen");    //display like above
        }

    }

    public void newFile() {
        //TODO: make a pop-up that asks if you'd like to save this one first

        textarea.setText(""); //essentially clears it and gives a blank slate
    }

    public void saveYourFile() {  //very similar to openAFile
        //File choosers are a life saver
        JFileChooser choose = new JFileChooser();
        //TODO: save as only .txt

        if (choose.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String path = choose.getSelectedFile().getAbsolutePath(); //get path
            File fselected = new File(path);                          //use path
            try {
                FileWriter fwrite = new FileWriter(fselected);
                BufferedWriter bwrite = new BufferedWriter(fwrite);
                //actually save
                bwrite.write(textarea.getText());
                bwrite.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, e.getMessage()); //show in frame!!
            }
        } else {
            JOptionPane.showMessageDialog(frame,"You chose not to save this file."); //TODO: be less passive aggressive
        }
    }

    public static void main(String[] args)  {
        getThisBread g = new getThisBread();

    }
}

