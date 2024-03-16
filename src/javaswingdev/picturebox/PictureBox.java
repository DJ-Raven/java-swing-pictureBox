package javaswingdev.picturebox;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class PictureBox extends JComponent {

    public Icon getImage() {
        return image;
    }

    public void setImage(Icon image) {
        this.image = image;
        imageRender = null;
        repaint();
    }

    public BoxFit getBoxFit() {
        return boxFit;
    }

    public void setBoxFit(BoxFit boxFit) {
        if (this.boxFit != boxFit) {
            this.boxFit = boxFit;
            imageRender = null;
            repaint();
        }
    }

    public RenderType getRenderType() {
        return renderType;
    }

    public void setRenderType(RenderType renderType) {
        if (this.renderType != renderType) {
            this.renderType = renderType;
            imageRender = null;
            repaint();
        }
    }

    public PictureBoxRender getPictureBoxRender() {
        return pictureBoxRender;
    }

    public void setPictureBoxRender(PictureBoxRender pictureBoxRender) {
        this.pictureBoxRender = pictureBoxRender;
        imageRender = null;
        repaint();
    }

    private Icon image;
    private BoxFit boxFit = BoxFit.COVER;
    private RenderType renderType = RenderType.IMAGE;
    private BufferedImage imageRender;
    private PictureBoxRender pictureBoxRender;

    private int oldWidth;
    private int oldHeight;

    public PictureBox() {
        pictureBoxRender = new DefaultPictureBoxRender();
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        boolean paint = updateImage();
        if (paint) {
            g.drawImage(imageRender, 0, 0, null);
        }
        super.paintComponent(g);
    }

    private boolean updateImage() {
        int width = getWidth();
        int height = getHeight();
        if (image == null || width == 0 || height == 0) {
            return false;
        }
        if (imageRender == null || oldWidth != width || oldHeight != height) {
            imageRender = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = imageRender.createGraphics();
            Rectangle rec = new Rectangle(0, 0, width, height);
            Rectangle rectangle = boxFit == BoxFit.FILL ? rec : getAutoSize(image);
            if (pictureBoxRender != null) {
                g2.setColor(getBackground());
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.fill(pictureBoxRender.render(renderType == RenderType.IMAGE ? getRectangle(rectangle, rec) : rec));
                g2.setComposite(AlphaComposite.SrcIn);
            }
            Image newImage = new ImageIcon(toImage(image).getScaledInstance(rectangle.width, rectangle.height, Image.SCALE_SMOOTH)).getImage();
            g2.drawImage(newImage, rectangle.x, rectangle.y, null);
            g2.dispose();
            oldWidth = width;
            oldHeight = height;
        }
        return true;
    }

    private Rectangle getRectangle(Rectangle image, Rectangle component) {
        Area area = new Area(component);
        area.intersect(new Area(image));
        return area.getBounds();
    }

    private Rectangle getAutoSize(Icon image) {
        int w = getWidth();
        int h = getHeight();
        if (boxFit == BoxFit.NONE) {
            int iconW = image.getIconWidth();
            int iconH = image.getIconHeight();
            int x = (w - iconW) / 2;
            int y = (h - iconH) / 2;
            return new Rectangle(new Point(x, y), new Dimension(iconW, iconH));
        } else {
            if (w > image.getIconWidth()) {
                w = image.getIconWidth();
            }
            if (h > image.getIconHeight()) {
                h = image.getIconHeight();
            }
            int iw = image.getIconWidth();
            int ih = image.getIconHeight();
            double xScale = (double) w / iw;
            double yScale = (double) h / ih;
            double scale = 0;
            if (boxFit == BoxFit.CONTAIN) {
                scale = Math.min(xScale, yScale);
            } else if (boxFit == BoxFit.COVER) {
                scale = Math.max(xScale, yScale);
            } else if (boxFit == BoxFit.FIT_HEIGHT) {
                scale = yScale;
            } else if (boxFit == BoxFit.FIT_WIDTH) {
                scale = xScale;
            }
            int width = (int) (scale * iw);
            int height = (int) (scale * ih);
            int x = (getWidth() - width) / 2;
            int y = (getHeight() - height) / 2;
            return new Rectangle(new Point(x, y), new Dimension(width, height));
        }
    }

    private Image toImage(Icon icon) {
        return ((ImageIcon) icon).getImage();
    }

    public static enum BoxFit {
        NONE, CONTAIN, FILL, COVER, FIT_WIDTH, FIT_HEIGHT
    }

    public static enum RenderType {
        IMAGE, COMPONENT
    }
}
