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
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class GoMoku extends JPanel {

    /**
     * Main routine makes it possible to run GoMoku as a stand-alone
     * application. Opens a window showing a GoMoku panel; the program ends when
     * the user closes the window.
     *
     * @param args
     */
    private JButton btnStartGame;  // bat dau tro choi caro 

    private JButton resignButton;   // Nguoi choi tu nhan thua truoc may tinh

    private JLabel message; 
    private JLabel message1;
    private JLabel message2;
    private JLabel message3;
    private JButton btnUndo = new JButton ("Quay lai") ;
    private JLabel labelNamePlayer  = new JLabel("Nhap ten nguoi choi ");
    private JTextField textField  = new JTextField(8);
    private Computer computer;
    private PanelImage panelImage = new PanelImage();
    
     // stack nay phuc vu cho chuc nang undo, hoan lai cac thao tac cua person truoc do
    public static void main(String[] args) {
        JFrame frame = new JFrame("Trí tuệ nhân tạo - Cờ Caro");
        GoMoku content = new GoMoku();
        frame.setContentPane(content);
        frame.pack();
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screensize.width - frame.getWidth()) / 2,
                (screensize.height - frame.getHeight()) / 2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    
//     * The constructor lays out the panel. The work of the game is all done in
//     * the Board object. A null layout is used, and all setup of sizes and
//     * positions is done here.
//     */
    public GoMoku() {

        setLayout(null);  // I will do the layout myself.
        message = new JLabel();
        message1= new JLabel();
        message2 = new JLabel();
        message3 = new JLabel();
        setPreferredSize(new Dimension(670, 600));

      

        
        Board board = new Board();  // Note: The constructor for the
        //   board also creates the buttons
        //   and label.
        add(board);
        add(btnStartGame);
        add(resignButton);
        add(message);
        add(message1);
        add(message2);
        add(btnUndo);
        add(labelNamePlayer);
        add(textField);
        add(panelImage);
        add(message3);
        /* Set the position and size of each component by calling
         its setBounds() method. */
        message.setText("Hướng dẫn: \n " +"Người chơi nhấn nút Start new game để bắt đầu chơi");
        message1.setText("Người chơi nhấn Resign nếu muốn từ bỏ cuộc choi và nhận thua");
        message2.setText("Người chơi nhấn nút quay lại nếu muốn trở lại nước đi cũ");
        message3.setText("Bạn cố gắng để có 5 ô liên tiếp cùng màu đen là thắng nhé");
        board.setBounds(16, 16, 402, 402); 
        btnStartGame.setBounds(500, 280, 120, 30);
        resignButton.setBounds(500, 340, 120, 30);
        message.setBounds(16, 430, 400, 35);
        message1.setBounds(16,480,400,35);
        message2.setBounds(16,520,400,35);
        message3.setBounds(16, 560, 400, 35);
//        message.setBorder(new LineBorder(Color.yellow));
//        message2.setBorder(new LineBorder(Color.yellow));
//        message1.setBorder(new LineBorder(Color.yellow));
        btnUndo.setBounds(500,400,120,30);
        btnUndo.setEnabled(false);
        labelNamePlayer.setBounds(500,460,120,30);
        textField.setBounds(500,520,120,30);
        panelImage.setBounds(420, 16, 250, 250);
        
    }

    // ----------------------- Nested class -----------------------------------
    /**
     * This class does the work of letting the users
     * play GoMoku, and it displays the checkerboard. In this program, the board
     * has 13 rows and columns of squares.
     */
    class Board extends JPanel implements ActionListener, MouseListener {

        public final int SIZE_BOARD = 20;
        public String namePlayer ;
        int[][] board;   // The data for the board is kept here.  The values
        //   in this array are chosen from the following constants.
        Stack<Point> stackPoint ;
        char[][] charater = new char[SIZE_BOARD][SIZE_BOARD]; 

        static final int EMPTY = 0, // Represents an empty square.
                WHITE = 1, // A white piece.
                BLACK = 2;       // A black piece.

        boolean gameInProgress; // Is a game currently in progress?

        int currentPlayer;      // Whose turn is it now?  The possible values
        //    are WHITE and BLACK.  (This is valid only while
        //    a game is in progress.)

        int win_r1, win_c1, win_r2, win_c2;  // When a player wins by getting five or more
        // pieces in a row, the squares at the
        // ends of the row are (win_r1,win_c1)
        // and (win_r2,win_c2).  A red line is
        // drawn between these squares.  When there
        // are not five pieces in a row, the value of
        // win_r1 is -1.  The values are set in the
        // count() method.  The value of win_r1 is
        // tested in the paintComponent() method.

        /**
         * Constructor. Create the buttons and label. Listen for mouse clicks
         * and for clicks on the buttons. Create the board and start the first
         * game.
         */
        public Board() {
            setBackground(Color.LIGHT_GRAY);
            addMouseListener(this);
            resignButton = new JButton("Resign");
            resignButton.setEnabled(false);
            resignButton.addActionListener(this);
            btnStartGame = new JButton("Start new game");
            btnStartGame.addActionListener(this);
//            message = new JLabel("", JLabel.CENTER);
//            message.setFont(new Font("Serif", Font.BOLD, 14));
//            message.setForeground(Color.GREEN);
            board = new int[SIZE_BOARD][SIZE_BOARD];
            computer = new Computer(SIZE_BOARD);
            gameInProgress = false ;
            //doNewGame();
            stackPoint = new Stack<>(); 
            setEventForBtnUndo();
        }

        /**
         * Respond to user's click on one of the two buttons.
         */
        @Override
        public void actionPerformed(ActionEvent evt) {
            Object src = evt.getSource();
            if (src == btnStartGame) {
                if (textField.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Bạn nhập tên của bạn vào nhé");
                }else{
                     namePlayer= textField.getText();
                    JOptionPane.showMessageDialog(null,"Xin chao :"+ textField.getText()+", Luot dau tien thuoc ve ban, " + namePlayer+" nhe");
                   
                doNewGame();
                btnStartGame.setEnabled(false);
                btnUndo.setEnabled(true);
                resignButton.setEnabled(true);
                textField.setText("");
                textField.setEnabled(false);
                }
            } else if (src == resignButton) {
                doResign();
              //  btnStartGame.
              JOptionPane.showMessageDialog(null,"Chan ban qua, chưa gì đã bỏ cuộc rồi ");
                btnUndo.setEnabled(false);
                btnStartGame.setEnabled(true);
                resignButton.setEnabled(false);
                textField.setEnabled(true);
                
            }
        }

        /**
         * Begin a new game; this is called by the actionPerformed() method when
         * a user clicks the New Game button.
         */
        void doNewGame() {
            if (gameInProgress == true) {
                // This should not be possible because New Game button
                // is enabled only when it is legal to use it, but it doesn't 
                // hurt to check.
                //message.setText("Finish the current game first!");
                return;
            }
            for (int row = 0; row < SIZE_BOARD; row++) // Fill the board with EMPTYs
            {
                for (int col = 0; col < SIZE_BOARD; col++) {
                    board[row][col] = EMPTY;
                    charater[row][col] = ' ';
                }
            }
            currentPlayer = BLACK;   // BLACK moves first.
           // message.setText("BLACK:  Make your move.");
            gameInProgress = true;
//            btnStartGame.setEnabled(false);
//            resignButton.setVisible(true);
            win_r1 = -1;  // This value indicates that no red line is to be drawn.
            repaint();
//            stackPoint = new Stack<>();   // khoi tao stack luu giu cac  nuoc di cau person 
            stackPoint.clear();
            //setEventForBtnUndo();
        }

        /**
         * Current player resigns; this is called by the actionPerformed()
         * method when a user clicks the Resign button. Game ends, and opponent
         * wins.
         */
        void doResign() {
            if (gameInProgress == false) {
                // This should not be possible.
               // message.setText("There is no game in progress!");
                return;
            }
            if (currentPlayer == WHITE) {
                //message.setText("WHITE resigns.  BLACK wins.");
            } else {
               // message.setText("BLACK resigns.  WHITE wins.");
            }
            // sau khi nguoi choi nhan thua, lap tuc ve lai tat cac cac o trong ban co
               for (int i = 0 ;i < SIZE_BOARD ; i ++){
                   for (int j = 0 ; j< SIZE_BOARD ; j++){
                       board[i][j] = EMPTY ;
                   }
               }
               win_r1 = -1 ;
               
               repaint();
               
            gameInProgress = false;
        }

        /**
         * This method is called whenever the game ends. The parameter, str, is
         * displayed as a message, and the buttons are enabled/disabled to
         * reflect the fact that a game is not currently in progress.
         */
        void gameOver(String str) {
           
            btnStartGame.setEnabled(true);
            resignButton.setEnabled(false);
            gameInProgress = false;
            JOptionPane.showMessageDialog(null, "Hic, " + namePlayer +" thua roi, cố gắng lần sau nhé ");
            textField.setEnabled(true);
            
            for (int i = 0 ; i < SIZE_BOARD ; i ++){
                for (int j = 0 ;j < SIZE_BOARD ; j ++){
                    charater[i][j] = ' ';
                    board[i][j] = EMPTY;
                }
            }
            win_r1 = -1;   // xoa dong ke mau do di
            repaint();
        }

        /**
         * This is called by mousePressed() when a player clicks on the square
         * in the specified row and col. It has already been checked that a game
         * is, in fact, in progress.
         */
        boolean doClickSquare(int row, int col) {

            // neu nguoi choi click vao o khong trong, thi tra lai gia tri false cho ham nay 
            if (board[row][col] != EMPTY) {
                System.out.println(board[row][col] + ":" + row + ":" + col);
                if (currentPlayer == BLACK) {
                   // message.setText("BLACK:  Please click an empty square.");
                } else {
                   // message.setText("WHITE:  Please click an empty square.");
                }
                return false;
            }

            // nguoc lai, nguoi choi kich vao 1 o trong 
            Point point = new Point(row, col);  // khoi tao gia tri moi de dua vao Stack 
            // add gia tri nuoc di do vao stack
            stackPoint.push(point);
            board[row][col] = currentPlayer;  
            if(currentPlayer == BLACK){
                charater[row][col] = 'x';
            } else if(currentPlayer == WHITE){
                charater[row][col] = 'o';
            }
            repaint();

            if (winner(row, col)) {  // First, check for a winner.
                if (currentPlayer == WHITE) {
                    gameOver("Hic, ban thua mat roi");
                } else {
                    gameOver("hic, ban thang roi !");
                }
                return false;
            }

            boolean emptySpace = false;     // Check if the board is full.
            for (int i = 0; i < SIZE_BOARD; i++) {
                for (int j = 0; j < SIZE_BOARD; j++) {
                    if (board[i][j] == EMPTY) {
                        emptySpace = true;
                    }
                }
            }
            if (emptySpace == false) {
                gameOver("Ban hoà với máy rồi nhé ");
                return false;
            }

            /* Continue the game.  It's the other player's turn. */
            if (currentPlayer == BLACK) {
                currentPlayer = WHITE;
                 System.out.println("Đỏi den sang trắng");
               // message.setText("WHITE:  Make your move.");
            } else {
                currentPlayer = BLACK;
                System.out.println("Đổi trắng sang đen");
               // message.setText("BLACK:  Make your move.");
            }
            return true;

        }  // end doClickSquare()

        /**
         * This is called just after a piece has been played on the square in
         * the specified row and column. It determines whether that was a
         * winning move by counting the number of squares in a line in each of
         * the four possible directions from (row,col). If there are 5 squares
         * (or more) in a row in any direction, then the game is won.
         */
        private boolean winner(int row, int col) {

            if (count(board[row][col], row, col, 1, 0) >= 5) {
                return true;
            }
            if (count(board[row][col], row, col, 0, 1) >= 5) {
                return true;
            }
            if (count(board[row][col], row, col, 1, -1) >= 5) {
                return true;
            }
            if (count(board[row][col], row, col, 1, 1) >= 5) {
                return true;
            }

            /* When we get to this point, we know that the game is not
          won.  The value of win_r1, which was changed in the count()
          method, has to be reset to -1, to avoid drawing a red line
          on the board. */
            win_r1 = -1;
            return false;

        }  // end winner()

        /**
         * Counts the number of the specified player's pieces starting at square
         * (row,col) and extending along the direction specified by (dirX,dirY).
         * It is assumed that the player has a piece at (row,col). This method
         * looks at the squares (row + dirX, col + dirY), (row + 2*dirX, col +
         * 2*dirY), ... until it hits a square that is off the board or is not
         * occupied by one of the player's pieces. It counts the squares that
         * are occupied by the player's pieces. Furthermore, it sets
         * (win_r1,win_c1) to mark last position where it saw one of the
         * player's pieces. Then, it looks in the opposite direction, at squares
         * (row - dirX, col-dirY), (row - 2*dirX, col - 2*dirY), ... and does
         * the same thing. Except, this time it sets (win_r2,win_c2) to mark the
         * last piece. Note: The values of dirX and dirY must be 0, 1, or -1. At
         * least one of them must be non-zero.
         */
        private int count(int player, int row, int col, int dirX, int dirY) {

            int ct = 1;  // Number of pieces in a row belonging to the player.

            int r, c;    // A row and column to be examined

            r = row + dirX;  // Look at square in specified direction.
            c = col + dirY;
            while (r >= 0 && r < SIZE_BOARD && c >= 0 && c < SIZE_BOARD && board[r][c] == player) {
                // Square is on the board and contains one of the players's pieces.
                ct++;
                r += dirX;  // Go on to next square in this direction.
                c += dirY;
            }

            win_r1 = r - dirX;  // The next-to-last square looked at.
            win_c1 = c - dirY;  //    (The LAST one looked at was off the board or
            //    did not contain one of the player's pieces.

            r = row - dirX;  // Look in the opposite direction.
            c = col - dirY;
            while (r >= 0 && r < SIZE_BOARD && c >= 0 && c < SIZE_BOARD && board[r][c] == player) {
                // Square is on the board and contains one of the players's pieces.
                ct++;
                r -= dirX;   // Go on to next square in this direction.
                c -= dirY;
            }

            win_r2 = r + dirX;
            win_c2 = c + dirY;

            // At this point, (win_r1,win_c1) and (win_r2,win_c2) mark the endpoints
            // of the line of pieces belonging to the player.
            return ct;

        }  // end count()

        /**
         * Draws the board and the pieces on the board. If the game has been won
         * by getting five or more pieces in a row, draws a red line through the
         * pieces.
         */
        @Override
        public void paintComponent(Graphics g) {

            super.paintComponent(g); // Fill with background color, lightGray

            /* Draw a two-pixel black border around the edges of the canvas,
          and draw grid lines in darkGray.  */
            g.setColor(Color.DARK_GRAY);
            for (int i = 1; i < SIZE_BOARD; i++) {
                g.drawLine(SIZE_BOARD * i, 0, SIZE_BOARD * i, 400);
                g.drawLine(0, SIZE_BOARD * i, 400, SIZE_BOARD * i);
            }
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, 400, 400);
            g.drawRect(1, 1, 400, 400);

            /* Draw the pieces that are on the board. */
            for (int row = 0; row < SIZE_BOARD; row++) {
                for (int col = 0; col < SIZE_BOARD; col++) {
                    if (board[row][col] != EMPTY) {
                        drawPiece(g, board[row][col], row, col);
                    }
                }
            }

            /* If the game has been won, then win_r1 >= 0.  Draw a line to mark
          the five (or more) winning pieces. */
            if (win_r1 >= 0) {
                drawWinLine(g);
            }

        }  // end paintComponent()

        /**
         * Draw a piece in the square at (row,col). The color is specified by
         * the piece parameter, which should be either BLACK or WHITE.
         */
        private void drawPiece(Graphics g, int piece, int row, int col) {
            if (piece == WHITE) {
                g.setColor(Color.WHITE);
            } else {
                g.setColor(Color.BLACK);
            }
            g.fillOval(3 + SIZE_BOARD * col, 3 + SIZE_BOARD * row, 15, 15);
        }

        /**
         * Draw a 2-pixel wide red line from the middle of the square at
         * (win_r1,win_c1) to the middle of the square at (win_r2,win_c2). This
         * routine is called to mark the pieces that won the game. The values of
         * the variables are set in the count() method.
         */
        private void drawWinLine(Graphics g) {
            g.setColor(Color.RED);
            g.drawLine(11 + SIZE_BOARD * win_c1, 11 + SIZE_BOARD * win_r1, 11 + SIZE_BOARD * win_c2, 11 + SIZE_BOARD * win_r2);
        }

        /**
         * Respond to a user click on the board. If no game is in progress, show
         * an error message. Otherwise, find the row and column that the user
         * clicked and call doClickSquare() to handle it.
         */
        @Override
        public void mousePressed(MouseEvent evt) {
            if (gameInProgress == false) {
              JOptionPane.showMessageDialog(null, "Ban nhan nut Start game de bat dau choi nhe");
            } else {
                int col = (evt.getX() - 2) / SIZE_BOARD;
                int row = (evt.getY() - 2) / SIZE_BOARD;
                if (col >= 0 && col < SIZE_BOARD && row >= 0 && row < SIZE_BOARD) {
                    boolean isPlayed = doClickSquare(row, col);
                    if (isPlayed) {
                        Square square = computer.Mini_Max(5, charater);
                        System.out.println("dm:"+square.getRow()+":"+ square.getCol());
                        doClickSquare(square.getRow(), square.getCol());
                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent evt) {
        }

        @Override
        public void mouseClicked(MouseEvent evt) {
        }

        @Override
        public void mouseEntered(MouseEvent evt) {
        }

        @Override
        public void mouseExited(MouseEvent evt) {
        }
        
         public void unDo(){
        Point point1 = null ;
        Point point2 = null ;
        try{
         point1 = stackPoint.pop();
         point2 = stackPoint.pop();
          
        charater[point1.getRow()][point1.getCol()]  = ' ';
        board[point1.getRow()][point1.getCol()] = EMPTY;
            repaint();
        charater[point2.getRow()][point2.getCol()]  = ' ';
        board[point2.getRow()][point2.getCol()] = EMPTY;
            repaint();
         
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null,"khong the tro lai ban nhe");
        }
        
       
    }

           
    public void setEventForBtnUndo(){
        btnUndo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unDo();
            }
        });
    }
    }  // end nested class Board

  
    
   
} // end class GoMoku
