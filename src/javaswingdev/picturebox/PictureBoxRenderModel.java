package javaswingdev.picturebox;

import java.awt.Rectangle;

public class PictureBoxRenderModel {

    public Rectangle getImage() {
        return image;
    }

    public void setImage(Rectangle image) {
        this.image = image;
    }

    public Rectangle getComponent() {
        return component;
    }

    public void setComponent(Rectangle component) {
        this.component = component;
    }

    public PictureBox.BoxFit getBoxFit() {
        return boxFit;
    }

    public void setBoxFit(PictureBox.BoxFit boxFit) {
        this.boxFit = boxFit;
    }

    public PictureBoxRenderModel(Rectangle image, Rectangle component, PictureBox.BoxFit boxFit) {
        this.image = image;
        this.component = component;
        this.boxFit = boxFit;
    }

    public PictureBoxRenderModel() {
    }

    private Rectangle image;
    private Rectangle component;
    private PictureBox.BoxFit boxFit;
}
