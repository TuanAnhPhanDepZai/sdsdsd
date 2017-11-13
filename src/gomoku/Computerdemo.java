package gomoku;

import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author QUANG
 */


public class Computerdemo {

    private int def_Scode[] = {0, 1, 9, 85, 1538};  //diem chan
    private int atk_Scode[] = {0, 4, 27, 256, 4616}; // diem cong
    private String[] valueX = {"\\sxx\\s", "\\sxxxo",
        "oxxx\\s", "\\sxxx\\s", "\\sx xx\\s", "\\sxx x\\s", "\\sxxxxo", "oxxxx\\s", "\\sxxxx\\s", "xxxxx"};
    private String[] valueO = {"\\soo\\s", "\\sooox", "xooo\\s", 
        "\\sooo\\s", "\\so oo\\s", "\\soo o\\s", "\\soooox", "xoooo\\s", "\\soooo\\s", "ooooo"};
    private int[] markList = {20, 30, 30, 50, 50, 50, 100, 100, 1000, 5000};
    private int mark;
    private int size;
    private char computer = 'x';
    private char player = 'o';

    public Computerdemo(int n) {

        size = n;
        computer = 'x';
        player = 'o';
    }

    //reset lai toan bo diem
    public int[][] setScore(char thePlayer, char[][] cell) {

        int row, column;
        int playerNumber;
        int computerNumber;
        int i = 0;
        //List nutList;

        int[][] mark = new int[size][size];
        //set theo hang
        for (row = 0; row < size; row++) {
            for (column = 0; column < size - 4; column++) {
                playerNumber = 0;
                computerNumber = 0;
                for (i = 0; i < 5; i++) {
                    if (cell[row][column + i] == computer) {
                        computerNumber++;
                    }
                    if (cell[row][column + i] == player) {
                        playerNumber++;
                    }
                }
                if (computerNumber * playerNumber == 0 && computerNumber != playerNumber) {
                    for (i = 0; i < 5; i++) {
                        if (cell[row][column + i] == ' ') {
                            if (computerNumber == 0) {
                                if (thePlayer == computer) {
                                    mark[row][column + i] += def_Scode[playerNumber];
                                } else {
                                    mark[row][column + i] += atk_Scode[playerNumber];
                                }

                            }
                            if (playerNumber == 0) {
                                if (thePlayer == player) {
                                    mark[row][column + i] += def_Scode[computerNumber];
                                } else {
                                    mark[row][column + i] += atk_Scode[computerNumber];
                                }

                            }
                        }
                    }
                }
            }
        }
        //set theo cot
        for (column = 0; column < size; column++) {

            for (row = 0; row < size - 4; row++) {

                playerNumber = 0;
                computerNumber = 0;
                for (i = 0; i < 5; i++) {
                    if (cell[row + i][column] == computer) {
                        computerNumber++;
                    }
                    if (cell[row + i][column] == player) {
                        playerNumber++;
                    }
                }
                if (computerNumber * playerNumber == 0 && computerNumber != playerNumber) {
                    for (i = 0; i < 5; i++) {
                        if (cell[row + i][column] == ' ') {
                            if (computerNumber == 0) {
                                if (thePlayer == computer) {
                                    mark[row + i][column] += def_Scode[playerNumber];
                                } else {
                                    mark[row + i][column] += atk_Scode[playerNumber];
                                }

                            }
                            if (playerNumber == 0) {
                                if (thePlayer == player) {
                                    mark[row + i][column] += def_Scode[computerNumber];
                                } else {
                                    mark[row + i][column] += atk_Scode[computerNumber];
                                }

                            }
                        }
                    }
                }
            }
        }
        //set theo duong cheo xuong
        for (row = 0; row < size - 4; row++) {
            for (column = 0; column < size - 4; column++) {

                playerNumber = 0;
                computerNumber = 0;
                for (i = 0; i < 5; i++) {
                    if (cell[row + i][column + i] == computer) {
                        computerNumber++;
                    }
                    if (cell[row + i][column + i] == player) {
                        playerNumber++;
                    }
                }
                if (computerNumber * playerNumber == 0 && computerNumber != playerNumber) {
                    for (i = 0; i < 5; i++) {
                        if (cell[row + i][column + i] == ' ') {
                            if (computerNumber == 0) {
                                if (thePlayer == computer) {
                                    mark[row + i][column + i] += def_Scode[playerNumber];
                                } else {
                                    mark[row + i][column + i] += atk_Scode[playerNumber];
                                }

                            }
                            if (playerNumber == 0) {
                                if (thePlayer == player) {
                                    mark[row + i][column + i] += def_Scode[computerNumber];
                                } else {
                                    mark[row + i][column + i] += atk_Scode[computerNumber];
                                }

                            }
                        }
                    }
                }
            }
        }
        //set theo duowng cheo len
        for (row = 4; row < size; row++) {
            for (column = 0; column < size - 4; column++) {

                playerNumber = 0;
                computerNumber = 0;
                for (i = 0; i < 5; i++) {
                    if (cell[row - i][column + i] == computer) {
                        computerNumber++;
                    }
                    if (cell[row - i][column + i] == player) {
                        playerNumber++;
                    }
                }
                if (computerNumber * playerNumber == 0 && computerNumber != playerNumber) {
                    for (i = 0; i < 5; i++) {
                        if (cell[row - i][column + i] == ' ') {
                            if (computerNumber == 0) {
                                if (thePlayer == computer) {
                                    mark[row - i][column + i] += def_Scode[playerNumber];
                                } else {
                                    mark[row - i][column + i] += atk_Scode[playerNumber];
                                }

                            }
                            if (playerNumber == 0) {
                                if (thePlayer == player) {
                                    mark[row - i][column + i] += def_Scode[computerNumber];
                                } else {
                                    mark[row - i][column + i] += atk_Scode[computerNumber];
                                }

                            }
                        }
                    }
                }
            }
        }
        return mark;
    }

