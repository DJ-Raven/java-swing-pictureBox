package javaswingdev.picturebox;

import java.awt.Rectangle;
import java.awt.Shape;

public interface PictureBoxRender {

    public Shape render(Rectangle rectangle);

    public Shape createCircle(Rectangle rectangle);

    public Shape createRound(Rectangle rectangle, int round);
}
