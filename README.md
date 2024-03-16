# java-swing-pictureBox

#### Method
  ```java
  public void setImage(Icon image);
  public void setBoxFit(BoxFit boxFit);
  public void setRenderType(RenderType renderType);
  public void setPictureBoxRender(PictureBoxRender pictureBoxRender);
  ```
#### Enum
  ```java
public static enum BoxFit {
    NONE, CONTAIN, FILL, COVER, FIT_WIDTH, FIT_HEIGHT
}

public static enum RenderType {
    IMAGE, COMPONENT
}
  ```
#### Example picturebox render
  ```java
pictureBox1.setPictureBoxRender(new DefaultPictureBoxRender() {
    @Override
    public Shape render(Rectangle rectangle) {
        return createRound(rectangle, 25);
    }
});
  ```
  
#### Demo
 
![2024-03-16_130920](https://github.com/DJ-Raven/java-swing-pictureBox/assets/58245926/53f9c1ca-a9aa-4a44-a329-09a37315f0af)
