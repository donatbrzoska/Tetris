/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.data.Coordinate;
import model.data.Cell;
import model.data.Celltype;
import model.data.Change;
import model.data.Place;
import model.data.Tile;
import model.data.Tiletype;
import model.threads.TickThread;
import model.threads.RemoveLinesThread;
import model.threads.ApplyAllowedThread;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Random;

/**
 * 20-0:30 (4,5)
 * 0.5
 * 18:45 - 20:45 (2)
 * 22:30 - 23:40 (1,16)
 * 0.25
 * 0.33
 * 18:10 - 20:36 (2.25)
 * 21:47 - 1:15 (4)
 * 00:00 - 1:00 (1)
 * 00:00 - 1:50 (1.8)
 * 00:00 - 3:45 (3.75)
 * 12:30 - 12:50 (0.33)
 * 13:00 - 14:00 (1)
 * 15:30 - 16:51 (1.83)
 * 18:30 - 19:30 (1)
 * 21:40 - 1:00 (3.33)
 * 2:30 - 3:30 (1)
 * 17:30 - 2:30 (9)
 * 19:00 - 23:00 (4)
 * 12:30 - 12:55 (0.5)
 * 17:07 - 21:33 (4)
 * 20:51 - 21:57 (1) = 48,5
 * 
 * 
 * 
 * Optimierung:
 * Was muss gezeichnet werden? -> Optimieren, Koordinaten evtl ebenfalls in Cell speichern -> Liste mit Cells wird zurueckgegeben
 * Extra-Methode fuer die GhostCollisionDetection?
 * 
 * 
 * Spaeter:
 * Kollisionsbugs einzeln beheben?
 * Tiles 2 hoeher spawnen
 * @author donatdeva
 */
public class Game {
    private Cell[][] field;
    private Tile current;
    private Tiletype hold;
    private boolean holdAllowed;
    private Tiletype next;
    private int score;
    private double ticksPerSecond;
    private boolean gameOver;
    private int applyCounter;
    private boolean paused;
    private TickThread tickThread;
    private int waitForApplyCounter;
    
    private static Difficulty difficulty = Difficulty.MEDIUM;
    
    private final int MAXCORRECTIONSTEPS = 2;
    private final boolean loggingOn = false; //setCells Logging requires a LOT of power
    
    
    public Game(){
        newGame();
    }
    
    /**
     * Method that determines whether a given coordinate is already set on the field
     * @param c Coordinate to be checked
     * @return Coordinate colliding with tile, no collision returns null
     */
    private Coordinate tileCollision(Coordinate c){
        LinkedList<Coordinate> setCells = getSetCells();
        log("Set cells: " + Arrays.toString(setCells.toArray()));
        for (int i=0; i<setCells.size(); i++){
            if (c.equals(setCells.get(i))) {
                return c;
            }
        }
        return null;
    }
    
    /**
     * Method that corrects collisions if occured
     * @return True, if something was corrected; false if not
     */
    private boolean collisionCorrection(){
        CorrectionCommand correctionCommand = collisionDetection();
        if (correctionCommand == null) {
            return false;
        } else {
            boolean fixed;
            int movedCounter;
            switch (correctionCommand){
                case APPLY:
                    current.returnToPrevious(1);
                    if (applyIsAllowed()){
                        applyCurrentTile();
                    }
                    break;
                case TRYLEFT:   //versuche zu fixen, wenn nicht erfolgreich -> zuruecksetzen
                    fixed = false;
                    movedCounter = 0;
                    for (int i=0; i<MAXCORRECTIONSTEPS; i++) {
                        current.moveLeft();
                        movedCounter++;
                        if (!collisionOccurs(current.getCoordinates())) {
                            fixed = true;
                            break;
                        }
                    }
                    if (!fixed){
                        current.returnToPrevious(movedCounter+1);
                    }
                    break;
                case TRYRIGHT:
                    fixed = false;
                    movedCounter = 0;
                    for (int i=0; i<MAXCORRECTIONSTEPS; i++) {
                        current.moveRight();
                        movedCounter++;
                        if (!collisionOccurs(current.getCoordinates())) {
                            fixed = true;
                            break;
                        }
                    }
                    if (!fixed){
                        current.returnToPrevious(movedCounter+1);
                    }
                    break;
                case TRYUP:
                    fixed = false;
                    movedCounter = 0;
                    for (int i=0; i<MAXCORRECTIONSTEPS; i++) {
                        current.moveUp();
                        movedCounter++;
                        if (!collisionOccurs(current.getCoordinates())) {
                            fixed = true;
                            break;
                        }
                    }
                    if (!fixed){
                        current.returnToPrevious(movedCounter+1);
                    }
                    break;
                case TRYDOWN:
                    fixed = false;
                    movedCounter = 0;
                    for (int i=0; i<MAXCORRECTIONSTEPS; i++) {
                        current.moveDown();
                        movedCounter++;
                        if (!collisionOccurs(current.getCoordinates())) {
                            fixed = true;
                            break;
                        }
                    }
                    if (!fixed){
                        current.returnToPrevious(movedCounter+1);
                    }
                    break;
                default:    //STEPBACK
                    try {
                        current.returnToPrevious(1);
//                        setApplyAllowed(true);
                    } catch (EmptyStackException e){
                        System.err.println("You triggered an error!");
//                        gameOver = true;
                    }
                    break;
            }
            return true;
        }
    }
    
