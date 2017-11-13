/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku;

/**
 *
 * @author QUANG
 */
public class Square {
    private int col;
    private int row;
    int mark;

    public Square() {
    }

    public Square(int row, int col, int mark) {
        this.col = col;
        this.row = row;
        this.mark = mark;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
    
    public void set(int row, int col, int mark){
        this.col = col;
        this.row = row;
        this.mark = mark;
    }
}
