package a_s.bloxforever;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import java.util.ArrayList;

/**
 * Created by sami on 20-May-15.
 */
public class GameView extends View {
    private char[][] map;
    private int numberOfMoves;
    private int ScreenWidth, ScreenHeight;
    private final int MatrixWidth = 10, MatrixHeight = 14;
    private BlockList blockList;
    private boolean touched = false;
    private int i = -1, j = -1;
    private double x = 0, y = 0;
    private GamePage gamePage;
    private ArrayList<char[][]> UndoMap=new ArrayList<char[][]>();
    public GameView(Context context, char[][] map ,GamePage gamePage) {
        super(context);
        this.map = map;
        this.gamePage=gamePage;
        numberOfMoves=0;
        //Set Width and height screen
        SetResolution();
        //Set Blocklist<blocks>
        SetBlockList();
    }
    //save map matrix on undo array
    public void SaveUndo(){
        if(!IsFalling() && !IsDisappearing()) {
            char[][] x = new char[map.length][map[0].length];
            for (int i = 0; i < map.length; i++)
                for (int j = 0; j < map[i].length; j++)
                    x[i][j] = this.map[i][j];
            UndoMap.add(x);
        }
    }
    //method for checking if there is blocks is disappearing
    public boolean IsDisappearing(){
        for (int i = 2; i < 10; i++) {
            ArrayList<Block> blocks = blockList.GetBlockList(i);//get all blocks
            int[] indexI = new int[blocks.size()], indexJ = new int[blocks.size()];
            //put i and j of All blocks with same color on i&j arrays
            for (int j = 0; j < indexI.length; j++) {
                indexI[j] = blocks.get(j).getI();
                indexJ[j] = blocks.get(j).getJ();
            }
            //check if 2 block beside each other if yes return true
            if (CheckBoom(indexI, indexJ))
                return true;
            else if (blocks.size() == 3) {//else check if there is another block have same color if yes swap on i,j array
                int temp = indexI[2];
                indexI[2] = indexI[1];
                indexI[1] = temp;
                temp = indexJ[2];
                indexJ[2] = indexJ[1];
                indexJ[1] = temp;
                if (CheckBoom(indexI, indexJ))//check
                    return true;
                 else {//else check from another side
                    temp = indexI[0];
                    indexI[0] = indexI[2];
                    indexI[2] = temp;
                    temp = indexJ[0];
                    indexJ[0] = indexJ[2];
                    indexJ[2] = temp;
                    if (CheckBoom(indexI, indexJ))//check
                        return true;
                }
            }
        }
        //if not return true so its false
        return false;
    }
    //method for checking if there is block is falling
    public boolean IsFalling(){
        for (int i = 2; i < 10; i++) {
            ArrayList<Block> blocks = blockList.GetBlockList(i);
            for (Block block : blocks)
                if (map[block.getI() + 1][block.getJ()] == '0')//if the index down the block is empty (equal to 0) so its falling
                    return true;
        }
        return false;
    }
    //to set Resolution of your device screen
    public void SetResolution() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        this.ScreenWidth = displayMetrics.widthPixels;
        this.ScreenHeight = displayMetrics.heightPixels;
    }
    //to set all the blocks on the map in blockList(arrayList)
    public void SetBlockList() {
        int width = ScreenWidth / MatrixWidth;
        int hight = ScreenHeight / MatrixHeight;
        this.blockList = new BlockList();
        Bitmap blocks = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.blocks), width * 5, hight * 10, false);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if(i==0 && j<3){//if its true so its wall or button so the type of block is 0
                    this.blockList.AddBlock(i, j, 0, j, blocks, width, hight);
                }
                if (map[i][j] != '0')
                    this.blockList.AddBlock(i, j, Integer.valueOf(String.valueOf(map[i][j])), 0, blocks, width, hight);
            }
        }
    }
    //method check if touched the screen
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            int width = ScreenWidth / MatrixWidth;
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN://if touched
                    x = event.getX();
                    y = event.getY();
                    i = (int) y / (ScreenHeight / MatrixHeight);
                    j = (int) x / (ScreenWidth / MatrixWidth);
                    //check if block is touched
                    if (map[i][j] != '0' && map[i][j] != '1' && i != 0)
                        touched = true;
                    //if not block then check if the click is on button
                    else if (i == 0 && j < 3) {
                        //if click on pause button
                        if (j == 0) {
                            this.gamePage.onPause();
                        }
                        //if click on undo button
                        else if (j == 1) {
                            if(UndoMap.size()>0) {
                                numberOfMoves--;
                                char [][] tmp = UndoMap.get(UndoMap.size() - 1);
                                for(int i=0;i<tmp.length;i++)
                                    for(int j =0;j<tmp[i].length;j++)
                                        this.map[i][j] = tmp[i][j];
                                UndoMap.remove(UndoMap.size() - 1);
                                SetBlockList();
                                invalidate();
                            }
                        }
                        //if click on rest button
                        else if (j == 2) {
                            this.gamePage.ResetButton();
                            numberOfMoves=0;
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP://when you raises your finger from the screen
                    touched = false;//return false
                    new AnimationThread().execute('r');//check if there is block is falling
                    new AnimationThread().execute('b');//check if there is block is Disappearing
                    break;
                case MotionEvent.ACTION_MOVE://if you move your finger on screen(move block)
                    //if you touched on block and there not block is falling and Disappearing
                    if (touched && !IsFalling() && !IsDisappearing()) {
                        if (event.getX() > x + width) {//if you move your finger right
                            if (canMove(0)) {//if the block can move
                                MoveBlock(0);
                                invalidate();
                                j++;
                                MoveBlock(2);//if there is block moving down
                                x = event.getX();
                            }
                        } else if (event.getX() < x - width) {//if move left
                            if (canMove(1)) {//if the block can move
                                MoveBlock(1);
                                invalidate();
                                j--;
                                MoveBlock(2);//if there is block moving down
                                x = event.getX();
                            }
                        }
                    }
                    break;
            }
        }catch (ArrayIndexOutOfBoundsException e){
            return false;
        }
        return true;
    }
    //method to get number of moves for sign your score on parse
    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    //method to move (direction=(right=0,left=1,down=2)) the block
    public void MoveBlock(int direction) {
        switch (direction) {
            case 0://right
                SaveUndo();//save the old mao on undo array
                //update BlockList and map matrix
                blockList.updateBlock(i, j, i, j + 1, Integer.valueOf(String.valueOf(map[i][j])), 1);
                map[i][j + 1] = map[i][j];
                map[i][j] = '0';
                gamePage.Sounds(0);//move sound
                numberOfMoves++;
                break;
            case 1://left
                SaveUndo();//save the old mao on undo array
                //update BlockList and map matrix
                blockList.updateBlock(i, j, i, j - 1, Integer.valueOf(String.valueOf(map[i][j])), 2);
                map[i][j - 1] = map[i][j];
                map[i][j] = '0';
                gamePage.Sounds(0);//move sound
                numberOfMoves++;
                break;
            case 2://down
                    new AnimationThread().execute('d');//check if move down
                    new AnimationThread().execute('b');//check if there is block will remove
                break;
        }
    }
    //method to checking if block can move (0=right,1=left)
    public boolean canMove(int direction) {
        //right
        if (direction == 0) {
            if (map[i][j + 1] == '0' && map[i + 1][j] != '0' && map[i + 1][j] != map[i][j])
                return true;
            else return false;
        }
        //left
        else if (direction == 1) {
            if (map[i][j - 1] == '0' && map[i + 1][j] != '0' && map[i + 1][j] != map[i][j])
                return true;
            else return false;
        }
        return false;
    }
    //method to check if there are no block on screen(win the game)
    public void FinishTheGame(){
        if(blockList.HasNoBlocks()){
            gamePage.NextLevel();
        }
    }
    //method onDraw to draw the map(matrix)on screen
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            int x = 0, y = 0;
            int width = ScreenWidth / MatrixWidth;//width its Screen width div to matrix width
            int hight = ScreenHeight / MatrixHeight;//height its Screen height div to matrix height
            canvas.drawARGB(255, 30, 30, 30);
            for (int i = 0; i < map.length; i++) {
                x = 0;
                for (int j = 0; j < map[i].length; j++, x += width) {
                    if (i == 0 && j < 3) {
                        Rect dst = new Rect(x, y, x + width, y + hight);//dest its rectangle that can draw on screen according to x,y
                        canvas.drawBitmap(this.blockList.getBlock(1, i, j).getBlock(), this.blockList.getBlock(1, i, j).getSrc(), dst, null);
                    }
                    if (map[i][j] != '0') {
                        int type = Integer.valueOf(String.valueOf(map[i][j]));//change type char to int
                        Rect dst = new Rect(x, y, x + width, y + hight);//dest its rectangle that can draw on screen according to x,y
                        canvas.drawBitmap(this.blockList.getBlock(type, i, j).getBlock(), this.blockList.getBlock(type, i, j).getSrc(), dst, null);
                    }
                }
                y += hight;
            }
            FinishTheGame();
        }catch (NullPointerException e){

        }
    }
    //method to check if there is 2 blocks same color beside each others then return true else return false
    public boolean CheckBoom(int[] i,int[] j){
        try {
            if (((i[0] == i[1] - 1 && j[0] == j[1]) || (i[0] == i[1] + 1 && j[0] == j[1]) ||
                    (i[0] == i[1] && j[0] == j[1] - 1) || (i[0] == i[1] && j[0] == j[1] + 1))) {
                return true;
            }
        }catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }
    //method to remove 2/3 blocks from the map
    public void RemoveBlock(int NumOfB,ArrayList<Block> blocks,int[] i,int[] j){
        try {
            //2 blocks
            if (NumOfB == 2) {
                //update BlockList and map matrix
                map[i[0]][j[0]] = '0';
                map[i[1]][j[1]] = '0';
                blockList.RemovBlock(blockList.getBlock(blocks.get(0).getTypeOfBlock(), i[1], j[1]), blocks.get(1).getTypeOfBlock());
                blockList.RemovBlock(blockList.getBlock(blocks.get(0).getTypeOfBlock(), i[0], j[0]), blocks.get(0).getTypeOfBlock());
            }
            //3 blocks
            else {
                //update BlockList and map matrix
                map[i[0]][j[0]] = '0';
                map[i[1]][j[1]] = '0';
                map[i[2]][j[2]] = '0';
                blockList.RemovBlock(blocks.get(2), blocks.get(2).getTypeOfBlock());
                blockList.RemovBlock(blocks.get(1), blocks.get(1).getTypeOfBlock());
                blockList.RemovBlock(blocks.get(0), blocks.get(0).getTypeOfBlock());
            }
            gamePage.Sounds(2);//remove sound
            MoveBlock(2);//check if there is block will move down
        }catch (ArrayIndexOutOfBoundsException e) {

        }
    }
    //class for Animation (block is falling or block is moving or 2/3 block will removed)
    private class AnimationThread extends AsyncTask<Character, Void, Void> {

        //method work in background According to params if params equal to 'd' so the type of animation is moving down
        //if 'r' so type of animation is to return all sprite
        //if 'b' so type of animation is to remove blocks
        @Override
        protected Void doInBackground(Character... params) {
            try {
                char typeOfAnitmation = params[0];
                switch (typeOfAnitmation) {
                    case 'd'://moving down
                        for (int i = 2; i < 10; i++) {
                            ArrayList<Block> blocks = blockList.GetBlockList(i);//get all blocks
                            for (Block block : blocks)
                                if (map[block.getI() + 1][block.getJ()] == '0') {
                                    int in = block.getI();//save index
                                    while (map[in + 1][block.getJ()] == '0') {
                                        //update block list and map matrix
                                        blockList.updateBlock(in, block.getJ(), in + 1, block.getJ(), Integer.valueOf(String.valueOf(map[in][block.getJ()])), 3);
                                        map[in + 1][block.getJ()] = map[in][block.getJ()];
                                        map[in][block.getJ()] = '0';
                                        in++;
                                        publishProgress();//to called OnDraw method
                                        try {
                                            Thread.sleep(100);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    gamePage.Sounds(1);//falling sound
                                    i = 1;//return i=1 to check if there is more block will falling after the update
                                }
                        }
                        break;
                    case 'r'://return sprite
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        publishProgress();//called OnDraw method
                        break;
                    case 'b'://boom
                        for (int i = 2; i < 10; i++) {
                            ArrayList<Block> blocks = blockList.GetBlockList(i);//get all blocks
                            int[] indexI = new int[blocks.size()], indexJ = new int[blocks.size()];
                            //put i and j of All blocks with same color on i&j arrays
                            for (int j = 0; j < indexI.length; j++) {
                                indexI[j] = blocks.get(j).getI();
                                indexJ[j] = blocks.get(j).getJ();
                            }
                            //Check if 2 block are next to each other
                            if (CheckBoom(indexI, indexJ)) {
                                int numberOfBlocks = 2;
                                //if blocks list from same color have 3 blocks
                                if (blocks.size() == 3) {
                                    //swap the i&j arrays to check if him beside each other
                                    int temp = indexI[2];
                                    indexI[2] = indexI[1];
                                    indexI[1] = temp;
                                    temp = indexJ[2];
                                    indexJ[2] = indexJ[1];
                                    indexJ[1] = temp;
                                    //if yes numberOfBlocks=3
                                    if (CheckBoom(indexI, indexJ)) {
                                        numberOfBlocks = 3;
                                    }
                                    //else swap i&j arrays to see if they beside each other from another side
                                    else {
                                        temp = indexI[0];
                                        indexI[0] = indexI[2];
                                        indexI[2] = temp;
                                        temp = indexJ[0];
                                        indexJ[0] = indexJ[2];
                                        indexJ[2] = temp;
                                        //if yes numberOfBlocks=3
                                        if (CheckBoom(indexI, indexJ))
                                            numberOfBlocks = 3;
                                        else {
                                            //return the swap to remove 2 blocks
                                            temp = indexI[1];
                                            indexI[1] = indexI[2];
                                            indexI[2] = temp;
                                            temp = indexJ[1];
                                            indexJ[1] = indexJ[2];
                                            indexJ[2] = temp;
                                        }
                                    }
                                }
                                //update sprite to remove the block according to numberOfBlocks
                                for (int j = 0; j < numberOfBlocks; j++)
                                    blockList.updateBlock(indexI[j], indexJ[j], indexI[j], indexJ[j], i, 4);
                                publishProgress();//called to method OnDraw
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                //remove blocks
                                RemoveBlock(numberOfBlocks, blocks, indexI, indexJ);
                                // if there are other two blocks next to each other (same color) (but in another position if the 2 blocks in fist check not beside each other but the third yes)
                            } else if (blocks.size() == 3) {
                                //swap the i&j to check
                                int temp = indexI[2];
                                indexI[2] = indexI[1];
                                indexI[1] = temp;
                                temp = indexJ[2];
                                indexJ[2] = indexJ[1];
                                indexJ[1] = temp;
                                //check if yes removed
                                if (CheckBoom(indexI, indexJ)) {
                                    for (int j = 0; j < 2; j++)
                                        blockList.updateBlock(indexI[j], indexJ[j], indexI[j], indexJ[j], i, 4);
                                    publishProgress();
                                    try {
                                        Thread.sleep(100);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    RemoveBlock(2, blocks, indexI, indexJ);
                                }
                                //else swap for check from another side
                                else {
                                    temp = indexI[0];
                                    indexI[0] = indexI[2];
                                    indexI[2] = temp;
                                    temp = indexJ[0];
                                    indexJ[0] = indexJ[2];
                                    indexJ[2] = temp;
                                    //if yse removed
                                    if (CheckBoom(indexI, indexJ)) {
                                        for (int j = 0; j < 2; j++)
                                            blockList.updateBlock(indexI[j], indexJ[j], indexI[j], indexJ[j], i, 4);
                                        publishProgress();
                                        try {
                                            Thread.sleep(100);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        RemoveBlock(2, blocks, indexI, indexJ);
                                    }
                                }
                            }
                        }
                        publishProgress();//called OnDraw()
                        break;
                }
            }catch (RuntimeException e){
                return null;
            }
            return null;
        }

        //method work on "ground" (screen) he called  method OnDraw to draw the changes
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            invalidate();
        }
        //method work when the Animation finish he returns all the sprites to regular and called method FinishTheGame()
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //after the progress is done the method returns all blocks to original sprite
            for (int i = 2; i < 10; i++) {
                ArrayList<Block> blocks = blockList.GetBlockList(i);
                for (Block block : blocks) {
                    //if block is being deleted from the map no need to return the original sprite
                    if(block.getSprite()!=4)
                        blockList.ReturnSprite(block.getI(), block.getJ(), Integer.valueOf(String.valueOf(map[block.getI()][block.getJ()])));
                }
            }
            FinishTheGame();
        }
    }
}
