package gomoku;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Tuan Anh Dep Zai
 */
public class Computer {

    private int MangDiemTanCong[] = {0, 5, 30, 300, 5000}; // diem tan cong
    private int MangDiemPhongThu[] = {0, 2, 10, 100, 2000};  //diem phong thu
    // AI uu tien tan cong hon, nen diem tan cong se cao hon phong thu

    private int[] markListForCell = {10, 35, 35, 50, 50, 50, 100, 100, 9000, 5000};
    private int mark;
    private int size;  // kich co cua ban co, cu the ta se xet ban co size bang 20
    private char player;
    private char computer;
    private String[] valueX = {"\\sxx\\s", "\\sxxxo",
        "oxxx\\s", "\\sxxx\\s", "\\sx xx\\s", "\\sxx x\\s", "\\sxxxxo", "oxxxx\\s", "\\sxxxx\\s", "xxxxx"};
    private String[] valueO = {"\\soo\\s", "\\sooox", "xooo\\s",
        "\\sooo\\s", "\\so oo\\s", "\\soo o\\s", "\\soooox", "xoooo\\s", "\\soooo\\s", "ooooo"};

    public Computer(int n) {

        size = n;   // kich thuoc ban co 
        computer = 'x';     // nguoi choi la may tinh (AI)
        player = 'o';          // nguoi choi la Player
    }

    //reset lai toan bo diem
    // ham nay nhan gia tri dau  vao la luot choi cua nguoi danh, va ban co hien co 
    // va tra lai gia tri la gia tri cua tat cac o trong ban co tren
    // tinh diem dua tren 2 tieu chi tan cong va phong thu, dua tren 2 mang diem tan cong va phong thu nhu o tren da kahi bao
    public int[][] setScoreForCell(char thePlayer, char[][] cell) {

        int[][] mark = new int[size][size]; // bang diem cua ca ban co 

        setScoreFollowRow(mark, thePlayer, cell);  //setDiem theo hang 
        setScoreFollowColumn(mark, thePlayer, cell); // setDiem theo cac cot cua ban co
        setScoreFollowDuongCheoLen(mark, thePlayer, cell);  // setDiem theo cac duong cheo co huong len tren
        setScoreFollowDuongCheoXuong(mark, thePlayer, cell);  // setDiem theo cac duong cheo co huong xuong 
        return mark;
    }

