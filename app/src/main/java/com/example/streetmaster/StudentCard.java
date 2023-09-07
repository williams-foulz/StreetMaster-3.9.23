package com.example.streetmaster;

public class StudentCard extends Student {

    private String scId;
    private int scTotLessons = 0;
    private int scTotTests = 0;
    private boolean scInTest;
    private boolean scBox1;
    private boolean scBox2;
    private boolean scBox3;
    private boolean scBox4;
    private boolean scBox5;
    private boolean scBox6;

    public StudentCard(String scId, int scTotLessons, int scTotTests, boolean scInTest, boolean scBox1, boolean scBox2, boolean scBox3, boolean scBox4, boolean scBox5, boolean scBox6) {
        this.scId = scId;
        this.scTotLessons = scTotLessons;
        this.scTotTests = scTotTests;
        this.scInTest = scInTest;
        this.scBox1 = scBox1;
        this.scBox2 = scBox2;
        this.scBox3 = scBox3;
        this.scBox4 = scBox4;
        this.scBox5 = scBox5;
        this.scBox6 = scBox6;
    }

    public boolean isScBox2() {
        return scBox2;
    }

    public void setScBox2(boolean scBox2) {
        this.scBox2 = scBox2;
    }

    public boolean isScBox3() {
        return scBox3;
    }

    public void setScBox3(boolean scBox3) {
        this.scBox3 = scBox3;
    }

    public boolean isScBox4() {
        return scBox4;
    }

    public void setScBox4(boolean scBox4) {
        this.scBox4 = scBox4;
    }

    public boolean isScBox5() {
        return scBox5;
    }

    public void setScBox5(boolean scBox5) {
        this.scBox5 = scBox5;
    }

    public boolean isScBox6() {
        return scBox6;
    }

    public void setScBox6(boolean scBox6) {
        this.scBox6 = scBox6;
    }

    public String getScId() {
        return scId;
    }

    public void setScId(String scId) {
        this.scId = scId;
    }

    public int getScTotLessons() {
        return scTotLessons;
    }

    public void setScTotLessons(int scTotLessons) {
        this.scTotLessons = scTotLessons;
    }

    public int getScTotTests() {
        return scTotTests;
    }

    public void setScTotTests(int scTotTests) {
        this.scTotTests = scTotTests;
    }

    public boolean isScInTest() {
        return scInTest;
    }

    public void setScInTest(boolean scInTest) {
        this.scInTest = scInTest;
    }

    public boolean isScBox1() {
        return scBox1;
    }

    public void setScBox1(boolean scBox1) {
        this.scBox1 = scBox1;
    }
}
