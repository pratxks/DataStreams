
import javax.swing.JFrame;


public class DataStreamProcessingRunner 
{
    public static void main(String[] args) 
    {
        DataStreamProcessingFrame MyStreamProcessingFrame = new DataStreamProcessingFrame();

        MyStreamProcessingFrame.SetDataStreamPrecessingFrameDisplay();

        MyStreamProcessingFrame.setSize(1000, 600);

        MyStreamProcessingFrame.setResizable(false);

        MyStreamProcessingFrame.setLocationRelativeTo(null);
        MyStreamProcessingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MyStreamProcessingFrame.setVisible(true);   
    }
}
