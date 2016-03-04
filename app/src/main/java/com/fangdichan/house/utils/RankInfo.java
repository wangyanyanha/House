package com.fangdichan.house.utils;

/**
 * Created by city on 2015/8/21.
 * Class for house.util
 */
public class RankInfo {
    //    int rank;
    String rank, nick, income, peoplenum;

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRank() {
        return rank;
    }

    public RankInfo() {

        rank = "";
        nick = "";
        income = "";
        peoplenum = "";

    }


    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getPeoplenum() {
        return peoplenum;
    }

    public void setPeoplenum(String peoplenum) {
        this.peoplenum = peoplenum;
    }
}
