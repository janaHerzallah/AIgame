import java.util.Scanner;

/*----------------------------------------------------------------*/
public class mode2 {
    /*
This class has constants BOARD_SIZE, EMPTY_CELL, PLAYER_BRICK, and COMPUTER_BRICK that define
the characteristics of the game board and the player's and computer's game pieces.
The board variable is a 2D char array that represents the game board.*/
    private static final int BOARD_SIZE = 8;
    private static final char EMPTY_CELL = '-';
    private static final char PLAYER_BRICK = '■';
    private static final char COMPUTER_BRICK = '□';
    private static char[][] board = new char[BOARD_SIZE][BOARD_SIZE];

    public mode2() {
        final int BOARD_SIZE = 8;
        final char EMPTY_CELL = '-';
        final char PLAYER_BRICK = '■';
        final char COMPUTER_BRICK = '□';
        char[][] board = new char[BOARD_SIZE][BOARD_SIZE];

    }

    /*
    * The execute() method is the main entry point for the game mode.
It initializes the game board, prints the initial state, and starts a game loop that continues until a player wins or there's a tie.
Inside the game loop, it alternates between the player's turn (manual input) and the computer's turn (automatic move).
After each move, it checks for a win condition or a tie by calling the respective methods.
*/
    public static void execute() {


        System.out.println("Magnetic Cave Game - Mode 2 (Manual vs Computer)");
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

            // Player ■ (Manual)
            System.out.println("Player ■ (Manual):");

            int[] move = getPlayerMove(scanner, PLAYER_BRICK);

            if (!isValidMove(move[0], move[1])) {

                continue;
            }



            makeMove(move[0], move[1], PLAYER_BRICK);

            printBoard();

            if (checkWinCondition(PLAYER_BRICK)) {
                System.out.println("Player ■ wins!");
                break;
            }

            if (isBoardFull()) {
                System.out.println("It's a tie!");
                break;
            }

            // Player □ (Automatic)

            System.out.println("Player □ (Automatic):");
            System.out.println("the computer is thinking...");

            move = getComputerMove();

            makeMove(move[0], move[1], COMPUTER_BRICK);




            printBoard();
            System.out.println("The computer make this move"  + "("+ move[0] + "," + move[1] +")" );
            System.out.println();

            if (checkWinCondition(COMPUTER_BRICK
            )) {
                System.out.println("Player □ wins!");
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
    /*The initializeBoard() method sets all cells of the game board to the EMPTY_CELL character, representing an empty cell.*/

    private static void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY_CELL;
            }
        }
    }
    /*
        It prompts the user to enter a row and column for their move.
        It checks if the move is valid by calling the isValidMove method.
        If the move is valid, it returns an array containing the row and column values.
        Otherwise, it displays an error message and prompts the user to try again.*/
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
    /*It updates the game board with the specified brick at the given row and column.
     */
    private static void makeMove(int row, int col, char brick) {
        board[row][col] = brick;
        //printBoard();
    }

    /*It implements the minimax algorithm with alpha-beta pruning to determine the best move for the computer player.
The method recursively evaluates the game state by calling itself with a decreased depth value.
It alternates between the maximizing player (computer) and the minimizing player (human).
The method keeps track of the best move found so far, along with its score.
It prunes branches of the game tree using alpha-beta cutoffs to improve performance.*/

    private static int[] minimax(int depth, boolean maximizingPlayer, int alpha, int beta) {

        if (depth == 0 || isBoardFull()) {
            int score = evaluateBoard2();
            return new int[]{-1, -1, score};
        }

        int[] bestMove = new int[3];

        if (maximizingPlayer) {
            bestMove[2] = Integer.MIN_VALUE;

            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (isValidMove(i, j)) {
                        makeMove(i, j, COMPUTER_BRICK);
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
                        makeMove(i, j, PLAYER_BRICK);
                        int[] move = minimax(depth - 1, true, alpha, beta);
                        makeMove(i, j, EMPTY_CELL);

                        int score = move[2];
                        // System.out.println("Current move: (" + i + ", " + j + ")");
                        //System.out.println("Score: " + score);

                        if (score < bestMove[2]) {
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



    /*It counts the number of consecutive bricks of a given type (computer or player) in horizontal, vertical, and diagonal directions.
It checks for horizontal, vertical, and two diagonal directions separately.
The method returns the total count of consecutive bricks found.*/
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

    /*It checks if a player has won the game by having 5 consecutive bricks in any direction.
It checks for horizontal, vertical, and two diagonal directions separately.
The method returns true if a win condition is found; otherwise, it returns false.*/

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

    /*
    * It checks if the game board is full (no empty cells remaining).
The method iterates through the board and returns true if an empty cell is found; otherwise, it returns false.*/
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

    /*It checks if a move at the specified row and column is valid.
A move is valid if the cell is empty and has at least one neighboring non-empty cell.*/
    private static boolean isValidMove(int row, int col) {
        return board[row][col] == EMPTY_CELL && (col == 0 || col == BOARD_SIZE - 1 || board[row][col - 1] != EMPTY_CELL || board[row][col + 1] != EMPTY_CELL);
    }

    /*
    * It prints the current state of the game board to the console.
It displays the row and column indices, along with the bricks present at each cell.*/
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

    /*
    * It uses the minimax algorithm to calculate the best move for the computer player.
It first checks if the human player is about to win, and if so, it tries to block the winning move.
Otherwise, it calls the minimax method with a depth of 4 (adjustable) to determine the best move.*/
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

    /*
    * It extends the evaluateBoard method to consider additional factors for scoring the game board.
It calculates separate scores for two consecutive bricks and three consecutive bricks for each player.
It assigns higher weights (10 and 5, respectively) to the counts of three consecutive bricks.
The method returns the final score by subtracting the player's score from the computer's score.*/
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

    //  It extends the getConsecutiveBricksCount method to count consecutive bricks of a specified length.
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