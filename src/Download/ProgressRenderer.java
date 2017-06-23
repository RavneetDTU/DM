package Download;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Created by ravneet on 23/6/17.
 */
public class ProgressRenderer extends JProgressBar implements TableCellRenderer {

    public ProgressRenderer(int max, int min){
        super(max,min);

    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setValue((int)((Float)value).floatValue());
        return this;
    }
}

