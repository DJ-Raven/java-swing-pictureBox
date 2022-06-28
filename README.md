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
#### Example
  ```java
pictureBox1.setPictureBoxRender(new DefaultPictureBoxRender() {
    @Override
    public Shape render(Rectangle rectangle) {
        return createRound(rectangle, 25);
    }
});
  ```
  
#### Demo
 
![2022-06-28_181407](https://user-images.githubusercontent.com/58245926/176165649-8d3f1a37-46d3-4aeb-b61c-d340fcdd9cea.png)
