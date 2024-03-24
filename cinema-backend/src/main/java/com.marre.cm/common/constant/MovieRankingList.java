package com.marre.cm.common.constant;

/**
 * @author :marRE
 * @Description: 电影排行列表
 * @Date :2024/3/24
 */

public class MovieRankingList {

    public static final String[] listNames = new String[3];

    // 总票房， 国内票房， 国外票房
    static {
        listNames[0] = "totalBoxOfficeList";
        listNames[1] = "domesticBoxOfficeList";
        listNames[2] = "foreignBoxOfficeList";
    }

}
