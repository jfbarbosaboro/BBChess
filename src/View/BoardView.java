package View;

import Controller.BoardController;
import Model.BoardModel;
import Model.Piece;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import Model.Position;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;

public class BoardView extends javax.swing.JFrame implements Observer {

    
  private Canvas canvas;  
  protected Position mouseCoord;
  protected BoardController controller;
  
  
  public BoardView(BoardModel model) {
    initComponents();
    canvas = new Canvas();
    mouseCoord = new Position();
    canvas.registerObserver(this);
    canvas.registerObserver(model);
    
    Dimension area = new Dimension(jPCanvas.getWidth(), jPCanvas.getHeight());
    canvas.setPreferredSize(area);
    jPCanvas.setLayout(new GridLayout(1, 1));
    jPCanvas.add(canvas);  
    
  }

    public Position getMouseCoord(int x, int y) {
        mouseCoord.setPosition(x, y);
        return mouseCoord;
    }

    public void setMouseCoord(Position mouseCoord) {
        this.mouseCoord = mouseCoord;
    }
  
    public JLabel getClickLabel() {
        return clickLabel;
    }

    public JLabel getCoordinateLabel() {
        return coordinateLabel;
    }

    public void setClickLabel(JLabel clickLabel) {
        this.clickLabel = clickLabel;
    }

    public void setCoordinateLabel(JLabel coordinateLabel) {
        this.coordinateLabel = coordinateLabel;
    }
    
    public JLabel getPieceNameLabel(){
        return pieceNameLabel;
    }
    
    public void setPieceNameLabel(JLabel pieceNameLabel){
        this.pieceNameLabel = pieceNameLabel;
    }
    public void addController(BoardController controller){
        this.controller = controller;
        jPCanvas.addMouseListener(controller);
        jPCanvas.addMouseMotionListener(controller);
    }
    
    /*public void drawMouseSquare(Graphics2D g) {
        
        int width = 76;
        int height = 76;
        
        int qx = (mouseCoord.x() - 56)/width;
        int qy = (mouseCoord.y() - 56)/height; 
        
        if (qx > 7){
            qx = 7;
        }
        if (qy > 7){
            qy = 7;
        }
        
            int squareWidth = 76;
            int squareHeight = 76;
        
            g.setColor(Color.decode("0xAAD400"));
            g.setStroke(new BasicStroke(4));
            g.drawRect(qx * squareWidth + 58, qy * squareHeight + 58, squareWidth-4, squareHeight-4);
            g.setColor(Color.BLACK);
        
    }*/
    
    public void drawSquarePerimeter(Graphics2D g, int x, int y, String color) {
        
        if (x > 7){
            x = 7;
        }
        if (y > 7){
            y = 7;
        }
        
        int squareWidth = 76;
        int squareHeight = 76;
        
        g.setColor(Color.decode(color)); // color in hexadecimal
        g.setStroke(new BasicStroke(4));
        g.drawRect(x * squareWidth + 58, y * squareHeight + 58, squareWidth-4, squareHeight-4);
    }
    
    public void drawSquareArea(Graphics2D g, int x, int y, String color) {
        
        if (x > 7){
            x = 7;
        }
        if (y > 7){
            y = 7;
        }
        
        int squareWidth = 76;
        int squareHeight = 76;
        
        g.setColor(Color.decode(color)); // color in hexadecimal
        g.setStroke(new BasicStroke(4));
        //g.drawRect(x * squareWidth + 58, y * squareHeight + 58, squareWidth-4, squareHeight-4); // Draw complete rectangle
        g.fillRect(x * squareWidth + 58, y * squareHeight + 58, squareWidth-4, squareHeight-4);
        
    }
    
    public void drawMouseSquare(Graphics2D g, boolean visibility) {
        
        int width = 76;
        int height = 76;
        
        int qx = (mouseCoord.x() - 56)/width;
        int qy = (mouseCoord.y() - 56)/height; 
        
        drawSquarePerimeter(g, qx, qy, "0xAAD400");
    }
    
    public void highlightCurrentPiece(Graphics2D g, boolean visibility) {
        
        int moveState = controller.getMoveState();
        
        if(moveState == 1) {
            Position pos = controller.getIni();
            drawSquarePerimeter(g, pos.x(), 7-pos.y(), "0x6FA8DC");
            //drawSquareArea(g, pos.x(), 7-pos.y(), "0x6FA8DC"); 
        }
    }
    
    public void drawPossibleMovesForPiece(Graphics2D g, boolean visibility) {
        int moveState = controller.getMoveState();
        
        if(moveState == 1) {
            Position pos = controller.getIni();
            ArrayList<Position> possiblePositions = controller.getListOfPossibleMovesForPieceIn(pos);
            ArrayList<Position> CapturableEnemies = controller.getListOfCapturableEnemiesForPieceIn(pos);
            
            
            for(int i = 0; i < possiblePositions.size(); i++) {
                drawSquareArea(g, possiblePositions.get(i).x(), 7 - possiblePositions.get(i).y(),"0xF67C00");
            } 
            
            for(int i = 0; i < CapturableEnemies.size(); i++) {
                drawSquareArea(g, CapturableEnemies.get(i).x(), 7 - CapturableEnemies.get(i).y(),"0xFD0000");
            }
            
            
        }
        
    }
    
    
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPCanvas = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        coordinateLabel = new javax.swing.JLabel();
        clickLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pieceNameLabel = new javax.swing.JLabel();
        undoButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BBChess");
        setMaximizedBounds(new java.awt.Rectangle(800, 800, 800, 800));
        setResizable(false);

        jPCanvas.setMaximumSize(null);
        jPCanvas.setPreferredSize(new java.awt.Dimension(720, 720));

        javax.swing.GroupLayout jPCanvasLayout = new javax.swing.GroupLayout(jPCanvas);
        jPCanvas.setLayout(jPCanvasLayout);
        jPCanvasLayout.setHorizontalGroup(
            jPCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
        );
        jPCanvasLayout.setVerticalGroup(
            jPCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
        );

        jLabel2.setText("Coordinate:");

        jLabel3.setText("click:");

        coordinateLabel.setText(" ");

        clickLabel.setText(" ");

        jLabel1.setText("Piece:");

        undoButton.setText("Undo");
        undoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(coordinateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clickLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pieceNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(undoButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPCanvas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPCanvas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(coordinateLabel)
                            .addComponent(jLabel1)
                            .addComponent(pieceNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(clickLabel)))
                    .addComponent(undoButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void undoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoButtonActionPerformed
        if (controller.thereIsNothingToUndo()){
            return;
        }
        controller.undo();
    }//GEN-LAST:event_undoButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel clickLabel;
    private javax.swing.JLabel coordinateLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPCanvas;
    private javax.swing.JLabel pieceNameLabel;
    private javax.swing.JButton undoButton;
    // End of variables declaration//GEN-END:variables

    
    @Override
    public void update(Observable o, Object arg) {
        drawMouseSquare((Graphics2D) arg, true);
        highlightCurrentPiece((Graphics2D) arg, true);
        drawPossibleMovesForPiece((Graphics2D) arg, true);
    }
}