    public void setScoreFollowRow(int [][] mark, char thePlayer , char [][] cell){
        int row, column;
        int playerNumber;
        int computerNumber;
        int i = 0;
          for (row = 0; row < size; row++) {
            for (column = 0; column < size - 4; column++) {
                playerNumber = 0;
                computerNumber = 0;
                for (i = 0; i < 5; i++) {
                    if (cell[row][column + i] == computer) {
                        computerNumber++;
                    }
                    if (cell[row][column + i] == player) {
                        playerNumber++;
                    }
                }
                if (computerNumber * playerNumber == 0 && computerNumber != playerNumber) {
                    for (i = 0; i < 5; i++) {
                        if (cell[row][column + i] == ' ') {
                            if (computerNumber == 0) {
                                if (thePlayer == computer) {
                                    mark[row][column + i] += def_Scode[playerNumber];
                                } else {
                                    mark[row][column + i] += atk_Scode[playerNumber];
                                }

                            }
                            if (playerNumber == 0) {
                                if (thePlayer == player) {
                                    mark[row][column + i] += def_Scode[computerNumber];
                                } else {
                                    mark[row][column + i] += atk_Scode[computerNumber];
                                }

                            }
                        }
                    }
                }
            }
        }
    }
   
    public void setScoreFollowColumn(int [][] mark, char thePlayer, char [][] cell){
        int row, column;
        int playerNumber;
        int computerNumber;
        int i ;
          for (column = 0; column < size; column++) {

            for (row = 0; row < size - 4; row++) {

                playerNumber = 0;
                computerNumber = 0;
                for (i = 0; i < 5; i++) {
                    if (cell[row + i][column] == computer) {
                        computerNumber++;
                    }
                    if (cell[row + i][column] == player) {
                        playerNumber++;
                    }
                }
                if (computerNumber * playerNumber == 0 && computerNumber != playerNumber) {
                    for (i = 0; i < 5; i++) {
                        if (cell[row + i][column] == ' ') {
                            if (computerNumber == 0) {
                                if (thePlayer == computer) {
                                    mark[row + i][column] += def_Scode[playerNumber];
                                } else {
                                    mark[row + i][column] += atk_Scode[playerNumber];
                                }

                            }
                            if (playerNumber == 0) {
                                if (thePlayer == player) {
                                    mark[row + i][column] += def_Scode[computerNumber];
                                } else {
                                    mark[row + i][column] += atk_Scode[computerNumber];
                                }

                            }
                        }
                    }
                }
            }
        }
    }
    
