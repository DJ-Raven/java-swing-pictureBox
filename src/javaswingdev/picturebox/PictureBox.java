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
        creatImage();
        repaint();
    }

    public BoxFit getBoxFit() {
        return boxFit;
    }

    public void setBoxFit(BoxFit boxFit) {
        this.boxFit = boxFit;
        creatImage();
        repaint();
    }

    public RenderType getRenderType() {
        return renderType;
    }

    public void setRenderType(RenderType renderType) {
        this.renderType = renderType;
        creatImage();
        repaint();
    }

    public PictureBoxRender getPictureBoxRender() {
        return pictureBoxRender;
    }

    public void setPictureBoxRender(PictureBoxRender pictureBoxRender) {
        this.pictureBoxRender = pictureBoxRender;
        creatImage();
        repaint();
    }

    private Icon image;
    private BoxFit boxFit = BoxFit.COVER;
    private RenderType renderType = RenderType.IMAGE;
    private BufferedImage imageRender;
    private PictureBoxRender pictureBoxRender;

    public PictureBox() {
        pictureBoxRender = new DefaultPictureBoxRender();
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (image != null) {
            if (imageRender != null) {
                creatImage();
            }
            Graphics2D g2 = (Graphics2D) g.create();
            g2.drawImage(imageRender, 0, 0, null);
            g2.dispose();
        }
        super.paintComponent(g);
    }

    private void creatImage() {
        int width = getWidth();
        int height = getHeight();
        if (width > 0 && height > 0) {
            imageRender = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = imageRender.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            Rectangle rec = new Rectangle(0, 0, width, height);
            Rectangle rectangle = boxFit == BoxFit.FILL ? rec : getAutoSize(image);
            if (pictureBoxRender != null) {
                g2.setColor(getBackground());
                g2.fill(pictureBoxRender.render(renderType == RenderType.IMAGE ? getRectangle(rectangle, rec) : rec));
                g2.setComposite(AlphaComposite.SrcIn);
            }
            g2.drawImage(toImage(image), rectangle.x, rectangle.y, rectangle.width, rectangle.height, null);
            g2.dispose();
        }
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

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        if (image != null) {
            creatImage();
        }
    }

    public static enum BoxFit {
        NONE, CONTAIN, FILL, COVER, FIT_WIDTH, FIT_HEIGHT
    }

    public static enum RenderType {
        IMAGE, COMPONENT
    }
}
