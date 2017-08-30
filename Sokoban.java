import java.math.*;
import java.util.*;
import java.util.Scanner;
import java.io.*;


public class Sokoban {

    private static int gridHeight = 0;
    private static int gridWidth = 0;
    private static int[][] grid;
    private static boolean[][] visited;


    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<String> file = new ArrayList<String>();
        String line;
        int currentRow = 0;
        int startRow = 0; 
        int startCol = 0; 
        boolean foundStart = false;
        boolean foundGoal = false;

        try {
            while ((line = br.readLine()) != null) {
                int length = line.length();
                if (length >= gridWidth){
                    gridWidth = length;
                }
                gridHeight++;
                file.add(line);
            }
        } catch (IOException e) {

        }

        grid = new int[gridHeight][gridWidth];
        visited = new boolean[gridHeight][gridWidth];

        for (String l : file) {
            int length = l.length();

            for(int i = 0; i < length; i++){
                switch (l.charAt(i)) {
                    // -1 = unmovable object
                    // 0 = free path
                    // 1 = start position
                    // 2 = goal
                    case '#': 
                        grid[currentRow][i] = -1;
                        break;

                    case '$': 
                        grid[currentRow][i] = -1;
                        break;

                    case '+': 
                        grid[currentRow][i] = 2;
                        break;

                    case '*': 
                        grid[currentRow][i] = -1;
                        break;

                    case ' ': 
                        grid[currentRow][i] = 0;
                        break;

                    case '@': 
                        grid[currentRow][i] = 1;
                        startRow = currentRow;
                        startCol = i;
                        foundStart = true;
                        break;

                    case '.': 
                        grid[currentRow][i] = 2;
                        foundGoal = true;
                        break;

                    default: System.out.println("Parse error");
                    
                }
            }
            currentRow++;
        }

        if (foundGoal && foundStart){
            String path = pathfinder(startRow, startCol);
            if(path.contains("X")) System.out.println("no path");
            else {
                System.out.println(path);
           }
        }
    }

    private static String pathfinder(int cr, int cc){
        if(grid[cr][cc] == 2) return "";
        else{
            visited[cr][cc] = true;

            if(((cc+1) < gridWidth) && !(visited[cr][cc+1]) && (grid[cr][cc+1]) != -1) return "R" + pathfinder(cr,cc+1);
            if(((cc-1) >= 0) && !(visited[cr][cc-1]) && (grid[cr][cc-1]) != -1) return "L" + pathfinder(cr,cc-1);
            if(((cr+1) < gridHeight) && !(visited[cr+1][cc]) && (grid[cr+1][cc]) != -1) return "D" + pathfinder(cr+1,cc);
            if(((cr-1) >= 0) && !(visited[cr-1][cc]) && (grid[cr-1][cc]) != -1) return "U" + pathfinder(cr-1,cc);

            return "X";
        }
    }

}