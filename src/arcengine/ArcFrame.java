package arcengine;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Gerwyn Jones
 */
public class ArcFrame extends javax.swing.JFrame implements Activator {

    protected MultiKeyPressListener multiKey;
    GraphicsIndex graphical;
    short state;
    double scale;
    Mouse mouse = new Mouse();
    Graphics2D g;
    BufferedImage buffer;
    protected volatile float xscale;
    protected volatile float yscale;
    protected volatile ArcSetUp arcfx;

    /**
     * Creates new form ArcFrame
     */
    public ArcFrame() {
        initComponents();
        //Toolkit tk = Toolkit.getDefaultToolkit();
        //Dimension dim = tk.getScreenSize();
        //this.setPreferredSize(dim);
        //this.setResizable(false);
        this.setVisible(true);
        pack();
        buffer = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        buffer = Game.gc.createCompatibleImage(this.getWidth(), this.getHeight(), buffer.getTransparency());
        xscale = 2.0f / this.getWidth() - 1.0f * (this.getHeight() / this.getWidth());
        yscale = 2.0f / this.getHeight() - 1.0f;
        graphical = new GraphicsIndex(buffer.createGraphics());

        multiKey = new MultiKeyPressListener();
        //this.addKeyListener(multiKey);
    }

    public ArcFrame(ArcSetUp x) {
        arcfx = x;
        initComponents();
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();
        this.setPreferredSize(dim);
        this.setResizable(false);
        pack();
        buffer = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        buffer = Game.gc.createCompatibleImage(this.getWidth(), this.getHeight(), buffer.getTransparency());
        xscale = 2.0f / this.getWidth() - 1.0f * (this.getHeight() / this.getWidth());
        yscale = 2.0f / this.getHeight() - 1.0f;

        graphical = new GraphicsIndex(buffer.createGraphics());
        ArcEngine.DefaultGraphical = graphical;
        multiKey = new MultiKeyPressListener();
        this.addKeyListener(multiKey);
    }

    public void Activate(ActionState act) {
        //System.out.println("State "+state);
        act.active(state);
    }

    public void InterActivate(MouseInterAction act) {
        if (mouse.event != null) {

            act.mouseAction(mouse);
            mouse.event = null;

        } else if (mouse.wheelEvent != null) {
            act.mouseAction(mouse);
            mouse.wheelEvent = null;
        } else if (mouse.dragged != null) {
            act.mouseAction(mouse);
            mouse.dragged = null;
        }
    }

    @Override
    public void paint(Graphics gr) {
        super.paint(gr);
        gr.drawImage(buffer, 0, 0, this);
        this.invalidate();
    }

    @Override
    protected void processKeyEvent(KeyEvent e) {
        super.processKeyEvent(e);
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            state = -1;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                formMouseWheelMoved(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        // TODO add your handling code here:
        mouse.event = evt;

    }//GEN-LAST:event_formMouseMoved

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:
        mouse.event = evt;
    }//GEN-LAST:event_formMousePressed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
        mouse.event = evt;
    }//GEN-LAST:event_formMouseClicked

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        // TODO add your handling code here:
        mouse.dragged = evt;
        
    }//GEN-LAST:event_formMouseDragged

    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        // TODO add your handling code here:
        mouse.wheelEvent = evt;
    }//GEN-LAST:event_formMouseWheelMoved

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
        multiKey.keyPressed(evt);
    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // TODO add your handling code here:
        multiKey.keyReleased(evt);
    }//GEN-LAST:event_formKeyReleased

    /**
     * @param args the command line arguments
     */
    public void PaintGame(Painter p) {
        g = buffer.createGraphics();
        graphical.SetAllGraphics(g);
        p.paintAll(g, scale);
        g.dispose();
        Graphics gr = this.getGraphics();
        if (gr != null && this.buffer != null) {
            gr.drawImage(buffer, 0, 20, null);
            gr.dispose();
        } else {
            System.out.println("PaintGame Error");
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public void Close() {

        this.dispose();
    }

    @Override
    public int GetWidth() {
        return getWidth();
    }

    @Override
    public int GetHeight() {

        return getHeight();
    }

    @Override
    public Game GetGame() {
        return arcfx.arc;
    }

    @Override
    public Graphical Graphic() {
        return graphical;
    }

    @Override
    public MultiKeyPressListener KeyBoard() {
        return multiKey;
    }

    @Override
    public void Update(GameTime time) {

    }
}
