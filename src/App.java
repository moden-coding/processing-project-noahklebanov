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
    float dashMotion = 1;
    float dashSpacing = 80;

    //obstacle variables
    float obstacleSpacing = 100;
    
    float obstacleX1 = random(250,425);
    float obstacleY1 = 0;
    float obstacleWidth1 = random(50,100);
    float obstacleHeight1 = 20;

    float obstacleX2 = random(250,425);
    float obstacleY2 = 0;
    float obstacleWidth2 = random(50,100);
    float obstacleHeight2 = 20;

    float obstacleX3 = random(250,425);
    float obstacleY3 = 0;
    float obstacleWidth3 = random(50,100);
    float obstacleHeight3 = 20;

    float obstacleX4 = random(250,425);
    float obstacleY4 = 0;
    float obstacleWidth4 = random(50,100);
    float obstacleHeight4 = 20;



    public void settings() {
        size(800, 600);
        
    }

    public void setup() {
        background(grassColor);
        img = loadImage("redCar.png");
    }

    public void draw(){
        fill(roadColorGray);
        strokeWeight(5);
        stroke(roadEdgeBlack);
        rect(roadX, roadY, roadWidth, roadHeight);

        noStroke();
        fill(dashColorYellow);
        dashMotion();

        fill(carColorRed);
        rect(carX, carY, carWidth, carLength);

        //fill(obstacleColor);
        obstacleMotion();
    }

    public void dashMotion() {
        for (int i = 0; i < 8; i++) {
            rect(dashX, dashY + i * dashSpacing, dashWidth, dashHeight);
        }
        dashY += dashMotion;

        if (dashY >= dashSpacing) {
            dashY = 0;
        }
    }

    public void obstacleMotion(){
        fill(255,0,0);
        rect(obstacleX1, obstacleY1, obstacleWidth1, obstacleHeight1);
        obstacleY1 += dashMotion;

        if(obstacleY1>=obstacleSpacing){
            fill(0,255,0);
            rect(obstacleX2, obstacleY2, obstacleWidth2, obstacleHeight2);
            obstacleY2 += dashMotion;
        }
        if(obstacleY2>=obstacleSpacing){
            fill(0,0,255);
            rect(obstacleX3, obstacleY3, obstacleWidth3, obstacleHeight3);
            obstacleY3 += dashMotion;
        }
        if(obstacleY3>=obstacleSpacing){
            fill(255);
            rect(obstacleX4, obstacleY4, obstacleWidth4, obstacleHeight4);
            obstacleY4 += dashMotion;
        }
        if(obstacleY1>=600){
            obstacleY1=0;
            obstacleY2=0;
        }


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
    public void mousePressed(){

    }

    
}
