package View;

import Controler.BoardController;
import Model.BoardModel;
import Model.Piece;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;

public class BoardView extends javax.swing.JFrame implements Observer {

    
  private Canvas canvas;  
  private Point mouseCoord;
  private BoardModel model;
  private BoardController control;
  
  
  public BoardView(BoardModel model) {
    initComponents();
    canvas = new Canvas();
    mouseCoord = new Point();
    canvas.registerObserver(this);
    this.model = model;
    canvas.registerObserver(model);
    
    Dimension area = new Dimension(jPCanvas.getWidth(), jPCanvas.getHeight());
    canvas.setPreferredSize(area);
    jPCanvas.setLayout(new GridLayout(1, 1));
    jPCanvas.add(canvas);  
    
  }

    public Point getMouseCoord() {
        return mouseCoord;
    }

    public void setMouseCoord(Point mouseCoord) {
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
        this.control = controller;
        jPCanvas.addMouseListener(controller);
        jPCanvas.addMouseMotionListener(controller);
    }
    
    public void addModel(BoardModel model){
        this.model = model;
    }
    
  
    public void drawMouseSquare(Graphics2D g) {
        
        int width = 76;
        int height = 76;
        
        int qx = (mouseCoord.x - 56)/width;
        int qy = (mouseCoord.y - 56)/height; 
        
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(coordinateLabel)
                    .addComponent(jLabel1)
                    .addComponent(pieceNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(clickLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel clickLabel;
    private javax.swing.JLabel coordinateLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPCanvas;
    private javax.swing.JLabel pieceNameLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(Observable o, Object arg) {
        drawMouseSquare((Graphics2D) arg);
    }
}

