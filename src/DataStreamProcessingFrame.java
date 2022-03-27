
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;


public class DataStreamProcessingFrame extends JFrame
{
    private JPanel mainPanel;
    private JPanel displayPanel;
    private JPanel searchPanel;
    private JPanel controlPanel;
    
    private JTextArea originalFileDisplayArea;
    private JTextArea filteredFileDisplayArea;
    private JScrollPane originalFileDisplayScrollPane;
    private JScrollPane filteredFileDisplayScrollPane;
    
    private JTextField searchTextField;
    
    private JButton loadFileButton;
    private JButton searchFileButton;
    private JButton quitButton;
    
    private ArrayList<String> originalFileContents;
    private ArrayList<String> filteredFileContents;
    
    public DataStreamProcessingFrame()
    {
        setTitle("Data Stream Processing");
    }
    
    public void SetDataStreamPrecessingFrameDisplay()
    {
        mainPanel = new JPanel();
        
        mainPanel.setLayout(new BorderLayout());
        
        createFileDisplayAreaPanel();
        createSearchFilePanel();
        createControlPanel();
        
        mainPanel.add(displayPanel, BorderLayout.NORTH);
        mainPanel.add(searchPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void createFileDisplayAreaPanel()
    {
        displayPanel = new JPanel();
        
        displayPanel.setLayout(new BorderLayout());
        
        JPanel originalFileDisplayPanel = new JPanel();
        JPanel filteredFileDisplayPanel = new JPanel();
        
        Border blueLineBorder = BorderFactory.createLineBorder(Color.BLUE, 1);
        Border originalFilePanelBorder = BorderFactory.createTitledBorder(blueLineBorder, "Original File");
        Border filteredFilePanelBorder = BorderFactory.createTitledBorder(blueLineBorder, "Filtered File");
        
        originalFileDisplayPanel.setBorder(originalFilePanelBorder);
        filteredFileDisplayPanel.setBorder(filteredFilePanelBorder);
        
        Font displayPanelFont  = new Font(Font.MONOSPACED, Font.PLAIN, 14);
        
        originalFileDisplayArea = new JTextArea(27, 58);
        originalFileDisplayArea.setFont(displayPanelFont);
        originalFileDisplayArea.setEditable(false);
        originalFileDisplayScrollPane = new JScrollPane(originalFileDisplayArea);
        originalFileDisplayScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        originalFileDisplayScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        filteredFileDisplayArea = new JTextArea(27, 58);
        filteredFileDisplayArea.setFont(displayPanelFont);
        filteredFileDisplayArea.setEditable(false);
        filteredFileDisplayScrollPane = new JScrollPane(filteredFileDisplayArea);
        filteredFileDisplayScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        filteredFileDisplayScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        originalFileDisplayPanel.add(originalFileDisplayScrollPane);
        filteredFileDisplayPanel.add(filteredFileDisplayScrollPane);
        
        displayPanel.add(originalFileDisplayPanel, BorderLayout.WEST);
        displayPanel.add(filteredFileDisplayPanel, BorderLayout.EAST);
    }
    
    private void createSearchFilePanel()
    {
        searchPanel = new JPanel();
        
        searchPanel.setLayout(new BorderLayout());
        
        Border blueLineBorder = BorderFactory.createLineBorder(Color.BLUE, 1);
        
        searchPanel.setBorder(blueLineBorder);
        
        Font searchPanelFont  = new Font(Font.SERIF, Font.PLAIN, 22);
        
        JLabel searchTextLabel = new JLabel("Search String");
        searchTextLabel.setFont(searchPanelFont);
        
        searchTextField = new JTextField(50);
        searchTextField.setFont(searchPanelFont);
        
        searchPanel.add(searchTextLabel, BorderLayout.WEST);
        searchPanel.add(searchTextField, BorderLayout.EAST);
    }
    
    private void createControlPanel()
    {
        controlPanel = new JPanel();
        
        GridLayout controlGridLayout = new GridLayout(1, 3);
        controlGridLayout.setHgap(10);
        controlGridLayout.setVgap(10);

        controlPanel.setLayout(controlGridLayout);
        
        Font controlPanelFont  = new Font(Font.DIALOG,  Font.BOLD, 22);
        
        loadFileButton = new JButton("Load File");
        searchFileButton = new JButton("Search File");
        quitButton = new JButton("Quit");
        
        loadFileButton.setFont(controlPanelFont);
        searchFileButton.setFont(controlPanelFont);
        quitButton.setFont(controlPanelFont);
        
        loadFileButton.addActionListener(ae -> loadFileButtonClicked());
        searchFileButton.addActionListener(ae -> searchFileButtonClicked());
        quitButton.addActionListener(ae -> {
            System.exit(0);
        });
                
        controlPanel.add(loadFileButton);
        controlPanel.add(searchFileButton);
        controlPanel.add(quitButton);
    }
    
    public void loadFileButtonClicked()
    {
        LoadOriginalFile();
        
        if(originalFileContents.size() > 0)
        {
            if(!originalFileDisplayArea.getText().isEmpty())
            {
                originalFileDisplayArea.setText("");
            }
            for(String originalFileLine : originalFileContents)
            {
                originalFileDisplayArea.append(originalFileLine + "\n");
            }
        }
    }
    
    public void searchFileButtonClicked()
    {
        if(searchTextField.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Please input some string to search from selected file");
        }
        else
        {
            if(originalFileDisplayArea.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Please load some file for searching");
                return;
            }
            
            filteredFileContents = new ArrayList<>();
            
            String searchString = searchTextField.getText();
            
            //Stream Approach use of Filter Starts
            
            //Java Data Stream Processing use of filter for searching string in line array
            Predicate<String> searchStringInFile = new Predicate<String>() 
            {
                @Override
                public boolean test(String fileLine) 
                {
                    if (fileLine.contains(searchString)) 
                    {
                        return true;
                    }
                    return false;
                }
            };

            //filter lambda expression filter call to search string in string arry
            originalFileContents.stream().filter(searchStringInFile).forEach(filteredFileContents::add);
            
            //Stream Approach use of Filter Ends
            
            if(filteredFileContents.size() > 0)
            {
                if(!filteredFileDisplayArea.getText().isEmpty())
                {
                    filteredFileDisplayArea.setText("");
                }
                for(String filteredFileLine : filteredFileContents)
                {
                    filteredFileDisplayArea.append(filteredFileLine + "\n");
                }
            }
        }
    }
    
    private void LoadOriginalFile()
    {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        originalFileContents = new ArrayList<>();
        
        try
        {
            File workingDirectory = new File(System.getProperty("user.dir"));

            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
            chooser.setCurrentDirectory(workingDirectory);
            chooser.setAcceptAllFileFilterUsed(true);

            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                selectedFile = chooser.getSelectedFile();

                //Stream Approach use of Stream to Read Lines of File Starts
                
                try (Stream<String> streamOfLines = Files.lines(Paths.get(selectedFile.getPath()))) 
                {
                    streamOfLines.forEach(originalFileContents::add);
		        }
                catch (IOException e1) //catching IOException while reading file
                {
			        JOptionPane.showMessageDialog(null, "Something Went Wrong Reading File");
		        }
                catch (Exception e2) //catching general Exception for unexpected errors
                {
			        JOptionPane.showMessageDialog(null, "Error Occured");
		        }
                
                //Stream Approach use of Stream to Read Lines of File Ends
            }
        }
        catch (Exception e) //catching general Exception for error in Selecting File using JFileChooser
        {
            JOptionPane.showMessageDialog(null, "Something Went Wrong Selecting File for Reading");
        }
    }
}
