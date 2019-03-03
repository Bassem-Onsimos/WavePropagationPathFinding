
package Game;

import Algorithm.WavePropagation;
import Model.Grid;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

public class Controller {
    
    private Game game;
    //
    private Grid grid;
    private final int rows = 15, columns = 15;
    private final int cellSize = 40;
    
    private Point start, end;
    private boolean movingStart, movingEnd;
    
    private WavePropagation wavePropagation;

    public Controller(Game game) {
        this.game = game;
    }
    
    public void initiate() {        
        grid = new Grid(this);  
        
        start = new Point(1, 1);
        end = new Point(columns-2, rows-2);
        
        wavePropagation = new WavePropagation(game);  
    }
    
    public void update() {
        if(game.getInput().isButtonUp(MouseEvent.BUTTON3)) {
            grid.getCell(game.getInput().getMouseX(), game.getInput().getMouseY()).toggleWall();
            wavePropagation.propagateWave();
            wavePropagation.createPath();
            wavePropagation.resetDisplayedWave();
        }
        
        if(game.getInput().isButtonDown(MouseEvent.BUTTON1)) {
            if(getCoordinates(game.getInput().getMouseX(), game.getInput().getMouseY()).equals(start)) movingStart = true;
            else if(getCoordinates(game.getInput().getMouseX(), game.getInput().getMouseY()).equals(end)) movingEnd = true;
        }
        
        if(game.getInput().isButton(MouseEvent.BUTTON1)) {
            if(movingStart) { 
                setStart(getCoordinates(game.getInput().getMouseX(), game.getInput().getMouseY()));
                wavePropagation.createPath();
            }
            if(movingEnd) { 
                if(setEnd(getCoordinates(game.getInput().getMouseX(), game.getInput().getMouseY()))) {
                    wavePropagation.propagateWave();
                    wavePropagation.createPath();
                    wavePropagation.resetDisplayedWave();
                }
            }
        }
        
        if(game.getInput().isButtonUp(MouseEvent.BUTTON1)) movingStart = movingEnd = false;
        
        wavePropagation.update();
    }
    
    public void render(Graphics2D g) {
        grid.render(g);
        wavePropagation.render(g);
    }
    
    public Point getCoordinates(double x, double y) {
        return new Point((int)(x/cellSize), (int)(y/cellSize));
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setStart(Point start) {
        if(grid.isValid(start.y, start.x))
            this.start = start;
    }
    
    public Point getStart() {
        return start;
    }

    public boolean setEnd(Point end) {
        if(grid.isValid(end.y, end.x)) {
            if(!grid.get(end.y, end.x).isWall()){
                this.end = end;
                return true;
            }
        }
        return false;
    }
    
    public Point getEnd() {
        return end;
    }

    public Grid getGrid() {
        return grid;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getDisplayedWave() {
        return wavePropagation.getDisplayedWave();
    }

    
}
