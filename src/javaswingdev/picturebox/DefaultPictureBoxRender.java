package javaswingdev.picturebox;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

public class DefaultPictureBoxRender implements PictureBoxRender {

    @Override
    public Shape render(Rectangle rectangle) {
        return rectangle;
    }

    @Override
    public final Shape createCircle(Rectangle rectangle) {
        double size = Math.min(rectangle.getWidth(), rectangle.getHeight());
        double x = rectangle.getX() + ((rectangle.getWidth() - size) / 2);
        double y = rectangle.getY() + ((rectangle.getHeight() - size) / 2);
        return new Ellipse2D.Double(x, y, size, size);
    }

    @Override
    public final Shape createRound(Rectangle rectangle, int round) {
        return new RoundRectangle2D.Double(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), round, round);
    }
}
