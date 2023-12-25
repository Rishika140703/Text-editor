import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TextEditor extends JFrame {
    private JTextPane textPane;
  

    public TextEditor() {
        setTitle("Text Editor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createMenuBar();
        createFormattingButtons();
        createFindReplaceFields();
        createsketchpad();
        createTextPane();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(createFindReplaceFields(), BorderLayout.NORTH);
        mainPanel.add(textPane, BorderLayout.CENTER); // Placed textPane below the formatting and sketchpad panel

        add(mainPanel, BorderLayout.CENTER);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem saveAsMenuItem = new JMenuItem("Save As");
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);

        JMenu editMenu = new JMenu("Edit");
        JMenuItem cutMenuItem = new JMenuItem(new DefaultEditorKit.CutAction());
        cutMenuItem.setText("Cut");
        JMenuItem copyMenuItem = new JMenuItem(new DefaultEditorKit.CopyAction());
        copyMenuItem.setText("Copy");
        JMenuItem pasteMenuItem = new JMenuItem(new DefaultEditorKit.PasteAction());
        pasteMenuItem.setText("Paste");
        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);

        JMenu reviewMenu = new JMenu("Review");
        JMenuItem actualSizeMenuItem = new JMenuItem("Actual Size");
        JMenuItem zoomInMenuItem = new JMenuItem("Zoom In");
        JMenuItem zoomOutMenuItem = new JMenuItem("Zoom Out");
        JMenuItem fullScreenMenuItem = new JMenuItem("Enter Full Screen");
        reviewMenu.add(actualSizeMenuItem);
        reviewMenu.add(zoomInMenuItem);
        reviewMenu.add(zoomOutMenuItem);
        reviewMenu.add(fullScreenMenuItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutMenuItem = new JMenuItem("About");
        helpMenu.add(aboutMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(reviewMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        actualSizeMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setActualSize();
            }
        });

        zoomInMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                zoomIn();
            }
        });

        zoomOutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                zoomOut();
            }
        });

        fullScreenMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enterFullScreen();
            }
        });
    }

    private void createTextPane() {
        textPane = new JTextPane();
        textPane.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(textPane);
        add(scrollPane, BorderLayout.SOUTH);
        setVisible(true);
        textPane.setBackground(Color.WHITE);
    }


    private void updateFont(String fontName, int fontSize) {
        MutableAttributeSet attrs = textPane.getInputAttributes();
        StyleConstants.setFontFamily(attrs, fontName);
        StyleConstants.setFontSize(attrs, fontSize);
        textPane.setCharacterAttributes(attrs, false);
    }

    private JPanel createFormattingButtons() {
        JPanel formattingPanel = new JPanel();

        JButton boldButton = new JButton("B");
        JButton italicButton = new JButton("I");
        JButton underlineButton = new JButton("U");
        JButton strikeButton = new JButton("S");

        formattingPanel.add(boldButton);
        formattingPanel.add(italicButton);
        formattingPanel.add(underlineButton);
        formattingPanel.add(strikeButton);

        add(formattingPanel, BorderLayout.NORTH);
        

        boldButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setBold();
            }
        });

        italicButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setItalic();
            }
        });

        underlineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setUnderline();
            }
        });

        strikeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setStrike();
            }
        });

        JButton leftAlignButton = new JButton(new ImageIcon("left align icon.png"));
        JButton rightAlignButton = new JButton(new ImageIcon("right align icon.png"));
        JButton centerAlignButton = new JButton(new ImageIcon("center-align-icon.png"));
        JButton justifyButton = new JButton(new ImageIcon("justify align icon.png"));

        leftAlignButton.setPreferredSize(new Dimension(40, 40));
        rightAlignButton.setPreferredSize(new Dimension(40, 40));
        centerAlignButton.setPreferredSize(new Dimension(40, 40));
        justifyButton.setPreferredSize(new Dimension(40, 40));
    
        formattingPanel.add(leftAlignButton);
        formattingPanel.add(rightAlignButton);
        formattingPanel.add(centerAlignButton);
        formattingPanel.add(justifyButton);
       
        leftAlignButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setAlignment(StyleConstants.ALIGN_LEFT);
            }
        });
        rightAlignButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setAlignment(StyleConstants.ALIGN_RIGHT);
            }
        });
    
        centerAlignButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setAlignment(StyleConstants.ALIGN_CENTER);
            }
        });
    
        justifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setAlignment(StyleConstants.ALIGN_JUSTIFIED);
            }
        });

        JComboBox<String> fontTypeComboBox = new JComboBox<>();
        JComboBox<Integer> fontSizeComboBox = new JComboBox<>();

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = ge.getAvailableFontFamilyNames();

        for (String fontName : fontNames) {
            fontTypeComboBox.addItem(fontName);
        }

        ArrayList<Integer> fontSizes = new ArrayList<>();
        for (int i = 8; i <= 72; i += 2) {
            fontSizes.add(i);
        }

        for (Integer fontSize : fontSizes) {
            fontSizeComboBox.addItem(fontSize);
        }

        fontTypeComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedFont = fontTypeComboBox.getSelectedItem().toString();
                updateFont(selectedFont, StyleConstants.getFontSize(new SimpleAttributeSet()));
            }
        });

        fontSizeComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedSize = (int) fontSizeComboBox.getSelectedItem();
                updateFont(StyleConstants.getFontFamily(new SimpleAttributeSet()), selectedSize);
            }
        });
        formattingPanel.add(new JLabel("Font:"));
        formattingPanel.add(fontTypeComboBox);
        formattingPanel.add(new JLabel("Size:"));
        formattingPanel.add(fontSizeComboBox);

        return formattingPanel;

       
    }
    

    private void setActualSize() {
        // Implement setting the text to actual size
    }

    private void zoomIn() {
        // Implement zooming in the text
    }

    private void zoomOut() {
        // Implement zooming out the text
    }

    private void enterFullScreen() {
        // Implement entering full screen
    }

    private void setBold() {
        MutableAttributeSet attrs = textPane.getInputAttributes();
        StyleConstants.setBold(attrs, !StyleConstants.isBold(attrs));
        textPane.setCharacterAttributes(attrs, false);
    }

    private void setItalic() {
        MutableAttributeSet attrs = textPane.getInputAttributes();
        StyleConstants.setItalic(attrs, !StyleConstants.isItalic(attrs));
        textPane.setCharacterAttributes(attrs, false);
    }

    private void setUnderline() {
        MutableAttributeSet attrs = textPane.getInputAttributes();
        StyleConstants.setUnderline(attrs, !StyleConstants.isUnderline(attrs));
        textPane.setCharacterAttributes(attrs, false);
    }

    private void setStrike() {
        MutableAttributeSet attrs = textPane.getInputAttributes();
        StyleConstants.setStrikeThrough(attrs, !StyleConstants.isStrikeThrough(attrs));
        textPane.setCharacterAttributes(attrs, false);
    }

    private void setAlignment(int alignment) {
        MutableAttributeSet attrs = textPane.getInputAttributes();
        StyleConstants.setAlignment(attrs, alignment);
        textPane.setParagraphAttributes(attrs, false);
    }

    private void createsketchpad() {
        JPanel sketchpad = new JPanel();
        sketchpad.setLayout(new GridLayout(6, 6)); // Set the layout manager with 6 rows
    
        
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.add(new JLabel("Sketchpad"));
        sketchpad.add(labelPanel);
    
        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 6)); // Separate panel for buttons
        buttonPanel.add(new JButton("Rectangle"));
        buttonPanel.add(new JButton("Oval"));
        buttonPanel.add(new JButton("Line"));
        buttonPanel.add(new JButton("Pentagon"));
        buttonPanel.add(new JButton("Triangle"));
        buttonPanel.add(new JButton("Clear"));
        buttonPanel.setPreferredSize(new Dimension(500, 10));

        sketchpad.add(buttonPanel);
        sketchpad.setBackground(Color.DARK_GRAY);
    
        // Add the sketchpad panel to the frame
        add(sketchpad, BorderLayout.EAST);
    
        setVisible(true); // Make sure the panel is visible
    }
    
    private JPanel createFindReplaceFields() {
        JPanel findReplacePanel = new JPanel();
        findReplacePanel.setLayout(new BorderLayout());
    
        // Panel for Find and Replace text fields
        JPanel textFieldPanel = new JPanel();
        textFieldPanel.setLayout(new GridLayout(4, 2));
    
        // "Find" label and text field
        JLabel findLabel = new JLabel("Find:");
        JTextField findTextField = new JTextField(5);
    
        // "Replace" label and text field
        JLabel replaceLabel = new JLabel("Replace:");
        JTextField replaceTextField = new JTextField(5);
    
        // Add components to the text field panel
        textFieldPanel.add(findLabel);
        textFieldPanel.add(findTextField);
        textFieldPanel.add(replaceLabel);
        textFieldPanel.add(replaceTextField);
    
        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
    
        // Buttons for Find and Replace
        JButton findAllButton = new JButton("Find All");
        JButton findNextButton = new JButton("Find Next");
        JButton replaceButton = new JButton("Replace");
        JButton replaceAllButton = new JButton("Replace All");
    
        // Add buttons to the button panel
        buttonPanel.add(findAllButton);
        buttonPanel.add(findNextButton);
        buttonPanel.add(replaceButton);
        buttonPanel.add(replaceAllButton);
    
        // Add the text field panel and button panel to the find/replace panel
        findReplacePanel.add(textFieldPanel, BorderLayout.NORTH);
        findReplacePanel.add(buttonPanel, BorderLayout.CENTER);
    
        // Add the find/replace panel above the textPane
        // add(findReplacePanel, BorderLayout.CENTER);

        return findReplacePanel;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TextEditor editor = new TextEditor();
            editor.setVisible(true);
        });
    }
}
