package Download;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by ravneet on 25/6/17.
 */
public class DownloadsTableModel extends AbstractTableModel implements Observer {

    private static final String[] ColumnName = {"URL","Size","Progress","Status"};
    private static final Class[] Columnclass = {String.class,String.class, JProgressBar.class,String.class};
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

    public String getColumnName(int col){
        return ColumnName[col];
    }



    @Override
    public int getRowCount() {
        return downloadlist.size();
    }

    @Override
    public int getColumnCount() {
        return ColumnName.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        Download download = downloadlist.get(row);
        switch (col){
            case 0:
                return download.getUrl();
            case 1:
                int size = download.getSize();
                return (size==-1)?"":Integer.toString(size);
            case 2:
                return new Float(download.getProgress());
            case 3:
                return Download.STATUSES[download.getStatus()];
                        }
        return "";
    }

    @Override
    public void update(Observable o, Object arg) {

        int index = downloadlist.indexOf(o);
        fireTableRowsUpdated(index,index);

    }
}
