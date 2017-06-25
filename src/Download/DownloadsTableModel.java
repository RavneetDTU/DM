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

    private static final String[] ColoumnName = {"URL","Size","Progress","Status"};
    private static final Class[] Coloumnclass = {String.class,String.class, JProgressBar.class,String.class};
    private ArrayList<Download> downloadlist = new ArrayList<Download>();

    public void addDownload(Download download){
        download.addObserver(this);
        downloadlist.add(download);
        fireTableRowsInserted(getRowCount()-1,getRowCount()-1);
    }

    public Download getDownload(int row){
        return downloadlist.get(row);
    }

    public void clearDownlod(int row){
        downloadlist.remove(row);
        fireTableRowsDeleted(row,row);
    }

    public int getColoumnCount(){
        return ColoumnName.length;
    }

    public String getColoumnName(int col){
        return ColoumnName[col];
    }

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
