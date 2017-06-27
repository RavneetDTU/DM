package Download;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
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
        addPanel.add(addTextField);
        JButton addButton = new JButton("ADD Download");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionadd();
            }
        });
        addPanel.add(addButton);

        tableModel = new DownloadsTableModel();
        table = new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                tableSelectionChanged();
            }
        });

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ProgressRenderer renderer = new ProgressRenderer(0,100);
        renderer.setStringPainted(true);
        table.setDefaultRenderer(JProgressBar.class,renderer);
        table.setRowHeight((int)renderer.getPreferredSize().getHeight());

        JPanel downloadPanel = new JPanel();
        downloadPanel.setBorder(BorderFactory.createTitledBorder("Download"));
        downloadPanel.setLayout(new BorderLayout());
        downloadPanel.add(new JScrollPane(table),BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionPause();
            }
        });
        pauseButton.setEnabled(false);
        buttonsPanel.add(pauseButton);

        resumeButton = new JButton("Resume");
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionResume();
            }
        });
        resumeButton.setEnabled(false);
        buttonsPanel.add(resumeButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionCancel();
            }
        });
        cancelButton.setEnabled(false);
        buttonsPanel.add(cancelButton);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionClear();
            }
        });
        clearButton.setEnabled(false);
        buttonsPanel.add(clearButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(addPanel,BorderLayout.NORTH);
        getContentPane().add(downloadPanel,BorderLayout.CENTER);
        getContentPane().add(buttonsPanel,BorderLayout.SOUTH);

    }

    private void actionExit(){
        System.exit(0);
    }
    private void actionadd(){

        URL verifiedURL = verifyURL(addTextField.getText());
        if(verifiedURL != null){
            tableModel.addDownload(new Download(verifiedURL));
            addTextField.setText("");
        }else {
            JOptionPane.showMessageDialog(this,"Invalid Download URL","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private URL verifyURL(String Url){
        if(!Url.toLowerCase().startsWith("http://"))
            return null;
        URL verifiedURL = null;
        try{
            verifiedURL = new URL(Url);
        }catch (Exception e){
            return null;
        }

        if(verifiedURL.getFile().length()<2)
            return null;

        return verifiedURL;
    }

    private void tableSelectionChanged(){
        if(selectedDownload != null){
            selectedDownload.deleteObserver(DownloadManager.this);
        }
        if(!clearing&&table.getSelectedRow()>-1){
            selectedDownload = tableModel.getDownload(table.getSelectedRow());
            updateButton();
        }
    }
    private void actionPause(){
        selectedDownload.pause();
        updateButton();
    }
    private void actionResume(){
        selectedDownload.resume();
        updateButton();
    }
    private void actionCancel(){
        selectedDownload.cancel();
        updateButton();
    }
    private void actionClear(){

        clearing = true;
        tableModel.clearDownlod(table.getSelectedRow());
        clearing = false;
        selectedDownload = null;
        updateButton();

    }

    private void updateButton(){

        if(selectedDownload != null){
            int status = selectedDownload.getStatus();

            switch (status){

                case Download.DOWNLOADING:
                    pauseButton.setEnabled(true);
                    resumeButton.setEnabled(false);
                    cancelButton.setEnabled(true);
                    clearButton.setEnabled(false);
                    break;

                case Download.PAUSED:
                    pauseButton.setEnabled(false);
                    resumeButton.setEnabled(true);
                    cancelButton.setEnabled(true);
                    clearButton.setEnabled(true);
                    break;

                case Download.ERROR:
                    pauseButton.setEnabled(false);
                    cancelButton.setEnabled(true);
                    resumeButton.setEnabled(true);
                    clearButton.setEnabled(true);
                    break;

                default:  // for Completed or Cancelled
                    pauseButton.setEnabled(false);
                    clearButton.setEnabled(true);
                    resumeButton.setEnabled(false);
                    cancelButton.setEnabled(false);
            }
        }else {  // When No Downloading is Selected

            pauseButton.setEnabled(false);
            resumeButton.setEnabled(false);
            cancelButton.setEnabled(false);
            cancelButton.setEnabled(false);

        }
    }


    @Override
    public void update(Observable o, Object arg) {

        if (selectedDownload != null && selectedDownload.equals(o)){
            updateButton();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DownloadManager manager = new DownloadManager();
                manager.setVisible(true);
            }
        });
    }
}
