
package Algorithm;

import Game.Controller;
import Game.Game;
import Model.Cell;
import Model.Grid;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class WavePropagation {
    
    private Game game;
    private Controller controller;
    
    private Grid grid;
    //
    private ArrayList<Cell> path; 
    private boolean pathCreated;
    //
    private boolean displayWaves = false;
    private int displayedWave = 1;
    private double displayedTime = 0;
    private int largestDistance = 1;
    //
    
    public WavePropagation(Game game) {
        this.game = game;
        this.controller = game.getController();
        this.grid = controller.getGrid();
        
        propagateWave();
        createPath();
        
    }
    
    public void update() {
        
        if(game.getInput().isKeyUp(KeyEvent.VK_SPACE)) {
            displayWaves = !displayWaves;
            resetDisplayedWave();
        }
        
        if(displayWaves) {
            displayedTime += game.getElapsedTime();

            if(displayedTime >= 0.4) {
                displayedTime = 0;
                displayedWave++;
                if(displayedWave > largestDistance) displayedWave = 1;
            }
        }
        
    }
    
    public void propagateWave() {
        
        grid.resetDistances();
    
        ArrayList<Cell> nodes = new ArrayList<>();
        
        nodes.add(grid.get(controller.getEnd().y, controller.getEnd().x));
        grid.get(controller.getEnd().y, controller.getEnd().x).setDistance(1);
        
        largestDistance = 1; //for displaying waves
        
        while(!nodes.isEmpty()) {
            
            ArrayList<Cell> newNodes = new ArrayList<>();
            
            for(Cell node : nodes) {
                
                int row = node.getRow();
                int column = node.getColumn();
                int distance = node.getDistance();
                
                if(grid.isValid(row + 1, column)) {
                    Cell cell = grid.get(row + 1, column);
                    if(cell.getDistance() == 0 && !newNodes.contains(cell)) {
                        newNodes.add(cell);
                        cell.setDistance(distance + 1);
                        
                        if(distance + 1 > largestDistance) largestDistance = distance + 1; // for displaying waves
                    }
                }
                
                if(grid.isValid(row + 1, column + 1)) {
                    Cell cell = grid.get(row + 1, column + 1);
                    if(cell.getDistance() == 0 && !newNodes.contains(cell)) {
                        newNodes.add(cell);
                        cell.setDistance(distance + 1);
                        
                        if(distance + 1 > largestDistance) largestDistance = distance + 1; // for displaying waves
                    }
                }
                
                if(grid.isValid(row - 1, column)) {
                    Cell cell = grid.get(row - 1, column);
                    if(cell.getDistance() == 0 && !newNodes.contains(cell)) {
                        newNodes.add(cell);
                        cell.setDistance(distance + 1);
                        
                        if(distance + 1 > largestDistance) largestDistance = distance + 1; // for displaying waves
                    }
                }
                
                if(grid.isValid(row - 1, column - 1)) {
                    Cell cell = grid.get(row - 1, column - 1);
                    if(cell.getDistance() == 0 && !newNodes.contains(cell)) {
                        newNodes.add(cell);
                        cell.setDistance(distance + 1);
                        
                        if(distance + 1 > largestDistance) largestDistance = distance + 1; // for displaying waves
                    }
                }
                
                if(grid.isValid(row, column + 1)) {
                    Cell cell = grid.get(row, column + 1);
                    if(cell.getDistance() == 0 && !newNodes.contains(cell)) {
                        newNodes.add(cell);
                        cell.setDistance(distance + 1);
                        
                        if(distance + 1 > largestDistance) largestDistance = distance + 1; // for displaying waves
                    }
                }
                
                if(grid.isValid(row - 1, column + 1)) {
                    Cell cell = grid.get(row - 1, column + 1);
                    if(cell.getDistance() == 0 && !newNodes.contains(cell)) {
                        newNodes.add(cell);
                        cell.setDistance(distance + 1);
                        
                        if(distance + 1 > largestDistance) largestDistance = distance + 1; // for displaying waves
                    }
                }
                
                if(grid.isValid(row, column - 1)) {
                    Cell cell = grid.get(row, column - 1);
                    if(cell.getDistance() == 0 && !newNodes.contains(cell)) {
                        newNodes.add(cell);
                        cell.setDistance(distance + 1);
                        
                        if(distance + 1 > largestDistance) largestDistance = distance + 1; // for displaying waves
                    }
                }
                
                if(grid.isValid(row + 1, column - 1)) {
                    Cell cell = grid.get(row + 1, column - 1);
                    if(cell.getDistance() == 0 && !newNodes.contains(cell)) {
                        newNodes.add(cell);
                        cell.setDistance(distance + 1);
                        
                        if(distance + 1 > largestDistance) largestDistance = distance + 1; // for displaying waves
                    }
                }
                
            }
            
            nodes.clear();
            nodes.addAll(newNodes);       
        }
        
    }
    
    public void createPath() {
        pathCreated = false;
        path = new ArrayList<>();
        boolean pathExists = true;
        int column = controller.getStart().x;
        int row = controller.getStart().y;
        
        if(grid.get(row, column).isWall()) pathExists = false;
        
        path.add(grid.get(controller.getStart().y, controller.getStart().x));
        
        while(!controller.getEnd().equals(new Point(column, row)) && pathExists) {
            
            Cell bestNeighbour = null;
            
            if(grid.isValid(row + 1, column)) {
                Cell cell = grid.get(row + 1, column);
                if(cell.getDistance() > 0) {
                    if(bestNeighbour == null) 
                        bestNeighbour = cell;
                    else if(cell.getDistance() < bestNeighbour.getDistance())
                        bestNeighbour = cell;
                }
            }
            
            if(grid.isValid(row - 1, column)) {
                Cell cell = grid.get(row - 1, column);
                if(cell.getDistance() > 0) {
                    if(bestNeighbour == null) 
                        bestNeighbour = cell;
                    else if(cell.getDistance() < bestNeighbour.getDistance())
                        bestNeighbour = cell;
                }
            }
            
            if(grid.isValid(row, column + 1)) {
                Cell cell = grid.get(row, column + 1);
                if(cell.getDistance() > 0) {
                    if(bestNeighbour == null) 
                        bestNeighbour = cell;
                    else if(cell.getDistance() < bestNeighbour.getDistance())
                        bestNeighbour = cell;
                }
            }
            
            if(grid.isValid(row, column - 1)) {
                Cell cell = grid.get(row, column - 1);
                if(cell.getDistance() > 0) {
                    if(bestNeighbour == null) 
                        bestNeighbour = cell;
                    else if(cell.getDistance() < bestNeighbour.getDistance())
                        bestNeighbour = cell;
                }
            }

            if(grid.isValid(row + 1, column + 1)) {
                Cell cell = grid.get(row + 1, column + 1);
                if(cell.getDistance() > 0) {
                    if(bestNeighbour == null) 
                        bestNeighbour = cell;
                    else if(cell.getDistance() < bestNeighbour.getDistance())
                        bestNeighbour = cell;
                }
            }
            
            if(grid.isValid(row - 1, column - 1)) {
                Cell cell = grid.get(row - 1, column - 1);
                if(cell.getDistance() > 0) {
                    if(bestNeighbour == null) 
                        bestNeighbour = cell;
                    else if(cell.getDistance() < bestNeighbour.getDistance())
                        bestNeighbour = cell;
                }
            }

            if(grid.isValid(row - 1, column + 1)) {
                Cell cell = grid.get(row - 1, column + 1);
                if(cell.getDistance() > 0) {
                    if(bestNeighbour == null) 
                        bestNeighbour = cell;
                    else if(cell.getDistance() < bestNeighbour.getDistance())
                        bestNeighbour = cell;
                }
            }
            
            if(grid.isValid(row + 1, column - 1)) {
                Cell cell = grid.get(row + 1, column - 1);
                if(cell.getDistance() > 0) {
                    if(bestNeighbour == null) 
                        bestNeighbour = cell;
                    else if(cell.getDistance() < bestNeighbour.getDistance())
                        bestNeighbour = cell;
                }
            }
            
            if(bestNeighbour == null) pathExists = false;           
            else {
                path.add(bestNeighbour);
                row = bestNeighbour.getRow();
                column = bestNeighbour.getColumn();
            }
        }
        if(pathExists) pathCreated = true;
        else path.clear();
    }
    
    public void render(Graphics2D g) {
        if(!pathCreated) return;
        
        g.setStroke(new BasicStroke(2));
        g.setColor(Color.yellow);
        
        for(int i=1; i < path.size() && pathCreated; i++) {
            Cell cell1 = path.get(i - 1);
            Cell cell2 = path.get(i);
            
            g.drawLine(cell1.getColumn() * controller.getCellSize() + controller.getCellSize()/2,
                    cell1.getRow() * controller.getCellSize() + controller.getCellSize()/2,
                    cell2.getColumn() * controller.getCellSize() + controller.getCellSize()/2,
                    cell2.getRow() * controller.getCellSize() + controller.getCellSize()/2
                );
            
            
            g.fillOval(cell2.getColumn() * controller.getCellSize() + 10,
                    cell2.getRow() * controller.getCellSize() + 10, 
                    controller.getCellSize() - 20,
                    controller.getCellSize() - 20
                );
        }
         
    }

    public void resetDisplayedWave() {
        displayedWave = 1;
        displayedTime = 0;
    }

    public int getDisplayedWave() {
        return displayedWave;
    }
    
}