    /**
     * Method that detects collisions and says wich correction command fixes them
     * @return correction command that fixes the collision
     */
    private CorrectionCommand collisionDetection(){
        Coordinate[] c = current.getCoordinates();
        for (int i=0; i<c.length; i++){
            if (c[i] != null) {
                //left border
                if (c[i].x<0) {
                    log("Out of bounds on the left");
                    return getCorrectionCommand(BorderCollision.LEFT);
                }
                //right border
                if (c[i].x==field[i].length){
                    log("Out of bounds on the right");
                    return getCorrectionCommand(BorderCollision.RIGHT);
                }
                //top border
                if (c[i].y<0){
                    log("Out of bounds at top (y=" + c[i].y + ")");
                    return getCorrectionCommand(BorderCollision.TOP);
                }
                //bottom border
                if (c[i].y==field.length){
                    log("Out of bounds at bottom (y=" + c[i].y + ")");
                    return getCorrectionCommand(BorderCollision.BOTTOM);
                }
                //tile collision
                Coordinate collisionCoordinate = tileCollision(c[i]);
                if (collisionCoordinate != null) {
                    log("Tile collision detected at " + c[i].toString());
                    return getCorrectionCommand(collisionCoordinate);
                }
            }
        }
        return null;
    }
    
    /**
     * Method that detects wether collisions occur     
     * @param Tile tile that needs to be checked for collisions
     * @return True if collisions occur; false if not
     */
    private boolean collisionOccurs(Coordinate[] c){
        for (int i=0; i<c.length; i++){
            if (c[i] != null) {
                //left border
                if (c[i].x<0) {
                    log("Out of bounds on the left");
                    return true;
                }
                //right border
                if (c[i].x==field[i].length){
                    log("Out of bounds on the right");
                    return true;
                }
                //top border
                if (c[i].y<0){
                    log("Out of bounds at top (y=" + c[i].y + ")");
                    return true;
                }
                //bottom border
                if (c[i].y==field.length){
                    log("Out of bounds at bottom (y=" + c[i].y + ")");
                    return true;
                }
                //tile collision
                Coordinate collisionCoordinate = tileCollision(c[i]);
                if (collisionCoordinate != null) {
                    log("Tile collision detected at " + c[i].toString());
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Method that determines, wich correction command may fix the collision with a border
     * @return Command that fixes the collision
     */
    CorrectionCommand getCorrectionCommand(BorderCollision borderCollision){
        Change lastChange = current.getLastChange();
        switch(lastChange){
            case MOVEDDOWN:
                return CorrectionCommand.APPLY;
            case ROTATED:
                switch (borderCollision){
                    case TOP:
                        return CorrectionCommand.TRYDOWN;
                    case RIGHT:
                        return CorrectionCommand.TRYLEFT;
                    case BOTTOM:
                        return CorrectionCommand.TRYUP;
                    case LEFT:
                        return CorrectionCommand.TRYRIGHT;
                }
            default:    //MOVEDLEFT, MOVEDRIGHT
                return CorrectionCommand.STEPBACK;
        }
    }
    
    /**
     * Method that determines, wich correction command may fix the collision with another coordinate
     * @param Coordinate 
     * @return Command that fixes the collision
     */
    CorrectionCommand getCorrectionCommand(Coordinate coordinate){
        Change lastChange = current.getLastChange();
        switch(lastChange){
            case MOVEDDOWN:
                return CorrectionCommand.APPLY;
            case ROTATED:
                CorrectionRestriction restriction = getCollisionCorrectionRestriction(coordinate);
                switch(restriction) {
                    case MOVELEFT:
                        return CorrectionCommand.TRYLEFT;
                    case MOVERIGHT:
                        return CorrectionCommand.TRYRIGHT;
                    case MOVEDOWN:
                        return CorrectionCommand.TRYDOWN;
                    case MOVEUP:
                        return CorrectionCommand.TRYUP;
                }
            default:    //MOVEDLEFT, MOVEDRIGHT
                return CorrectionCommand.STEPBACK;
        }
    }
    
    /**
     * Method that determines in wich direction the tile is allowed to be corrected
     * @param collided Coordinate of the collided tile that collided with another tile
     * @return Restriction of the direction the tile is allowed to be corrected
     */
    CorrectionRestriction getCollisionCorrectionRestriction(Coordinate collided){
        LinkedList<Coordinate> otherTileCoords = new LinkedList<>();
        Coordinate[] currentCoords = current.getCoordinates();
        for (int i=0; i<currentCoords.length; i++){     //extrahiere die Koordinaten des current tiles die nicht kollidiert sind
            if (currentCoords[i] != collided) {
                otherTileCoords.add(currentCoords[i]);
            }
        }
        log("Colliding: " + collided.toString());
        log("Rest: " + otherTileCoords.toString());
        
        //zaehlen wie viele vom Rest der Koordinaten links, rechts, ueber und unter der kollidierten Koordinate sind
        int left = 0;
        int right = 0;
        int under = 0;
        int above = 0;
        
        for (int i=0; i<otherTileCoords.size(); i++){
            Coordinate currentOther = otherTileCoords.get(i);
            if (currentOther.x < collided.x){
                left++;
            }
            if (currentOther.x > collided.x){
                right++;
            }
            if (currentOther.y < collided.y){
                above++;
            }
            if (currentOther.y > collided.y){
                under++;
            }
        }
        
        log("left: " + left);
        log("right: " + right);
        log("above: " + above);
        log("under: " + under);
        if (left>right & left>under & left>above){
            return CorrectionRestriction.MOVELEFT;
        }
        if (right>left & right>under & right>above){
            return CorrectionRestriction.MOVERIGHT;
        }
        if (under>left & under>right & under>above){
            return CorrectionRestriction.MOVEDOWN;
        }
        if (above>left & above>right & above>under){
            return CorrectionRestriction.MOVEUP;
        }
        return null;    //NULLPOINTER EXCEPTION WIRD IM AUSNAHMEFALL GEWORFEN
    }
    
    private void removeFinishedLines(){
        LinkedList<Integer> finished = getFinishedLines();
        
        RemoveLinesThread rmlThread = new RemoveLinesThread(this, finished); //new thread, waits through the animation, then removes
        rmlThread.start();
    }
    
    public void removeLines(LinkedList<Integer> finished){
        if (! finished.isEmpty()) {
            if (finished.size() == 4){  //Scoring
                score = score + 8;
            } else {
                score = score + finished.size();
            }
            updateTicksPerSecond();
            for (int i=finished.size()-1; i>=0; i--){      //Lines, from upper lines to lower lines deleted
                int currentLine = finished.get(i);
                for (int j=0; j<field[currentLine].length; j++){    //X Coordinate
                    log("removing Line " + currentLine);
                    for (int k=currentLine; k>0; k--){      //Y Coordinate
                        log("Moving " + j + "|" + (k-1) + " to " + j + "|" + k);
                        field[k][j] = field[k-1][j];
                        field[k-1][j] = new Cell();
                    }
                }
            }
        }
    }
    
    /**
     * Method to determine the complete lines
     * @return List with y coordinates of complete lines
     */
    private LinkedList<Integer> getFinishedLines(){
        LinkedList<Integer> finished = new LinkedList<>();
        for (int i=field.length-1; i>=0; i--){
            boolean complete = true;
            for (int j=0; j<field[i].length; j++){
                if (!(field[i][j].isSet())){
                    complete = false;
                    break;
                }
            }
            if (complete) {
                finished.add(i);
                log("Line " + i + " is finished");
            }
        }
        return finished;
    }
    
    private LinkedList<Coordinate> getSetCells(){
        LinkedList<Coordinate> setCells = new LinkedList<>();
        for (int i=0; i<field.length; i++){
            for (int j=0; j<field[i].length; j++){
                if (field[i][j].isSet()){
                    setCells.add(new Coordinate(j,i));
                }
            }
        } 
        return setCells;
    }
    
    /**
     * Method returning the tiletype in the nextfield while generating the new next tile
     * @return Next tile
     */
    private Tiletype nextTile(){
        Tiletype h = next;
        next = generateTiletype();
        return h;
    }
    
    private Tiletype generateTiletype(){
        Random r = new Random();
        int t = r.nextInt(7);
        Tiletype type = null;
        switch (t) {
            case 0:
                type = Tiletype.I;
                break;
            case 1:
                type = Tiletype.J;
                break;
            case 2:
                type = Tiletype.L;
                break;
            case 3:
                type = Tiletype.O;
                break;
            case 4:
                type = Tiletype.S;
                break;
            case 5:
                type = Tiletype.T;
                break;
            case 6:
                type = Tiletype.Z;
                break;
        }
        return type;
    }
    
    private void applyCurrentTile(){
        Coordinate[] c = current.getCoordinates();
        for (int i=0; i<c.length; i++) {
            field[c[i].y][c[i].x].setType(current.getType().getType());         //optimierbar???
        }
        
        log("Tile applied");
        log(applyCounter++ + " Tiles applied");
        
        removeFinishedLines();
        
        current = new Tile(nextTile(), Place.FIELD);
        log("New tile generated: " + current.getType().getType() + " at " + current.getPosition().toString());
        if (collisionOccurs(current.getCoordinates())){
            gameOver=true;
        } else {
            collisionCorrection();
            holdAllowed = true;
        }
    }
    
    /**
     * Method that generates the coordinates of the preview ghost
     * @return Array with the ghost coordinates
     */
    private Coordinate[] getGhostCoordinates(){
        Coordinate[] currentCoords = current.getCoordinates();
        Coordinate[] ghostCoordinates = new Coordinate[currentCoords.length];
        for (int i=0; i<ghostCoordinates.length; i++){
            ghostCoordinates[i] = new Coordinate(currentCoords[i].x,currentCoords[i].y);
        }
        while (!collisionOccurs(ghostCoordinates)){
            for (int i=0; i<ghostCoordinates.length; i++){
                ghostCoordinates[i].y++;
            }
        }
        if (!gameOver){
            log("Correcting ghost one up");
            for (int i=0; i<ghostCoordinates.length; i++){
                ghostCoordinates[i].y--;
            }
        }
        return ghostCoordinates;
    }
    
    private Celltype toGhostType(Celltype ct){
        switch (ct){
            case L:
                return Celltype.GL;
            case J:
                return Celltype.GJ;
            case O:
                return Celltype.GO;
            case I:
                return Celltype.GI;
            case S:
                return Celltype.GS;
            case Z:
                return Celltype.GZ;
            case T:
                return Celltype.GT;
            default:
                return null;
        }
    }
    
    boolean applyIsAllowed(){
        return waitForApplyCounter==0;
    }
    
    public void setApplyAllowed(boolean allowed){
        if (allowed == false){
            waitForApplyCounter++;
        } else {
            if (!applyIsAllowed())
                waitForApplyCounter--;
        }
    }
    
    //DEBUG PUBLIC (private)
    public void updateTicksPerSecond(){
        double speed;   //bad variable name
        switch (difficulty){
            case EASY:
                speed = 50;
                break;
            case HARD:
                speed = 2.5;
                break;
            default:
                speed = 12;
                break;
        }
        
        ticksPerSecond = (score < speed) ? 1 : score/speed;
        log("updated to: " + ticksPerSecond);
    }
    
    public void hold(){
        if (!paused){
            if (holdAllowed){
                if (hold==null) {
                    hold = current.getType();
                    current = new Tile(nextTile(), Place.FIELD);
                } else {
                    Tiletype h = hold;
                    hold = current.getType();
                    current = new Tile(h, Place.FIELD);
                }
                holdAllowed = false;
            }
        }
    }
    
    public void rotate(){
        if (!paused) {
            current.rotate();
            collisionCorrection();
            ApplyAllowedThread applyAllowedThread = new ApplyAllowedThread(this);
            applyAllowedThread.start();
        }
    }
    
    public void moveRight(){
        if (!paused){
            current.moveRight();
            collisionCorrection();
            ApplyAllowedThread applyAllowedThread = new ApplyAllowedThread(this);
            applyAllowedThread.start();
        }
    }
    
    public void moveDown(){
        if (!paused){
            current.moveDown();
            collisionCorrection();
        }
    }
    
    public void moveLeft(){
        if (!paused) {
            current.moveLeft();
            collisionCorrection();
            ApplyAllowedThread applyAllowedThread = new ApplyAllowedThread(this);
            applyAllowedThread.start();
        }
    }
    
    public void instantLock(){
        if (!paused) {
            while (true){
                log("Instantlocking...");
                current.moveDown();
                if (collisionCorrection()){
                    break;
                }
            }
            while (!applyIsAllowed()) {     //Wartezeit dezimieren
                setApplyAllowed(true);
            }
            current.moveDown();
            collisionCorrection();
            //System.out.println(applyIsAllowed());
            log("Instantlock finished");
        }
    }
    
    public boolean getGameOver(){
        return gameOver;
    }

    /**
     * Method that looks for all set cells inclusive the current tile
     * @return Array of cells that need to be set at the gui
     */
    public Cell[][] getSetMatrix(){
        Cell[][] set = new Cell[field.length][field[0].length];
        for (int i=0; i<set.length; i++){
            for (int j=0; j<set[i].length; j++){
                set[i][j] = new Cell();
            }
        }
        //Uebertragung des Feldes
        LinkedList<Coordinate> fieldCells = getSetCells();
        for (int i=0; i<fieldCells.size(); i++){
            Coordinate c = fieldCells.get(i);
            set[c.y][c.x].setType(field[c.y][c.x].getType());
        }
        
        log("Game over: " + getGameOver());
        //Uebertragung des Ghosts
        Coordinate[] ghostCells = getGhostCoordinates();
        log("Ghost Cells: " + Arrays.toString(ghostCells));
        for (int i=0; i<ghostCells.length; i++){
            Coordinate c = ghostCells[i];
            set[c.y][c.x].setType(toGhostType(current.getType().getType()));
        }
        
        //Uebertragung des moving Tiles
        Coordinate[] currentCells = current.getCoordinates();
        log("Current Cells: " + Arrays.toString(currentCells));
        for (int i=0; i<currentCells.length; i++){
            Coordinate c = currentCells[i];
            set[c.y][c.x].setType(current.getType().getType());
        }
        return set;
    }
    
    public Tile getNext(){
        return new Tile(next, Place.NEXT);
    }
    
    public Tile getHold(){
        if (hold != null) {
            return new Tile(hold, Place.HOLD);
        } else {
            return null;
        }
    }
    
//    public void togglePause(){
//        if (paused) {
//            tickThread = new TickThread(this);
//            tickThread.start();
//        } else {
//            tickThread.stopExecuting();
//        }
//        paused = !paused;
//    }
    
    public void pause(){
        tickThread.stopExecuting();
        paused = true;
    }
    
    public void unpause(){
        tickThread = new TickThread(this);
        tickThread.start();
        paused = false;
    }
    
    public boolean isPaused(){
        return paused;
    }
    
    public int getScore(){
        return score;
    }
    
    public double getTicksPerSecond(){
        return ticksPerSecond;
    }
    
    public void tick(){
        if (!gameOver) {
//            current.moveDown();
//            correctTiles();
            moveDown();
        }
    }
    
    public void log(String text){
        if (loggingOn) {
            System.out.println(text);
        }
    }
    
    public void setDifficulty(Difficulty difficulty){
        Game.difficulty = difficulty;
        updateTicksPerSecond();
    }
    
    public Difficulty getDifficulty(){
        return difficulty;
    }
    
    final public void newGame(){
        field = new Cell[18][10];
        for (int i=0; i<field.length; i++){
            for (int j=0; j<field[i].length; j++){
                field[i][j] = new Cell();
            }
        }
        current = new Tile(generateTiletype(), Place.FIELD);
        hold = null;
        holdAllowed = true;
        next = generateTiletype();
        score = 0;
        ticksPerSecond = 1;
        gameOver = false;
        applyCounter = 0;
        paused = true;
        tickThread = new TickThread(this);
        waitForApplyCounter = 0;
    }
    
    //HACK
    public void incScore(){
        score = score + 10;
    }
}