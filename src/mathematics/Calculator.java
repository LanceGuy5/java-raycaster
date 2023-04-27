package mathematics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import window.Window;

public class Calculator{

    //CONSTANTS


    // Instance variables
    private int[][] map;
    private Camera camera;
    private int WIDTH, HEIGHT;

    public Calculator(int[][] map, Camera camera, Window window){
        this.map = map;
        this.camera = camera;
        this.WIDTH = (int)window.getFrameDimensions().getWidth();
        this.HEIGHT = (int)window.getFrameDimensions().getHeight();
        // new SideMap(new Dimension(500, 520), "Top view", this);
    }

    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D)g;

        g.setColor(Color.black);
        g.fillRect(WIDTH - 260, 5, 240, 240);

        g2d.setColor(Color.red);
        g2d.setStroke(new BasicStroke(1));

        // for(int i = 0; i <= 240; i++){
        //     if(i % 10 == 0){
        //         g2d.drawLine(WIDTH - 260 + i, 5, WIDTH - 260 + i, 240);
        //     }
        // }

        // for(int i = 0; i <= 240; i++){
        //     if(i % 10 == 0){
        //         g2d.drawLine(WIDTH - 260, 5 + i, WIDTH - 20, 5 + i);
        //     }
        // }

        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                switch(map[i][j]){
                    case 1: 
                        g2d.setColor(Color.RED);
                        break;
                    case 2: 
                        g2d.setColor(Color.GREEN); 
                        break;
                    case 3: 
                        g2d.setColor(Color.BLUE);
                        break;
                    case 4: 
                        g2d.setColor(Color.WHITE);
                        break;
                    default: 
                        g2d.setColor(Color.YELLOW);
                        break;
                }
                g.fillRect(i * 10 + (WIDTH - 260), j * 10 + (5), 10, 10);
            }
        }

        g.setColor(Color.ORANGE);
        g.fillRect((int)(camera.getPose()[0] * 10 + (WIDTH - 260)), 
                   (int)(camera.getPose()[1] * 10 + (5)), 10, 10);

        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.white);
        g2d.drawRect(WIDTH - 260, 5, 240, 240);
    }

    /**
     * Method that turns a map into a set of lines on a screen
     * TODO Determine return type
     * @param map A 2D int array representing a top-down view of the map
     */
    public void calculate(int[][] map){

    }
    
}
