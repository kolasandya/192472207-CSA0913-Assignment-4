package Task04_DrawPad;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class DrawPad extends Frame {
    private final List<Point> points = new ArrayList<>();
    private final Label status = new Label("Clicks: 0");
    private int count = 0;

    public DrawPad() {
        super("Draw Pad - Forensics Fixed");
        add(status, BorderLayout.SOUTH);

        // TODO 1 Completed: Correct mouseClicked listener, store point, update label, repaint
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                count++;
                status.setText("Clicks: " + count);
                points.add(e.getPoint());
                repaint();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });

        setSize(400, 300);
    }

    // TODO 2 Completed: Draw every stored point as a 6x6 filled circle centered on the point
    @Override
    public void paint(Graphics g) {
        g.setColor(java.awt.Color.BLUE);
        for (Point p : points) {
            g.fillOval(p.x - 3, p.y - 3, 6, 6);
        }
    }

    public static void main(String[] args) {
        new DrawPad().setVisible(true);
    }
}
