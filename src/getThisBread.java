import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.Scanner;
import java.util.Stack;
import java.awt.event.*;
import javax.swing.text.*;

//TODO: change the class name

public class getThisBread extends JFrame implements ActionListener, ChangeListener {   //undoable edit listener?
    //should override actionPerformed from action listener

    private JFrame frame;
    private JTextArea textarea;
    private Stack<Integer> undoing = new Stack<Integer>();
    private JSlider slide;
    //private Document txtareaDoc;

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


        textarea = new JTextArea("");
        textarea.setLineWrap(true);
        textarea.setWrapStyleWord(true);
        Font def = new Font("Calibri Body", Font.PLAIN,14);
        textarea.setFont(def);

        //txtareaDoc = textarea.getDocument(); //undo redo stuff


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

        //undo/re-do
        JMenu menu2 = new JMenu("Undo/Redo");
        JMenuItem ur1 = new JMenuItem("Undo");        //not functional yet, FINISH
        ur1.addActionListener(this);
        menu2.add(ur1);
        JMenuItem ur2 = new JMenuItem("Redo");
        ur2.addActionListener(this);
        menu2.add(ur2);

        //FONT SIZE SLIDER WEWEEEEHEE
        slide = new JSlider(0,75,14);
        slide.addChangeListener(this);
        JMenu slideMenu = new JMenu("Font Size");
        slideMenu.add(slide);





        //delete/copy/paste
        JPopupMenu menu3 = new JPopupMenu("Edit:");
        PopUpEdit(menu3);
        PopMenuRCListener mlisten = new PopMenuRCListener();  //separate class since must inherit from another class
        mlisten.popop = menu3;
        textarea.addMouseListener(mlisten);

        frame.setJMenuBar(menubar);
        menubar.add(menu1);
        menubar.add(menu2);
        menubar.add(slideMenu);
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
            case "Undo": undoLast();  //TODO
                break;
            case "Redo": redoLast();  //TODO
                break;
            case "Copy": copySel();
                break;
            case "Paste": pasteFrom();
                break;
            case "Delete": deleteAndCopy();
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

    public void copySel() {
        textarea.copy();
    }

    public void pasteFrom() {
        textarea.paste();
    }
    public void deleteAndCopy() {
        textarea.cut();
    }
    public static void main(String[] args)  {
        getThisBread g = new getThisBread();

    }

    public static void undoLast() {
        //TODO: finish
    }

    public static void redoLast() {
        //TODO: finish
    }


    public void PopUpEdit(JPopupMenu popop) {

        JMenuItem e1 = new JMenuItem("Copy");
        e1.addActionListener(this);  //listening to the current object, use this
        popop.add(e1);
        JMenuItem e2 = new JMenuItem("Paste");
        e2.addActionListener(this);
        popop.add(e2);
        JMenuItem e3 = new JMenuItem("Delete");
        e3.addActionListener(this);
        popop.add(e3);
        popop.setBorderPainted(true);

    }

    public void stateChanged(ChangeEvent e)  //slider listener
    {
       // l.setText("value of Slider is =" + b.getValue());
        //System.out.println("hey king");  //okay it quite literally works omg
        int a  = slide.getValue();
        Font f = new Font("Calibri Body",Font.PLAIN,a);
        textarea.setFont(f);
        
    }



	/*TODO: word wrap within a paper-like space instead of entire window, vertical scrolling, fonts,
	text sizes, highlighting?*/

    //this edit added: set default font to Calibri body, working font size slider, not sure what
    //else since I haven't touched this in a while

}
