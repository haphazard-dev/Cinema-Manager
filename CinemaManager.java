import java.util.Scanner;

class Cinema {
    private static int purchasedSeats = 0;
    private static int sales = 0;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Establishes the dimensions for the two dimensional array
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();

        //Creates the two dim array based upon user entered dimensions
        char[][] room = new char[rows + 1][seats + 1];

        //For loop fills array with relevant values
        for (int i = 0; i < room.length; i++) {
            for (int j = 0; j < room[i].length; j++) {
                if (i == 0 && j == 0) {
                    room[i][j] = ' ';
                } else if (i == 0) {
                    room[i][j] = (char) (j + '0');
                } else if (j == 0) {
                    room[i][j] = (char) (i + '0');
                } else {
                    room[i][j] = 'S';
                }
            }
        }
        menu(room);
    }

    public static void menu(char[][] room) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit\n");

        int rows = room.length - 1;
        boolean aboveSixty = room.length * room[0].length >= 60;

        switch (scanner.nextInt()) {
            case 1: {
                displayChart(room);
            }
            case 2: {
                purchaseSeat(room, aboveSixty, rows);
            }
            case 3: {
                statistics(room);
            }
            case 0: {
                break;
            }
        }
    }

    //Displays seating chart
    public static void displayChart(char[][] room) {
        System.out.println("Cinema:");
        for (char[] chars : room) {
            for (char aChar : chars) {
                System.out.print(aChar + " ");
            }
            System.out.println();
        }
        System.out.println();

        menu(room);
    }

    //Purchases a seat and updates chart
    public static void purchaseSeat(char[][] room, boolean aboveSixty, int rows) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a row number:");
        int row = scanner.nextInt();
        boolean rowOutOfBounds = row > room.length - 1 || row < 1;

        System.out.println("Enter a seat number in that row:");
        int seat = scanner.nextInt();
        boolean seatOutOfBounds = seat > room[0].length - 1 || seat < 1;

        if (rowOutOfBounds || seatOutOfBounds) {
            System.out.println("Wrong input!\n");
            purchaseSeat(room, aboveSixty, rows);
        } else if (room[row][seat] == 'B') {
            System.out.println("That ticket has already been purchased!\n");
            purchaseSeat(room, aboveSixty, rows);
        } else {
            purchasedSeats++;

            //Checks ticket price and updates sales
            if (aboveSixty && row > rows / 2) {
                sales += 8;
                System.out.println("Ticket price: $8\n");
            } else {
                sales += 10;
                System.out.println("Ticket price: $10\n");
            }

            //Updates array to reflect purchased seat
            room[row][seat] = 'B';
        }
        menu(room);
    }
    
    //Display cinema statistics
    public static void statistics(char[][] room) {
        int lengthX = room.length - 1;
        int lengthY = room[0].length - 1;
        int half = lengthX / 2;
        boolean sixty = lengthX * lengthY > 60;
        float percentage = purchasedSeats != 0 ? purchasedSeats * 100.0f / (float) (lengthX * lengthY) : 0;
        int total = sixty ? half * lengthY * 10 + (lengthX - half) * lengthY * 8 : lengthX * lengthY * 10;
        System.out.printf("Number of purchased tickets: %d%n", purchasedSeats);
        System.out.printf("Percentage: %.2f%%%n", percentage);
        System.out.printf("Current income: $%d%n", sales);
        System.out.printf("Total Income: $%d%n", total);

        menu(room);
    }
}
