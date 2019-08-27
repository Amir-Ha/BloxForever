package a_s.bloxforever;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by sami on 26-May-15.
 */
public class BlockList {

    private final ArrayList<ArrayList<Block>> list;//ArrayList for ArrayList<block> for 9 color
    public BlockList(){
        //set Arrays
        list = new ArrayList<ArrayList<Block>>();
        for (int i=0; i < 9; i++) {
            list.add(new ArrayList<Block>());
        }
    }
    //Remove Block from Array
    public void RemovBlock(Block block,int type){
        list.get(type-1).remove(block);
    }
    //method to check if there is no blocks(from all color(ArrayList)) if yes return true else false
    public boolean HasNoBlocks(){
        for(int i=1;i<list.size();i++)
            if(list.get(i).size()!=0)
                return false;
        return true;
    }

    public ArrayList<Block> GetBlockList(int type){
        return list.get(type-1);//(type -1) because the type in maps on database start from 1
    }
    //method to add block to array list according to type
    public void AddBlock(int i, int j, int type, int sprite, Bitmap blocks, int w, int h){
        Block block = new Block(blocks, type, w,h,sprite);
        block.setI(i);
        block.setJ(j);
        if(type==0)//if type equal to 0 so its not block its wall or button(pause ,undo ,reset)
            list.get(type).add(block);
        else//else add block to list
            list.get(type - 1).add(block);
    }
    //method to update new  i&j and sprite
    public void updateBlock(int oldi,int oldj,int newi, int newj, int type,int sprite){
            int IndexOfBlock = GetIndex(type,oldi,oldj);
        if(IndexOfBlock!=-1) {
            list.get(type - 1).get(IndexOfBlock).setI(newi);
            list.get(type - 1).get(IndexOfBlock).setJ(newj);
            list.get(type - 1).get(IndexOfBlock).setSprite(sprite);
            list.get(type - 1).get(IndexOfBlock).setSrc();
        }
    }
    //method to return sprite(regular face of block) according to i,j,type
    public void ReturnSprite(int i,int j,int type){
        int IndexOfBlock = GetIndex(type,i,j);
        if(IndexOfBlock!=-1) {
            list.get(type - 1).get(IndexOfBlock).setSprite(0);
            list.get(type - 1).get(IndexOfBlock).setSrc();
        }
    }
    //method to get index on list according to type,i,j if cant find return -1
    public int GetIndex(int type,int i, int j){
        try {
            for (int x = 0; x < list.get(type - 1).size(); x++)
                if (list.get(type - 1).get(x).getI() == i && list.get(type - 1).get(x).getJ() == j)
                    return x;
            return -1;
        }
        catch (Exception e) {
            return -1;
        }
    }
    //method to get block according to type ,i,j if cant find return null
    public Block getBlock(int type, int i, int j) {
        for (Block block : list.get(type-1))
            if (block.getI() == i && block.getJ() == j)
                return block;
        return null;
    }
}
