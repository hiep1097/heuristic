package com.cqdat.master.thesis.gwoforconstruction;

public class Site {
    public int siteID;  //Mã công trường
    public int SCT;     //Thời gian công trường bắt đầu đỗ bê Tông --> tính bằng Phút (quy về 00:00)
    public int R;       //Số lượng bê tông công trường cần mua
    public String PT;   //Loại cấu kiện

    public int  ABD;    //Thời gian cho phép công trường chờ xe
    public int  ABT;    //Thời gian tối được chờ của xe

    public int numOfTruck;        //Số xe công trường cần mua

    public int CD;      //Thời gian đổ loại cấu kiện

    public void calNumOfTruck(int powOfTruck){
        numOfTruck = R / powOfTruck + ((R % powOfTruck > 0) ? 1 : 0);
    }

    public int calTimeTruckMove(int TDG){
        return  SCT - TDG;
    }

    @Override
    public String toString()
    {
        return siteID + "\tSCT:" + SCT + "\tR:" + String.format("%03d", R) + "\tPT:" + PT + "\t" + ABD + "\t" + ABT + "\ttr:" + numOfTruck + "\tCD:" + CD;
    }
}
