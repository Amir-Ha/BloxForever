package a_s.bloxforever;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ameer on 4/27/2015.
 */
public class LevelsDatabase extends SQLiteOpenHelper {
    private final int MatrixWidth=10,MatrixHight=14;
    public static boolean FirstTime=false;
    public LevelsDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //set database and tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "create table if not exists LevelsMaps(num int, map text, canplay int)";
        String query2="create table if not exists Player(name text)";
        db.execSQL(query1);
        db.execSQL(query2);
    }
    //method to add player name
    public void AddPlayer(String name){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cn = new ContentValues();
        cn.put("name",name);
        db.insert("Player", null, cn);
        db.close();
    }
    //method to get player name from database
    public String GetPlayer(){
        String query = "select * from Player";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cr = db.rawQuery(query,null);
        if(cr.moveToFirst()) {
           String name= cr.getString(0);
            db.close();
            return name;
        }
        db.close();
        return null;
    }
    //method to check if there is a player
    public boolean PlayerExists(){
        String query = "select * from Player";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cr = db.rawQuery(query,null);
        if(!cr.moveToFirst()) {
            db.close();
            return false;
        }
        db.close();
        return true;
    }
    //Delete database
    public void DeleteDataBase(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("LevelsMaps", null, null);
        db.close();
    }
    //Method to update level to play
    public void UpdateLevel(int level){
        SQLiteDatabase db = getWritableDatabase();
        String query = "update LevelsMaps set canplay=1 where num='"+level+"'";
        db.execSQL(query);
        db.close();
    }
    //add all maps matrix to LevelsMaps table
    public void AddLevels(){
        String query = "select * from LevelsMaps";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cr = db.rawQuery(query,null);
        if(!cr.moveToFirst()){
            Maps map = new Maps("0000000000" +
                    "0000000000" +
                    "0000000000" +
                    "1111111111" +
                    "1000000001" +
                    "1000000001" +
                    "1234000001" +
                    "1111000001" +
                    "0001302401" +
                    "0001111111" +
                    "0000000000" +
                    "0000000000" +
                    "0000000000" +
                    "0000000000",1,true);
            ContentValues cn = new ContentValues();
            cn.put("map",map.getMap());
            cn.put("num",map.getNum());
            cn.put("canplay",map.Canplay());
            db.insert("LevelsMaps",null,cn);

            map = new Maps("0000000000" +
                    "0000000000" +
                    "0000000000" +
                    "1111111111" +
                    "1000000001" +
                    "1000000041" +
                    "1400000031" +
                    "1200000021" +
                    "1311011111" +
                    "1111110011" +
                    "0000000000" +
                    "0000000000" +
                    "0000000000" +
                    "0000000000",2,false);
            cn = new ContentValues();
            cn.put("map",map.getMap());
            cn.put("num",map.getNum());
            cn.put("canplay",map.Canplay());
            db.insert("LevelsMaps",null,cn);

            map = new Maps("0000000000" +
                    "0000000000" +
                    "0000000000" +
                    "1111111111" +
                    "1000000031" +
                    "1300000021" +
                    "1420000011" +
                    "1111100431" +
                    "0000101111" +
                    "0000101000" +
                    "0000101000" +
                    "0000111000" +
                    "0000000000" +
                    "0000000000",3,false);
            cn = new ContentValues();
            cn.put("map",map.getMap());
            cn.put("num",map.getNum());
            cn.put("canplay",map.Canplay());
            db.insert("LevelsMaps",null,cn);

            map = new Maps("0000000000" +
                    "0000000000" +
                    "0000000000" +
                    "1111111111" +
                    "1345006421" +
                    "1111001111" +
                    "0001001000" +
                    "0111001110" +
                    "0100000410" +
                    "0131116510" +
                    "0121015110" +
                    "0111011100" +
                    "0000000000" +
                    "0000000000",4,false);
            cn = new ContentValues();
            cn.put("map",map.getMap());
            cn.put("num",map.getNum());
            cn.put("canplay",map.Canplay());
            db.insert("LevelsMaps",null,cn);

            map = new Maps("0000000000" +
                    "0000000000" +
                    "0111100000" +
                    "0120100000" +
                    "0115100000" +
                    "0103100000" +
                    "0101100000" +
                    "0140111000" +
                    "0137001000" +
                    "0111040100" +
                    "0125676100" +
                    "0111111100" +
                    "0000000000" +
                    "0000000000",5,false);
            cn = new ContentValues();
            cn.put("map",map.getMap());
            cn.put("num",map.getNum());
            cn.put("canplay",map.Canplay());
            db.insert("LevelsMaps",null,cn);

            map = new Maps("0000000000" +
                    "0000000000" +
                    "1111111111" +
                    "1000000021" +
                    "1400000031" +
                    "1110000021" +
                    "1000000011" +
                    "1000600001" +
                    "1000400001" +
                    "1500600201" +
                    "1400500301" +
                    "1111111111" +
                    "0000000000" +
                    "0000000000",6,false);
            cn = new ContentValues();
            cn.put("map",map.getMap());
            cn.put("num",map.getNum());
            cn.put("canplay",map.Canplay());
            db.insert("LevelsMaps",null,cn);

            map = new Maps("0000000000" +
                    "0000000000" +
                    "0111111110" +
                    "0100000010" +
                    "0100500010" +
                    "0100200010" +
                    "0100300010" +
                    "0100400010" +
                    "0100300010" +
                    "0100200010" +
                    "0100300010" +
                    "0104250010" +
                    "0111111110" +
                    "0000000000",7,false);
            cn = new ContentValues();
            cn.put("map",map.getMap());
            cn.put("num",map.getNum());
            cn.put("canplay",map.Canplay());
            db.insert("LevelsMaps",null,cn);

            map = new Maps("0000000000" +
                    "0000000000" +
                    "0111111100" +
                    "0100026100" +
                    "0100541100" +
                    "0101111000" +
                    "0101111110" +
                    "0107000010" +
                    "0102000010" +
                    "0154070010" +
                    "0123763010" +
                    "0111111110" +
                    "0000000000" +
                    "0000000000",8,false);
            cn = new ContentValues();
            cn.put("map",map.getMap());
            cn.put("num",map.getNum());
            cn.put("canplay",map.Canplay());
            db.insert("LevelsMaps",null,cn);

            map = new Maps("0000000000" +
                    "0000000000" +
                    "0001110000" +
                    "0010021000" +
                    "0100050100" +
                    "0100030100" +
                    "0100340100" +
                    "0100230100" +
                    "0100120100" +
                    "0150014100" +
                    "0010051000" +
                    "0001110100" +
                    "0000000010" +
                    "0000000000",9,false);
            cn = new ContentValues();
            cn.put("map",map.getMap());
            cn.put("num",map.getNum());
            cn.put("canplay",map.Canplay());
            db.insert("LevelsMaps",null,cn);

            map = new Maps("0000000000" +
                    "0000000000" +
                    "1111111111" +
                    "1380000751" +
                    "1240007641" +
                    "1110001111" +
                    "1000000001" +
                    "1001110001" +
                    "1001350001" +
                    "1781231461" +
                    "1111111111" +
                    "0000000000" +
                    "0000000000" +
                    "0000000000",10,false);
            cn = new ContentValues();
            cn.put("map",map.getMap());
            cn.put("num",map.getNum());
            cn.put("canplay",map.Canplay());
            db.insert("LevelsMaps",null,cn);

            map = new Maps("" +
                    "0000000000" +
                    "0000000000" +
                    "0111111110" +
                    "0120106410" +
                    "0114109310" +
                    "0105181110" +
                    "0101190010" +
                    "0100110010" +
                    "0150070010" +
                    "0141111910" +
                    "0132167810" +
                    "0111111110" +
                    "0000000000" +
                    "0000000000",11,false);
            cn = new ContentValues();
            cn.put("map",map.getMap());
            cn.put("num",map.getNum());
            cn.put("canplay",map.Canplay());
            db.insert("LevelsMaps",null,cn);

            map = new Maps("0000000000" +
                    "0000000000" +
                    "0000110000" +
                    "0001001000" +
                    "0018006100" +
                    "0127005310" +
                    "0111001110" +
                    "0189000210" +
                    "0167000110" +
                    "0111140210" +
                    "0190030110" +
                    "0154110310" +
                    "0111111110" +
                    "0000000000",12,false);
            cn = new ContentValues();
            cn.put("map",map.getMap());
            cn.put("num",map.getNum());
            cn.put("canplay",map.Canplay());
            db.insert("LevelsMaps",null,cn);

            map = new Maps("0000000000" +
                    "0000000000" +
                    "1111111111" +
                    "1200000211" +
                    "1300005131" +
                    "1100001041" +
                    "1010010711" +
                    "1001200101" +
                    "1700101001" +
                    "1600456001" +
                    "1111111111" +
                    "0000000000" +
                    "0000000000" +
                    "0000000000",13,false);
            cn = new ContentValues();
            cn.put("map",map.getMap());
            cn.put("num",map.getNum());
            cn.put("canplay",map.Canplay());
            db.insert("LevelsMaps",null,cn);

            map = new Maps("0000000000" +
                    "0000000000" +
                    "1111111111" +
                    "1000002871" +
                    "1000006431" +
                    "1200011111" +
                    "1500500001" +
                    "1300100001" +
                    "1456187001" +
                    "1243179091" +
                    "1111111111" +
                    "0000000000" +
                    "0000000000" +
                    "0000000000",14,false);
            cn = new ContentValues();
            cn.put("map",map.getMap());
            cn.put("num",map.getNum());
            cn.put("canplay",map.Canplay());
            db.insert("LevelsMaps",null,cn);

            map = new Maps("0000000000" +
                    "0000000000" +
                    "0000000000" +
                    "1111111111" +
                    "1201000071" +
                    "1951000081" +
                    "1811000091" +
                    "1700000061" +
                    "1307892431" +
                    "1401111111" +
                    "1651000000" +
                    "1111000000" +
                    "0000000000" +
                    "0000000000",15,false);
            cn = new ContentValues();
            cn.put("map",map.getMap());
            cn.put("num",map.getNum());
            cn.put("canplay",map.Canplay());
            db.insert("LevelsMaps",null,cn);

            map = new Maps("0000000000" +
                    "0000000000" +
                    "1110111111" +
                    "1710101191" +
                    "1810191181" +
                    "1610171151" +
                    "1311161131" +
                    "1200050041" +
                    "1100040011" +
                    "0111030110" +
                    "0001014100" +
                    "0001012100" +
                    "0001111100" +
                    "0000000000",16,false);
            cn = new ContentValues();
            cn.put("map",map.getMap());
            cn.put("num",map.getNum());
            cn.put("canplay",map.Canplay());
            db.insert("LevelsMaps",null,cn);
        }
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //get all maps as ArrayList<map>
    public ArrayList<Maps> GetAllMaps(){
        ArrayList<Maps> mapsList = new ArrayList<Maps>();
        String query = "select * from LevelsMaps";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cr = db.rawQuery(query,null);
        Maps map;
        Boolean Canplay;
        if(cr.moveToFirst()){
            do{
                if(cr.getInt(2) == 1)
                    Canplay=true;
                else Canplay = false;
                map=new Maps(cr.getString(1),cr.getInt(0),Canplay);
                mapsList.add(map);
            }while(cr.moveToNext());
        }
        db.close();
        return mapsList;
    }
    //get  map matrix according to level
    public char[][] GetLevelMap(int levelNum){
        String query = "select * from LevelsMaps where num="+levelNum;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cr = db.rawQuery(query, null);
        if(cr.moveToFirst())
            return ConvertStringToMatrix(cr.getString(1));
        else
            return null;
    }
    //method to convert string to matrix (char [][])
    public char[][] ConvertStringToMatrix(String map){
        char[][] matrix=new char[MatrixHight][MatrixWidth];
        for(int i=0;i<MatrixHight*MatrixWidth;i+=MatrixWidth)
            matrix[i / MatrixWidth] = map.substring(i,i+MatrixWidth).toCharArray();
        return matrix;
    }
}
