import java.util.Scanner;

public class mode3 {
    private static final int BOARD_SIZE = 8;
    private static final char EMPTY_CELL = '-';
    private static final char PLAYER_BRICK = '□';
    private static final char COMPUTER_BRICK = '■';
    private static char[][] board = new char[BOARD_SIZE][BOARD_SIZE];

    public mode3() {
        final int BOARD_SIZE = 8;
        final char EMPTY_CELL = '-';
        final char PLAYER_BRICK = '□';
        final char  COMPUTER_BRICK = '■';
        char[][] board = new char[BOARD_SIZE][BOARD_SIZE];

    }


    public static void execute(){
    	
    	  System.out.println("Magnetic Cave Game - Mode 3 (Manual vs Computer)");
          System.out.println("========================================================");
          System.out.println("Instructions:");
          System.out.println("Enter the row and column numbers to make your move.");
          System.out.println("Example: To place a brick in row 2, column 3, enter '2 3'");
          System.out.println("========================================================");
          System.out.println();

        initializeBoard();
        printBoard();

        Scanner scanner = new Scanner(System.in);

        // Game loop
        while (true) {

            // Player □ (Manual)
            System.out.println("Player □ (Manual):");

            int[] move = getPlayerMove(scanner, PLAYER_BRICK);
            
            if (!isValidMove(move[0], move[1])) {
          	  
                continue;
            }
            

            makeMove(move[0], move[1], PLAYER_BRICK);
            printBoard();

            if (checkWinCondition(PLAYER_BRICK)) {
                System.out.println("Player □ wins!");
                break;
            }

            if (isBoardFull()) {
                System.out.println("It's a tie!");
                break;
            }


            // Player ■ (Automatic)

            System.out.println("Player ■ (Automatic):");
            System.out.println("the computer is thinking...");
            move = getComputerMove();

            makeMove(move[0], move[1], COMPUTER_BRICK);
         
           


            printBoard();
            System.out.println("The computer make this move"  + "("+ move[0] + "," + move[1] +")" );
            System.out.println();

            if (checkWinCondition(COMPUTER_BRICK
            )) {
                System.out.println("Player ■ wins!");
                break;
            }

            if (isBoardFull()) {
                System.out.println("It's a tie!");
                break;
            }
        }
        
        
        
        boolean playagain = false;
        do {

            System.out.println("Do you want to play again? (yes/no)");
            String playAgainInput = scanner.next();

            if (playAgainInput.equalsIgnoreCase("yes")) {
                playagain = false;
            }
            else if (playAgainInput.equalsIgnoreCase("no")) {
                System.exit(0);

            }
            else {
                playagain = true;

            }

        } while (playagain);
        Game f = new Game ();
		f .minu ();


        scanner.close();


    }