    public void setScoreFollowDuongCheoXuong(int [][] mark, char thePlayer , char [][] cell){
        int row, column;
        int playerNumber;
        int computerNumber;
        int i ;
         for (row = 0; row < size - 4; row++) {
            for (column = 0; column < size - 4; column++) {

                playerNumber = 0;
                computerNumber = 0;
                for (i = 0; i < 5; i++) {
                    if (cell[row + i][column + i] == computer) {
                        computerNumber++;
                    }
                    if (cell[row + i][column + i] == player) {
                        playerNumber++;
                    }
                }
                if (computerNumber * playerNumber == 0 && computerNumber != playerNumber) {
                    for (i = 0; i < 5; i++) {
                        if (cell[row + i][column + i] == ' ') {
                            if (computerNumber == 0) {
                                if (thePlayer == computer) {
                                    mark[row + i][column + i] += def_Scode[playerNumber];
                                } else {
                                    mark[row + i][column + i] += atk_Scode[playerNumber];
                                }

                            }
                            if (playerNumber == 0) {
                                if (thePlayer == player) {
                                    mark[row + i][column + i] += def_Scode[computerNumber];
                                } else {
                                    mark[row + i][column + i] += atk_Scode[computerNumber];
                                }

                            }
                        }
                    }
                }
            }
        }
    }
    
    public void setScoreFollowDuongCheoLen(int [][] mark , char thePlayer, char [][] cell){
        int row, column;
        int playerNumber;
        int computerNumber;
        int i ;
          for (row = 4; row < size; row++) {
            for (column = 0; column < size - 4; column++) {

                playerNumber = 0;
                computerNumber = 0;
                for (i = 0; i < 5; i++) {
                    if (cell[row - i][column + i] == computer) {
                        computerNumber++;
                    }
                    if (cell[row - i][column + i] == player) {
                        playerNumber++;
                    }
                }
                if (computerNumber * playerNumber == 0 && computerNumber != playerNumber) {
                    for (i = 0; i < 5; i++) {
                        if (cell[row - i][column + i] == ' ') {
                            if (computerNumber == 0) {
                                if (thePlayer == computer) {
                                    mark[row - i][column + i] += def_Scode[playerNumber];
                                } else {
                                    mark[row - i][column + i] += atk_Scode[playerNumber];
                                }

                            }
                            if (playerNumber == 0) {
                                if (thePlayer == player) {
                                    mark[row - i][column + i] += def_Scode[computerNumber];
                                } else {
                                    mark[row - i][column + i] += atk_Scode[computerNumber];
                                }

                            }
                        }
                    }
                }
            }
        }
    }
    public int val(char[][] square) {
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
            mark += temp2 * markList[i];//điểm cho O, O là máy
            mark -= temp1 * markList[i];//điểm cua r nười chơi

        }
        return mark;
    }

    
    
    //tìm ra 4 điểm có mức điểm cao nhất
    public ArrayList<Square> maxNode(int[][] mark) {
        ArrayList<Square> a = new ArrayList<>();
        ArrayList<Square> b = new ArrayList<>();
        int Max = 0;
        int i, j, k, t;
        Random rand = new Random();
        for (i = 0; i < 4; i++) {
            Max = 1;
            b.clear();
            t = 0;
            for (j = 0; j < size; j++) {
                for (k = 0; k < size; k++) {

                    if (mark[j][k] > Max) {
                        Max = mark[j][k];
                        b.clear();
                        t = 0;
                        Square temp = new Square(j, k, Max);
                        b.add(temp);
                    } else if (mark[j][k] == Max) {
                        Square temp = new Square(j, k, Max);
                        b.add(temp);

                        t++;
                    }
                }

            }
            for (j = 0; j < b.size(); j++) {

                mark[b.get(j).getRow()][b.get(j).getCol()] = 0;

            }

            a.add(b.get(rand.nextInt(b.size())));
            if (b.get(0).mark > 1500 || b.get(0).mark == 2 || b.get(0).mark == 1) {
                break;
            }
        }
        return a;
    }
