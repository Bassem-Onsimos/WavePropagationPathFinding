
package Model;

import Game.Controller;
import java.awt.Graphics2D;
import java.awt.Point;

public class Grid {
    
    private int rows, columns;
    private int cellSize;
    private Cell[][] cells;
    //
    private Controller controller;
    
    public Grid(Controller controller) {
        this.rows = controller.getRows();
        this.columns = controller.getColumns();
        this.cellSize = controller.getCellSize();
        this.controller = controller;
        
        cells = new Cell[rows][columns];
        
        for(int row=0; row<rows; row++)
            for(int column=0; column<columns; column++) {
                cells[row][column] = new Cell(row, column, controller);
                if(row==0 || column==0 || row==rows-1 || column==columns-1) cells[row][column].setWall(true);              
            }       
    }
    
    public void render(Graphics2D g) {
        for(int row=0; row<rows; row++)
            for(int column=0; column<columns; column++)
                cells[row][column].render(g);
    }
    
    public Cell getCell(double x, double y) {
        return cells[(int)(y/cellSize)][(int)(x/cellSize)];
    }
    
    public Cell get(int row, int column) {
        return cells[row][column];
    }
    
    public boolean isValid(int row, int column) {
        return (row >= 0) && (row < rows) && (column >= 0) && (column < columns);
    }
    
    public void resetDistances() {
        for(int row=0; row<rows; row++)
            for(int column=0; column<columns; column++)
                if(!cells[row][column].isWall()) cells[row][column].setDistance(0);
    }
    
}
