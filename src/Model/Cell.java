
package Model;

import Game.Controller;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Cell {

    private int row, column;
    private int cellSize;
    private boolean wall;
    //
    private int distance;
    //
    private Controller controller;
    
    public Cell(int row, int column, Controller controller) {
        this.row = row;
        this.column = column;
        this.cellSize = controller.getCellSize();
        this.controller = controller;
        
        wall = false;
        distance = 0;
    }
    
    public void render(Graphics2D g) {     
        if(row == controller.getStart().getY() && column == controller.getStart().getX()) g.setColor(new Color(0, 100, 0));
        else if(row == controller.getEnd().getY() && column == controller.getEnd().getX()) g.setColor(Color.blue);
        else if(wall) g.setColor(Color.gray);
        else if (distance == controller.getDisplayedWave()) g.setColor(Color.pink);
        else g.setColor(Color.red);
        
        g.fillRect(column * cellSize, row * cellSize, cellSize, cellSize);
             
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(3));
        g.drawRect(column * cellSize, row * cellSize, cellSize, cellSize);
        
        g.drawString(Integer.toString(distance), column * cellSize + 5, row * cellSize + 15);
        
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isWall() {
        return wall;
    }

    public void setWall(boolean wall) {
        this.wall = wall;
        if(wall==true) distance = -1;
        else distance = 0;
    }
    
    public void toggleWall() {
        setWall(!wall);
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
    
    
    
}
