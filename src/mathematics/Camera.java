package mathematics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import window.Window;

public class Camera extends KeyAdapter{

    private double[] pose;
    private double[] angle = new double[]{-1, 0};
    private double[] plane = new double[]{0, 0.66};
    private int[][] map;
    private boolean[] keysDown; //0 - W, 1 - S, 2 - A, 3 - D
    private int WIDTH, HEIGHT;
    private double turnAmount = 0.02;
    private double moveSpeed = 0.05;

    public Camera(double[] startPose, int[][] map, Window window){
        assert startPose.length == 2;
        this.pose = startPose;
        this.map = map;
        this.keysDown = new boolean[]{false, false, false, false};
        this.WIDTH = window.getFrameDimensions().width;
        this.HEIGHT = window.getFrameDimensions().height;
    }

    public void tick(){
        if(keysDown[0]){
            try{
                if(map[(int)(pose[0] + angle[0] * moveSpeed)][(int)pose[1]] == 0){
                    pose[0] += angle[0] * moveSpeed;
                    // if(pose[0] > map[0].length - 1) pose[0] = map[0].length - 1;
                    // if(pose[0] < 0) pose[0] = 0;
                }
                if(map[(int)pose[0]][(int)(pose[1] + angle[1] * moveSpeed)] == 0){
                    pose[1] += angle[1] * moveSpeed;
                    // if(pose[1] > map.length - 1) pose[1] = map.length - 1;
                    // if(pose[1] < 0) pose[1] = 0;
                }
            }catch(IndexOutOfBoundsException ignored){}
        }
        if(keysDown[1]){
            try{
                if(map[(int)(pose[0] - angle[0] * moveSpeed)][(int)pose[1]] == 0){
                    pose[0] -= angle[0] * moveSpeed;
                    // if(pose[0] > map[0].length - 1) pose[0] = map[0].length - 1;
                    // if(pose[0] < 0) pose[0] = 0;
                }
                if(map[(int)pose[0]][(int)(pose[1] - angle[1] * moveSpeed)] == 0){
                    pose[1] -= angle[1] * moveSpeed;
                    // if(pose[1] > map.length - 1) pose[1] = map.length - 1;
                    // if(pose[1] < 0) pose[1] = 0;
                }
            }catch(IndexOutOfBoundsException ignored){}
        }
        if(keysDown[2]){
            double oldDirX = angle[0];
            angle[0] = angle[0] * Math.cos(turnAmount) - angle[1] * Math.sin(turnAmount);
            angle[1] = oldDirX * Math.sin(turnAmount) + angle[1] * Math.cos(turnAmount);
            double oldPlaneX = plane[0];
            plane[0] = plane[0] * Math.cos(turnAmount) - plane[1] * Math.sin(turnAmount);
            plane[1] = oldPlaneX * Math.sin(turnAmount) + plane[1] * Math.cos(turnAmount);
        }
        if(keysDown[3]){
            double oldDirX = angle[0];
            angle[0] = angle[0] * Math.cos(-turnAmount) - angle[1] * Math.sin(-turnAmount);
            angle[1] = oldDirX * Math.sin(-turnAmount) + angle[1] * Math.cos(-turnAmount);
            double oldPlaneX = plane[0];
            plane[0] = plane[0] * Math.cos(-turnAmount) - plane[1] * Math.sin(-turnAmount);
            plane[1] = oldPlaneX * Math.sin(-turnAmount) + plane[1] * Math.cos(-turnAmount);
        }
    }

    public void render(Graphics g){
        //In here, we constantly calculate the position of the camera using some fun trig!
        for(int i = 0; i < WIDTH; i++){
            double cameraX = 2 * i / (double)WIDTH - 1;
            double rayDirX = angle[0] + plane[0] * cameraX;
            double rayDirY = angle[1] + plane[1] * cameraX;

            int mapX = (int)pose[0];
            int mapY = (int)pose[1];

            double sideDistX, sideDistY;
            double deltaDistX = Math.abs(1 / rayDirX);
            double deltaDistY = Math.abs(1 / rayDirY);

            double perpWallDist;

            int stepX, stepY;

            int hit = 0;
            int side = -1;

            if(rayDirX < 0){
                stepX = -1;
                sideDistX = (pose[0] - mapX) * deltaDistX;
            }else{
                stepX = 1;
                sideDistX = (mapX + 1 - pose[0]) * deltaDistX;
            }

            if(rayDirY < 0){
                stepY = -1;
                sideDistY = (pose[1] - mapY) * deltaDistY;
            }else{
                stepY = 1;
                sideDistY = (mapY + 1 - pose[1]) * deltaDistY;
            }

            while(hit == 0){
                if(sideDistX < sideDistY){
                    sideDistX += deltaDistX;
                    mapX += stepX;
                    side = 0;
                }else{
                    sideDistY += deltaDistY;
                    mapY += stepY;
                    side = 1;
                }
                try{
                    if(map[mapX][mapY] > 0) hit = 1;
                }catch(IndexOutOfBoundsException ignored){}
            }

            if(side == 0) perpWallDist = (sideDistX - deltaDistX);
            else perpWallDist = (sideDistY - deltaDistY);

            int lineHeight = (int)(HEIGHT / perpWallDist);

            int drawStart = -lineHeight / 2 + HEIGHT / 2;
            if(drawStart < 0) drawStart = 0;
            int drawEnd = lineHeight / 2 + HEIGHT / 2;
            if(drawEnd >= HEIGHT) drawEnd = HEIGHT - 1;

            switch(map[mapX][mapY]){
                case 1: 
                    g.setColor(Color.RED);
                    break;
                case 2: 
                    g.setColor(Color.GREEN); 
                    break;
                case 3: 
                    g.setColor(Color.BLUE);
                    break;
                case 4: 
                    g.setColor(Color.WHITE);
                    break;
                default: 
                    g.setColor(Color.YELLOW);
                    break;
            }

            if(side == 1) g.setColor(g.getColor().darker());
            g.drawLine(i, drawStart, i, drawEnd);
        }
    }

    @Override
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_W){
            keysDown[0] = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            keysDown[1] = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_A){
            keysDown[2] = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            keysDown[3] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_W) keysDown[0] = false;
        if(e.getKeyCode() == KeyEvent.VK_S) keysDown[1] = false;
        if(e.getKeyCode() == KeyEvent.VK_A) keysDown[2] = false;
        if(e.getKeyCode() == KeyEvent.VK_D) keysDown[3] = false;
    }

    public double[] getPose(){
        return pose;
    }

    public double[] getDirection(){
        return angle;
    }
    
}
