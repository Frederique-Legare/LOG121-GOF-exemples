import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
   This class defines arrowheads of various shapes.
*/
public class ArrowHead extends SerializableEnumeration
{
   private ArrowHead() {}
   /**
      Draws the arrowhead.
      @param g2 the graphics context
      @param p a point on the axis of the arrow head
      @param q the end point of the arrow head
   */
   public void draw(Graphics2D g2, Point2D p, Point2D q)
   {
      if (this == NONE) return;
      final double ARROW_ANGLE = Math.PI / 6;
      final double ARROW_LENGTH = 8;

      double dx = q.getX() - p.getX();
      double dy = q.getY() - p.getY();
      double angle = Math.atan2(dy, dx);
      double x1 = q.getX() 
         - ARROW_LENGTH * Math.cos(angle - ARROW_ANGLE);
      double y1 = q.getY() 
         - ARROW_LENGTH * Math.sin(angle - ARROW_ANGLE);
      double x2 = q.getX() 
         - ARROW_LENGTH * Math.cos(angle + ARROW_ANGLE);
      double y2 = q.getY() 
         - ARROW_LENGTH * Math.sin(angle + ARROW_ANGLE);

      GeneralPath path = new GeneralPath();
      path.moveTo((float) q.getX(), (float) q.getY());
      path.lineTo((float) x1, (float) y1);
      if (this == V)
      {
         path.moveTo((float) x2, (float) y2);
         path.lineTo((float) q.getX(), (float) q.getY());
      }
      else if (this == TRIANGLE)
      {
         path.lineTo((float) x2, (float) y2);
         path.closePath();                  
      }
      else if (this == DIAMOND || this == BLACK_DIAMOND)
      {
         double x3 = x1 - ARROW_LENGTH * Math.cos(angle + ARROW_ANGLE);
         double y3 = y1 - ARROW_LENGTH * Math.sin(angle + ARROW_ANGLE);
         path.lineTo((float) x3, (float) y3);
         path.lineTo((float) x2, (float) y2);
         path.closePath();         
      }
      Color oldColor = g2.getColor();
      if (this == BLACK_DIAMOND)
         g2.setColor(Color.BLACK);
      else
         g2.setColor(Color.WHITE);
      g2.fill(path);
      g2.setColor(oldColor);
      g2.draw(path);
   }

   public static final ArrowHead NONE = new ArrowHead();
   public static final ArrowHead TRIANGLE = new ArrowHead();
   public static final ArrowHead V = new ArrowHead();
   public static final ArrowHead DIAMOND = new ArrowHead();
   public static final ArrowHead BLACK_DIAMOND = new ArrowHead();
}
