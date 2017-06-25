package Download;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by ravneet on 25/6/17.
 */
public class DownloadsTableModel extends AbstractTableModel implements Observer {

    private static final String[] coloumnName = {"URL","Size","Progress","Status"};
    private static final Class[] coloumnclass = {String.class,String.class, JProgressBar.class,String.class};
    private ArrayList<Download> downloadlist = new ArrayList<Download>();

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
