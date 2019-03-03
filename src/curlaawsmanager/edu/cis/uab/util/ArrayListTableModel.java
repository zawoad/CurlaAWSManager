

package curlaawsmanager.edu.cis.uab.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.table.AbstractTableModel;


public class ArrayListTableModel extends AbstractTableModel {


  private ArrayList data; // Holds the table data
  private String[] columnNames; // Holds the column names.
  private HashSet editablecellSet = new HashSet();

  /**
   * Constructor: Initializes the table structure, including number of columns
   * and column headings. Also initializes table data with default values.
   *
   * @param columnscolumns[] array of column titles.
   * @param defaultvdefaultv array of default value objects, for each column.
   * @param rowsrows number of rows initially.
   */

  public ArrayListTableModel(String[] columns, Object[] defaultv, int rows) {

    // Initialize number of columns and column headings
    columnNames = new String[ columns.length ];

    for(int i = 0; i < columns.length; i++) {
      columnNames [ i ] = new String(columns [ i ]);
    }

    // Instantiate Data ArrayList, and fill it up with default values
    data = new ArrayList();


    for(int i = 0; i < rows; i++) {

      ArrayList cols = new ArrayList();

      for(int j = 0; j < columns.length; j++) {
        cols.add(defaultv [ j ]);
      }

      data.add(cols);
    }
  }



  public ArrayListTableModel(String[] columns, ArrayList data)
  {

      columnNames = columns;

      this.data = data;
  }

  public ArrayListTableModel(String[] columns, ArrayList data, HashSet editebleCells)
  {

      columnNames = columns;

      this.data = data;
      this.editablecellSet = editebleCells;
  }



  /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    @Override
    public boolean isCellEditable(int row, int col)
    {
        if (this.editablecellSet.contains(col))
        {
            return true;
        }
        else
        {
            return false;
        }
    }


  /**

   * Overrides AbstractTableModel method. Returns the number of columns in
   * table
   *
   * @return <b>int</b> number of columns in the table.
   */
  public int getColumnCount() {

    return columnNames.length;
  }
  /**
   * Overrides AbstractTableModel method. Returns the number of rows in table
   *
   * @return <b>int</b> number of rows in the table.
   */

  public int getRowCount() {

    return data==null?0:data.size();
  }
  /**
   * Overrides AbstractTableModel method.
   *
   * @param colcolumn number column number
   *
   * @return <b>String </b> column name.
   */
  public String getColumnName(int col) {

    return columnNames [ col ];
  }

  /**
   * Overrides AbstractTableModel method.
   *
   * @param rowrows row number
   * @param colrows column number
   *
   * @return <b>Object</b> the value at the specified cell.
   */
  public Object getValueAt(int row, int col) {

    if(data==null)
        return null;
    ArrayList colArrayList = (ArrayList) data.get(row);

    return colArrayList.get(col);
  }
  /**
   * Overrides AbstractTableModel method. Returns the class for the specified

   * column.
   *
   * @param colcol column number
   *
   * @return <b> Class </b> the class for the specified column.
   */
  public Class getColumnClass(int col) {

    // If value at given cell is null, return default class-String
    return getValueAt(0, col) == null ? String.class
                                      : getValueAt(0, col).getClass();
  }
  /**
   * Overrides AbstractTableModel method. Sets the value at the specified cell
   * to object.
   *
   * @param objObject

   * @param rowrow row number
   * @param colcolumn column number
   */
  public void setValueAt(Object obj, int row, int col) {

    ArrayList colArrayList = (ArrayList) data.get(row);
    colArrayList.set(col, obj);
    fireTableCellUpdated(row, col);
  }
  /**
   * Adds a new row to the table.
   *
   * @param newrowArrayList new row data
   */
  public void insertRow(ArrayList newrow) {
    data.add(newrow);
    super.fireTableDataChanged();
  }
  /**

   * Deletes the specified row from the table.
   *
   * @param rowrow row number
   */
  public void deleteRow(int row) {
    data.remove(row);
    super.fireTableDataChanged();
  }
  /**
   * Delete all the rows existing after the selected row from the JTable
   *
   * @param rowrow row number
   */
  public void deleteAfterSelectedRow(int row) {

    // Get the initial size of the table before the deletion has started.
    int size = this.getRowCount();

    // The number of items to be deleted is got by subtracting the

    // selected row + 1 from size. This is done because as each row is deleted
    // the rows next to it are moved up by one space and the number of rows
    // in the JTable decreases. So the technique used here is always deleting
    // the row next to the selected row from the table n times so that all the
    // rows after the selected row are deleted.
    int n = size - (row + 1);

    for(int i = 1; i <= n; i++) {
      data.remove(row + 1);
    }

    super.fireTableDataChanged();
  }
  /**
   * Returns the values at the specified row as a ArrayList.
   *
   * @param rowrow row number
   *
   * @return DOCUMENT ME!
   */

  public ArrayList getRow(int row) {

    return (ArrayList) data.get(row);
  }
  /**
   * Updates the specified row. It replaces the row ArrayList at the specified
   * row with the new ArrayList.
   *
   * @param updatedRowArrayList row data
   * @param rowrow row number
   */
  public void updateRow(ArrayList updatedRow, int row) {
    data.set(row, updatedRow);
    super.fireTableDataChanged();
  }
  /**
   * Clears the table data.
   */
  public void clearTable() {
    data = new ArrayList();
    super.fireTableDataChanged();

  }


  public void build(String[] columnNames, ArrayList data)
    {
        this.columnNames = columnNames;
        this.data = data;
        fireTableStructureChanged();
    }
    public void build(String[] columns, List<Object[]> data)
    {
        this.data = new ArrayList();
        this.columnNames = columns;
        for (Object[] rowData : data)
        {
            ArrayList row = new ArrayList();

            for (int j = 0; j < (columns.length+1); j++)
            {
                row.add(rowData[j]);
            }

            this.data.add(row);
        }
        fireTableStructureChanged();
    }
  public Object getPrimaryKey(int sel)
    {
        if (columnNames == null)
        {
            return 0;
        }
        //    return data[sel][getColumnCount()];
        return ((ArrayList) data.get(sel)).get(getColumnCount());
    }
}
