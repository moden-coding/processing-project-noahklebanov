import processing.core.*;

public class App extends PApplet {
    public static void main(String[] args) {
        PApplet.main("App");
    }
    PImage img;

    // Colors
    int grassColor = color(130, 180, 50);
    int carColorRed = color(250, 50, 50);
    int roadColorGray = color(200);
    float roadEdgeBlack = color(0);
    int dashColorYellow = color(250, 230, 0);
    int obstacleColor = color(50);

    // car variables
    float carX = 300;
    float carY = 475;
    float carWidth = 50;
    float carLength = 100;
    float carMotion = 15;//(150 works for only two positions)

    // stationary road variables
    float roadX = 250;
    float roadY = -10;
    float roadWidth = 300;
    float roadHeight = 1000;

    // moving road line
    float dashX = 395;
    float dashY = 0;
    float dashWidth = 10;
    float dashHeight = 20;
    float dashMotion = 5;
    float dashSpacing = 80;

    //obstacle variables
    float[] obstacleX = new float[4];
    float[] obstacleY = new float[4];
    float[] obstacleWidth = new float[4];
    float[] obstacleHeight = new float[4];
    /*float obstacleX = random(250,425);
    float obstacleY = 10;
    float obstacleWidth = random(25,125);
    float obstacleHeight = 20;*/



    public void settings() {
        size(800, 600);
        
    }

    public void setup() {
        background(grassColor);
        img = loadImage("redCar.png");

        for (int i = 0; i < 4; i++) {
            obstacleX[i] = random(250, 425);
            obstacleY[i] = -i * 150;
            obstacleWidth[i] = random(25, 125);
            obstacleHeight[i] = 20;               
        }
    }
    public void draw() {
        fill(roadColorGray);
        strokeWeight(5);
        stroke(roadEdgeBlack);
        rect(roadX, roadY, roadWidth, roadHeight);

        noStroke();
        fill(carColorRed);
        rect(carX, carY, carWidth, carLength);


        fill(dashColorYellow);
        for (int i = 0; i < 8; i++) {
            rect(dashX, dashY + i * dashSpacing, dashWidth, dashHeight);
        }
        dashY += dashMotion;

        if (dashY >= dashSpacing) {
            dashY = 0;
        }

        fill(obstacleColor);
        /*for(int i=1;i<=4;i++){
            rect(obstacleX[i], obstacleY[i], obstacleWidth[i], obstacleHeight[i]);
            obstacleY[i] += dashMotion;
        }*/
    }
    public void dashMotion() {

    }

    public void keyPressed() {
        if (keyCode == RIGHT && carX < 475) {
            carX = carX + carMotion;
        }
        if (keyCode == LEFT && carX > 275) {
            carX = carX - carMotion;
        }
        if (keyCode == UP && dashMotion <= 20) {
            dashMotion += 0.25;
        }
        if (keyCode == DOWN && dashMotion > 0) {
            dashMotion -= 0.25;
        }
    }

    public void carMotion() {

    }
}