    private static void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY_CELL;
            }
        }
    }

    private static int[] getPlayerMove(Scanner scanner, char brick) {
        while (true) {
        	  System.out.print("Enter your move (row column): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

           if (row >7 || col > 7 ) {
            	 System.out.println("Invalid move! Please enter a valid move.");
            }
           else if (isValidMove(row, col)) {
               return new int[]{row, col};
           } 
            
            else {
            	 System.out.println("Invalid move! Please enter a valid move.");
            }
        }
    }

    private static void makeMove(int row, int col, char brick) {
        board[row][col] = brick;
        //printBoard();
    }

    private static int[] minimax(int depth, boolean maximizingPlayer, int alpha, int beta) {


        if (depth == 0 || isBoardFull()) {

            int score = evaluateBoard2();
           // System.out.println("**");
            //System.out.println("score " + score);
            return new int[]{-1, -1, score};

        }


        int[] bestMove = new int[3];

        if (maximizingPlayer) {

            bestMove[2] = Integer.MIN_VALUE;

            for (int i = 0; i < BOARD_SIZE; i++) {

              


                for (int j = 0; j < BOARD_SIZE; j++) {

                   
                    if (isValidMove(i, j)) {
                        // first time enter this
                        // assume manual choose (0,0) then the next moves for the PC are
                        // (0,1) (0,7) (1,0) (1,7) (2,0) (2,7) ..... (7,0) (7,7)

                       // System.out.println(depth + "possible moves of computer (" + i + "," + j + ")");

                        makeMove(i, j, COMPUTER_BRICK);
                       // printBoard();


                        int[] move = minimax(depth - 1, false, alpha, beta);


                        makeMove(i, j, EMPTY_CELL);

                        int score = move[2];
                        if (score > bestMove[2]) {
                            bestMove[0] = i;
                            bestMove[1] = j;
                            bestMove[2] = score;
                        }

                        alpha = Math.max(alpha, score);
                        if (alpha >= beta) {
                            return bestMove;
                        }
                    }
                }
            }
        } else {
            bestMove[2] = Integer.MAX_VALUE;

            for (int i = 0; i < BOARD_SIZE; i++) {

                for (int j = 0; j < BOARD_SIZE; j++) {

                    if (isValidMove(i, j)) {

                       // System.out.println("i manual" + i);

                        //System.out.println("j manual" + j);

                        //System.out.println(depth +"possible moves of manual (" + i +"," + j+")"   );
                        makeMove(i, j, PLAYER_BRICK);

                        int[] move = minimax(depth - 1, true, alpha, beta);

                        makeMove(i, j, EMPTY_CELL);
                       // System.out.println("depth: " + (depth - 1));

                        int score = move[2];
                        if (score < bestMove[2]) {
                           // System.out.println(score);
                            bestMove[0] = i;
                            bestMove[1] = j;
                            bestMove[2] = score;
                        }

                        beta = Math.min(beta, score);
                        if (alpha >= beta) {

                            return bestMove;
                        }
                    }
                }
            }
        }

        return bestMove;
    }



    private static int getConsecutiveBricksCount(char brick) {
        int count = 0;

        // Check for horizontal consecutives
        for (int i = 0; i < BOARD_SIZE; i++) {
            int consecutive = 0;
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == brick) {
                    consecutive++;
                    if (consecutive >= 5) {
                        return consecutive;
                    }
                } else {
                    consecutive = 0;
                }
            }
        }

        // Check for vertical consecutives
        for (int i = 0; i < BOARD_SIZE; i++) {
            int consecutive = 0;
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[j][i] == brick) {
                    consecutive++;
                    if (consecutive >= 5) {
                        return consecutive;
                    }
                } else {
                    consecutive = 0;
                }
            }
        }

        // Check for diagonal consecutives (top left to bottom right)
        for (int i = 0; i <= BOARD_SIZE - 5; i++) {
            for (int j = 0; j <= BOARD_SIZE - 5; j++) {
                int consecutive = 0;
                for (int k = 0; k < 5; k++) {
                    if (board[i + k][j + k] == brick) {
                        consecutive++;
                        if (consecutive >= 5) {
                            return consecutive;
                        }
                    } else {
                        consecutive = 0;
                    }
                }
            }
        }

        // Check for diagonal consecutives (top right to bottom left)
        for (int i = 0; i <= BOARD_SIZE - 5; i++) {
            for (int j = 4; j < BOARD_SIZE; j++) {
                int consecutive = 0;
                for (int k = 0; k < 5; k++) {
                    if (board[i + k][j - k] == brick) {
                        consecutive++;
                        if (consecutive >= 5) {
                            return consecutive;
                        }
                    } else {
                        consecutive = 0;
                    }
                }
            }
        }

        return count;
    }


    private static boolean checkWinCondition(char brick) {
        // Check for horizontal win
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j <= BOARD_SIZE - 5; j++) {
                boolean win = true;
                for (int k = 0; k < 5; k++) {
                    if (board[i][j + k] != brick) {
                        win = false;
                        break;
                    }
                }
                if (win) {
                    return true;
                }
            }
        }

        // Check for vertical win
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j <= BOARD_SIZE - 5; j++) {
                boolean win = true;
                for (int k = 0; k < 5; k++) {  // 4
                    if (board[j + k][i] != brick) {
                        win = false;
                        break;
                    }
                }
                if (win) {
                    return true;
                }
            }
        }

        // Check for diagonal win (top left to bottom right)
        for (int i = 0; i <= BOARD_SIZE - 5; i++) {
            for (int j = 0; j <= BOARD_SIZE - 5; j++) {
                boolean win = true;
                for (int k = 0; k < 5; k++) {
                    if (board[i + k][j + k] != brick) {
                        win = false;
                        break;
                    }
                }
                if (win) {
                    return true;
                }
            }
        }

        // Check for diagonal win (top right to bottom left)
        for (int i = 0; i <= BOARD_SIZE - 5; i++) {
            for (int j = 4; j < BOARD_SIZE; j++) {
                boolean win = true;
                for (int k = 0; k < 5; k++) {
                    if (board[i + k][j - k] != brick) {
                        win = false;
                        break;
                    }
                }
                if (win) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean isBoardFull() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == EMPTY_CELL) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValidMove(int row, int col) {
        return board[row][col] == EMPTY_CELL && (col == 0 || col == BOARD_SIZE - 1 || board[row][col - 1] != EMPTY_CELL || board[row][col + 1] != EMPTY_CELL);
    }

    private static void printBoard() {

        System.out.println("  0 1 2 3 4 5 6 7");

        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < BOARD_SIZE; j++) {

                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static int[] getComputerMove() {
        int[] bestMove = minimax(4, true, Integer.MIN_VALUE, Integer.MAX_VALUE);

        // Check if manual player is about to win
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (isValidMove(i, j)) {
                    makeMove(i, j, PLAYER_BRICK);
                    if (checkWinCondition(PLAYER_BRICK)) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        makeMove(i, j, EMPTY_CELL); // Undo the move
                        return bestMove;
                    }
                    makeMove(i, j, EMPTY_CELL); // Undo the move
                }
            }
        }

        return new int[]{bestMove[0], bestMove[1]};
    }

    private static int evaluateBoard2() {
        int computerScore = getConsecutiveBricksCount(COMPUTER_BRICK);
        int playerScore = getConsecutiveBricksCount(PLAYER_BRICK);

        if (computerScore >= 5) {
            return Integer.MAX_VALUE;
        } else if (playerScore >= 5) {
            return Integer.MIN_VALUE;
        }

        int computerTwo = getConsecutiveBricksCount2(COMPUTER_BRICK, 2);
        int playerTwo = getConsecutiveBricksCount2(PLAYER_BRICK, 2);

        int computerThree = getConsecutiveBricksCount2(COMPUTER_BRICK, 3);
        int playerThree = getConsecutiveBricksCount2(PLAYER_BRICK, 3);

        int finalcomputerScore = 10 * computerThree + 5 * computerTwo;
        int finalplayerScore = 10 * playerThree + 5 * playerTwo;

        return finalcomputerScore - finalplayerScore;
    }
    private static int getConsecutiveBricksCount2(char brick, int length) {
        int count = 0;

        // Check for horizontal consecutives
        for (int i = 0; i < BOARD_SIZE; i++) {
            int consecutive = 0;
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == brick) {
                    consecutive++;
                    if (consecutive >= length) {
                        count++;
                    }
                } else {
                    consecutive = 0;
                }
            }
        }
        // Check for vertical consecutives
        for (int i = 0; i < BOARD_SIZE; i++) {
            int consecutive = 0;
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[j][i] == brick) {
                    consecutive++;
                    if (consecutive >= length) {
                        count++;
                    }
                } else {
                    consecutive = 0;
                }
            }
        }
        // Check for diagonal consecutives (top left to bottom right)
        for (int i = 0; i <= BOARD_SIZE - length; i++) {
            for (int j = 0; j <= BOARD_SIZE - length; j++) {
                int consecutive = 0;
                for (int k = 0; k < length; k++) {
                    if (board[i + k][j + k] == brick) {
                        consecutive++;
                        if (consecutive >= length) {
                            count++;
                        }
                    } else {
                        consecutive = 0;
                    }
                }
            }
        }
        // Check for diagonal consecutives (top right to bottom left)
        for (int i = 0; i <= BOARD_SIZE - length; i++) {
            for (int j = length - 1; j < BOARD_SIZE; j++) {
                int consecutive = 0;
                for (int k = 0; k < length; k++) {
                    if (board[i + k][j - k] == brick) {
                        consecutive++;
                        if (consecutive >= length) {
                            count++;
                        }
                    } else {
                        consecutive = 0;
                    }
                }
            }
        }

        return count;
    }


}
