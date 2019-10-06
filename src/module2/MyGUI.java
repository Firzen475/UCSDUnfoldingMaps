package module2;

import processing.core.PApplet;

import java.awt.*;

public class MyGUI extends PApplet {
    public void setup(){
        size(400,400);
        background(Color.GRAY.getRGB());
    }

    public void draw(){
        ellipse(width/2,height/2, width/5,height/5);
        fill(Color.yellow.getRGB());

    }
}