    public void setScoreFollowRow(int[][] mark, char thePlayer, char[][] cell) {
        int row, column;
        int countPlayer;
        int countComputer;
        int i = 0;
        for (row = 0; row < size; row++) {
            for (column = 0; column < size - 4; column++) {
                countPlayer = 0;
                countComputer = 0;
                for (i = 0; i < 5; i++) {
                    if (cell[row][column + i] == computer) {
                        countComputer++;
                    }
                    if (cell[row][column + i] == player) {
                        countPlayer++;
                    }
                }
                if (countComputer * countPlayer == 0 && countComputer != countPlayer) {
                    for (i = 0; i < 5; i++) {
                        if (cell[row][column + i] == ' ') {
                            if (countComputer == 0) {
                                if (thePlayer == computer) {
                                    mark[row][column + i] += MangDiemPhongThu[countPlayer];
                                } else {
                                    mark[row][column + i] += MangDiemTanCong[countPlayer];
                                }

                            }
                            if (countPlayer == 0) {
                                if (thePlayer == player) {
                                    mark[row][column + i] += MangDiemPhongThu[countComputer];
                                } else {
                                    mark[row][column + i] += MangDiemTanCong[countComputer];
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    public void setScoreFollowColumn(int[][] mark, char thePlayer, char[][] cell) {
        int row, column;
        int countPlayer;
        int countComputer;
        int i;
        for (column = 0; column < size; column++) {

            for (row = 0; row < size - 4; row++) {

                countPlayer = 0;
                countComputer = 0;
                for (i = 0; i < 5; i++) {
                    if (cell[row + i][column] == computer) {
                        countComputer++;
                    }
                    if (cell[row + i][column] == player) {
                        countPlayer++;
                    }
                }
                if (countComputer * countPlayer == 0 && countComputer != countPlayer) {
                    for (i = 0; i < 5; i++) {
                        if (cell[row + i][column] == ' ') {
                            if (countComputer == 0) {
                                if (thePlayer == computer) {
                                    mark[row + i][column] += MangDiemPhongThu[countPlayer];
                                } else {
                                    mark[row + i][column] += MangDiemTanCong[countPlayer];
                                }

                            }
                            if (countPlayer == 0) {
                                if (thePlayer == player) {
                                    mark[row + i][column] += MangDiemPhongThu[countComputer];
                                } else {
                                    mark[row + i][column] += MangDiemTanCong[countComputer];
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    public void setScoreFollowDuongCheoXuong(int[][] mark, char thePlayer, char[][] cell) {
        int row, column;
        int countPlayer;
        int countComputer;
        int i;
        for (row = 0; row < size - 4; row++) {
            for (column = 0; column < size - 4; column++) {

                countPlayer = 0;
                countComputer = 0;
                for (i = 0; i < 5; i++) {
                    if (cell[row + i][column + i] == computer) {
                        countComputer++;
                    }
                    if (cell[row + i][column + i] == player) {
                        countPlayer++;
                    }
                }
                if (countComputer * countPlayer == 0 && countComputer != countPlayer) {
                    for (i = 0; i < 5; i++) {
                        if (cell[row + i][column + i] == ' ') {
                            if (countComputer == 0) {
                                if (thePlayer == computer) {
                                    mark[row + i][column + i] += MangDiemPhongThu[countPlayer];
                                } else {
                                    mark[row + i][column + i] += MangDiemTanCong[countPlayer];
                                }

                            }
                            if (countPlayer == 0) {
                                if (thePlayer == player) {
                                    mark[row + i][column + i] += MangDiemPhongThu[countComputer];
                                } else {
                                    mark[row + i][column + i] += MangDiemTanCong[countComputer];
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    public void setScoreFollowDuongCheoLen(int[][] mark, char thePlayer, char[][] cell) {
        int row, column;
        int countPlayer;
        int countComputer;
        int i;
        for (row = 4; row < size; row++) {
            for (column = 0; column < size - 4; column++) {

                countPlayer = 0;
                countComputer = 0;
                for (i = 0; i < 5; i++) {
                    if (cell[row - i][column + i] == computer) {
                        countComputer++;
                    }
                    if (cell[row - i][column + i] == player) {
                        countPlayer++;
                    }
                }
                if (countComputer * countPlayer == 0 && countComputer != countPlayer) {
                    for (i = 0; i < 5; i++) {
                        if (cell[row - i][column + i] == ' ') {
                            if (countComputer == 0) {
                                if (thePlayer == computer) {
                                    mark[row - i][column + i] += MangDiemPhongThu[countPlayer];
                                } else {
                                    mark[row - i][column + i] += MangDiemTanCong[countPlayer];
                                }

                            }
                            if (countPlayer == 0) {
                                if (thePlayer == player) {
                                    mark[row - i][column + i] += MangDiemPhongThu[countComputer];
                                } else {
                                    mark[row - i][column + i] += MangDiemTanCong[countComputer];
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    public int valOfCell(char[][] square) {
        String s = "";
        int row, column;

        //hàng
        for (row = 0; row < size; row++) {
            s += ";";
            for (column = 0; column < size; column++) {
                s += square[row][column];
            }
        }
        //cột
        for (row = 0; row < size; row++) {
            s += ";";
            for (column = 0; column < size; column++) {
                s += square[column][row];
            }
        }
        //chéo xuống
        for (row = 0; row < size - 4; row++) {
            s += ";";
            for (column = 0; column < size - row; column++) {
                s += square[row + column][column];
            }
            if (row < size - 5) {
                s += ";";
                for (column = 0; column < size - row - 1; column++) {
                    s += square[column][row + column + 1];
                }
            }
        }
        // chéo lên
        for (row = 4; row < size; row++) {
            s += ";";
            for (column = 0; column <= row; column++) {
                s += square[row - column][column];
            }
        }
        for (row = 0; row < size - 5; row++) {
            s += ";";
            for (column = size - 1; column > row; column--) {
                s += square[column][size - column + row];
            }
        }
        s += ";";
        mark = 0;
        for (int i = 0; i < valueX.length; i++) {
            int temp1 = 0, temp2 = 0;
            Pattern p = Pattern.compile(valueX[i]);
            Matcher m = p.matcher(s); // get a matcher object
            while (m.find()) {
                temp1++;
            }
            p = Pattern.compile(valueO[i]);
            m = p.matcher(s);
            while (m.find()) {
                temp2++;
            }
            mark += temp2 * markListForCell[i];//điểm cho O, O là máy
            mark -= temp1 * markListForCell[i];//điểm cua r nười chơi

        }
        return mark;
    }

    //tìm ra 3 điểm có mức điểm cao nhất cho vao danh sach ung cu vien
    // du lieu tra ve la mang gom co 3 phan tu chua nhung phan tu trong ban co mang diem cao nhat
    public ArrayList<Square> findThreeMax(int[][] mark) {
        // dung mang a de luu giu 3 gia tri co muc diem cao nhat 
        ArrayList<Square> listMaxNode = new ArrayList<>();
        ArrayList<Square> b = new ArrayList<>();
        Random rand = new Random();
        int MaxScore = 0;
        int i, j, k, t;  // cac bien phuc vu cho nhiem vu dem

        for (i = 0; i < 1; i++) {
            MaxScore = 1;
            b.clear();
            t = 0;
            for (j = 0; j < size; j++) {
                for (k = 0; k < size; k++) {

                    if (mark[j][k] > MaxScore) {
                        MaxScore = mark[j][k];
                        b.clear();
                        t = 0;
                        Square square = new Square(j, k, MaxScore);
                        b.add(square);
                    } else if (mark[j][k] == MaxScore) {
                        Square square = new Square(j, k, MaxScore);
                        b.add(square);
                        t++;
                    }
                }

            }
            for (j = 0; j < b.size(); j++) {

                mark[b.get(j).getRow()][b.get(j).getCol()] = 0;

            }

            listMaxNode.add(b.get(0));
            if (b.get(0).mark > 1500 || b.get(0).mark == 2 || b.get(0).mark == 1) {
                break;
            }
        }
        return listMaxNode;
    }

    public Square findMax(int[][] mark) {
        // dung mang a de luu giu  gia tri co muc diem cao nhat 
       // ArrayList<Square> listMaxNode = new ArrayList<>();
       // ArrayList<Square> b = new ArrayList<>();
        Random rand = new Random();
        int MaxScore = 0;
        int i, j, k, t;  // cac bien phuc vu cho nhiem vu dem
        Square square = null ;
        for (j = 0; j < size; j++) {
            for (k = 0; k < size; k++) {

                if (mark[j][k] > MaxScore) {
                    MaxScore = mark[j][k];
                 //   b.clear();

                    square = new Square(j, k, MaxScore);
                  //  b.add(square);
                } else if (mark[j][k] == MaxScore) {
                    square = new Square(j, k, MaxScore);
                   // b.add(square);

                }
            }

        }
//        for (j = 0; j < b.size(); j++) {
//
//            mark[b.get(j).getRow()][b.get(j).getCol()] = 0;
//
//        }

//        listMaxNode.add(b.get(0));

        return square ;
    }

    public Square Mini_Max(int depth, char[][] u) {
        // co them do sau, cu the depth = 5 ;\
        // do sau giup khoanh vunh khong gian tim kiem cho chung ta hon
        char[][] square = new char[size][size];//bàn cờ
        int[][] mark;// mảng điểm cho ban co 
        int Depth = depth; // do sau gioi han, cu the : 5 

        Random rand = new Random();

        for (int i = 0; i < size; i++) {// tao ban sao cac quan co hien tai
            for (int j = 0; j < size; j++) {
                square[i][j] = u[i][j];
            }
        }
        ArrayList<Square> threeHighestScore = new ArrayList();
        mark = setScoreForCell('o', square); // tính điểm theo o
        threeHighestScore = findThreeMax(mark);
        Square squareMax = findMax(mark);
        int max = Integer.MIN_VALUE;
        ArrayList<Square> listApplicate = new ArrayList();

        for (int i = 0; i < threeHighestScore.size(); i++) {

            square[threeHighestScore.get(i).getRow()][threeHighestScore.get(i).getCol()] = 'o';

           // int t = Min(depth, square, alpha , beta);
            int currentScore = min_Score(Depth, square);
            if (max < currentScore) {
                max = currentScore;
                listApplicate.clear();
                listApplicate.add(threeHighestScore.get(i));
            } else if (max == currentScore) {
                listApplicate.add(threeHighestScore.get(i));
            }
           square[threeHighestScore.get(i).getRow()][threeHighestScore.get(i).getCol()] = ' ';

        }

        return listApplicate.get(0);//khí có nhiều nút cùng giá trị thì chọn ngẫu nhiên 1 nút
    }

    public int min_Score(int depth, char[][] square) {  // person 
        int[][] mark = new int[size][size];

        int Value = valOfCell(square);

        if (depth == 0 || Math.abs(Value) > 800) {
            // khi do sau tro ve 0 thi thuat toan ngung tim kiem 

            return Value;
        } else {
            int minScore = Integer.MAX_VALUE;
            ArrayList<Square> threeHighestScore = new ArrayList();
            mark = setScoreForCell('x', square);
            threeHighestScore = findThreeMax(mark);
           // threeHighestScore = findMax(mark);
            for (int i = 0; i < threeHighestScore.size(); i++) {

                square[threeHighestScore.get(i).getRow()][threeHighestScore.get(i).getCol()] = 'x';
                int currentScore = max_Score(depth - 1, square);
                if (currentScore < minScore) {
                    minScore = currentScore;
                }
                square[threeHighestScore.get(i).getRow()][threeHighestScore.get(i).getCol()] = ' ';

            }
            return minScore;
        }
    }

    public int max_Score(int depth, char[][] square) {  //may tinh 
        int[][] mark = new int[size][size];
        int Val = valOfCell(square);
        if (depth == 0 || Math.abs(Val) > 800) {
            return Val;
        } else {
            int maxScore = Integer.MIN_VALUE;
            ArrayList<Square> threeHighestScore = new ArrayList();
            mark = setScoreForCell('o', square);
            threeHighestScore = findThreeMax(mark);
           //  threeHighestScore = findMax(mark);
            //setScore('o', square, temp);
            //System.out.println("Max size: "+temp.size());
            for (int i = 0; i < threeHighestScore.size(); i++) {
                square[threeHighestScore.get(i).getRow()][threeHighestScore.get(i).getCol()] = 'o';
                int currentScore = min_Score(depth - 1, square);
                if (currentScore > maxScore) {
                    maxScore = currentScore;
                }
                square[threeHighestScore.get(i).getRow()][threeHighestScore.get(i).getCol()] = ' ';

            }
            return maxScore;
        }
    }
}
