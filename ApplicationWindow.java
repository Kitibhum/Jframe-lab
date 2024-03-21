import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ApplicationWindow implements ActionListener{
    private JFrame topCon;
    private ColorStrategy colorStrategy = new DefaultColorStrategy();

    public ApplicationWindow(int w, int h){
            topCon = new JFrame("Composite in Action");
            topCon.setSize(w,h);
            topCon.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JMenuBar menuBar = new JMenuBar();
            topCon.setJMenuBar(menuBar);
            JMenu colorMenu = new JMenu("Color Options");
            menuBar.add(colorMenu);
            JMenuItem defaultColors = new JMenuItem("Default");
            defaultColors.addActionListener(this);
            JMenuItem randomColors = new JMenuItem("Random");
            randomColors.addActionListener(this);
            colorMenu.add(defaultColors);
            colorMenu.add(randomColors);

            topCon.add(new MainCanvas(w,h));
            setColorsForGridCanvases();
            topCon.pack();
            topCon.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();
        topCon.setVisible(false);

        //Handle the "Color Strategy"
        if(command.equals("Default")){
            colorStrategy = new DefaultColorStrategy();
        }
        else if(command.equals("Random")){
            colorStrategy = new RandomColorStrategy();
        }

        setColorsForGridCanvases();
        
        topCon.setVisible(true);
    }

    private void setColorsForGridCanvases() {
        Component[] components = topCon.getContentPane().getComponents();
        for (Component comp : components) {
            if (comp instanceof MainCanvas) {
                MainCanvas mainCanvas = (MainCanvas) comp;
                Component[] gridCanvases = mainCanvas.getComponents();
                for (Component gridCanvas : gridCanvases) {
                    if (gridCanvas instanceof GridCanvas) {
                        GridCanvas gc = (GridCanvas) gridCanvas;
                        gc.setColors(colorStrategy);
                    }
                }
            }
        }
    }

    public ApplicationWindow() {
        this(600,600);
    }
}