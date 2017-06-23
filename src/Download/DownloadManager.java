package Download;

import javax.swing.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by ravneet on 23/6/17.
 */
public class DownloadManager extends JFrame implements Observer {

    private  JTextField addTextField;
    private DownloadsTableModel tableModel;
    private JTable table;
    private JButton pauseButton,resumeButton,cancelButton,clearButton;
    private Download selectedDownload;
    private boolean clearing;

    public DownloadManager(){

        setTitle("Download Manager");
        setSize(800,750);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                actionExit();
            }
        });

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        JMenuItem fileExitMenuItem = new JMenuItem("Exit",KeyEvent.VK_X);
        fileExitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionExit();
            }
        });
        fileMenu.add(fileExitMenuItem);
        menuBar.add(fileMenu);

        JPanel addPanel = new JPanel();
        addTextField = new JTextField(30);

    }


    @Override
    public void update(Observable o, Object arg) {

    }
}