// cat tia alpha beta

  

  
  
///////////////////


    // thuc hien ham Mini_max thay cho anphabeta 
    public Square Mini_max(int depth , char [][] u){
        char [][] square = new char[size][size];  // ban co 
        int [][] mark ;  // bang diem 
        Random random = new Random();
        
        // tao ban sao moi cua cac o co 
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                square[i][j] = u[i][j];
            }
        }
        
        //tinh diem lai toan bo ban co theo 'o', tim ra nhung nuoc di huu ich nhat doi voi o
        // li do : day la luot choi cua may tinh, nguoi danh truoc, nhu vay may tinh 
        // danh lan thu 2, khi do tren ban co , so quan den luon nhieu hon quan trang 
        // playercount > computercount, t uu tien tim nhung nuoc co co uu the cho nguoi choi va danh vao de chan
        mark = setScore('o', square);
        // su dung 1 mang cac square luu tru cac phan tu dang co loi cho nguoi choi<person>
        ArrayList<Square> sixhighestscore = new ArrayList<>() ;   
        sixhighestscore = maxNode(mark);
        int maxScore = Integer.MIN_VALUE;
        // su dung 1 mang cac square luu giu lai cac ung cu vien phu hop 
        ArrayList<Square> listSquare = new ArrayList<>();
        for (int i = 0 ; i< sixhighestscore.size() ; i++){
            // tim cac ung cu vien phu hop voi muc diem cao nhat 
            square[sixhighestscore.get(i).getRow()][sixhighestscore.get(i).getCol()] = 'o';
            // tinh toan diem cua cac ung  cu vien bang thuat toan mini_max 
            int currentScore = min_Score(depth, square);
            if (maxScore < currentScore ){
                maxScore = currentScore;
                listSquare.clear();
                Square s = sixhighestscore.get(i);
                listSquare.add(s);
            }else if (maxScore == currentScore){
                Square s = listSquare.get(i);
                listSquare.add(s);
            }
            square[sixhighestscore.get(i).getRow()][sixhighestscore.get(i).getCol()] = ' ';
        }
        
        return listSquare.get(random.nextInt(listSquare.size()));
    }
      public Square Alpha_beta(int depth, char[][] u) {
        // co them do sau, cu the depth = 5 ;\
        // do sau giup khoanh vunh khong gian tim kiem cho chung ta hon
        char[][] square = new char[size][size];//bàn cờ
        int[][] mark;// mảng điểm 
        int Depth = depth;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        Random rand = new Random();
        Square nut = new Square(0, 0, 0);
        for (int i = 0; i < size; i++) {// tao ban sao cac quan co hien tai
            for (int j = 0; j < size; j++) {
                square[i][j] = u[i][j];
            }
        }
        ArrayList<Square> temp = new ArrayList();
        mark = setScore('o', square); // tính điểm theo o
        temp = maxNode(mark);
        int maxP = -20000;
        ArrayList<Square> list = new ArrayList();
        
        
        
        for (int i = 0; i < temp.size(); i++) {

            square[temp.get(i).getRow()][temp.get(i).getCol()] = 'o';

            int t = Min(depth, square, alpha, beta);
            if (maxP < t) {
                maxP = t;
                list.clear();
                list.add(temp.get(i));
            } else if (maxP == t) {
                list.add(temp.get(i));
            }
            square[temp.get(i).getRow()][temp.get(i).getCol()] = ' ';

        }

        return list.get(rand.nextInt(list.size()));//khí có nhiều nút cùng giá trị thì chọn ngẫu nhiên 1 nút
    }
      
    public int min_Score(int depth, char [][] square){  // person 
        int [][] mark  = new int [size][size] ;
        int val = val(square);  // tinh diem toan bo ban co
        int min  = Integer.MAX_VALUE; 
        if ( depth == 0 || Math.abs(val)  >= 1000){
            return val ;
        }
        else {
            // dinh gia lai ban co theo 'x'
            mark = setScore('x', square) ;
            ArrayList<Square> sixhighestScore = new ArrayList<>();
            sixhighestScore = maxNode(mark);
            for (int i = 0 ; i< sixhighestScore .size(); i++){
                square[sixhighestScore.get(i).getRow()][sixhighestScore.get(i).getRow()] = 'x';
                int currentScore = max_Score(depth-1,square);
                if ( currentScore <= min ){
                    min = currentScore ;
                }
                square[sixhighestScore.get(i).getRow()][sixhighestScore.get(i).getRow()] = ' ';
            }
        }
             return min ;
    }
      public int Min(int depth, char[][] square, int alpha, int beta) {//player
        int[][] mark;// bang diem
        int Val = val(square);
        int t;
        if (depth == 0 || Math.abs(Val) > 930) {
            // khi do sau tro ve 0 thi thuat toan ngung tim kiem 
            
            return Val ;
        } else {
            ArrayList<Square> temp = new ArrayList();
            mark = setScore('x', square);
            temp = maxNode(mark);

            for (int i = 0; i < temp.size(); i++) {

                square[temp.get(i).getRow()][temp.get(i).getCol()] = 'x';
                t = Max(depth - 1, square, alpha, beta);
                beta = (beta < t) ? beta : t;
                square[temp.get(i).getRow()][temp.get(i).getCol()] = ' ';
                if (alpha >= beta) {
                    break;
                }
            }
            return beta;
        }

    }

    public int max_Score(int depth , char [][] square ){  //may tinh 
        int [][] mark ;
        int max = Integer.MIN_VALUE;
        int val = val(square);
        if (depth == 0 || Math.abs(val) > 1000 ){
            return val + depth ;
        }else {
            mark = setScore('o', square);
            ArrayList<Square> sixhighestScore = new ArrayList<>();
            sixhighestScore = maxNode(mark); // tim ra 6 nut co loi the nhat doi voi may tinh
            
            for (int i = 0 ; i < sixhighestScore.size() ; i++){
                square[sixhighestScore.get(i).getRow()][sixhighestScore.get(i).getCol()] = 'o';
                int currentScore = min_Score(depth-1, square);
                if (currentScore >= max){
                    max = currentScore ;
                }
                square[sixhighestScore.get(i).getRow()][sixhighestScore.get(i).getCol()] = ' ';
            }
            
            return max ;
        }
        
    }
      public int Max(int depth, char[][] square, int alpha, int beta) {//computer
        int[][] mark ;
        int Val = val(square);
        int t;

        if (depth == 0 || Math.abs(Val) > 930) {
            return Val;
        } else {
            ArrayList<Square> temp = new ArrayList();
            mark = setScore('o', square);
            temp = maxNode(mark);
            //setScore('o', square, temp);
            //System.out.println("Max size: "+temp.size());
            for (int i = 0; i < temp.size(); i++) {
                square[temp.get(i).getRow()][temp.get(i).getCol()] = 'o';
                t = Min(depth - 1, square, alpha, beta);
                alpha = (alpha > t) ? alpha : t;
                square[temp.get(i).getRow()][temp.get(i).getCol()] = ' ';
                if (alpha >= beta) {
                    break;
                }
            }
            return alpha;
        }
    }
}
