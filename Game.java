/*“We certify that this submission is the original work of members of the group and meets
        the Faculty's Expectations of Originality”,
      Jana Herzallah 	1201139
      Lana Thabit       1200071
      20-6-2023
*/
import java.util.Scanner;


public class Game {

    private static char[][] gameboard;
    private static char currentPlayer;
    static Game game = new Game();

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        minu ();
    }


    static void minu () {
        System.out.println("Welcome to our Magnetic Cave Game! Have fun!");
        System.out.println("Please choose one of the following modes");
        System.out.println("================================================================================");

        System.out.println("Mode 1: Manual moves for both players");
        System.out.println("Mode 2: Manual moves for ■ and automatic moves for □");
        System.out.println("Mode 3: Manual moves for □ and automatic moves for ■");
        System.out.println("Mode 4: Exit the program");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println();

        System.out.println("Enter your choice:");
        int choice = scanner.nextInt();//accept user input

        switch(choice){
            case 1: case1 ();
                //method call or logic for case 1
                break;
            case 2:
                case2();
                //method call or logic for case 2
                break;
            case 3:
                case3 ();
                //method call or logic for case 2
                break;
            case 4: System.out.println("Exiting the application");
                System.exit(0);
            default: System.out.println("Incorrect input!!! Please re-enter choice from our menu");
        }

    }

    static  void  MagneticCaveGame() {
        gameboard = new char[8][8];
        currentPlayer = '■'; // ■ starts the game
        System.out.println(currentPlayer);
        initializeBoard();
    }


    private static void initializeBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                gameboard[row][col] = '-';
            }
        }
    }


    public static void displayBoard() {
        System.out.println("  0 1 2 3 4 5 6 7");
        for (int row = 0; row < 8; row++) {
            System.out.print(row + " ");
            for (int col = 0; col < 8; col++) {
                System.out.print(gameboard[row][col] + " ");
            }
            System.out.println();
        }
    }

    public boolean makeMove(int row, int col) {
        if (row < 0 || row >= 8 || col < 0 || col >= 8 || gameboard[row][col] != '-') {
            return false; // Invalid move
        }

        // Check if the move is valid based on the game rules
        if (col == 0 || col == 7) {
            // Move is valid if the brick is stacked directly on the left or right wall
            gameboard[row][col] = currentPlayer;
            return true;
        } else {
            // Check if there is a brick to the left or right of the desired position
            if ((col > 0 && gameboard[row][col - 1] == '■') ||
                    (col < 7 && gameboard[row][col + 1] == '■') ||
                    (col > 0 && gameboard[row][col - 1] == '□') ||
                    (col < 7 && gameboard[row][col + 1] == '□')

            ) {
                gameboard[row][col] = currentPlayer;
                return true;
            }
        }

        return false; // Invalid move
    }



    public boolean checkWin() {
        // Check rows for a winning configuration
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col <= 3; col++) {
                if (gameboard[row][col] == currentPlayer &&
                        gameboard[row][col + 1] == currentPlayer &&
                        gameboard[row][col + 2] == currentPlayer &&
                        gameboard[row][col + 3] == currentPlayer &&
                        gameboard[row][col + 4] == currentPlayer) {
                    return true; // Player has won horizontally
                }
            }
        }

        // Check columns for a winning configuration
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row <= 3; row++) {
                if (gameboard[row][col] == currentPlayer &&
                        gameboard[row + 1][col] == currentPlayer &&
                        gameboard[row + 2][col] == currentPlayer &&
                        gameboard[row + 3][col] == currentPlayer &&
                        gameboard[row + 4][col] == currentPlayer) {
                    return true; // Player has won vertically
                }
            }
        }

        // Check diagonals (top-left to bottom-right) for a winning configuration
        for (int row = 0; row <= 3; row++) {
            for (int col = 0; col <= 3; col++) {
                if (gameboard[row][col] == currentPlayer &&
                        gameboard[row + 1][col + 1] == currentPlayer &&
                        gameboard[row + 2][col + 2] == currentPlayer &&
                        gameboard[row + 3][col + 3] == currentPlayer &&
                        gameboard[row + 4][col + 4] == currentPlayer) {
                    return true; // Player has won diagonally (top-left to bottom-right)
                }
            }
        }

        // Check diagonals (top-right to bottom-left) for a winning configuration
        for (int row = 0; row <= 3; row++) {
            for (int col = 4; col < 8; col++) {
                if (gameboard[row][col] == currentPlayer &&
                        gameboard[row + 1][col - 1] == currentPlayer &&
                        gameboard[row + 2][col - 2] == currentPlayer &&
                        gameboard[row + 3][col - 3] == currentPlayer &&
                        gameboard[row + 4][col - 4] == currentPlayer) {
                    return true; // Player has won diagonally (top-right to bottom-left)
                }
            }
        }

        return false; // No winning configuration found
    }

    public boolean checkTie() {
        // Check for a tie condition
        // Return true if the game board is full and no player has won, false otherwise
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (gameboard[row][col] == '-') {
                    return false; // Board is not full
                }
            }
        }
        return true; // Board is full
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == '■') ? '□' : '■';
    }



    public static void case1 () {
        MagneticCaveGame() ;
        System.out.println("Magnetic Cave Game - Mode 1 (Manual moves for both players)");
        System.out.println("========================================================");
        System.out.println("Instructions:");
        System.out.println("Enter the row and column numbers to make your move.");
        System.out.println("Example: To place a brick in row 2, column 3, enter '2 3'");
        System.out.println("========================================================");
        System.out.println();

        while (true) {

            game.displayBoard();
            System.out.println("Current Player: " + game.currentPlayer);
            System.out.print("Enter your move (row column): ");

            int row = scanner.nextInt();
            int col = scanner.nextInt();

            boolean validMove = game.makeMove(row, col);
            
            if (!validMove) {
                System.out.println("Invalid move! Please enter a valid move.");
                continue;
            }

            if (game.checkWin()) {
                System.out.println("Player " + game.currentPlayer + " wins!");
                break;
            }

            if (game.checkTie()) {
                System.out.println("It's a tie!");
                break;
            }

            game.switchPlayer();
            System.out.println();
        }


        game.displayBoard();

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
        minu ();



    }

    public static void case2 () {

        // make an instance from class mode2
        mode2 modeee = new mode2();
        modeee.execute();

    }

    public static void case3 () {

        // make an instance from class mode3

        mode3 mode33 = new mode3();
        mode33.execute();

    }

}