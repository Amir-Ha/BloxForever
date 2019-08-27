package a_s.bloxforever;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Created by sami on 20-May-15.
 */
public class Block {
    private Bitmap block;//image
    private int typeOfBlock;//type mean the color
    private int i,j,width,height,sprite;//i&j index on map ,width&height for image ,sprite is the type of face on block image
    private Rect src;//src is rectangle you cut from the image according to sprite and type

    public Block(Bitmap block, int typeOfBlock, int width, int height, int sprite) {
        setBlock(block);
        setHeight(height);
        setTypeOfBlock(typeOfBlock);
        setSprite(sprite);
        setWidth(width);
        setSrc();
    }

    public Bitmap getBlock() {
        return block;
    }

    public void setBlock(Bitmap block) {
        this.block = block;
    }

    public int getSprite() {
        return sprite;
    }

    public void setSprite(int sprite) {
        this.sprite = sprite;
    }

    public int getTypeOfBlock() {
        return typeOfBlock;
    }

    public void setTypeOfBlock(int typeOfBlock) {
        this.typeOfBlock = typeOfBlock;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rect getSrc() {
        return src;
    }

    //set src according to (type (color) * height of image) and (sprite * width)
    public void setSrc() {
        this.src=new Rect(getSprite()*width,getTypeOfBlock()*height,getSprite()*width+width,getTypeOfBlock()*height+height);
    }

}
